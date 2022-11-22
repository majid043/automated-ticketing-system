package com.resturant.automatedticketingsystem.evaluaterules;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.resturant.automatedticketingsystem.util.Constants;

final class LessThanOrEqualToOperatorImpl extends AbstractOperator {

	private static final Logger lgr = LogManager.getLogger(LessThanOrEqualToOperatorImpl.class.getName());
	private static LessThanOrEqualToOperatorImpl operatorImpl = null;
	private LessThanOrEqualToOperatorImpl() {}

	static LessThanOrEqualToOperatorImpl getInstance() {
		if (null == operatorImpl) {
			operatorImpl = new LessThanOrEqualToOperatorImpl();
		}
		return operatorImpl;
	}

	@Override
	public boolean applyOpertion(String dataType, String operand1, String operand2) {
		lgr.info(lgr.isInfoEnabled() ? "dataType [" + dataType + "], operand1 ["+operand1+"] and operand2 ["+operand2+"]" : null);
		if (Constants.DATA_TYPE_NUMERIC.equals(dataType)) {
			Long op1 = Long.valueOf(operand1);
			Long op2 = Long.valueOf(operand2);
			return Long.compare(op1, op2) <= 0;
		}
		if (Constants.DATA_TYPE_DECIMAL.equals(dataType)) {
			Double op1 = Double.valueOf(operand1);
			Double op2 = Double.valueOf(operand2);
			return Double.compare(op1, op2) <= 0;
		}
		return false;
	}
}