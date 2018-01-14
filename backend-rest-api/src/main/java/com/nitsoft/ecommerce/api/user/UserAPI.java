package com.nitsoft.ecommerce.api.user;

import com.nitsoft.ecommerce.api.APIName;
import com.nitsoft.ecommerce.api.controller.AbstractBaseController;
import com.nitsoft.ecommerce.api.request.model.AuthRequestModel;
import com.nitsoft.ecommerce.api.request.model.UserChangePasswordModel;
import com.nitsoft.ecommerce.api.request.model.UserListRequestModel;
import com.nitsoft.ecommerce.api.request.model.UserRequestModel;
import com.nitsoft.ecommerce.api.response.model.APIResponse;
import com.nitsoft.ecommerce.api.response.model.PagingResponseModel;
import com.nitsoft.ecommerce.api.response.model.UserDetailResponseModel;
import com.nitsoft.ecommerce.api.response.util.APIStatus;
import com.nitsoft.ecommerce.database.model.User;
import com.nitsoft.ecommerce.database.model.UserAddress;
import com.nitsoft.ecommerce.database.model.UserToken;
import com.nitsoft.ecommerce.exception.ApplicationException;
import com.nitsoft.ecommerce.service.UserAddressService;
import com.nitsoft.ecommerce.service.UserService;
import com.nitsoft.ecommerce.service.UserTokenService;
import com.nitsoft.ecommerce.service.auth.AuthService;
import com.nitsoft.util.Constant;
import com.nitsoft.util.DateUtil;
import com.nitsoft.util.MD5Hash;
import com.nitsoft.util.UniqueID;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(APIName.USERS)
public class UserAPI extends AbstractBaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserTokenService userTokenService;
    @Autowired
    private UserAddressService userAddressService;
    @Autowired
    private AuthService authService;

    @RequestMapping(value = APIName.USERS_LOGIN, method = RequestMethod.POST, produces = APIName.CHARSET)
    public ResponseEntity<APIResponse> login(
            @PathVariable Long company_id,
            @RequestBody AuthRequestModel authRequestModel
    ) {

        if ("".equals(authRequestModel.getUsername()) || "".equals(authRequestModel.getPassword())) {
            // invalid paramaters
            throw new ApplicationException(APIStatus.INVALID_PARAMETER);
        } else {
            User userLogin = userService.getUserByEmail(authRequestModel.getUsername(), company_id, Constant.USER_STATUS.ACTIVE.getStatus());

            if (userLogin != null) {
                String passwordHash = null;
                try {
                    passwordHash = MD5Hash.MD5Encrypt(authRequestModel.getPassword() + userLogin.getSalt());
                } catch (NoSuchAlgorithmException ex) {
                    throw new RuntimeException("User login encrypt password error", ex);
                }

                if (passwordHash.equals(userLogin.getPasswordHash())) {
                    UserToken userToken = authService.createUserToken(userLogin, authRequestModel.isKeepMeLogin());
                    // Create Auth User -> Set to filter config
                    // Perform the security
                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            userLogin.getEmail(),
                            userLogin.getPasswordHash()
                    );
                    //final Authentication authentication = authenticationManager.authenticate();
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    return responseUtil.successResponse(userToken.getToken());
                } else {
                    // wrong password
                    throw new ApplicationException(APIStatus.ERR_USER_NOT_VALID);
                }

            } else {
                // can't find user by email address in database
                throw new ApplicationException(APIStatus.ERR_USER_NOT_EXIST);
            }
        }
    }

    @RequestMapping(value = APIName.USERS_LOGOUT, method = RequestMethod.POST, produces = APIName.CHARSET)
    public ResponseEntity<APIResponse> logout(
            @PathVariable Long company_id,
            HttpServletRequest request) {
        
        String authToken = request.getHeader(Constant.HEADER_TOKEN);
        UserToken userToken = userTokenService.getTokenById(authToken);
        if (userToken != null) {
            userTokenService.invalidateToken(userToken);
            return responseUtil.successResponse(APIStatus.OK);
        } else {
            throw new ApplicationException(APIStatus.ERR_UNAUTHORIZED);
        }

    }

    @RequestMapping(path = APIName.USER_REGISTER, method = RequestMethod.POST, produces = APIName.CHARSET)
    public ResponseEntity<APIResponse> register(
            @PathVariable Long company_id,
            @RequestBody UserRequestModel user
    ) {
        // check user already exists
        User existedUser = userService.getUserByEmail(user.getEmail(), company_id, Constant.STATUS.ACTIVE_STATUS.getValue());
        if (existedUser == null) {
            // email is valid to create user
            String email = user.getEmail();

            if (email != null && !email.equals("")) {

                Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(email);

                if (!matcher.matches()) {
                    throw new ApplicationException(APIStatus.ERR_INVALID_DATA);
                }

                User userSignUp = new User();
                userSignUp.setUserId(UniqueID.getUUID());
                userSignUp.setCompanyId(company_id);
                userSignUp.setCreateDate(new Date());
                userSignUp.setEmail(email);
                userSignUp.setFirstName(user.getFirstName());
                userSignUp.setLastName(user.getLastName());
                userSignUp.setMiddleName(user.getMiddleName());
                userSignUp.setSalt(UniqueID.getUUID());
                userSignUp.setRoleId(Constant.USER_ROLE.NORMAL_USER.getRoleId());
                try {
//                    String generatedString = RandomStringUtils.randomAlphabetic(6);
                    String generatedString = "123456";
                    String password = MD5Hash.MD5Encrypt(generatedString);
                    userSignUp.setPasswordHash(MD5Hash.MD5Encrypt(password + userSignUp.getSalt()));
                } catch (NoSuchAlgorithmException ex) {
                    throw new RuntimeException("Encrypt user password error", ex);
                }

                userSignUp.setRoleId(Constant.USER_ROLE.NORMAL_USER.getRoleId());
                userSignUp.setStatus(Constant.USER_STATUS.ACTIVE.getStatus());

                userService.save(userSignUp);

                UserAddress userAddress = new UserAddress();
                userAddress.setUserId(userSignUp.getUserId());
                userAddress.setAdress(user.getAddress());
                userAddress.setCity(user.getCity());
                userAddress.setCountry(user.getCountry());
                userAddress.setFax(user.getFax());
                userAddress.setPhone(user.getPhone());
                userAddress.setStatus(Constant.STATUS.ACTIVE_STATUS.getValue());
                userAddressService.save(userAddress);
                // do send mail notify...
                return responseUtil.successResponse(userSignUp);
            } else {
                throw new ApplicationException(APIStatus.ERR_INVALID_DATA);
            }

        } else {
            // notify user already exists
            throw new ApplicationException(APIStatus.USER_ALREADY_EXIST);
        }

    }

    @RequestMapping(value = APIName.USER_LIST, method = RequestMethod.POST, produces = APIName.CHARSET)
    public ResponseEntity<APIResponse> getUserList(
            HttpServletRequest httpRequest,
            @PathVariable Long company_id,
            @RequestBody UserListRequestModel request) {

        try {
            String userId = getAuthUserFromSession(httpRequest).getId();
            Page<User> users = userService.doFilterSearchSortPagingUser(userId, company_id, request.getSearchKey(), request.getSortCase(), request.getAscSort(), request.getPageSize(), request.getPageNumber());
            PagingResponseModel finalRes = new PagingResponseModel(users.getContent(), users.getTotalElements(), users.getTotalPages(), users.getNumber());
            return responseUtil.successResponse(finalRes);
        } catch (Exception ex) {
            throw new ApplicationException(APIStatus.ERR_GET_LIST_USERS);
        }

    }

    @RequestMapping(path = APIName.USER_DETAILS, method = RequestMethod.GET, produces = APIName.CHARSET)
    public ResponseEntity<APIResponse> getUserDetails(
            @PathVariable Long company_id,
            @PathVariable String userId
    ) {

        // check user already exists
        User existedUser = userService.getUserByUserIdAndComIdAndStatus(userId, company_id, Constant.USER_STATUS.ACTIVE.getStatus());
        if (existedUser != null) {
            UserAddress userAddress = userAddressService.getAddressByUserIdAndStatus(userId, Constant.STATUS.ACTIVE_STATUS.getValue());
            if (userAddress != null) {
                UserDetailResponseModel response = new UserDetailResponseModel();
                response.setUserId(userId);
                response.setCompanyId(existedUser.getCompanyId());
                response.setRoleId(existedUser.getRoleId());
                response.setFirstName(existedUser.getFirstName());
                response.setLastName(existedUser.getLastName());
                response.setMiddleName(existedUser.getMiddleName());
                response.setEmail(existedUser.getEmail());
                response.setCreateDate(existedUser.getCreateDate());
                response.setSalt(existedUser.getSalt());
                response.setPhone(userAddress.getPhone());
                response.setFax(userAddress.getFax());
                response.setAddress(userAddress.getAdress());
                response.setCity(userAddress.getCity());
                response.setCountry(userAddress.getCountry());
                return responseUtil.successResponse(response);
            } else {
                // notify user already exists
                throw new ApplicationException(APIStatus.ERR_USER_NOT_FOUND);
            }

        } else {
            // notify user already exists
            throw new ApplicationException(APIStatus.ERR_USER_NOT_FOUND);
        }
    }

    @RequestMapping(path = APIName.UPDATE_USER, method = RequestMethod.POST, produces = APIName.CHARSET)
    public ResponseEntity<APIResponse> updateUser(
            @PathVariable Long company_id,
            @RequestBody UserRequestModel user
    ) {

        // check user already exists
        User existedUser = userService.getUserByUserIdAndComIdAndStatus(user.getUserId(), company_id, Constant.USER_STATUS.ACTIVE.getStatus());
        if (existedUser != null) {
            existedUser.setFirstName(user.getFirstName());
            existedUser.setLastName(user.getLastName());
            if (user.getMiddleName() != null && !user.getMiddleName().isEmpty()) {
                existedUser.setMiddleName(user.getMiddleName());
            }
            userService.save(existedUser);
            UserAddress userAddress = userAddressService.getAddressByUserIdAndStatus(user.getUserId(), Constant.STATUS.ACTIVE_STATUS.getValue());
            if (userAddress != null) {
                userAddress.setAdress(user.getAddress());
                userAddress.setCity(user.getCity());
                userAddress.setCountry(user.getCountry());
                userAddress.setFax(user.getFax());
                userAddress.setPhone(user.getPhone());
                userAddressService.save(userAddress);
            } else {
                throw new ApplicationException(APIStatus.ERR_USER_ADDRESS_NOT_FOUND);
            }
            return responseUtil.successResponse(existedUser);
        } else {
            // notify user already exists
            throw new ApplicationException(APIStatus.ERR_USER_NOT_FOUND);
        }
    }

    @RequestMapping(path = APIName.DELETE_USER, method = RequestMethod.POST, produces = APIName.CHARSET)
    public ResponseEntity<APIResponse> deleteUsers(
            @PathVariable Long company_id,
            @RequestBody List<String> userIds
    ) {
        if (userIds != null && userIds.size() > 0) {

            for (String userId : userIds) {
                User user = userService.getUserByUserIdAndComIdAndStatus(userId, company_id, Constant.USER_STATUS.ACTIVE.getStatus());
                if (user != null) {
                    user.setStatus(Constant.USER_STATUS.INACTIVE.getStatus());
                    userService.save(user);

                    UserAddress userAddress = userAddressService.getAddressByUserIdAndStatus(userId, Constant.STATUS.ACTIVE_STATUS.getValue());
                    if (userAddress != null) {
                        userAddress.setStatus(Constant.STATUS.DELETED_STATUS.getValue());
                        userAddressService.save(userAddress);
                    }
                }
            }
            return responseUtil.successResponse("Delete User Successfully");
        } else {
            throw new ApplicationException(APIStatus.ERR_BAD_REQUEST);
        }

    }

    @RequestMapping(path = APIName.CHANGE_PASSWORD_USER, method = RequestMethod.POST, produces = APIName.CHARSET)
    public ResponseEntity<APIResponse> changePasswordUser(
            @PathVariable Long company_id,
            @RequestBody UserChangePasswordModel user
    ) throws NoSuchAlgorithmException {

        // check user already exists
        User existedUser = userService.getUserByUserIdAndComIdAndStatus(user.getUserId(), company_id, Constant.USER_STATUS.ACTIVE.getStatus());
        if (existedUser != null) {
            String oldHashPassword = MD5Hash.MD5Encrypt(user.getOldPassword() + existedUser.getSalt());
            if (oldHashPassword.equals(existedUser.getPasswordHash())) {
                if (user.getNewPassword() != null || !user.getNewPassword().isEmpty()) {
                    existedUser.setPasswordHash(MD5Hash.MD5Encrypt(user.getNewPassword() + existedUser.getSalt()));
                    userService.save(existedUser);
                    return responseUtil.successResponse(existedUser);
                } else {
                    throw new ApplicationException(APIStatus.ERR_MISSING_PASSWORD);
                }
            } else {
                throw new ApplicationException(APIStatus.ERR_UNCORRECT_PASSWORD);
            }

        } else {
            // notify user already exists
            throw new ApplicationException(APIStatus.ERR_USER_NOT_FOUND);
        }
    }
}
