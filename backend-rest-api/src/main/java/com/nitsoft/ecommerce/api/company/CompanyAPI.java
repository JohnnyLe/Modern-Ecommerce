package com.nitsoft.ecommerce.api.company;

import com.nitsoft.ecommerce.api.APIName;
import com.nitsoft.ecommerce.api.AbstractBaseAPI;
import com.nitsoft.ecommerce.api.response.model.StatusResponse;
import com.nitsoft.ecommerce.database.model.Company;
import com.nitsoft.ecommerce.service.CompanyService;
import java.util.List;
import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompanyAPI extends AbstractBaseAPI {

    @Autowired
    private CompanyService companyService;

    @RequestMapping(value = APIName.COMPANIES, method = RequestMethod.GET, produces = APIName.CHARSET)
    public String getAllCompanies() {
        List<Company> companies = (List<Company>) companyService.findAll();
        return writeObjectToJson(new StatusResponse(200, companies));
    }

    @RequestMapping(value = APIName.COMPANIES_SEARCH_BY_ID, method = RequestMethod.GET, produces = APIName.CHARSET)
    public String getCompanyById(@PathParam(value = "id") Integer companyId) {

        Company companies = companyService.findByCompanyId(companyId);

        return writeObjectToJson(new StatusResponse(200, companies));
    }
}
