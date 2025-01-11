package com.chanochoca.ecom.order.application;

import com.chanochoca.ecom.order.domain.order.aggregate.DetailCartItemRequest;
import com.chanochoca.ecom.order.domain.order.aggregate.DetailCartRequest;
import com.chanochoca.ecom.order.domain.order.aggregate.DetailCartResponse;
import com.chanochoca.ecom.order.domain.order.repository.OrderRepository;
import com.chanochoca.ecom.order.domain.order.service.CartReader;
import com.chanochoca.ecom.order.domain.order.service.OrderCreator;
import com.chanochoca.ecom.order.domain.order.vo.StripeSessionId;
import com.chanochoca.ecom.order.domain.user.aggregate.User;
import com.chanochoca.ecom.order.infrastructure.secondary.service.stripe.StripeService;
import com.chanochoca.ecom.product.application.ProductsApplicationService;
import com.chanochoca.ecom.product.domain.aggregate.Product;
import com.chanochoca.ecom.product.domain.vo.PublicId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderApplicationService {

  private final ProductsApplicationService productsApplicationService;
  private final CartReader cartReader;
  private final UsersApplicationService usersApplicationService;
  private final OrderCreator orderCreator;

  public OrderApplicationService(ProductsApplicationService productsApplicationService,
                                 UsersApplicationService usersApplicationService,
                                 OrderRepository orderRepository,
                                 StripeService stripeService) {
    this.productsApplicationService = productsApplicationService;
    this.usersApplicationService = usersApplicationService;
    this.cartReader = new CartReader();
    this.orderCreator = new OrderCreator(orderRepository, stripeService);
  }

  @Transactional(readOnly = true)
  public DetailCartResponse getCartDetails(DetailCartRequest detailCartRequest) {
    List<PublicId> publicIds = detailCartRequest.items().stream().map(DetailCartItemRequest::productId).toList();
    List<Product> productsInformation = productsApplicationService.getProductsByPublicIdsIn(publicIds);
    return cartReader.getDetails(productsInformation);
  }

  @Transactional
  public StripeSessionId createOrder(List<DetailCartItemRequest> items) {
    User authenticatedUser = usersApplicationService.getAuthenticatedUser();
    List<PublicId> publicIds = items.stream().map(DetailCartItemRequest::productId).toList();
    List<Product> productsInformation = productsApplicationService.getProductsByPublicIdsIn(publicIds);
    return orderCreator.create(productsInformation, items, authenticatedUser);
  }
}
