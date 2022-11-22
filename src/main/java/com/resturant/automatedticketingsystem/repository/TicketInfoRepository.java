package com.resturant.automatedticketingsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.resturant.automatedticketingsystem.model.TicketInfo;

public interface TicketInfoRepository extends JpaRepository<TicketInfo, Long> {
	public List<TicketInfo> findAllByOrderByPriorityAsc();
}