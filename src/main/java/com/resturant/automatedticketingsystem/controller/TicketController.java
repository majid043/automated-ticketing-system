package com.resturant.automatedticketingsystem.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.resturant.automatedticketingsystem.exception.BadRequestException;
import com.resturant.automatedticketingsystem.model.TicketInfo;
import com.resturant.automatedticketingsystem.service.TicketService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin
@RequestMapping(value = TicketController.BASE_URL)
public class TicketController
{
	public static final String BASE_URL = "v1";
	private final TicketService ticketService;
	private static final Logger lgr = LogManager.getLogger(TicketController.class.getName());
	
	TicketController(TicketService ticketService) {
		this.ticketService = ticketService;
	}

	@ApiOperation(value = "Get Tickets Info", response = List.class)
	@ApiResponses(value = {	@ApiResponse(code = 200, message = "Success|OK"),
			@ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!")})
	@GetMapping(path = "/api/tickets", produces = "application/json")	
	public ResponseEntity<List<TicketInfo>> findAllTicketsByPriority() {
		try {
			List<TicketInfo> tikcetInfos = ticketService.findAllByOrderByPriorityAsc();
			if (null != tikcetInfos) {
				lgr.info(lgr.isInfoEnabled() ? "Total tickets retured [" + tikcetInfos.size() + "]" : null);
				return ResponseEntity.ok(tikcetInfos);
			}
			return ResponseEntity.notFound().build();
		} catch (Throwable e) {
			throw new BadRequestException(e.getMessage());
		}
	}
}