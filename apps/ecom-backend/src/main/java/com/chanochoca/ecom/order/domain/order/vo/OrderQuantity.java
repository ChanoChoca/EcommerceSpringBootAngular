package com.chanochoca.ecom.order.domain.order.vo;

import com.chanochoca.ecom.shared.error.domain.Assert;

public record OrderQuantity(long value) {

  public OrderQuantity {
    Assert.field("value", value).positive();
  }
}
