package com.nitsoft.ecommerce.api.product;

import com.nitsoft.ecommerce.api.APIName;
import com.nitsoft.ecommerce.api.controller.AbstractBaseController;
import com.nitsoft.ecommerce.api.request.model.CreateProductModel;
import com.nitsoft.ecommerce.api.request.model.ListProductModel;
import com.nitsoft.ecommerce.api.request.model.ProductCompanyModel;
import com.nitsoft.ecommerce.api.response.model.APIResponse;
import com.nitsoft.ecommerce.api.response.model.PagingResponseModel;
import com.nitsoft.ecommerce.api.response.util.APIStatus;
import com.nitsoft.ecommerce.database.model.Category;
import com.nitsoft.ecommerce.database.model.Product;
import com.nitsoft.ecommerce.database.model.ProductAttributeDetail;
import com.nitsoft.ecommerce.database.model.ProductCategory;
import com.nitsoft.ecommerce.database.model.ProductCategoryId;
import com.nitsoft.ecommerce.exception.ApplicationException;
import com.nitsoft.ecommerce.repository.CategoryRepository;
import com.nitsoft.ecommerce.repository.ProductCategoryRepository;
import com.nitsoft.ecommerce.service.ProductAttributeDetailService;
import com.nitsoft.ecommerce.service.product.ProductServiceImpl;
import com.nitsoft.util.Constant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "products")
@RestController
@RequestMapping(APIName.PRODUCTS)
public class ProductAPI extends AbstractBaseController {

    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private ProductAttributeDetailService productAttributeService;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @ApiOperation(value = "get product by company id", notes = "")
    @RequestMapping(method = RequestMethod.GET, produces = APIName.CHARSET)
    public ResponseEntity<APIResponse> getAllProducts(
            @PathVariable("companyId") Long companyId,
            @RequestParam(required = false, defaultValue = Constant.DEFAULT_PAGE_NUMBER) Integer pageNumber,
            @RequestParam(required = false, defaultValue = Constant.DEFAULT_PAGE_SIZE) Integer pageSize) {

        Page<Product> products = productService.getByCompanyId(companyId, pageNumber, pageSize);
//        statusResponse = new StatusResponse(APIStatus.OK.getCode(), products.getContent(), products.getTotalElements());

        return responseUtil.successResponse(products.getContent());
    }

    @ApiOperation(value = "get products by product id", notes = "")
    @RequestMapping(path = APIName.PRODUCT_BY_ID, method = RequestMethod.GET, produces = APIName.CHARSET)
    public ResponseEntity<APIResponse> getProductById(HttpServletRequest request,
            @PathVariable Long product_id,
            @PathVariable Long company_id) {
        System.out.println("id company : " + company_id.toString());
        // get product
        Product p = productService.getProductById(company_id, product_id);
        if (p != null) {
            // get all attributes of product
//        ProductAttributeDetail pad = productAttributeService.findByProductIdAndAttributeId(product_Id, Constant.PRODUCT_ATTRIBUTE.DETAIL_IMAGES.getId());
            List<ProductCategory> listProductCate = productCategoryRepository.getProCateByProductId(product_id);
            List<Map<String, Object>> listCate = new ArrayList<Map<String, Object>>();
            for (ProductCategory result : listProductCate) {
                Map<String, Object> category = new HashMap();
                //find category name with categoryId
                Category cate = categoryRepository.findByCategoryId(result.getId().getCategoryId());
                if (cate != null) {
                    category.put("text", cate.getName());
                    category.put("id", cate.getCategoryId());
                }
                //add category name to list String
                listCate.add(category);
            }
            Map<String, Object> result = new HashMap();
            result.put("product", p);
            result.put("list_category", listCate);

            return responseUtil.successResponse(result);
        } else {
            throw new ApplicationException(APIStatus.GET_PRODUCT_ERROR);
        }
    }

    @ApiOperation(value = "get list product by product ids", notes = "")
    @RequestMapping(path = APIName.PRODUCT_BY_IDS, method = RequestMethod.POST, produces = APIName.CHARSET)
    public ResponseEntity<APIResponse> getListProductByIds(
            @PathVariable Long companyId,
            @RequestBody List<Long> productIds) {

        if (productIds != null && !productIds.isEmpty()) {
            List<Product> products = (List<Product>) productService.getProductsById(companyId, productIds);
            if (products != null) {
//                statusResponse = new StatusResponse(APIStatus.OK.getCode(), products, products.size());
                return responseUtil.successResponse(products);
            } else {
                throw new ApplicationException(APIStatus.INVALID_PARAMETER);
            }
        } else {
            throw new ApplicationException(APIStatus.INVALID_PARAMETER);
        }

//        return writeObjectToJson(statusResponse);
    }

    @ApiOperation(value = "get products by category id", notes = "")
    @RequestMapping(value = APIName.PRODUCTS_BY_CATEGORY, method = RequestMethod.GET, produces = APIName.CHARSET)
    public ResponseEntity<APIResponse> getProductByCategoryId(
            @PathVariable("companyId") Long companyId,
            @RequestParam Long categoryId,
            @RequestParam(required = false, defaultValue = Constant.DEFAULT_PAGE_NUMBER) Integer pageNumber,
            @RequestParam(required = false, defaultValue = Constant.DEFAULT_PAGE_SIZE) Integer pageSize) {

        Page<Product> products = productService.getByCompanyIdAndCategoryId(companyId, categoryId, pageNumber, pageSize);
//        return writeObjectToJson(new StatusResponse(APIStatus.OK.getCode(), products.getContent(), products.getTotalElements()));
        return responseUtil.successResponse(products.getContent());
    }

    @ApiOperation(value = "filter product list", notes = "")
    @RequestMapping(value = APIName.PRODUCTS_FILTER_LIST, method = RequestMethod.POST, produces = APIName.CHARSET)
    public ResponseEntity<APIResponse> getProductFilterList(HttpServletRequest request,
            @RequestBody ListProductModel listProduct) {
        try {
            Page<Product> products = productService.doFilterSearchSortPagingProduct(listProduct.getCompanyId(), listProduct.getCategoryId(), listProduct.getAttributeId(), listProduct.getSearchKey(), listProduct.getMinPrice(), listProduct.getMaxPrice(), listProduct.getMinRank(), listProduct.getMaxRank(), listProduct.getSortCase(), listProduct.getAscSort(), listProduct.getPageSize(), listProduct.getPageNumber());
            PagingResponseModel finalRes = new PagingResponseModel(products.getContent(), products.getTotalElements(), products.getTotalPages(), products.getNumber());
            return responseUtil.successResponse(finalRes);
        } catch (Exception ex) {
            throw new ApplicationException(APIStatus.GET_LIST_PRODUCT_ERROR);
        }
    }

    @ApiOperation(value = "create product", notes = "")
    @RequestMapping(value = APIName.PRODUCT_CREATE, method = RequestMethod.POST, produces = APIName.CHARSET)
    public ResponseEntity<APIResponse> createProduct(HttpServletRequest request,
            @RequestBody CreateProductModel productRequest) {
        try {
            Product product = new Product();
            product.setBrowsingName(productRequest.getBrowsingName());
            product.setCompanyId(productRequest.getCompanyId());
            product.setCreatedOn(new Date());
            product.setDefaultImage(productRequest.getDefaultImage());
            product.setDescription(productRequest.getDescription());
            product.setIsStockControlled(productRequest.getIsStockControlled());
            product.setListPrice(productRequest.getListPrice());
            product.setName(productRequest.getName());
            product.setOverview(productRequest.getOverview());
            product.setQuantity(productRequest.getQuantity());
            product.setRank(productRequest.getRank());
            product.setSalePrice(productRequest.getSalePrice());
            product.setSku(productRequest.getSku());
            product.setStatus(Constant.STATUS.ACTIVE_STATUS.getValue());
            product.setUpdatedOn(new Date());
            //create product
            productService.save(product);
            //create product categories
            for (Long categoriesId : productRequest.getListCategoriesId()) {
                ProductCategoryId productCategoryId = new ProductCategoryId();
                ProductCategory productCategory = new ProductCategory();
                productCategoryId.setCategoryId(categoriesId);
                productCategoryId.setProductId(product.getProductId());
                productCategory.setId(productCategoryId);
                System.out.println("id product : " + product.getProductId().toString());
                productCategoryRepository.save(productCategory);
//                productService.saveProductCategory(productCategory);
            }
            return responseUtil.successResponse(product);
        } catch (Exception ex) {
            throw new ApplicationException(APIStatus.CREATE_PRODUCT_ERROR);
        }
    }

    @ApiOperation(value = "delete product list", notes = "")
    @RequestMapping(value = APIName.PRODUCTS_DELETE, method = RequestMethod.POST, produces = APIName.CHARSET)
    public ResponseEntity<APIResponse> deleteProduct(HttpServletRequest request,
            @RequestBody List<Long> ids,
            @PathVariable Long company_id) {
        try {
            for (Long id : ids) {
                Product product = productService.getProductById(company_id, id);
                if (product != null) {
//                //update status
                    product.setStatus(Constant.STATUS.DELETED_STATUS.getValue());
                    productService.update(product);
                    List<ProductCategory> listProductCate = productCategoryRepository.getProCateByProductId(id);
                    for (ProductCategory result : listProductCate) {
                        //delete product category
                        productCategoryRepository.delete(result);
                    }
                }
            }
            return responseUtil.successResponse(null);
        } catch (Exception ex) {
            throw new ApplicationException(APIStatus.DELETE_PRODUCT_ERROR);
        }
    }

    @ApiOperation(value = "update product", notes = "")
    @RequestMapping(value = APIName.PRODUCTS_UPDATE, method = RequestMethod.POST, produces = APIName.CHARSET)
    public ResponseEntity<APIResponse> updateProduct(HttpServletRequest request,
            @RequestBody CreateProductModel productRequest) {
        try {
            Product product = productService.getProductById(productRequest.getCompanyId(), productRequest.getProductId());
            if (product != null) {
                product.setBrowsingName(productRequest.getBrowsingName());
                product.setDefaultImage(productRequest.getDefaultImage());
                product.setDescription(productRequest.getDescription());
                product.setIsStockControlled(productRequest.getIsStockControlled());
                product.setListPrice(productRequest.getListPrice());
                product.setName(productRequest.getName());
                product.setOverview(productRequest.getOverview());
                product.setQuantity(productRequest.getQuantity());
                product.setRank(productRequest.getRank());
                product.setSalePrice(productRequest.getSalePrice());
                product.setSku(productRequest.getSku());
                product.setUpdatedOn(new Date());
                //update product
                productService.update(product);
                //delete old list product category
                List<ProductCategory> listProductCate = productCategoryRepository.getProCateByProductId(productRequest.getProductId());
                for (ProductCategory result : listProductCate) {
                    //delete product category
                    productCategoryRepository.delete(result);
                }
                //create new list product categories
                for (Long categoriesId : productRequest.getListCategoriesId()) {
                    ProductCategoryId productCategoryId = new ProductCategoryId();
                    productCategoryId.setCategoryId(categoriesId);
                    productCategoryId.setProductId(product.getProductId());
                    ProductCategory productCategory = new ProductCategory();
                    productCategory.setId(productCategoryId);
                    productCategoryRepository.save(productCategory);
                }
                return responseUtil.successResponse(product);
            } else {
                throw new ApplicationException(APIStatus.GET_PRODUCT_ERROR);
            }

        } catch (Exception ex) {
            throw new ApplicationException(APIStatus.UPDATE_PRODUCT_ERROR);
        }
    }
}
