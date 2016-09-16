package com.nitsoft.ecommerce.api.product;

import com.nitsoft.ecommerce.api.APIName;
import com.nitsoft.ecommerce.api.APIUtil;
import com.nitsoft.ecommerce.api.response.APIStatus;
import com.nitsoft.ecommerce.api.response.StatusResponse;
import com.nitsoft.ecommerce.database.model.Product;
import com.nitsoft.ecommerce.database.model.ProductAttributeDetail;
import com.nitsoft.ecommerce.service.ProductAttributeDetailService;
import com.nitsoft.ecommerce.service.ProductService;
import com.nitsoft.util.Constant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
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
    @Autowired
    private ProductAttributeDetailService productAttributeService;

    @ApiOperation(value = "get product by company id", notes = "")
    @RequestMapping(method = RequestMethod.GET, produces = APIName.CHARSET)
    public String getAllProducts(
            @PathVariable("companyId") Long companyId,
            @RequestParam(required = false, defaultValue = Constant.DEFAULT_PAGE_NUMBER) Integer pageNumber,
            @RequestParam(required = false, defaultValue = Constant.DEFAULT_PAGE_SIZE) Integer pageSize) {

        Page<Product> products = productService.findByCompanyId(companyId, pageNumber, pageSize);
        statusResponse = new StatusResponse(APIStatus.OK.getCode(), products.getContent(), products.getTotalElements());

        return writeObjectToJson(statusResponse);
    }

    @RequestMapping(path = APIName.PRODUCT_BY_ID, method = RequestMethod.GET, produces = APIName.CHARSET)
    public String getProductById(
            @PathVariable Long companyId,
            @PathVariable Long productId) {

        // get product
        Product p = productService.findProductById(companyId, productId);
        // get all attributes of product
        ProductAttributeDetail pad = productAttributeService.findByProductIdAndAttributeId(productId, Constant.PRODUCT_ATTRIBUTE.DETAIL_IMAGES.getId());

        Map<String, Object> result = new HashMap();
        result.put("product", p);
        result.put("attributes", pad);
        statusResponse = new StatusResponse(APIStatus.OK.getCode(), result);

        return writeObjectToJson(statusResponse);
    }

    @ApiOperation(value = "get products by category id", notes = "")
    @RequestMapping(value = APIName.PRODUCTS_BY_CATEGORY, method = RequestMethod.GET, produces = APIName.CHARSET)
    public String getProductByCategoryId(
            @PathVariable("companyId") Long companyId,
            @RequestParam Long categoryId,
            @RequestParam(required = false, defaultValue = Constant.DEFAULT_PAGE_NUMBER) Integer pageNumber,
            @RequestParam(required = false, defaultValue = Constant.DEFAULT_PAGE_SIZE) Integer pageSize) {

        Page<Product> products = productService.findByCompanyIdAndCategoryId(companyId, categoryId, pageNumber, pageSize);
        return writeObjectToJson(new StatusResponse(APIStatus.OK.getCode(), products.getContent(), products.getTotalElements()));

    }

    @ApiOperation(value = "filter product list", notes = "")
    @RequestMapping(value = APIName.PRODUCTS_FILTER_LIST, method = RequestMethod.GET, produces = APIName.CHARSET)
    public String getProductFilterList(
            @PathVariable("companyId") Long companyId,
            @RequestParam(required = false, defaultValue = "-1") Long categoryId,
            @RequestParam(required = false, defaultValue = "-1") Long attributeId,
            @RequestParam(required = false) String searchKey,
            @RequestParam(required = false, defaultValue = "0") Double minPrice,
            @RequestParam(required = false, defaultValue = "-1") Double maxPrice,
            @RequestParam(required = false, defaultValue = "0") Integer minRank,
            @RequestParam(required = false, defaultValue = "-1") Integer maxRank,
            @RequestParam(required = false, defaultValue = "-1") Integer sortCase,
            @RequestParam(required = false, defaultValue = "1") Boolean ascSort,
            @RequestParam(required = false, defaultValue = Constant.DEFAULT_PAGE_NUMBER) Integer pageNumber,
            @RequestParam(required = false, defaultValue = Constant.DEFAULT_PAGE_SIZE) Integer pageSize) {

        Page<Product> products = productService.doFilterSearchSortPagingProduct(companyId, categoryId, attributeId, searchKey, minPrice, maxPrice, minRank, maxRank, sortCase, ascSort, pageSize, pageNumber);
        return writeObjectToJson(new StatusResponse(APIStatus.OK.getCode(), products.getContent(), products.getTotalElements()));

    }
}
