
package com.nitsoft.ecommerce.service.orders;

import com.nitsoft.ecommerce.database.model.OrderDetail;
import com.nitsoft.ecommerce.repository.OrderDetailRepository;
import com.nitsoft.ecommerce.service.AbstractBaseService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Louis Duong
 */
@Component
public class OrderDetailImpl extends AbstractBaseService implements OrderDetailService {

    @Autowired
    OrderDetailRepository orderDetailRepository;
    
    @Override
    public OrderDetail saveOrUpdate(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }
    
    @Override
    public List<OrderDetail> getListOrderDetail(Long orderId) {
        return orderDetailRepository.findAllByOrderId(orderId);
    }

    
    
}
