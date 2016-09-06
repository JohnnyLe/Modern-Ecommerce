
package com.nitsoft.ecommerce.api.user;

import com.nitsoft.ecommerce.api.APIName;
import com.nitsoft.ecommerce.api.APIUtil;
import com.nitsoft.ecommerce.api.response.StatusResponse;
import com.nitsoft.ecommerce.dao.UserImpl;
import com.nitsoft.ecommerce.model.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class UserAPI extends APIUtil{
    @Autowired
    private UserImpl userDAO;
    
    @RequestMapping(value = APIName.GETUSER, method = RequestMethod.GET, produces = APIName.CHARSET)
    @ResponseBody
    public String getListUser(){
        
         List<User> users = userDAO.getUser();

         return writeObjectToJson(new StatusResponse<List<User>>(200, users));
        
    }

}
