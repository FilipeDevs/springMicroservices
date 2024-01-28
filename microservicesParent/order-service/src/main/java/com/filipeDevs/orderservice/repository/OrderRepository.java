package com.filipeDevs.orderservice.repository;

import com.filipeDevs.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
