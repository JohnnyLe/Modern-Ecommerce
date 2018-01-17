package com.nitsoft.ecommerce.api.orders;

import com.nitsoft.ecommerce.api.APIName;
import com.nitsoft.ecommerce.api.controller.AbstractBaseController;
import com.nitsoft.ecommerce.api.request.model.OrdersRequestModel;
import com.nitsoft.ecommerce.api.response.model.APIResponse;
import com.nitsoft.ecommerce.api.response.util.APIStatus;
import com.nitsoft.ecommerce.database.model.OrderAddress;
import com.nitsoft.ecommerce.database.model.OrderDetail;
import com.nitsoft.ecommerce.database.model.OrderPayment;
import com.nitsoft.ecommerce.database.model.Orders;
import com.nitsoft.ecommerce.database.model.Payment;
import com.nitsoft.ecommerce.database.model.Product;
import com.nitsoft.ecommerce.database.model.UserAddress;
import com.nitsoft.ecommerce.exception.ApplicationException;
import com.nitsoft.ecommerce.repository.PaymentRepository;
import com.nitsoft.ecommerce.repository.UserAddressRepository;
import com.nitsoft.ecommerce.service.orders.OrderAddressService;
import com.nitsoft.ecommerce.service.orders.OrderDetailService;
import com.nitsoft.ecommerce.service.orders.OrderPaymentService;
import com.nitsoft.ecommerce.service.orders.OrderService;
import com.nitsoft.ecommerce.service.product.ProductService;
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

    @Autowired
    OrderDetailService orderDetailService;

    @Autowired
    OrderAddressService orderAddresslService;

    @Autowired
    OrderPaymentService orderPaymentService;

    @Autowired
    ProductService productService;

    @Autowired
    UserAddressRepository userAddressRepository;

    @Autowired
    PaymentRepository paymentRepository;

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
            Page<Orders> listOrders = orderService.doPagingOrders(ordersRequestModel, companyId);
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
    @RequestMapping(path = APIName.ORDERS_DETAIL_BY_COMPANY, method = RequestMethod.GET)
    public ResponseEntity<APIResponse> getDetailOrders(
            @PathVariable("company_id") Long companyId,
            @PathVariable("order_id") Long orderId
    ) {
        Map<String, Object> resultOrders = new HashMap<String, Object>();
        try {
            //get order by id
            Orders order = orderService.getOrderByOrderIdAndCompanyID(orderId, companyId);
            if (order != null) {
                resultOrders.put("orders", order);

                // get list order detail by order id
                List<OrderDetail> orderDetailByOrderId = orderDetailService.getListOrderDetail(orderId);
                List<Map<String, Object>> listOrdersDetail = new ArrayList<Map<String, Object>>();
                if (orderDetailByOrderId != null && !orderDetailByOrderId.isEmpty()) {
                    for (OrderDetail orderDetail : orderDetailByOrderId) {
                        Map<String, Object> detail = new HashMap<String, Object>();
                        //find product by proId
                        Product product = productService.getProductById(companyId, orderDetail.getProductId());
                        Payment payment = paymentRepository.findByPaymentId(order.getPaymentId());
                        if (product != null && payment != null) {
                            detail.put("product", product);
                            detail.put("payment", payment);
                            detail.put("ordersDetail", orderDetail);
                            listOrdersDetail.add(detail);
                        }
                    }
                    resultOrders.put("listOrdersDetail", listOrdersDetail);
                }

                // get order address by order id
//                OrderAddress orderAddress = orderAddresslService.getOrderAddressByOrderId(orderId);
//                if (orderAddress != null) {
                //get user address
                UserAddress userAddress = userAddressRepository.findByAdressIdAndStatus(order.getAdressId(), Constant.STATUS.ACTIVE_STATUS.getValue());
                resultOrders.put("orderAddress", userAddress);
//                }
                // get list order payment by order id
//                OrderPayment orderPayment = orderPaymentService.getOrderPaymentByOrderId(orderId);
//                if (orderPayment != null) {
//                    System.out.println("error get orders detail 5");
//                    Payment payMent = paymentRepository.findByPaymentId(orderPayment.getPaymentId());
//                    resultOrders.put("orderPayment", payMent);
//                }
            }

            return responseUtil.successResponse(resultOrders);
        } catch (Exception e) {
            System.out.println("error get orders detail" + e.getMessage());
            throw new ApplicationException(APIStatus.ERR_GET_DETAIL_ORDERS);
        }
    }

    /**
     * Delete order by company
     *
     * @param companyId
     * @param orderId
     * @param status
     * @param orders
     * @return
     */
    @RequestMapping(path = APIName.CHANGE_STATUS_ORDERS_BY_COMPANY, method = RequestMethod.GET)
    public ResponseEntity<APIResponse> changeOrders(
            @PathVariable("company_id") Long companyId,
            @PathVariable("order_id") Long orderId,
            @PathVariable("status") int status
    ) {
        try {
            // check param company , order
            if (companyId != null) {
                if (orderId != null) {
                    // get order id by company id and status active
                    // check valid orderId
                    Orders order = orderService.getOrderByOrderIdAndCompanyID(orderId, companyId);
                    if (order != null) {
                        // Update status order (update status = completed)
                        switch (status) {
                            case 0:
                                order.setStatus(Constant.ORDER_STATUS.PENDING.getStatus());
                                break;
                            case 1:
                                order.setStatus(Constant.ORDER_STATUS.SHIPPING.getStatus());
                                break;
                            case 2:
                                order.setStatus(Constant.ORDER_STATUS.COMPLETED.getStatus());
                                break;
                            default:
                                order.setStatus(Constant.ORDER_STATUS.PENDING.getStatus());
                                break;
                        }
                        orderService.updateStatusOrder(order);

                    } else {
                        throw new ApplicationException(APIStatus.ERR_ORDER_ID_NOT_FOUND);
                    }
                    return responseUtil.successResponse("Change status order succesfully");
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
