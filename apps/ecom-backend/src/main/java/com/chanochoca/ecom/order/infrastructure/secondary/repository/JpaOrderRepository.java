package com.chanochoca.ecom.order.infrastructure.secondary.repository;

import com.chanochoca.ecom.order.infrastructure.secondary.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderRepository extends JpaRepository<OrderEntity, Long> {
}
