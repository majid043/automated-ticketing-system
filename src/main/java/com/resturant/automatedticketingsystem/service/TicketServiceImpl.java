package com.resturant.automatedticketingsystem.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.resturant.automatedticketingsystem.model.TicketInfo;
import com.resturant.automatedticketingsystem.repository.TicketInfoRepository;

@Service
public class TicketServiceImpl implements TicketService {

	private final TicketInfoRepository ticketInfoRepository;

	public TicketServiceImpl(TicketInfoRepository ticketInfoRepository) {
		this.ticketInfoRepository = ticketInfoRepository;
	}

	@Override
	public List<TicketInfo> getAll() {
		List<TicketInfo> ticketList = new ArrayList<>();
		ticketInfoRepository.findAll().forEach(ticketList::add);
		return ticketList;
	}

	public TicketInfo save(TicketInfo ticket) {
		return ticketInfoRepository.save(ticket);
	}

	@Override
	public TicketInfo findById(Long ticketId) {
		Optional<TicketInfo> dbTicket = ticketInfoRepository.findById(ticketId);
		return dbTicket.orElse(null);
	}

	@Override
	public List<TicketInfo> findAllByOrderByPriorityAsc() {
		List<TicketInfo> ticketList = new ArrayList<>();
		ticketInfoRepository.findAllByOrderByPriorityAsc().forEach(ticketList::add);
		return ticketList;
	}
}