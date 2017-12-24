package com.nitsoft.ecommerce.api.orders;

import com.nitsoft.ecommerce.api.APIName;
import com.nitsoft.ecommerce.api.controller.AbstractBaseController;
import com.nitsoft.ecommerce.api.request.model.AuthRequestModel;
import com.nitsoft.ecommerce.api.request.model.OrdersRequestModel;
import com.nitsoft.ecommerce.api.response.model.APIResponse;
import com.nitsoft.ecommerce.api.response.util.APIStatus;
import com.nitsoft.ecommerce.database.model.Orders;
import com.nitsoft.ecommerce.exception.ApplicationException;
import com.nitsoft.ecommerce.service.orders.OrderService;
import com.nitsoft.util.Constant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Louis Duong
 */
@RestController
@RequestMapping(APIName.ORDERS)
public class OrdersController extends AbstractBaseController {

    @Autowired
    OrderService orderService;

    /**
     * Get list orders by company have paging, search, sort and filter
     *
     * @param companyId
     * @param ordersRequestModel
     * @return
     */
    @RequestMapping(path = APIName.ORDERS_BY_COMPANY, method = RequestMethod.POST)
    public ResponseEntity<APIResponse> getPagingOrders(
            @PathVariable("company_id") Long companyId,
            @RequestBody OrdersRequestModel ordersRequestModel
    ) {
        try {
            Page<Orders> listOrders = orderService.doPagingOrders(ordersRequestModel,companyId);
            return responseUtil.successResponse(listOrders);
        } catch (Exception ex) {
            throw new ApplicationException(APIStatus.ERR_GET_LIST_ORDERS);
        }
    }

    /**
     * Get detail order by company
     *
     * @param companyId
     * @param orderId
     * @return
     */
    @RequestMapping(path = APIName.ORDERS_DETAIL_BY_COMPANY, method = RequestMethod.POST)
    public ResponseEntity<APIResponse> getDetailOrders(
            @PathVariable("company_id") Long companyId,
            @PathVariable("order_id") Long orderId
    ) {
        Map<String, Object> resultOrders = new HashMap<String, Object>();
        try {
            //

            return responseUtil.successResponse(resultOrders);
        } catch (Exception e) {
            throw new ApplicationException(APIStatus.ERR_GET_DETAIL_ORDERS);
        }
    }

    /**
     * Delete order by company
     *
     * @param companyId
     * @param orders
     * @return
     */
    @RequestMapping(path = APIName.DELETE_ORDERS_BY_COMPANY, method = RequestMethod.DELETE)
    public ResponseEntity<APIResponse> deleteOrders(
            @PathVariable("company_id") Long companyId,
            @RequestBody List<Orders> orders
    ) {
        try {
            // check param company , order
            if (companyId != null) {
                if (orders != null && !"".equals(orders)) {
                    for (Orders id : orders) {
                        // get order id by company id and status active
                        // check valid orderId
                        Orders order = orderService.getOrderByOrderIdAndCompanyID(id.getId(), companyId, Constant.STATUS.ACTIVE_STATUS.getValue());
                        if (order != null) {
                            // Delete order (update status = Inactive)
                            order.setStatus(Constant.STATUS.DELETED_STATUS.getValue());
                            orderService.updateStatusOrder(order);

                        } else {
                            throw new ApplicationException(APIStatus.ERR_ORDER_ID_NOT_FOUND);
                        }
                    }
                    return responseUtil.successResponse("Delete order succesfully");
                } else {
                    throw new ApplicationException(APIStatus.ERR_ORDER_ID_EMPTY);
                }
            } else {
                throw new ApplicationException(APIStatus.ERR_COMPANY_ID_EMPTY);
            }
        } catch (Exception e) {
            throw new ApplicationException(APIStatus.ERR_DELETE_ORDER);
        }

    }

}
