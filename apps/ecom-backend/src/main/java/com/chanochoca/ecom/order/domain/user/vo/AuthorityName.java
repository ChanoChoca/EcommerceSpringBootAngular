package com.chanochoca.ecom.order.domain.user.vo;

import com.chanochoca.ecom.shared.error.domain.Assert;

public record AuthorityName(String name) {

  public AuthorityName {
    Assert.field("name", name).notNull();
  }
}
