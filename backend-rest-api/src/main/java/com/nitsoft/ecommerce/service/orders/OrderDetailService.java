
package com.nitsoft.ecommerce.service.orders;

import com.nitsoft.ecommerce.database.model.OrderDetail;
import java.util.List;

/**
 *
 * @author Louis Duong
 */
public interface OrderDetailService {
    public OrderDetail saveOrUpdate(OrderDetail orderDetail);
    public List<OrderDetail> getListOrderDetail(Long orderId);
}
