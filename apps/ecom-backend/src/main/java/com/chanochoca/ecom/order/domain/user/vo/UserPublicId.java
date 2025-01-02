package com.chanochoca.ecom.order.domain.user.vo;

import com.chanochoca.ecom.shared.error.domain.Assert;

import java.util.UUID;

public record UserPublicId(UUID value) {

  public UserPublicId {
    Assert.notNull("value", value);
  }
}
