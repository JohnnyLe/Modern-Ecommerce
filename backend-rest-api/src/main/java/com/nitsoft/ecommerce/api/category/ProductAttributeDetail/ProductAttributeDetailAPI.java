/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.api.category.ProductAttributeDetail;

import com.nitsoft.ecommerce.api.APIName;
import com.nitsoft.ecommerce.api.APIUtil;
import com.nitsoft.ecommerce.api.response.StatusResponse;
import com.nitsoft.ecommerce.database.model.ProductAttributeDetail;
import com.nitsoft.ecommerce.repository.ProductAttributeDetailRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author NHU LINH
 */
@RestController
@Api(value = "ProductAttributeDetail API")

public class ProductAttributeDetailAPI extends APIUtil {

    @Autowired
    ProductAttributeDetailRepository repository;

    @ApiOperation(value = "getProductDetail")
    @RequestMapping(value = APIName.PRODUCT_DETAILS, method = RequestMethod.GET, produces = APIName.CHARSET)
    public String getProductDetail(@PathVariable(value = "product_id") Long productId) {
        
        List<ProductAttributeDetail> productdetails = (List<ProductAttributeDetail>) repository.findAllByProductId(productId);
        if (productdetails != null) {
            return writeObjectToJson(new StatusResponse(200, productdetails));   
        } else {
            statusResponse.setResult("not found");
        }

        return writeObjectToJson(statusResponse);
        
    }
}
