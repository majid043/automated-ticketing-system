package com.resturant.automatedticketingsystem.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "eval_rules")
public class EvalRules {

	@Id
	@Column(name = "rule_id")
	private Long ruleId;

	@Column(name = "attr_id")
	private Long attrId;

	@Column(name = "operator_code")
	private Long operatorCode;

	@Column(name = "attr_value_type")
	private String attrValueType;

	@Column(name = "attr_value")
	private String attrValue;

	public EvalRules() {}
	
	public EvalRules(Long ruleId, Long attrId, Long operatorCode, String attrValueType, String attrValue) {
		this.ruleId = ruleId;
		this.attrId = attrId;
		this.operatorCode = operatorCode;
		this.attrValueType = attrValueType;
		this.attrValue = attrValue;
	}

	public Long getRuleId() {
		return ruleId;
	}

	public Long getAttrId() {
		return attrId;
	}

	public Long getOperatorCode() {
		return operatorCode;
	}

	public String getAttrValueType() {
		return attrValueType;
	}

	public String getAttrValue() {
		return attrValue;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EvalRules [ruleId=");
		builder.append(ruleId);
		builder.append(", attrId=");
		builder.append(attrId);
		builder.append(", operatorCode=");
		builder.append(operatorCode);
		builder.append(", attrValueType=");
		builder.append(attrValueType);
		builder.append(", attrValue=");
		builder.append(attrValue);
		builder.append("]");
		return builder.toString();
	}
}