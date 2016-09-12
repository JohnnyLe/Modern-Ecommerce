package com.nitsoft.ecommerce.api.customer;

import com.nitsoft.ecommerce.api.APIName;
import com.nitsoft.ecommerce.api.APIUtil;
import com.nitsoft.ecommerce.api.response.APIStatus;
import com.nitsoft.ecommerce.api.response.StatusResponse;
import com.nitsoft.ecommerce.database.model.User;
import com.nitsoft.ecommerce.exception.ApplicationException;
import com.nitsoft.ecommerce.service.CustomerService;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAPI extends APIUtil {

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = APIName.USERS_ADD, method = RequestMethod.POST, produces = APIName.CHARSET)
    private String createUser(@RequestParam(value = "company_id") int company_Id, @RequestParam(value = "create_date") String create_Date,
            @RequestParam(value = "email") String Email, @RequestParam(value = "first_name") String first_Name,
            @RequestParam(value = "last_name") String last_Lame, @RequestParam(value = "middle_name") String middle_Name,
            @RequestParam(value = "password_hash") String password_Hash, @RequestParam(value = "role_id") int role_Id,
            @RequestParam(value = "salt") String Salt, @RequestParam(value = "status") int Status) throws IOException {

        Date createdate;

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        try {
            createdate = dateFormat.parse(create_Date);
        } catch (ParseException e) {
            throw new ApplicationException(APIStatus.INVALID_PARAMETER);
        }

        Date now = new Date();
        User users = new User();
        users.setCompanyId(company_Id);
        users.setCreateDate(createdate);
        users.setEmail(Email);
        users.setFirstName(first_Name);
        users.setLastName(last_Lame);
        users.setMiddleName(middle_Name);
        users.setPasswordHash(password_Hash);
        users.setRoleId(role_Id);
        users.setSalt(Salt);
        users.setStatus(Status);
        users.setUserId(Salt);
        
        customerService.save(users);
        statusResponse = new StatusResponse(APIStatus.OK.getCode(), "Create User successfully");
        return writeObjectToJson(statusResponse);

    }
}
