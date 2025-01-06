package com.chanochoca.ecom.product.vo;

import com.chanochoca.ecom.shared.error.domain.Assert;

public record ProductPrice(double value) {

  public ProductPrice {
    Assert.field("value", value).min(0.1);
  }
}
