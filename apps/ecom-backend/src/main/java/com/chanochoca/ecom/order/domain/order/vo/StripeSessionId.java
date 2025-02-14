package com.chanochoca.ecom.order.domain.order.vo;

import com.chanochoca.ecom.shared.error.domain.Assert;

public record StripeSessionId(String value) {

  public StripeSessionId {
    Assert.notNull("value", value);
  }
}
