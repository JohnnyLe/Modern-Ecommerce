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
import com.nitsoft.ecommerce.exception.ApplicationException;
import com.nitsoft.ecommerce.repository.OrdersRepository;
import io.swagger.annotations.Api;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@Api(value = "orders API")
public class OrdersAPI extends APIUtil {

    @Autowired
    OrdersRepository repository;
    @RequestMapping(value = APIName.ORDERS, method = RequestMethod.POST, produces = APIName.CHARSET)
    @ResponseBody
    public String addOrders(@RequestParam(name = "user_id", required = true) String userId,
            @RequestParam(name = "company_id", required = true) int companyId,
            @RequestParam(name = "created_at", required = true) String createdAt,
            @RequestParam(name = "updated_at", required = false) String updatedAt,
            @RequestParam(name = "is_active", required = false) Short isActive,
            @RequestParam(name = "is_virtual", required = false) Short isVirtual,
            @RequestParam(name = "is_multi_shipping", required = false) Short isMultiShipping,
            @RequestParam(name = "status", required = true) int status,
            @RequestParam(name = "items_count", required = false) Integer itemsCount,
            @RequestParam(name = "items_quantity", required = false) BigDecimal itemsQuantity,
            @RequestParam(name = "grand_total", required = false) BigDecimal grandTotal,
            @RequestParam(name = "base_grand_total", required = false) BigDecimal baseGrandTotal,
            @RequestParam(name = "checkout_comment", required = false) String checkoutComment,
            @RequestParam(name = "customer_email", required = false) String customerEmail,
            @RequestParam(name = "customer_prefix", required = false) String customerPrefix,
            @RequestParam(name = "customer_firstname", required = false) String customerFirstname,
            @RequestParam(name = "customer_middlename", required = false) String customerMiddlename,
            @RequestParam(name = "customer_lastname", required = false) String customerLastname,
            @RequestParam(name = "customer_suffix", required = false) String customerSuffix,
            @RequestParam(name = "customer_dob", required = false) String customerDob,
            @RequestParam(name = "customer_is_guest", required = false) Short customerIsGuest,
            @RequestParam(name = "remote_ip", required = false) String remoteIp,
            @RequestParam(name = "customer_gender", required = false) String customerGender,
            @RequestParam(name = "subtotal", required = false) BigDecimal subtotal,
            @RequestParam(name = "base_subtotal", required = false) BigDecimal baseSubtotal,
            @RequestParam(name = "is_changed", required = false) Integer isChanged){
        Date createday;
        Date updateday;
        Date customerbirthday;
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        try {
            createday = dateFormat.parse(createdAt);
            updateday = dateFormat.parse(updatedAt);
            customerbirthday = dateFormat.parse(customerDob);
        } catch (ParseException e) {
            throw new ApplicationException(APIStatus.INVALID_PARAMETER);
        }

        Orders orders = new Orders();
//        orders.setId(id);
        orders.setUserId(userId);
        orders.setCompanyId(companyId);
        orders.setCreatedAt(createday);
        orders.setUpdatedAt(updateday);
        orders.setIsActive(isActive);
        orders.setIsVirtual(isVirtual);
        orders.setIsMultiShipping(isMultiShipping);
        orders.setStatus(status);
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
        orders.setCustomerDob(customerbirthday);
        orders.setCustomerIsGuest(customerIsGuest);
        orders.setRemoteIp(remoteIp);
        orders.setCustomerGender(customerGender);
        orders.setSubtotal(subtotal);
        orders.setBaseSubtotal(baseSubtotal);
        orders.setIsChanged(isChanged);
        
        repository.save(orders);
        return writeObjectToJson(new StatusResponse<>(HttpStatus.OK.value(), orders));

    }
}

