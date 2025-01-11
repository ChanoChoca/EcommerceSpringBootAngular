package com.chanochoca.ecom.product.application;

import com.chanochoca.ecom.order.domain.order.aggregate.DetailCartItemRequest;
import com.chanochoca.ecom.order.domain.order.aggregate.DetailCartRequest;
import com.chanochoca.ecom.order.domain.order.aggregate.DetailCartResponse;
import com.chanochoca.ecom.order.domain.order.service.CartReader;
import com.chanochoca.ecom.product.domain.aggregate.Product;
import com.chanochoca.ecom.product.domain.vo.PublicId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderApplicationService {

  private final ProductsApplicationService productsApplicationService;
  private final CartReader cartReader;

  public OrderApplicationService(ProductsApplicationService productsApplicationService) {
    this.productsApplicationService = productsApplicationService;
    this.cartReader = new CartReader();
  }

  @Transactional(readOnly = true)
  public DetailCartResponse getCartDetails(DetailCartRequest detailCartRequest) {
    List<PublicId> publicIds = detailCartRequest.items().stream().map(DetailCartItemRequest::productId).toList();
    List<Product> productsInformation = productsApplicationService.getProductsByPublicIdsIn(publicIds);
    return cartReader.getDetails(productsInformation);
  }
}
