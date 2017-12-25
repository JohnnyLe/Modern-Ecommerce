
package com.nitsoft.ecommerce.service.orders;

import com.nitsoft.ecommerce.database.model.OrderAddress;
import com.nitsoft.ecommerce.database.model.OrderDetail;
import java.util.List;

/**
 *
 * @author Louis Duong
 */
public interface OrderAddressService {
    public OrderAddress getOrderAddressByOrderId(Long orderId);
}
