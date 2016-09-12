package com.nitsoft.ecommerce.api.rest.product;

import com.nitsoft.ecommerce.api.APIName;
import com.nitsoft.ecommerce.api.APIUtil;
import com.nitsoft.ecommerce.api.response.StatusResponse;
import com.nitsoft.ecommerce.database.model.Product;
import com.nitsoft.ecommerce.database.model.ProductAttributeDetail;
import com.nitsoft.ecommerce.service.ProductAttributeService;
import com.nitsoft.ecommerce.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "products")
public class ProductAPI extends APIUtil {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductAttributeService productAttributeService;

    @ApiOperation(value = "get all product", notes = "")
    @RequestMapping(value = APIName.PRODUCTS, method = RequestMethod.GET, produces = APIName.CHARSET)
    public String getAllProducts() {
        List<Product> products = (List<Product>) productService.findAllProduct();
        List<Object> results = new LinkedList<>();

        for (Product product : products) {
            List<ProductAttributeDetail> productDetais = (List<ProductAttributeDetail>) productAttributeService.findAllByProductId(product.getProductId());

            Map<String, Object> mapObj = new HashMap<>();
            mapObj.put("product", product);
            mapObj.put("attributes", productDetais);

            results.add(mapObj);
        }

        return writeObjectToJson(new StatusResponse(200, results));
    }

    @ApiOperation(value = "get products by category id", notes = "")
    @RequestMapping(value = APIName.PRODUCTS_BY_CATEGORY, method = RequestMethod.GET, produces = APIName.CHARSET)
    public String getProductByCategoryId(@RequestParam int categoryId) {

        List<Product> products = (List<Product>) productService.findAllByCategoryId(categoryId);

        return writeObjectToJson(new StatusResponse(200, products));
    }

    @RequestMapping(value = APIName.PRODUCTS_FILTER_LIST, method = RequestMethod.GET, produces = APIName.CHARSET)
    public String getProductFilterList(
            @RequestParam Long companyId,
            @RequestParam(required = false, defaultValue = "-1") Long categoryId,
            @RequestParam(required = false, defaultValue = "-1") Long attributeId,
            @RequestParam(required = false) String searchKey,
            @RequestParam(required = false, defaultValue = "0") Double minPrice,
            @RequestParam(required = false, defaultValue = "0") Double maxPrice,
            @RequestParam(required = false, defaultValue = "-1") Integer sortCase,
            @RequestParam(required = false, defaultValue = "1") Boolean ascSort,
            @RequestParam(required = false, defaultValue = "-1") Integer pageSize,
            @RequestParam(required = false, defaultValue = "-1") Integer pageNumber
    ) {

        List<Product> products = (List<Product>) productService.doFilterSearchSortPagingProduct(companyId, categoryId, attributeId, searchKey, minPrice, maxPrice, sortCase, ascSort, pageSize, pageNumber);

        return writeObjectToJson(new StatusResponse(200, products));
    }
}
