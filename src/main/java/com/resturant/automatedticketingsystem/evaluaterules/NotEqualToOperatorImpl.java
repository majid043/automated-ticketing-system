package com.resturant.automatedticketingsystem.evaluaterules;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.resturant.automatedticketingsystem.util.Constants;

final class NotEqualToOperatorImpl extends AbstractOperator {

	private static final Logger lgr = LogManager.getLogger(NotEqualToOperatorImpl.class.getName());
	private static NotEqualToOperatorImpl operatorImpl = null;
	private NotEqualToOperatorImpl() {}

	static NotEqualToOperatorImpl getInstance() {
		if (null == operatorImpl) {
			operatorImpl = new NotEqualToOperatorImpl();
		}
		return operatorImpl;
	}

	@Override
	public boolean applyOpertion(String dataType, String operand1, String operand2) {
		lgr.info(lgr.isInfoEnabled() ? "dataType [" + dataType + "], operand1 ["+operand1+"] and operand2 ["+operand2+"]" : null);
		if (Constants.DATA_TYPE_STRING.equals(dataType)) {
			return !operand1.equalsIgnoreCase(operand2);
		}
		if (Constants.DATA_TYPE_NUMERIC.equals(dataType)) {
			Long op1 = Long.valueOf(operand1);
			Long op2 = Long.valueOf(operand2);
			return Long.compare(op1, op2) != 0;
		}
		if (Constants.DATA_TYPE_DECIMAL.equals(dataType)) {
			Double op1 = Double.valueOf(operand1);
			Double op2 = Double.valueOf(operand2);
			return Double.compare(op1, op2) != 0;
		}
		return false;
	}
}