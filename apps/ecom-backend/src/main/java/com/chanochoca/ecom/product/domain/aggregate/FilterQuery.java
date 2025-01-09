package com.chanochoca.ecom.product.domain.aggregate;

import com.chanochoca.ecom.product.domain.vo.ProductSize;
import com.chanochoca.ecom.product.domain.vo.PublicId;
import org.jilt.Builder;

import java.util.List;

@Builder
public record FilterQuery(PublicId categoryId, List<ProductSize> sizes) {
}
