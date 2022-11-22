package com.resturant.automatedticketingsystem.jobs;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

@ExtendWith(MockitoExtension.class)
public class MonitorDeliveryTest {

	@InjectMocks
	MonitorDelivery monitorDelivery;
	@Mock
	UseCaseRepository useCaseRepository;
	@Mock
	EvalRuleRepository evalRuleRepository;
	@Mock
	EvalAttributesRepository evalAttributesRepository;
	@Mock
	DeliveryInfoRepository deliveryInfoRepository;
	@Mock
	TicketInfoRepository ticketInfoRepository;
	@Mock
	TicketInfo ticketInfoSave;

	@Test
	public void test_VIP_Customer() {
		monitorDelivery = new MonitorDelivery(useCaseRepository, evalRuleRepository,
				evalAttributesRepository, deliveryInfoRepository, ticketInfoRepository);

		List<DeliveryInfo> deliveryInfos = new ArrayList<>();
		DeliveryInfo deliveryInfo = new DeliveryInfo(1l, "VIP", "RECEIVED", null,
				8d, 4l, 15l, 12l, null);
		deliveryInfos.add(deliveryInfo);
		List<UseCases> userCasesList = new ArrayList<>();
		UseCases useCases = new UseCases(1l, "High Priority! Customer is VIP", 1l, 1l, 1l);
		userCasesList.add(useCases);
		Set<Long> ruleIds = userCasesList.stream().map(UseCases::getRuleId).collect(Collectors.toSet());
		List<EvalRules> evalRulesList = new ArrayList<>();
		EvalRules evalRules = new EvalRules(1l, 2l, 1l, "F", "VIP");
		evalRulesList.add(evalRules);
		Set<Long> attrIds = evalRulesList.stream().map(EvalRules::getAttrId).collect(Collectors.toSet());
		List<EvalAttributes> evalAttrsList = new ArrayList<>();
		EvalAttributes evalAttributes = new EvalAttributes(2l, "Customer Type", "customerType", "S");
		evalAttrsList.add(evalAttributes);
		TicketInfo ticketInfo = new TicketInfo(UUID.randomUUID());

		when(deliveryInfoRepository.findByDeliveryStatusNotAndTicketIdIsNull(Constants.DELIVERY_STATUS_DELIVERED)).thenReturn(deliveryInfos);
		when(useCaseRepository.findAllByOrderByExecOrderAsc()).thenReturn(userCasesList);
		when(evalRuleRepository.findByRuleIdIn(ruleIds)).thenReturn(evalRulesList);
		when(evalAttributesRepository.findByAttrIdIn(attrIds)).thenReturn(evalAttrsList);
		lenient().when(ticketInfoRepository.save(any())).thenReturn(ticketInfo);

		monitorDelivery.calculateDeliveryPriority();
		assertEquals(ticketInfo.getTicketId(), deliveryInfo.getTicketId());
	}

	@Test
	public void test_ExpectedTimeOfDeliveryPassed() {
		monitorDelivery = new MonitorDelivery(useCaseRepository, evalRuleRepository,
				evalAttributesRepository, deliveryInfoRepository, ticketInfoRepository);

		List<DeliveryInfo> deliveryInfos = new ArrayList<>();
		DeliveryInfo deliveryInfo = new DeliveryInfo(2l, "Loyal", "RECEIVED", new Timestamp(Instant.now().minus(30, ChronoUnit.MINUTES).toEpochMilli()),
				7d, 5l, 15l, 10l, null);
		deliveryInfos.add(deliveryInfo);
		List<UseCases> userCasesList = new ArrayList<>();
		UseCases useCases = new UseCases(2l, "High Priority! Expected time of delivery is passed", 2l, 2l, 1l);
		userCasesList.add(useCases);
		Set<Long> ruleIds = userCasesList.stream().map(UseCases::getRuleId).collect(Collectors.toSet());
		List<EvalRules> evalRulesList = new ArrayList<>();
		EvalRules evalRules = new EvalRules(2l, 4l, 3l, "A", "9");
		evalRulesList.add(evalRules);
		Set<Long> attrIds = evalRulesList.stream().map(EvalRules::getAttrId).collect(Collectors.toSet());
		attrIds.add(9l);
		List<EvalAttributes> evalAttrsList = new ArrayList<>();
		EvalAttributes evalAttributes = new EvalAttributes(4l, "Expected Time", "expectedTime", "T");
		EvalAttributes evalAttributes1 = new EvalAttributes(9l, "Current Time", "currentTime", "T");
		evalAttrsList.add(evalAttributes);
		evalAttrsList.add(evalAttributes1);
		TicketInfo ticketInfo = new TicketInfo(UUID.randomUUID());

		when(deliveryInfoRepository.findByDeliveryStatusNotAndTicketIdIsNull(Constants.DELIVERY_STATUS_DELIVERED)).thenReturn(deliveryInfos);
		when(useCaseRepository.findAllByOrderByExecOrderAsc()).thenReturn(userCasesList);
		when(evalRuleRepository.findByRuleIdIn(ruleIds)).thenReturn(evalRulesList);
		when(evalAttributesRepository.findByAttrIdIn(attrIds)).thenReturn(evalAttrsList);
		lenient().when(ticketInfoRepository.save(any())).thenReturn(ticketInfo);

		monitorDelivery.calculateDeliveryPriority();
		assertEquals(ticketInfo.getTicketId(), deliveryInfo.getTicketId());
	}

	@Test
	public void test_EstimationGreaterThanExpectedTime() {
		monitorDelivery = new MonitorDelivery(useCaseRepository, evalRuleRepository,
				evalAttributesRepository, deliveryInfoRepository, ticketInfoRepository);

		List<DeliveryInfo> deliveryInfos = new ArrayList<>();
		DeliveryInfo deliveryInfo = new DeliveryInfo(3l, "New", "RECEIVED", new Timestamp(Instant.now().plus(30, ChronoUnit.MINUTES).toEpochMilli()),
				6d, 3l, 20l, 8l, null);
		deliveryInfos.add(deliveryInfo);
		List<UseCases> userCasesList = new ArrayList<>();
		UseCases useCases = new UseCases(3l, "Medium Priority! Estimation is greater than the expected time", 3l, 3l, 2l);
		userCasesList.add(useCases);
		Set<Long> ruleIds = userCasesList.stream().map(UseCases::getRuleId).collect(Collectors.toSet());
		List<EvalRules> evalRulesList = new ArrayList<>();
		EvalRules evalRules = new EvalRules(3l, 10l, 3l, "A", "4");
		evalRulesList.add(evalRules);
		Set<Long> attrIds = evalRulesList.stream().map(EvalRules::getAttrId).collect(Collectors.toSet());
		attrIds.add(4l);
		List<EvalAttributes> evalAttrsList = new ArrayList<>();
		EvalAttributes evalAttributes = new EvalAttributes(10l, "Estimated Delivery Time", "estimatedDeliveryTime", "T");
		EvalAttributes evalAttributes1 = new EvalAttributes(4l, "Expected Time", "expectedTime", "T");
		evalAttrsList.add(evalAttributes);
		evalAttrsList.add(evalAttributes1);
		TicketInfo ticketInfo = new TicketInfo(UUID.randomUUID());

		when(deliveryInfoRepository.findByDeliveryStatusNotAndTicketIdIsNull(Constants.DELIVERY_STATUS_DELIVERED)).thenReturn(deliveryInfos);
		when(useCaseRepository.findAllByOrderByExecOrderAsc()).thenReturn(userCasesList);
		when(evalRuleRepository.findByRuleIdIn(ruleIds)).thenReturn(evalRulesList);
		when(evalAttributesRepository.findByAttrIdIn(attrIds)).thenReturn(evalAttrsList);
		lenient().when(ticketInfoRepository.save(any())).thenReturn(ticketInfo);

		monitorDelivery.calculateDeliveryPriority();
		assertEquals(ticketInfo.getTicketId(), deliveryInfo.getTicketId());
	}

	@Test
	public void test_DistanceFromDestinationIsLow_ActualHigh_NonSuccessful() {
		monitorDelivery = new MonitorDelivery(useCaseRepository, evalRuleRepository,
				evalAttributesRepository, deliveryInfoRepository, ticketInfoRepository);

		List<DeliveryInfo> deliveryInfos = new ArrayList<>();
		DeliveryInfo deliveryInfo = new DeliveryInfo(4l, "Loyal", "PREPARING", new Timestamp(Instant.now().plus(25, ChronoUnit.MINUTES).toEpochMilli()),
				7d, 5l, 15l, 10l, null);
		deliveryInfos.add(deliveryInfo);
		List<UseCases> userCasesList = new ArrayList<>();
		UseCases useCases = new UseCases(4l, "Medium Priority! Distance From Destination is low", 4l, 4l, 2l);
		userCasesList.add(useCases);
		Set<Long> ruleIds = userCasesList.stream().map(UseCases::getRuleId).collect(Collectors.toSet());
		List<EvalRules> evalRulesList = new ArrayList<>();
		EvalRules evalRules = new EvalRules(4l, 5l, 4l, "F", "5");
		evalRulesList.add(evalRules);
		Set<Long> attrIds = evalRulesList.stream().map(EvalRules::getAttrId).collect(Collectors.toSet());
		List<EvalAttributes> evalAttrsList = new ArrayList<>();
		EvalAttributes evalAttributes = new EvalAttributes(5l, "Distance From Destination", "distanceFrmDest", "D");
		evalAttrsList.add(evalAttributes);
		TicketInfo ticketInfo = new TicketInfo(UUID.randomUUID());

		when(deliveryInfoRepository.findByDeliveryStatusNotAndTicketIdIsNull(Constants.DELIVERY_STATUS_DELIVERED)).thenReturn(deliveryInfos);
		when(useCaseRepository.findAllByOrderByExecOrderAsc()).thenReturn(userCasesList);
		when(evalRuleRepository.findByRuleIdIn(ruleIds)).thenReturn(evalRulesList);
		when(evalAttributesRepository.findByAttrIdIn(attrIds)).thenReturn(evalAttrsList);
		lenient().when(ticketInfoRepository.save(any())).thenReturn(ticketInfo);

		monitorDelivery.calculateDeliveryPriority();
		assertNotEquals(ticketInfo.getTicketId(), deliveryInfo.getTicketId());
	}

	@Test
	public void test_DistanceFromDestinationIsLow() {
		monitorDelivery = new MonitorDelivery(useCaseRepository, evalRuleRepository,
				evalAttributesRepository, deliveryInfoRepository, ticketInfoRepository);

		List<DeliveryInfo> deliveryInfos = new ArrayList<>();
		DeliveryInfo deliveryInfo = new DeliveryInfo(5l, "Loyal", "PICKEDUP", new Timestamp(Instant.now().plus(10, ChronoUnit.MINUTES).toEpochMilli()),
				4d, 5l, 15l, 10l, null);
		deliveryInfos.add(deliveryInfo);
		List<UseCases> userCasesList = new ArrayList<>();
		UseCases useCases = new UseCases(4l, "Medium Priority! Distance From Destination is low", 4l, 4l, 2l);
		userCasesList.add(useCases);
		Set<Long> ruleIds = userCasesList.stream().map(UseCases::getRuleId).collect(Collectors.toSet());
		List<EvalRules> evalRulesList = new ArrayList<>();
		EvalRules evalRules = new EvalRules(4l, 5l, 4l, "F", "5");
		evalRulesList.add(evalRules);
		Set<Long> attrIds = evalRulesList.stream().map(EvalRules::getAttrId).collect(Collectors.toSet());
		List<EvalAttributes> evalAttrsList = new ArrayList<>();
		EvalAttributes evalAttributes = new EvalAttributes(5l, "Distance From Destination", "distanceFrmDest", "D");
		evalAttrsList.add(evalAttributes);
		TicketInfo ticketInfo = new TicketInfo(UUID.randomUUID());

		when(deliveryInfoRepository.findByDeliveryStatusNotAndTicketIdIsNull(Constants.DELIVERY_STATUS_DELIVERED)).thenReturn(deliveryInfos);
		when(useCaseRepository.findAllByOrderByExecOrderAsc()).thenReturn(userCasesList);
		when(evalRuleRepository.findByRuleIdIn(ruleIds)).thenReturn(evalRulesList);
		when(evalAttributesRepository.findByAttrIdIn(attrIds)).thenReturn(evalAttrsList);
		lenient().when(ticketInfoRepository.save(any())).thenReturn(ticketInfo);

		monitorDelivery.calculateDeliveryPriority();
		assertEquals(ticketInfo.getTicketId(), deliveryInfo.getTicketId());
	}

	@Test
	public void test_DistanceFromDestinationIsHigh() {
		monitorDelivery = new MonitorDelivery(useCaseRepository, evalRuleRepository,
				evalAttributesRepository, deliveryInfoRepository, ticketInfoRepository);

		List<DeliveryInfo> deliveryInfos = new ArrayList<>();
		DeliveryInfo deliveryInfo = new DeliveryInfo(6l, "New", "PICKEDUP", new Timestamp(Instant.now().plus(10, ChronoUnit.MINUTES).toEpochMilli()),
				6d, 3l, 20l, 8l, null);
		deliveryInfos.add(deliveryInfo);
		List<UseCases> userCasesList = new ArrayList<>();
		UseCases useCases = new UseCases(5l, "Low Priority! Distance From Destination is high", 5l, 5l, 3l);
		userCasesList.add(useCases);
		Set<Long> ruleIds = userCasesList.stream().map(UseCases::getRuleId).collect(Collectors.toSet());
		List<EvalRules> evalRulesList = new ArrayList<>();
		EvalRules evalRules = new EvalRules(5l, 5l, 6l, "F", "5");
		evalRulesList.add(evalRules);
		Set<Long> attrIds = evalRulesList.stream().map(EvalRules::getAttrId).collect(Collectors.toSet());
		List<EvalAttributes> evalAttrsList = new ArrayList<>();
		EvalAttributes evalAttributes = new EvalAttributes(5l, "Distance From Destination", "distanceFrmDest", "D");
		evalAttrsList.add(evalAttributes);
		TicketInfo ticketInfo = new TicketInfo(UUID.randomUUID());

		when(deliveryInfoRepository.findByDeliveryStatusNotAndTicketIdIsNull(Constants.DELIVERY_STATUS_DELIVERED)).thenReturn(deliveryInfos);
		when(useCaseRepository.findAllByOrderByExecOrderAsc()).thenReturn(userCasesList);
		when(evalRuleRepository.findByRuleIdIn(ruleIds)).thenReturn(evalRulesList);
		when(evalAttributesRepository.findByAttrIdIn(attrIds)).thenReturn(evalAttrsList);
		lenient().when(ticketInfoRepository.save(any())).thenReturn(ticketInfo);

		monitorDelivery.calculateDeliveryPriority();
		assertEquals(ticketInfo.getTicketId(), deliveryInfo.getTicketId());
	}

	@Test
	public void test_AlreadyDeliveredOrder() {
		monitorDelivery = new MonitorDelivery(useCaseRepository, evalRuleRepository,
				evalAttributesRepository, deliveryInfoRepository, ticketInfoRepository);

		DeliveryInfo deliveryInfo = new DeliveryInfo(7l, "VIP", "DELIVERED", null,
				8d, 4l, 15l, 12l, null);
		TicketInfo ticketInfo = new TicketInfo(UUID.randomUUID());

		when(deliveryInfoRepository.findByDeliveryStatusNotAndTicketIdIsNull(Constants.DELIVERY_STATUS_DELIVERED)).thenReturn(null);

		monitorDelivery.calculateDeliveryPriority();
		assertNotEquals(ticketInfo.getTicketId(), deliveryInfo.getTicketId());
	}
}