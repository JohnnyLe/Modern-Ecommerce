package com.nitsoft.ecommerce.api;

import com.nitsoft.ecommerce.api.response.APIStatus;
import com.nitsoft.ecommerce.api.response.StatusResponse;
import com.nitsoft.ecommerce.database.model.Category;
import com.nitsoft.ecommerce.repository.CategoryRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api(value = "category API")
public class CategoryAPI extends APIUtil {

    @Autowired
    CategoryRepository repository;

    @ApiOperation(value = "getCategory")
    @RequestMapping(value = APIName.CATEGORIES, method = RequestMethod.GET, produces = APIName.CHARSET)
    public String getCategories() {

        List<Category> categories = (List<Category>) repository.findAll();
        return writeObjectToJson(new StatusResponse<>(HttpStatus.OK.value(), categories));

    }

    @RequestMapping(value = APIName.CATEGORIES, method = RequestMethod.POST, produces = APIName.CHARSET)
    @ResponseBody
    public String addCatrgory(@RequestParam(name = "company_id", required = false) int companyId,
            @RequestParam(name = "parent_id", required = false) Long ParentID,
            @RequestParam(name = "name", required = true) String name,
            @RequestParam(name = "status", required = false) Integer Status) {
        Category category = new Category();
        category.setCompanyId(companyId);
        category.setParentId(ParentID);
        category.setName(name);
        category.setStatus(Status);

        repository.save(category);
        return writeObjectToJson(new StatusResponse<>(HttpStatus.OK.value(), category));

    }

    @RequestMapping(value = "api/test/{id}", method = RequestMethod.DELETE, produces = APIName.CHARSET)
    public String deleteCategory(@PathVariable(value = "id") Long categoryId) {
        System.out.println("category " + categoryId);
        Category category = repository.findByCategoryId(categoryId);
        if (category != null) {
            repository.delete(category);
            statusResponse = new StatusResponse(APIStatus.OK.getCode(), "delete account successfully");
        } else {
            statusResponse.setResult("not found");
        }

        return writeObjectToJson(statusResponse);

    }
}
