
package com.nitsoft.ecommerce.repository;

import com.nitsoft.ecommerce.database.model.OrderAddress;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author Louis Duong
 */
public interface OrderAddressRepository extends PagingAndSortingRepository<OrderAddress, Long> {
    OrderAddress findOneByOrderId(Long orderId);
}
