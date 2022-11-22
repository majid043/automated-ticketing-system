package com.resturant.automatedticketingsystem.model;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "delivery_infos")
public class DeliveryInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "delivery_id")
	private Long deliveryId;

	@Column(name = "customer_type")
	private String customerType;

	@Column(name = "delivery_status")
	private String deliveryStatus;

	@Column(name = "expected_time")
	private Timestamp expectedTime;

	@Column(name = "distance_frm_dest")
	private Double distanceFrmDest;

	@Column(name = "rider_rating")
	private Long riderRating;

	@Column(name = "mean_time")
	private Long meanTime;

	@Column(name = "dest_reach_time")
	private Long destReachTime;
	
	@Column(name = "ticket_id")
	private UUID ticketId;

	public DeliveryInfo() {}
	
	public DeliveryInfo(Long deliveryId, String customerType, String deliveryStatus, Timestamp expectedTime,
			Double distanceFrmDest, Long riderRating, Long meanTime, Long destReachTime, UUID ticketId) {
		this.deliveryId = deliveryId;
		this.customerType = customerType;
		this.deliveryStatus = deliveryStatus;
		this.expectedTime = expectedTime;
		this.distanceFrmDest = distanceFrmDest;
		this.riderRating = riderRating;
		this.meanTime = meanTime;
		this.destReachTime = destReachTime;
		this.ticketId = ticketId;
	}

	public Long getDeliveryId() {
		return deliveryId;
	}

	public String getCustomerType() {
		return customerType;
	}

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public Timestamp getExpectedTime() {
		return expectedTime;
	}

	public Double getDistanceFrmDest() {
		return distanceFrmDest;
	}

	public Long getRiderRating() {
		return riderRating;
	}

	public Long getMeanTime() {
		return meanTime;
	}

	public Long getDestReachTime() {
		return destReachTime;
	}

	public UUID getTicketId() {
		return ticketId;
	}

	public void setTicketId(UUID ticketId) {
		this.ticketId = ticketId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DeliveryInfo [deliveryId=");
		builder.append(deliveryId);
		builder.append(", customerType=");
		builder.append(customerType);
		builder.append(", deliveryStatus=");
		builder.append(deliveryStatus);
		builder.append(", expectedTime=");
		builder.append(expectedTime);
		builder.append(", distanceFrmDest=");
		builder.append(distanceFrmDest);
		builder.append(", riderRating=");
		builder.append(riderRating);
		builder.append(", meanTime=");
		builder.append(meanTime);
		builder.append(", destReachTime=");
		builder.append(destReachTime);
		builder.append(", ticketId=");
		builder.append(ticketId);
		builder.append("]");
		return builder.toString();
	}	
	
}