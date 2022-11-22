package com.resturant.automatedticketingsystem.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import com.resturant.automatedticketingsystem.model.EvalRules;

public interface EvalRuleRepository extends JpaRepository<EvalRules, Long> {
	public EvalRules findByRuleId(Long ruleId);
	public List<EvalRules> findByRuleIdIn(Set<Long> ruleIds);
}
