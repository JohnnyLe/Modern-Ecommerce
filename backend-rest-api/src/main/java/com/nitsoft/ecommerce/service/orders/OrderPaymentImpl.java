package com.nitsoft.ecommerce.service.orders;

import com.nitsoft.ecommerce.database.model.OrderPayment;
//import com.nitsoft.ecommerce.repository.OrderPaymentRepository;
import com.nitsoft.ecommerce.service.AbstractBaseService;
import org.springframework.stereotype.Component;

/**
 *
 * @author Louis Duong
 */
@Component
public class OrderPaymentImpl extends AbstractBaseService implements OrderPaymentService {

//    @Autowired
//    OrderPaymentRepository orderPaymentRepository;

    @Override
    public OrderPayment getOrderPaymentByOrderId(Long orderId) {
        try {
            return null;
//            return orderPaymentRepository.findOneByOrderIdAndStatus(orderId, Constant.STATUS.ACTIVE_STATUS.getValue());
        } catch (Exception ex) {
            System.out.println("error when get order payment : " + ex.getLocalizedMessage());
            return null;
        }
    }

}
