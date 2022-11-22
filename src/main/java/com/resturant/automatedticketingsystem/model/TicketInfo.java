package com.resturant.automatedticketingsystem.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "ticket_infos")
public class TicketInfo {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "ticket_id", columnDefinition = "VARCHAR(255)")
	private UUID ticketId;

	@Column(name = "priority")
	private Long priority;

	@Column(name = "delivery_id")
	private Long deliveryId;

	@Column(name = "message")
	private String message;
	
	public TicketInfo() {}
	
	public TicketInfo(UUID ticketId) {
		this.ticketId = ticketId;
	}
	
	public TicketInfo(Long priority, Long deliveryId, String message) {
		this.priority = priority;
		this.deliveryId = deliveryId;
		this.message = message;
	}

	public UUID getTicketId() {
		return ticketId;
	}

	public Long getPriority() {
		return priority;
	}

	public Long getDeliveryId() {
		return deliveryId;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TicketInfo [ticketId=");
		builder.append(ticketId);
		builder.append(", priority=");
		builder.append(priority);
		builder.append(", deliveryId=");
		builder.append(deliveryId);
		builder.append(", message=");
		builder.append(message);
		builder.append("]");
		return builder.toString();
	}
}