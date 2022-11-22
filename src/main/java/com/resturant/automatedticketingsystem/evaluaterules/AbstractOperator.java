package com.resturant.automatedticketingsystem.evaluaterules;

import java.sql.Timestamp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.resturant.automatedticketingsystem.exception.DataNotFoundException;
import com.resturant.automatedticketingsystem.util.Constants;

public abstract class AbstractOperator {
	private static final Logger lgr = LogManager.getLogger(AbstractOperator.class.getName());
	
	public static AbstractOperator getInstance(Long operatorCode) {
		lgr.info(lgr.isInfoEnabled() ? "Operator Code received [" + operatorCode + "]" : null);
		if (Long.compare(Constants.OPERATOR_CODE_EQUAL, operatorCode) == 0) {
			return EqualToOperatorImpl.getInstance();
		}
		if (Long.compare(Constants.OPERATOR_CODE_NOT_EQUAL, operatorCode) == 0) {
			return NotEqualToOperatorImpl.getInstance();
		}
		if (Long.compare(Constants.OPERATOR_CODE_GREATER_THAN, operatorCode) == 0) {
			return GreaterThanOperatorImpl.getInstance();
		}
		if (Long.compare(Constants.OPERATOR_CODE_GREATER_THAN_EQUALTO, operatorCode) == 0) {
			return GreaterThanEqualToOperatorImpl.getInstance();
		}
		if (Long.compare(Constants.OPERATOR_CODE_LESS_THAN, operatorCode) == 0) {
			return LessThanOperatorImpl.getInstance();
		}
		if (Long.compare(Constants.OPERATOR_CODE_LESS_THAN_EQUALTO, operatorCode) == 0) {
			return LessThanOrEqualToOperatorImpl.getInstance();
		}
		throw new DataNotFoundException("Invalid Operator Code received [" + operatorCode + "]");
	}

	public abstract boolean applyOpertion(String dataType, String operand1, String operand2);
	
	protected Timestamp getTimestamp(String operand) {
		Timestamp timestamp = null;
		try {
			timestamp = Timestamp.valueOf(operand);
		} catch (IllegalArgumentException e) {
			timestamp = new Timestamp(Long.valueOf(operand));
		}
		return timestamp;
	}
}