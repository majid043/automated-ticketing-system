package com.resturant.automatedticketingsystem.jobs;

import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.resturant.automatedticketingsystem.bean.RulePrepareInfo;
import com.resturant.automatedticketingsystem.evaluaterules.AbstractOperandValueFetcher;
import com.resturant.automatedticketingsystem.evaluaterules.AbstractOperator;
import com.resturant.automatedticketingsystem.exception.DataNotFoundException;
import com.resturant.automatedticketingsystem.exception.InternalServerException;
import com.resturant.automatedticketingsystem.model.DeliveryInfo;
import com.resturant.automatedticketingsystem.model.EvalAttributes;
import com.resturant.automatedticketingsystem.model.EvalRules;
import com.resturant.automatedticketingsystem.model.TicketInfo;
import com.resturant.automatedticketingsystem.model.UseCases;
import com.resturant.automatedticketingsystem.repository.DeliveryInfoRepository;
import com.resturant.automatedticketingsystem.repository.EvalAttributesRepository;
import com.resturant.automatedticketingsystem.repository.EvalRuleRepository;
import com.resturant.automatedticketingsystem.repository.TicketInfoRepository;
import com.resturant.automatedticketingsystem.repository.UseCaseRepository;
import com.resturant.automatedticketingsystem.util.Constants;
import com.resturant.automatedticketingsystem.util.ObjectToMap;

@Service
public class MonitorDelivery {

	private static final Logger lgr = LogManager.getLogger(MonitorDelivery.class.getName());
	private final UseCaseRepository useCaseRepository;
	private final EvalRuleRepository evalRuleRepository;
	private final EvalAttributesRepository evalAttributesRepository;
	private final DeliveryInfoRepository deliveryInfoRepository;
	private final TicketInfoRepository ticketInfoRepository;

	public MonitorDelivery(UseCaseRepository useCaseRepository, EvalRuleRepository evalRuleRepository,
			EvalAttributesRepository evalAttributesRepository, DeliveryInfoRepository deliveryInfoRepository, TicketInfoRepository ticketInfoRepository) {
		this.useCaseRepository = useCaseRepository;
		this.evalRuleRepository = evalRuleRepository;
		this.evalAttributesRepository = evalAttributesRepository;
		this.deliveryInfoRepository = deliveryInfoRepository;
		this.ticketInfoRepository = ticketInfoRepository;
	}

	@Scheduled(fixedRate = 30000)
	public void calculateDeliveryPriority() {
		Map<String, String> evaluationData = null;
		AbstractOperandValueFetcher abstractOperandValueFetcher = null;
		AbstractOperator abstractOperator = null;
		String value = null;
		String fixedAttrValue = null;
		try {
			List<DeliveryInfo> deliveryInfos = deliveryInfoRepository.findByDeliveryStatusNotAndTicketIdIsNull(Constants.DELIVERY_STATUS_DELIVERED);
			if (null == deliveryInfos || deliveryInfos.isEmpty()) {
				lgr.atWarn().log("No delivery info found!");
				return;
			}

			RulePrepareInfo ruleInfo = prepareRuleEvaluationData();
			if (null == ruleInfo) {
				lgr.atWarn().log("No rule info found!");
				return;
			}
			lgr.atDebug().log(ruleInfo);

			for (DeliveryInfo deliveryInfo : deliveryInfos) {
				lgr.atDebug().log(deliveryInfo);
				evaluationData = ObjectToMap.convertValue(deliveryInfo);
				calculateEstimatedTimeOfDelivery(evaluationData, deliveryInfo);
				addCurrentTime(evaluationData);
				lgr.atDebug().log(evaluationData);

				for (UseCases useCase : ruleInfo.getUseCases()) {
					EvalRules evalRule = ruleInfo.getRulesById().get(useCase.getRuleId());
					EvalAttributes evalAttributes = ruleInfo.getAttrById().get(evalRule.getAttrId());

					abstractOperandValueFetcher =  AbstractOperandValueFetcher.getInstance(evalRule.getAttrValueType());
					value = abstractOperandValueFetcher.fetchValue(evalAttributes.getAttrKey(), evaluationData, ruleInfo.getAttrById(), evalRule.getAttrValue());

					if (Constants.ATTRIBUTE_TYPE.equalsIgnoreCase(evalRule.getAttrValueType())) {
						fixedAttrValue = value;
						value = AbstractOperandValueFetcher.getInstance(Constants.FIXED_TYPE).fetchValue(evalAttributes.getAttrKey(), evaluationData, ruleInfo.getAttrById(), value);
					} else {
						fixedAttrValue = evalRule.getAttrValue();
					}

					abstractOperator = AbstractOperator.getInstance(evalRule.getOperatorCode());

					if (null == abstractOperator) {
						lgr.atWarn().log("Invalid Operator Code [" + evalRule.getOperatorCode() + "] against Rule Id ["+evalRule.getRuleId()+"]");
						continue;
					}

					boolean isRuleQualified = abstractOperator.applyOpertion(evalAttributes.getDataType(), fixedAttrValue, value);
					lgr.atInfo().log("isRuleQualified ["+isRuleQualified+"]");
					if (isRuleQualified) {
						notifyCustomerSupport(deliveryInfo, useCase.getPriority(), useCase.getUseCaseName());
						break;
					}
				}
			}
		} catch (Throwable e) {
			throw new InternalServerException(e.getMessage(), e);
		}
	}

	@Async
	private void notifyCustomerSupport(DeliveryInfo deliveryInfo, Long priority, String message) {
		lgr.atInfo().log("deliveryInfo ["+deliveryInfo+"], priority ["+priority+"] and message ["+message+"]");
		TicketInfo ticketInfo = ticketInfoRepository.save(new TicketInfo(priority, deliveryInfo.getDeliveryId(), message));
		deliveryInfo.setTicketId(ticketInfo.getTicketId());
		lgr.atInfo().log("generated ticket id ["+ticketInfo.getTicketId()+"]");
		deliveryInfoRepository.save(deliveryInfo);
	}

	private void calculateEstimatedTimeOfDelivery(Map<String, String> evaluationData, DeliveryInfo deliveryInfo) {
		if (null == deliveryInfo.getMeanTime() || null == deliveryInfo.getDestReachTime()) {
			return;
		}
		evaluationData.put(Constants.EVAL_DATA_ESTIMATED_DELIVERY_TIME_KEY, String.valueOf(new Timestamp(System.currentTimeMillis()).toInstant().plus(deliveryInfo.getMeanTime() + deliveryInfo.getDestReachTime(), ChronoUnit.MINUTES).toEpochMilli()));
	}

	private void addCurrentTime(Map<String, String> evaluationData) {
		evaluationData.put(Constants.EVAL_DATA_CURRENT_TIME_KEY, String.valueOf(new Timestamp(System.currentTimeMillis()).getTime()));
	}

	private RulePrepareInfo prepareRuleEvaluationData() {
		List<UseCases> userCases = useCaseRepository.findAllByOrderByExecOrderAsc();
		if (null == userCases || userCases.isEmpty()) {
			lgr.atWarn().log("No delivery info found!");
			return null;
		}

		Set<Long> ruleIds = userCases.stream().map(UseCases::getRuleId).collect(Collectors.toSet());
		if (null == ruleIds || ruleIds.isEmpty()) {
			throw new DataNotFoundException("Rule id(s) from usecases not found in system!");
		}
		lgr.atDebug().log(ruleIds);

		List<EvalRules> evalRules = evalRuleRepository.findByRuleIdIn(ruleIds);
		if (null == evalRules || evalRules.isEmpty()) {
			throw new DataNotFoundException("Evaluation Rules(s) not found in system!");
		}
		lgr.atDebug().log(evalRules);

		Map<Long, EvalRules> rulesById = evalRules.stream().collect(Collectors.toMap(EvalRules::getRuleId, Function.identity()));
		if (null == rulesById || rulesById.isEmpty()) {
			throw new DataNotFoundException("Rule id(s) not found in system!");
		}
		lgr.atDebug().log(rulesById);

		Set<Long> attrIds = evalRules.stream().map(EvalRules::getAttrId).collect(Collectors.toSet());
		if (null == attrIds || attrIds.isEmpty()) {
			throw new DataNotFoundException("Attribute id(s) from evaluate rules not found in system!");
		}

		Set<Long> attrIdsOfTypeAttr = evalRules.stream().filter(e ->  Constants.ATTRIBUTE_TYPE.equalsIgnoreCase(e.getAttrValueType())).map(EvalRules::getAttrValue).map(Long::valueOf).collect(Collectors.toSet());
		if (null != attrIdsOfTypeAttr && !attrIdsOfTypeAttr.isEmpty()) {
			attrIds.addAll(attrIdsOfTypeAttr);
		}
		lgr.atDebug().log(attrIds);

		List<EvalAttributes> evalAttrs = evalAttributesRepository.findByAttrIdIn(attrIds);
		if (null == evalAttrs || evalAttrs.isEmpty()) {
			throw new DataNotFoundException("Evaluation attribute(s) not found in system!");
		}
		lgr.atDebug().log(evalAttrs);

		Map<Long, EvalAttributes> attrById = evalAttrs.stream().collect(Collectors.toMap(EvalAttributes::getAttrId, Function.identity()));
		if (null == attrById || attrById.isEmpty()) {
			throw new DataNotFoundException("Attribute id(s) from evaluation attributes not found in system!");
		}
		lgr.atDebug().log(attrById);

		return new RulePrepareInfo(userCases, rulesById, attrById);
	}
}
