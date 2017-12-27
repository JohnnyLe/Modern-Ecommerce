
package com.nitsoft.ecommerce.service.orders;

import com.nitsoft.ecommerce.api.request.model.OrdersRequestModel;
import com.nitsoft.ecommerce.database.model.Orders;
import com.nitsoft.ecommerce.repository.OrdersRepository;
import com.nitsoft.ecommerce.repository.specification.OrdersSpecification;
import com.nitsoft.ecommerce.service.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

/**
 *
 * @author Louis Duong
 */
@Component
public class OrderServiceImpl extends AbstractBaseService implements OrderService {

    @Autowired
    OrdersRepository ordersRepository;
    
    /*
    ** Get list paging ,search sort for paging order
    */
    @Override
    public Page<Orders> doPagingOrders(OrdersRequestModel ordersRequestModel,Long companyId) {
        Page<Orders> listOrders = ordersRepository.findAll(new OrdersSpecification(companyId,ordersRequestModel.getSearchKey(), ordersRequestModel.getSortCase(), ordersRequestModel.isAscSort(),ordersRequestModel.getStatus()), new PageRequest(ordersRequestModel.getPageNumber(), ordersRequestModel.getPageSize()));
        return listOrders;
    }

    @Override
    public Orders getOrderByOrderIdAndCompanyID(Long orderId, Long companyId) {
        return ordersRepository.findOneByIdAndCompanyId(orderId, companyId);
    }

    @Override
    public void updateStatusOrder(Orders order) {
        ordersRepository.save(order);
    }

    
}
