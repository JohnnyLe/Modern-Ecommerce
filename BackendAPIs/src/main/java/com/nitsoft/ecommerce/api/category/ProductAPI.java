package com.nitsoft.ecommerce.api.category;

import com.nitsoft.ecommerce.api.APIName;
import com.nitsoft.ecommerce.api.APIUtil;
import com.nitsoft.ecommerce.api.response.StatusResponse;
import com.nitsoft.ecommerce.dao.ProductAtrributeDetaiImpl;
import com.nitsoft.ecommerce.dao.ProductImpl;
import com.nitsoft.ecommerce.model.Product;
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
public class ProductAPI extends APIUtil {

    @Autowired
    private ProductImpl productsImpl;
    @Autowired
    private ProductAtrributeDetaiImpl productAtrributeDetaiImpl;

    @RequestMapping(value = APIName.PRODUCTS, method = RequestMethod.GET, produces = APIName.CHARSET)
    public String getAllProducts() {
        List<Product> products = productsImpl.findAll(Product.class);
        List<Object> results = new LinkedList<>();

        for (Product product : products) {
            List productDetais = productAtrributeDetaiImpl.findByProductId(product.getProductId());

            Map<String, Object> mapObj = new HashMap<>();
            mapObj.put("product", product);
            mapObj.put("attributes", productDetais);

            results.add(mapObj);
        }

        return writeObjectToJson(new StatusResponse(200, results));
    }

    @RequestMapping(value = APIName.PRODUCTS_BY_CATEGORY, method = RequestMethod.GET, produces = APIName.CHARSET)
    public String getProductByCategoryId(@RequestParam int categoryId) {

        List<Product> products = productsImpl.findAllByCategoryId(categoryId);

        return writeObjectToJson(new StatusResponse(200, products));
    }
}
