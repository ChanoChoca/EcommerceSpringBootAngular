package com.chanochoca.ecom.product.domain.vo;

import com.chanochoca.ecom.shared.error.domain.Assert;

public record ProductDescription(String value) {

  public ProductDescription {
    Assert.field("value", value).notNull().minLength(10);
  }
}
