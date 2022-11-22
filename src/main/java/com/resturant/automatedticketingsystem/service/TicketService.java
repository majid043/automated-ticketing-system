package com.resturant.automatedticketingsystem.service;

import java.util.List;

import com.resturant.automatedticketingsystem.model.TicketInfo;

public interface TicketService {
	public List<TicketInfo> getAll();
	public TicketInfo save(TicketInfo ticket);
	public TicketInfo findById(Long ticketId);
	public List<TicketInfo> findAllByOrderByPriorityAsc();
}