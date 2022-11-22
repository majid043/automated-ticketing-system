package com.resturant.automatedticketingsystem.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "data_types")
public class DataType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "data_type_id")
	private Long dataTypeId;

	@Column(name = "data_type")
	private String dataType;

	@Column(name = "data_type_desc")
	private String dataTypeDesc;

	public DataType() {}
	
	public DataType(Long dataTypeId, String dataType, String dataTypeDesc) {
		this.dataTypeId = dataTypeId;
		this.dataType = dataType;
		this.dataTypeDesc = dataTypeDesc;
	}

	public Long getDataTypeId() {
		return dataTypeId;
	}

	public String getDataType() {
		return dataType;
	}

	public String getDataTypeDesc() {
		return dataTypeDesc;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DataType [dataTypeId=");
		builder.append(dataTypeId);
		builder.append(", dataType=");
		builder.append(dataType);
		builder.append(", dataTypeDesc=");
		builder.append(dataTypeDesc);
		builder.append("]");
		return builder.toString();
	}
	
}