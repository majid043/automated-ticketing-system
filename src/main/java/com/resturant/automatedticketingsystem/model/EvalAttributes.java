package com.resturant.automatedticketingsystem.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "eval_attributes")
public class EvalAttributes {

	@Id
	@Column(name = "attr_id")
	private Long attrId;

	@Column(name = "attr_name")
	private String attrName;

	@Column(name = "attr_key")
	private String attrKey;

	@Column(name = "data_type")
	private String dataType;

	public EvalAttributes() {}
	
	public EvalAttributes(Long attrId, String attrName, String attrKey, String dataType) {
		this.attrId = attrId;
		this.attrName = attrName;
		this.attrKey = attrKey;
		this.dataType = dataType;
	}

	public Long getAttrId() {
		return attrId;
	}

	public String getAttrName() {
		return attrName;
	}

	public String getAttrKey() {
		return attrKey;
	}

	public String getDataType() {
		return dataType;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EvalAttributes [attrId=");
		builder.append(attrId);
		builder.append(", attrName=");
		builder.append(attrName);
		builder.append(", attrKey=");
		builder.append(attrKey);
		builder.append(", dataType=");
		builder.append(dataType);
		builder.append("]");
		return builder.toString();
	}
	
}