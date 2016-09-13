package com.nitsoft.ecommerce.api.product;

import com.nitsoft.ecommerce.api.APIName;
import com.nitsoft.ecommerce.api.APIUtil;
import com.nitsoft.ecommerce.api.response.StatusResponse;
import com.nitsoft.ecommerce.database.model.Product;
import com.nitsoft.ecommerce.service.ProductService;
import com.nitsoft.util.Constant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "products")
@RestController
@RequestMapping(APIName.PRODUCTS)
public class ProductAPI extends APIUtil {

    @Autowired
    private ProductService productService;

    @ApiOperation(value = "get product by company id", notes = "")
    @RequestMapping(method = RequestMethod.GET, produces = APIName.CHARSET)
    public String getAllProducts(
            @RequestParam Long companyId,
            @RequestParam(required = false, defaultValue = Constant.DEFAULT_PAGE_NUMBER) Integer pageNumber,
            @RequestParam(required = false, defaultValue = Constant.DEFAULT_PAGE_SIZE) Integer pageSize
    ) {
        
        Page<Product> products = productService.findByCompanyId(companyId, pageNumber, pageSize);
        return writeObjectToJson(new StatusResponse(HttpStatus.OK.value(), products.getContent(), products.getTotalElements()));
    
    }

    @ApiOperation(value = "get products by category id", notes = "")
    @RequestMapping(value = APIName.PRODUCTS_BY_CATEGORY, method = RequestMethod.GET, produces = APIName.CHARSET)
    public String getProductByCategoryId(
            @RequestParam Long companyId,
            @RequestParam Long categoryId,
            @RequestParam(required = false, defaultValue = Constant.DEFAULT_PAGE_NUMBER) Integer pageNumber,
            @RequestParam(required = false, defaultValue = Constant.DEFAULT_PAGE_SIZE) Integer pageSize
    ) {

        Page<Product> products = productService.findByCompanyIdAndCategoryId(companyId, categoryId, pageNumber, pageSize);
        return writeObjectToJson(new StatusResponse(HttpStatus.OK.value(), products.getContent(), products.getTotalElements()));

    }

    @ApiOperation(value = "filter product list", notes = "")
    @RequestMapping(value = APIName.PRODUCTS_FILTER_LIST, method = RequestMethod.GET, produces = APIName.CHARSET)
    public String getProductFilterList(
            @RequestParam Long companyId,
            @RequestParam(required = false, defaultValue = "-1") Long categoryId,
            @RequestParam(required = false, defaultValue = "-1") Long attributeId,
            @RequestParam(required = false) String searchKey,
            @RequestParam(required = false, defaultValue = "0") Double minPrice,
            @RequestParam(required = false, defaultValue = "-1") Double maxPrice,
            @RequestParam(required = false, defaultValue = "-1") Integer sortCase,
            @RequestParam(required = false, defaultValue = "1") Boolean ascSort,
            @RequestParam(required = false, defaultValue = Constant.DEFAULT_PAGE_NUMBER) Integer pageNumber,
            @RequestParam(required = false, defaultValue = Constant.DEFAULT_PAGE_SIZE) Integer pageSize
    ) {

        Page<Product> products = productService.doFilterSearchSortPagingProduct(companyId, categoryId, attributeId, searchKey, minPrice, maxPrice, sortCase, ascSort, pageSize, pageNumber);
        return writeObjectToJson(new StatusResponse(HttpStatus.OK.value(), products.getContent(), products.getTotalElements()));

    }
}
