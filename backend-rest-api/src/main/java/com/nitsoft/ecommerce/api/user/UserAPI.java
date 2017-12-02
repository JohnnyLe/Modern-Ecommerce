package com.nitsoft.ecommerce.api.user;

import com.nitsoft.ecommerce.api.APIName;
import com.nitsoft.ecommerce.api.AbstractBaseAPI;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(APIName.USERS)
public class UserAPI extends AbstractBaseAPI {

    @Autowired
    private UserService userService;
    @Autowired
    private UserTokenService userTokenService;

    @RequestMapping(path = APIName.USERS_REGISTER, method = RequestMethod.POST, produces = APIName.CHARSET)
    public String register(
            @PathVariable Long companyId,
            @RequestBody User user
    ) {

        // check user already exists
        User existed = userService.getUserByEmail(user.getEmail(), companyId);
        if (existed == null) {
            // email is valid to create user
            String email = user.getEmail(),
                    password = user.getPasswordHash();
            
            if (email != null && !email.equals("") && password != null && !password.equals("")) {

                Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(email);

                if (!matcher.matches() || password.length() < 6) {
                    statusResponse = new StatusResponse(APIStatus.ERR_INVALID_DATA);
                    return writeObjectToJson(statusResponse);
                }

                User userSignUp = new User();
                userSignUp.setUserId(UniqueID.getUUID());
                userSignUp.setCompanyId(companyId);
                userSignUp.setCreateDate(new Date());
                userSignUp.setEmail(email);
                userSignUp.setFirstName(user.getFirstName());
                userSignUp.setLastName(user.getLastName());
                userSignUp.setMiddleName(user.getMiddleName());
                userSignUp.setSalt(UniqueID.getUUID());

                try {
                    userSignUp.setPasswordHash(MD5Hash.MD5Encrypt(password + userSignUp.getSalt()));
                } catch (NoSuchAlgorithmException ex) {
                    throw new RuntimeException("Encrypt user password error", ex);
                }

                userSignUp.setRoleId(Constant.USER_ROLE.REGISTED_USER.getRoleId());
                userSignUp.setStatus(Constant.USER_STATUS.ACTIVE.getStatus());

                userService.save(userSignUp);
                // do send mail notify...
                statusResponse = new StatusResponse(APIStatus.OK.getCode(), userSignUp);
            } else {
                statusResponse = new StatusResponse(APIStatus.ERR_INVALID_DATA);
                return writeObjectToJson(statusResponse);
            }

        } else {
            // notify user already exists
            statusResponse = new StatusResponse(APIStatus.USER_ALREADY_EXIST);
        }

        return writeObjectToJson(statusResponse);
    }

    @RequestMapping(value = APIName.USERS_LOGIN, method = RequestMethod.POST, produces = APIName.CHARSET)
    public String login(
            @PathVariable Long companyId,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam Boolean keepMeLogin
    ) {

        if ("".equals(email) || "".equals(password)) {
            // invalid paramaters
            statusResponse = new StatusResponse(APIStatus.INVALID_PARAMETER);
        } else {
            User userLogin = userService.getUserByEmail(email, companyId);

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

                        Date expirationDate = keepMeLogin ? new Date(currentDate.getTime() + Constant.DEFAULT_REMEMBER_LOGIN_MILISECONDS) : new Date(currentDate.getTime() + Constant.DEFAULT_SESSION_TIME_OUT);
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
    public String logout(@PathVariable Long companyId, @RequestParam String token) {

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
