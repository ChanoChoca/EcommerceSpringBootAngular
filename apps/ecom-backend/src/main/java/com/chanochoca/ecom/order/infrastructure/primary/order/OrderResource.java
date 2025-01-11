package com.chanochoca.ecom.order.infrastructure.primary.order;

import com.chanochoca.ecom.order.domain.order.CartPaymentException;
import com.chanochoca.ecom.order.domain.order.aggregate.DetailCartItemRequest;
import com.chanochoca.ecom.order.domain.order.aggregate.DetailCartRequest;
import com.chanochoca.ecom.order.domain.order.aggregate.DetailCartRequestBuilder;
import com.chanochoca.ecom.order.domain.order.aggregate.DetailCartResponse;
import com.chanochoca.ecom.order.application.OrderApplicationService;
import com.chanochoca.ecom.order.domain.order.vo.StripeSessionId;
import com.chanochoca.ecom.product.domain.vo.PublicId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderResource {

  private final OrderApplicationService orderApplicationService;

  public OrderResource(OrderApplicationService orderApplicationService) {
    this.orderApplicationService = orderApplicationService;
  }

  @GetMapping("/get-cart-details")
  public ResponseEntity<RestDetailCartResponse> getDetails(@RequestParam List<UUID> productIds) {
    List<DetailCartItemRequest> cartItemRequests = productIds.stream()
      .map(uuid -> new DetailCartItemRequest(new PublicId(uuid), 1))
      .toList();

    DetailCartRequest detailCartRequest = DetailCartRequestBuilder.detailCartRequest().items(cartItemRequests).build();
    DetailCartResponse cartDetails = orderApplicationService.getCartDetails(detailCartRequest);
    return ResponseEntity.ok(RestDetailCartResponse.from(cartDetails));
  }

  @PostMapping("/init-payment")
  public ResponseEntity<RestStripeSession> initPayment(@RequestBody List<RestCartItemRequest> items) {
    List<DetailCartItemRequest> detailCartItemRequests = RestCartItemRequest.to(items);
    try {
      StripeSessionId stripeSessionInformation = orderApplicationService.createOrder(detailCartItemRequests);
      RestStripeSession restStripeSession = RestStripeSession.from(stripeSessionInformation);
      return ResponseEntity.ok(restStripeSession);
    } catch (CartPaymentException cpe) {
      return ResponseEntity.badRequest().build();
    }
  }
}
