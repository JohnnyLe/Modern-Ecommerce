
package com.nitsoft.ecommerce.repository;

import com.nitsoft.ecommerce.database.model.OrderDetail;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author Louis Duong
 */
public interface OrderDetailRepository extends PagingAndSortingRepository<OrderDetail, Long> {
    
    List<OrderDetail> findAllByOrderId(Long orderId);
}
