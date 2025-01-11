package com.chanochoca.ecom.order.infrastructure.primary.order;

import com.chanochoca.ecom.order.domain.order.aggregate.DetailCartResponse;
import org.jilt.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public record RestDetailCartResponse(List<RestProductCart> products) {

  public static RestDetailCartResponse from(DetailCartResponse detailCartResponse) {
    return RestDetailCartResponseBuilder.restDetailCartResponse()
      .products(detailCartResponse.getProducts().stream().map(RestProductCart::from).toList())
        .build();
  }
}
