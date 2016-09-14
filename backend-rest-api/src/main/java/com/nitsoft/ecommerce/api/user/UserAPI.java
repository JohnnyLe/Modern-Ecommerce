package com.nitsoft.ecommerce.api.user;

import com.nitsoft.ecommerce.api.APIName;
import com.nitsoft.ecommerce.api.APIUtil;
import com.nitsoft.ecommerce.api.response.APIStatus;
import com.nitsoft.ecommerce.api.response.StatusResponse;
import com.nitsoft.ecommerce.database.model.User;
import com.nitsoft.ecommerce.database.model.UserToken;
import com.nitsoft.ecommerce.service.UserService;
import com.nitsoft.ecommerce.service.UserTokenService;
import com.nitsoft.util.Constant;
import com.nitsoft.util.DateUtil;
import com.nitsoft.util.MD5Hash;
import com.nitsoft.util.UniqueID;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(APIName.USERS)
public class UserAPI extends APIUtil {

    @Autowired
    private UserService userService;
    @Autowired
    private UserTokenService userTokenService;

    @RequestMapping(path = APIName.USERS_REGISTER, method = RequestMethod.POST, produces = APIName.CHARSET)
    public String register(
            @PathVariable Long companyId,
            @RequestParam String email,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam(required = false) String middleName,
            @RequestParam String password
    ) {

        // check user already exists
        User existed = userService.getUserByEmail(email, companyId);
        if (existed == null) {
            // email is valid to create user
            User user = new User();
            user.setUserId(UniqueID.getUUID());
            user.setCompanyId(companyId);
            user.setCreateDate(new Date());
            user.setEmail(email);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setMiddleName(middleName);
            user.setSalt(UniqueID.getUUID());

            try {
                user.setPasswordHash(MD5Hash.MD5Encrypt(password + user.getSalt()));
            } catch (NoSuchAlgorithmException ex) {
                throw new RuntimeException("Encrypt user password error", ex);
            }

            user.setRoleId(Constant.USER_ROLE.REGISTED_USER.getRoleId());
            user.setStatus(Constant.USER_STATUS.PENDING.getStatus());

            userService.save(user);
            // do send mail notify...

            statusResponse = new StatusResponse(APIStatus.OK.getCode(), user);
        } else {
            // notify user already exists
            statusResponse = new StatusResponse(APIStatus.USER_ALREADY_EXIST);
        }

        return writeObjectToJson(statusResponse);
    }

    @RequestMapping(value = APIName.USERS_LOGIN, method = RequestMethod.POST, produces = APIName.CHARSET)
    public String login(
            @PathVariable Long companyId,
            @RequestParam String emailAddress,
            @RequestParam String password,
            @RequestParam Boolean keepMeLogin
    ) {

        if ("".equals(emailAddress) || "".equals(password)) {
            // invalid paramaters
            statusResponse = new StatusResponse(APIStatus.INVALID_PARAMETER);
        } else {
            User userLogin = userService.getUserByEmail(emailAddress, companyId);

            if (userLogin != null) {
                String passwordHash = null;
                try {
                    passwordHash = MD5Hash.MD5Encrypt(password + userLogin.getSalt());
                } catch (NoSuchAlgorithmException ex) {
                    throw new RuntimeException("User login encrypt password error", ex);
                }

                int userStatus = userLogin.getStatus();
                if (userStatus == Constant.USER_STATUS.ACTIVE.getStatus()) {
                    if (passwordHash.equals(userLogin.getPasswordHash())) {
                        UserToken userToken = new UserToken();
                        userToken.setToken(UniqueID.getUUID());
                        userToken.setCompanyId(companyId);
                        userToken.setUserId(userLogin.getUserId());

                        Date currentDate = new Date();
                        userToken.setLoginDate(DateUtil.convertToUTC(currentDate));

                        Date expirationDate = keepMeLogin ? new Date(currentDate.getTime() + Constant.DEFAULT_REMEMBER_LOGIN_MILISECONDS) : new Date();
                        userToken.setExpirationDate(DateUtil.convertToUTC(expirationDate));

                        userTokenService.save(userToken);
                        statusResponse = new StatusResponse<>(HttpStatus.OK.value(), userToken);
                    } else {
                        // wrong password
                        statusResponse = new StatusResponse(APIStatus.ERR_USER_NOT_VALID);
                    }
                } else if (userStatus == Constant.USER_STATUS.PENDING.getStatus()) {
                    statusResponse = new StatusResponse(APIStatus.USER_PENDING_STATUS);
                } else {
                    statusResponse = new StatusResponse(APIStatus.ERR_USER_NOT_VALID);
                }
            } else {
                // can't find user by email address in database
                statusResponse = new StatusResponse(APIStatus.ERR_USER_NOT_EXIST);
            }
        }

        return writeObjectToJson(statusResponse);
    }

    @RequestMapping(value = APIName.USERS_LOGOUT, method = RequestMethod.POST, produces = APIName.CHARSET)
    public String logout(@PathVariable Long companyID, @RequestParam String token) {

        UserToken userToken = userTokenService.getTokenById(token);
        if (userToken != null) {
            userTokenService.invalidateToken(userToken);
            statusResponse = new StatusResponse(APIStatus.OK);
        } else {
            statusResponse = new StatusResponse(APIStatus.INVALID_TOKEN);
        }

        return writeObjectToJson(statusResponse);
    }

}
