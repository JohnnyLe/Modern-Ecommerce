
package com.nitsoft.ecommerce.repository;

import com.nitsoft.ecommerce.database.model.OrderPayment;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author Louis Duong
 */
public interface OrderPaymentRepository extends PagingAndSortingRepository<OrderPayment, Long> {
    List<OrderPayment> findOneByOrderId(Long orderId);
}
