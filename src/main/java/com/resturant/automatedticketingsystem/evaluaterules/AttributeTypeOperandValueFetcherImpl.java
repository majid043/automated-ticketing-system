package com.resturant.automatedticketingsystem.evaluaterules;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.resturant.automatedticketingsystem.model.EvalAttributes;

final class AttributeTypeOperandValueFetcherImpl extends AbstractOperandValueFetcher {

	private static final Logger lgr = LogManager.getLogger(AttributeTypeOperandValueFetcherImpl.class.getName());
	static AttributeTypeOperandValueFetcherImpl oprandValueFetcher = null;
	private AttributeTypeOperandValueFetcherImpl() {}

	static AttributeTypeOperandValueFetcherImpl getInstance() {
		if (null == oprandValueFetcher) {
			oprandValueFetcher = new AttributeTypeOperandValueFetcherImpl();
		}
		return oprandValueFetcher;
	}

	@Override
	public String fetchValue(String key, Map<String, String> evaluatioData, Map<Long, EvalAttributes> attrById, String findKey) {
		lgr.info(lgr.isInfoEnabled() ? "key [" + key + "], evaluatioData ["+evaluatioData+"], attrByIdMap ["+attrById+"] and findKey ["+findKey+"]" : null);
		EvalAttributes attrInfo = attrById.get(Long.valueOf(findKey));
		String value = evaluatioData.get(attrInfo.getAttrKey());
		lgr.info(lgr.isInfoEnabled() ? "value [" + value + "]" : null);
		return value;
	}
}