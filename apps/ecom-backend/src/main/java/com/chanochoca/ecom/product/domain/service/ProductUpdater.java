package com.chanochoca.ecom.product.domain.service;

import com.chanochoca.ecom.order.domain.order.aggregate.OrderProductQuantity;
import com.chanochoca.ecom.product.domain.repository.ProductRepository;

import java.util.List;

public class ProductUpdater {

  private final ProductRepository productRepository;

  public ProductUpdater(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public void updateProductQuantity(List<OrderProductQuantity> orderProductQuantities) {
    for (OrderProductQuantity orderProductQuantity : orderProductQuantities) {
      productRepository.updateQuantity(orderProductQuantity.productPublicId(),
        orderProductQuantity.quantity().value());
    }
  }
}
