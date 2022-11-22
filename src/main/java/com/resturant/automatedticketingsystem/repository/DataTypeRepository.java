package com.resturant.automatedticketingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.resturant.automatedticketingsystem.model.DataType;

public interface DataTypeRepository extends JpaRepository<DataType, Long> {
}
