package com.chanochoca.ecom.order.domain.order.aggregate;

import com.chanochoca.ecom.product.domain.vo.PublicId;
import org.jilt.Builder;

@Builder
public record DetailCartItemRequest(PublicId productId, long quantity) { }
