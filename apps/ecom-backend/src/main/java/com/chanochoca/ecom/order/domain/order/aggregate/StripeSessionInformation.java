package com.chanochoca.ecom.order.domain.order.aggregate;

import com.chanochoca.ecom.order.domain.order.vo.StripeSessionId;
import com.chanochoca.ecom.order.domain.user.vo.UserAddressToUpdate;
import org.jilt.Builder;

import java.util.List;

@Builder
public record StripeSessionInformation(StripeSessionId stripeSessionId,
                                       UserAddressToUpdate userAddress,
                                       List<OrderProductQuantity> orderProductQuantity) {
}
