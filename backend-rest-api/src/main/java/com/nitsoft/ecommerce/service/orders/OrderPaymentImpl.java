
package com.nitsoft.ecommerce.service.orders;

import com.nitsoft.ecommerce.database.model.OrderPayment;
import com.nitsoft.ecommerce.repository.OrderPaymentRepository;
import com.nitsoft.ecommerce.service.AbstractBaseService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Louis Duong
 */
@Component
public class OrderPaymentImpl extends AbstractBaseService implements OrderPaymentService {

    @Autowired
    OrderPaymentRepository orderPaymentRepository;

    @Override
    public List<OrderPayment> getOrderPaymentByOrderId(Long orderId) {
        return orderPaymentRepository.findOneByOrderId(orderId);
    }

}
