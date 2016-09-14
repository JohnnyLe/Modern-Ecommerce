/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.api.orders;

import com.nitsoft.ecommerce.api.APIName;
import com.nitsoft.ecommerce.api.APIUtil;
import com.nitsoft.ecommerce.api.response.APIStatus;
import com.nitsoft.ecommerce.api.response.StatusResponse;
import com.nitsoft.ecommerce.database.model.Orders;
import com.nitsoft.ecommerce.database.model.User;
import com.nitsoft.ecommerce.exception.ApplicationException;
import com.nitsoft.ecommerce.service.CustomerService;
import com.nitsoft.ecommerce.service.OrdersService;
import com.nitsoft.util.Constant;
import com.nitsoft.util.UniqueID;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.math.BigDecimal;
import java.util.Date;
import org.hibernate.validator.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author NHU LINH
 */
@RestController
@Api(value = "create orders API")
@RequestMapping(value = APIName.ORDERS)
public class OrdersAPI extends APIUtil {

    @Autowired
    OrdersService ordersService;
    @Autowired
    CustomerService customerService;

    @RequestMapping(method = RequestMethod.POST, produces = APIName.CHARSET)
    @ResponseBody
    public String addOrders(@RequestParam(name = "user_id", required = false) String userId,
            @RequestParam(name = "email", required = false) @Email String email,
            @RequestParam(name = "company_id", required = true) Long companyId,
            @RequestParam(name = "is_active", required = false) Short isActive,
            @RequestParam(name = "is_virtual", required = false) Short isVirtual,
            @RequestParam(name = "is_multi_shipping", required = false) Short isMultiShipping,
            @RequestParam(name = "status", required = true) int status,
            @RequestParam(name = "items_count", required = false) Integer itemsCount,
            @RequestParam(name = "items_quantity", required = false) BigDecimal itemsQuantity,
            @RequestParam(name = "grand_total", required = false) BigDecimal grandTotal,
            @RequestParam(name = "base_grand_total", required = false) BigDecimal baseGrandTotal,
            @RequestParam(name = "checkout_comment", required = false) String checkoutComment,
            @RequestParam(name = "customer_email", required = true) String customerEmail,
            @RequestParam(name = "customer_prefix", required = true) String customerPrefix,
            @RequestParam(name = "customer_firstname", required = true) String customerFirstname,
            @RequestParam(name = "customer_middlename", required = true) String customerMiddlename,
            @RequestParam(name = "customer_lastname", required = true) String customerLastname,
            @RequestParam(name = "customer_suffix", required = true) String customerSuffix,
            @RequestParam(name = "customer_dob", required = true) String customerDob,
            @RequestParam(name = "customer_is_guest", required = false) Short customerIsGuest,
            @RequestParam(name = "remote_ip", required = false) String remoteIp,
            @RequestParam(name = "customer_gender", required = false) String customerGender,
            @RequestParam(name = "subtotal", required = false) BigDecimal subtotal,
            @RequestParam(name = "base_subtotal", required = false) BigDecimal baseSubtotal,
            @RequestParam(name = "is_changed", required = false) Integer isChanged) {

        Date createDate = new Date();
//        Date customerbirthday;
//        Date create_date;

//        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
//        try {
//            createday = dateFormat.parse(new String createdAt);
//            updateday = dateFormat.parse(updatedAt);
//            customerbirthday = dateFormat.parse(customerDob);
//            create_date = dateFormat.parse(createDate);
//        } catch (ParseException e) {
//            throw new ApplicationException(APIStatus.INVALID_PARAMETER);
//        }
        Orders orders = new Orders();
        User users = new User();

        // validate user id
        if (userId == null || userId.equals("")) {
            // validate email
            if (email != null) {
                // create anonymous user
                users.setUserId(UniqueID.getUUID());
                users.setCompanyId(companyId);
                users.setCreateDate(createDate);
                users.setRoleId(Constant.USER_ROLE.ANONYMOUS_USER.getRoleId());
                users.setPasswordHash(UniqueID.getUUID());
                users.setFirstName("");
                users.setLastName("");
                users.setStatus(Constant.USER_STATUS.PENDING.getStatus());
                users.setSalt(UniqueID.getUUID());
                customerService.save(users);
            } else {
                throw new ApplicationException(APIStatus.INVALID_PARAMETER, new IllegalArgumentException("Email address is null"));
            }
        } else {

        }

        orders.setUserId(users.getUserId());// get userId vừa mới tạo và gắn vào order
        orders.setCompanyId(companyId);
//        orders.setCreatedAt(createday);
//        orders.setUpdatedAt(updateday);
        orders.setIsActive(isActive);
        orders.setIsVirtual(isVirtual);
        orders.setIsMultiShipping(isMultiShipping);
        orders.setStatus(Constant.ORDER_STATUS.PENDING.getStatus());
        orders.setItemsCount(itemsCount);
        orders.setItemsQuantity(itemsQuantity);
        orders.setGrandTotal(grandTotal);
        orders.setBaseGrandTotal(baseGrandTotal);
        orders.setCheckoutComment(checkoutComment);
        orders.setCustomerEmail(customerEmail);
        orders.setCustomerPrefix(customerPrefix);
        orders.setCustomerFirstname(customerFirstname);
        orders.setCustomerMiddlename(customerMiddlename);
        orders.setCustomerLastname(customerLastname);
        orders.setCustomerSuffix(customerSuffix);
//        orders.setCustomerDob(customerbirthday);
        orders.setCustomerIsGuest(customerIsGuest);
        orders.setRemoteIp(remoteIp);
        orders.setCustomerGender(customerGender);
        orders.setSubtotal(subtotal);
        orders.setBaseSubtotal(baseSubtotal);
        orders.setIsChanged(isChanged);

        ordersService.save(orders);
        return writeObjectToJson(new StatusResponse<>(HttpStatus.OK.value(), orders));
    }

    @ApiOperation(value = "get orders by company id", notes = "")
    @RequestMapping(value = APIName.ORDERS_BY_COMPANY, method = RequestMethod.GET, produces = APIName.CHARSET)
    public String getOrdersCompanyId(
            @PathVariable(value = "id") Long companyId,
            @RequestParam(defaultValue = Constant.DEFAULT_PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(defaultValue = Constant.DEFAULT_PAGE_SIZE, required = false) int pageSize) {

        //http://localhost:8080/api/orders/1?pagenumber=1&pagesize=2
        Page<Orders> orders = ordersService.findAllByCompanyId(companyId, pageNumber, pageSize);
        return writeObjectToJson(new StatusResponse(200, orders.getContent(), orders.getTotalElements()));

    }
}
