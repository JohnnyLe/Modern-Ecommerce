package com.nitsoft.ecommerce.service.orders;

import com.nitsoft.ecommerce.api.request.model.OrdersRequestModel;
import com.nitsoft.ecommerce.database.model.Orders;
import org.springframework.data.domain.Page;

/**
 *
 * @author Louis Duong
 */
public interface OrderService {

    public Page<Orders> doPagingOrders(OrdersRequestModel ordersRequestModel, Long companyId);

    public Orders getOrderByOrderIdAndCompanyID(Long orderId, Long companyId);

    public void updateStatusOrder(Orders order);
}
