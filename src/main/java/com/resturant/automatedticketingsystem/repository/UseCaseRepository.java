package com.resturant.automatedticketingsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.resturant.automatedticketingsystem.model.UseCases;

public interface UseCaseRepository extends JpaRepository<UseCases, Long> {
	public List<UseCases> findAllByOrderByExecOrderAsc();
}
