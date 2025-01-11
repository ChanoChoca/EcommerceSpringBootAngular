package com.chanochoca.ecom.order.domain.order.service;

import com.chanochoca.ecom.order.domain.order.aggregate.DetailCartResponse;
import com.chanochoca.ecom.order.domain.order.aggregate.DetailCartResponseBuilder;
import com.chanochoca.ecom.order.domain.order.aggregate.ProductCart;
import com.chanochoca.ecom.product.domain.aggregate.Product;

import java.util.List;

public class CartReader {

  public CartReader() {}

  public DetailCartResponse getDetails(List<Product> products) {
    List<ProductCart> cartProducts = products.stream().map(ProductCart::from).toList();
    return DetailCartResponseBuilder.detailCartResponse().products(cartProducts)
      .build();
  }
}
