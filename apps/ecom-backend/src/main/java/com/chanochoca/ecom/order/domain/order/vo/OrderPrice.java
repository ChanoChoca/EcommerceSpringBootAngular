package com.chanochoca.ecom.order.domain.order.vo;

import com.chanochoca.ecom.shared.error.domain.Assert;

public record OrderPrice(double value) {

  public OrderPrice {
    Assert.field("value", value).strictlyPositive();
  }
}
