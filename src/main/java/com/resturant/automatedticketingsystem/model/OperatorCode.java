package com.resturant.automatedticketingsystem.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "operator_codes")
public class OperatorCode {

	@Id
	@Column(name = "operator_code")
	private Long operatorCode;

	@Column(name = "operator_code_desc")
	private String operatorCodeDesc;

	@Column(name = "operator_value")
	private String operatorValue;

	public OperatorCode() {}
	
	public OperatorCode(Long operatorCode, String operatorCodeDesc, String operatorValue) {
		this.operatorCode = operatorCode;
		this.operatorCodeDesc = operatorCodeDesc;
		this.operatorValue = operatorValue;
	}

	public Long getOperatorCode() {
		return operatorCode;
	}

	public String getOperatorCodeDesc() {
		return operatorCodeDesc;
	}

	public String getOperatorValue() {
		return operatorValue;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OperatorCode [operatorCode=");
		builder.append(operatorCode);
		builder.append(", operatorCodeDesc=");
		builder.append(operatorCodeDesc);
		builder.append(", operatorValue=");
		builder.append(operatorValue);
		builder.append("]");
		return builder.toString();
	}
}