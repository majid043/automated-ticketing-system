package com.resturant.automatedticketingsystem.evaluaterules;

import java.sql.Timestamp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.resturant.automatedticketingsystem.util.Constants;

final class GreaterThanOperatorImpl extends AbstractOperator {

	private static final Logger lgr = LogManager.getLogger(GreaterThanOperatorImpl.class.getName());
	private static GreaterThanOperatorImpl operatorImpl = null;
	private GreaterThanOperatorImpl() {}

	static GreaterThanOperatorImpl getInstance() {
		if (null == operatorImpl) {
			operatorImpl = new GreaterThanOperatorImpl();
		}
		return operatorImpl;
	}

	@Override
	public boolean applyOpertion(String dataType, String operand1, String operand2) {
		lgr.info(lgr.isInfoEnabled() ? "dataType [" + dataType + "], operand1 ["+operand1+"] and operand2 ["+operand2+"]" : null);
		if (Constants.DATA_TYPE_NUMERIC.equals(dataType)) {
			Long op1 = Long.valueOf(operand1);
			Long op2 = Long.valueOf(operand2);
			return Long.compare(op1, op2) > 0;
		}
		if (Constants.DATA_TYPE_DECIMAL.equals(dataType)) {
			Double op1 = Double.valueOf(operand1);
			Double op2 = Double.valueOf(operand2);
			return Double.compare(op1, op2) > 0;
		}
		if (Constants.DATA_TYPE_TIME.equals(dataType)) {
			Timestamp op1 = getTimestamp(operand1);
			Timestamp op2 = getTimestamp(operand2);
			return op1.after(op2);
		}
		return false;
	}
}