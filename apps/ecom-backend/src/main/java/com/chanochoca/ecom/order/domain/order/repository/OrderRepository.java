package com.chanochoca.ecom.order.domain.order.repository;

import com.chanochoca.ecom.order.domain.order.aggregate.Order;

public interface OrderRepository {

  void save(Order order);
}
