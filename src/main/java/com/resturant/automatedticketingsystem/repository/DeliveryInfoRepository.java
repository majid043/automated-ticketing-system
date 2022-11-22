package com.resturant.automatedticketingsystem.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.resturant.automatedticketingsystem.model.DeliveryInfo;

public interface DeliveryInfoRepository extends JpaRepository<DeliveryInfo, Long> {
	public List<DeliveryInfo> findByDeliveryStatusNotAndTicketIdIsNull(String status);
}
