
package com.nitsoft.ecommerce.service.orders;

import com.nitsoft.ecommerce.database.model.OrderPayment;
import java.util.List;

/**
 *
 * @author Louis Duong
 */
public interface OrderPaymentService {
    public OrderPayment getOrderPaymentByOrderId(Long orderId);
}
