package com.resturant.automatedticketingsystem.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import com.resturant.automatedticketingsystem.model.OperatorCode;

public interface OperatorCodeRepository extends JpaRepository<OperatorCode, Long> {
	public OperatorCode findByOperatorCode(Long operatorCode);
	public List<OperatorCode> findByOperatorCodeIn(Set<Long> operatorCodes);
}
