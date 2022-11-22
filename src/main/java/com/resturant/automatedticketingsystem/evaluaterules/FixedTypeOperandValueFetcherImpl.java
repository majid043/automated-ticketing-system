package com.resturant.automatedticketingsystem.evaluaterules;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.resturant.automatedticketingsystem.model.EvalAttributes;

final class FixedTypeOperandValueFetcherImpl extends AbstractOperandValueFetcher {

	private static final Logger lgr = LogManager.getLogger(FixedTypeOperandValueFetcherImpl.class.getName());
	static FixedTypeOperandValueFetcherImpl oprandValueFetcher = null;
	private FixedTypeOperandValueFetcherImpl() {}

	static FixedTypeOperandValueFetcherImpl getInstance() {
		if (null == oprandValueFetcher) {
			oprandValueFetcher = new FixedTypeOperandValueFetcherImpl();
		}
		return oprandValueFetcher;
	}

	public String fetchValue(String key, Map<String, String> evaluatioData, Map<Long, EvalAttributes> attrById, String findKey) {
		lgr.info(lgr.isInfoEnabled() ? "key [" + key + "], evaluatioData ["+evaluatioData+"], attrByIdMap ["+attrById+"] and findKey ["+findKey+"]" : null);
		String value = evaluatioData.get(key);
		lgr.info(lgr.isInfoEnabled() ? "value [" + value + "]" : null);
		return value;
	}
}