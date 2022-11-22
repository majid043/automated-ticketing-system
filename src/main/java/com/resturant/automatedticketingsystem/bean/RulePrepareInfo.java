package com.resturant.automatedticketingsystem.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.resturant.automatedticketingsystem.model.EvalAttributes;
import com.resturant.automatedticketingsystem.model.EvalRules;
import com.resturant.automatedticketingsystem.model.UseCases;

public class RulePrepareInfo {

	private final List<UseCases> useCases;
	private final Map<Long, EvalRules> rulesById;
	private final Map<Long, EvalAttributes> attrById;

	public RulePrepareInfo(List<UseCases> useCases, Map<Long, EvalRules> rulesById, Map<Long, EvalAttributes> attrById) {
		this.useCases = useCases;
		this.rulesById = rulesById;
		this.attrById = attrById;
	}

	public List<UseCases> getUseCases() {
		return new ArrayList<>(useCases);
	}

	public Map<Long, EvalRules> getRulesById() {
		return new ConcurrentHashMap<Long, EvalRules>(rulesById);
	}

	public Map<Long, EvalAttributes> getAttrById() {
		return new ConcurrentHashMap<Long, EvalAttributes>(attrById);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RulePrepareInfo [useCases=");
		builder.append(useCases);
		builder.append(", rulesById=");
		builder.append(rulesById);
		builder.append(", attrById=");
		builder.append(attrById);
		builder.append("]");
		return builder.toString();
	}
}