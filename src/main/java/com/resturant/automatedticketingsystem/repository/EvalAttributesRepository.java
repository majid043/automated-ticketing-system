package com.resturant.automatedticketingsystem.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import com.resturant.automatedticketingsystem.model.EvalAttributes;

public interface EvalAttributesRepository extends JpaRepository<EvalAttributes, Long> {
	public EvalAttributes findByAttrId(Long attrId);
	public List<EvalAttributes> findByAttrIdIn(Set<Long> attrIds);
}
