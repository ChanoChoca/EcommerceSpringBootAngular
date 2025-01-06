package com.chanochoca.ecom.product.vo;

import com.chanochoca.ecom.shared.error.domain.Assert;

import java.util.UUID;

public record PublicId(UUID value) {

  public PublicId {
    Assert.notNull("value", value);
  }
}
