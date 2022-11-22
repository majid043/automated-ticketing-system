package com.resturant.automatedticketingsystem.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "use_cases")
public class UseCases {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "use_case_id")
	private Long useCaseId;

	@Column(name = "use_case_name")
	private String useCaseName;

	@Column(name = "rule_id")
	private Long ruleId;

	@Column(name = "exec_order")
	private Long execOrder;

	@Column(name = "priority")
	private Long priority;

	public UseCases() {}
	
	public UseCases(Long useCaseId, String useCaseName, Long ruleId, Long execOrder, Long priority) {
		this.useCaseId = useCaseId;
		this.useCaseName = useCaseName;
		this.ruleId = ruleId;
		this.execOrder = execOrder;
		this.priority = priority;
	}

	public Long getUseCaseId() {
		return useCaseId;
	}

	public String getUseCaseName() {
		return useCaseName;
	}

	public Long getRuleId() {
		return ruleId;
	}

	public Long getExecOrder() {
		return execOrder;
	}

	public Long getPriority() {
		return priority;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UseCases [useCaseId=");
		builder.append(useCaseId);
		builder.append(", useCaseName=");
		builder.append(useCaseName);
		builder.append(", ruleId=");
		builder.append(ruleId);
		builder.append(", execOrder=");
		builder.append(execOrder);
		builder.append(", priority=");
		builder.append(priority);
		builder.append("]");
		return builder.toString();
	}

}