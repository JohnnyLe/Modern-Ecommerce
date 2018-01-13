
package com.nitsoft.ecommerce.service.orders;

import com.nitsoft.ecommerce.database.model.OrderAddress;

/**
 *
 * @author Louis Duong
 */
public interface OrderAddressService {
    public OrderAddress saveOrUpdate(OrderAddress orderAddress);
    public OrderAddress getOrderAddressByOrderId(Long orderId);
}
