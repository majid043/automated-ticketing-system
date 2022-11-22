package com.resturant.automatedticketingsystem.evaluaterules;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.resturant.automatedticketingsystem.model.EvalAttributes;
import com.resturant.automatedticketingsystem.util.Constants;

public abstract class AbstractOperandValueFetcher {
	
	private static final Logger lgr = LogManager.getLogger(AbstractOperandValueFetcher.class.getName());
	
	public static final AbstractOperandValueFetcher getInstance(String attrValueType) {
		lgr.info(lgr.isInfoEnabled() ? "Attribute Value Type received [" + attrValueType + "]" : null);
		if (Constants.ATTRIBUTE_TYPE.equalsIgnoreCase(attrValueType)) {
			return AttributeTypeOperandValueFetcherImpl.getInstance();
		} else {
			return FixedTypeOperandValueFetcherImpl.getInstance();
		}
	}
	public abstract String fetchValue(String key, Map<String, String> evaluatioData, Map<Long, EvalAttributes> attrById, String findKey);
}