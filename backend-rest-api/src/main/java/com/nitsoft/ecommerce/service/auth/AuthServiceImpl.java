/*
 * Copyright (c) NIT-Software. All Rights Reserved.
 * This software is the confidential and proprietary information of NIT-Software,
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with NIT-Software.
 */
package com.nitsoft.ecommerce.service.auth;

import com.nitsoft.ecommerce.api.request.AuthRequestModel;
import com.nitsoft.ecommerce.api.response.APIStatus;
import com.nitsoft.ecommerce.api.response.StatusResponse;
import com.nitsoft.ecommerce.database.model.User;
import com.nitsoft.ecommerce.database.model.UserToken;
import com.nitsoft.ecommerce.exception.ApplicationException;
import com.nitsoft.ecommerce.repository.UserRepository;
import com.nitsoft.ecommerce.repository.UserTokenRepository;
import com.nitsoft.ecommerce.service.AbstractBaseService;
import com.nitsoft.util.Constant;
import com.nitsoft.util.DateUtil;
import com.nitsoft.util.MD5Hash;
import com.nitsoft.util.UniqueID;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 *
 * @author Quy Duong
 */
@Component
public class AuthServiceImpl extends AbstractBaseService implements AuthService{
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    private UserTokenRepository userTokenRepository;
    
    @Override
    public ResponseEntity<StatusResponse> adminLogin(Long companyId, AuthRequestModel authRequestModel) {
        if (authRequestModel == null || "".equals(authRequestModel.getUsername()) || "".equals(authRequestModel.getPassword())){
            throw  new ApplicationException(APIStatus.INVALID_PARAMETER);
        }else{
            User adminUser = userRepository.findByEmailAndCompanyIdAndStatus(authRequestModel.getUsername(), companyId, Constant.USER_STATUS.ACTIVE.getStatus());
            if (adminUser == null){
                throw new ApplicationException(APIStatus.ERR_USER_NOT_EXIST);
            }else{
                String passwordHash = null;
                try {
                    passwordHash = MD5Hash.MD5Encrypt(authRequestModel.password + adminUser.getSalt());
                } catch (NoSuchAlgorithmException ex) {
                    throw new RuntimeException("User login encrypt password error", ex);
                }
                
                if (passwordHash.equals(adminUser.getPasswordHash())) {
                    // Check role
                    if (adminUser.getRoleId() == Constant.USER_ROLE.SYS_ADMIN.getRoleId()
                            || adminUser.getRoleId() == Constant.USER_ROLE.STORE_MANAGER.getRoleId()
                            || adminUser.getRoleId() == Constant.USER_ROLE.STORE_MANAGER.getRoleId()){
                        
                        // TODO login
                        UserToken userToken = createUserToken(adminUser, authRequestModel.isKeepMeLogin());
                        
                        return responseUtil.successResponse(userToken);
                    }else{
                        throw  new ApplicationException(APIStatus.ERR_PERMISSION_DENIED);
                    }
                }else{
                    throw new ApplicationException(APIStatus.ERR_USER_NOT_VALID);
                }
                
            }
        }
    }
    
    private UserToken createUserToken (User userLogin, boolean keepMeLogin){
        try {
            UserToken userToken = new UserToken();
            userToken.setToken(UniqueID.getUUID());
            userToken.setCompanyId(userLogin.getCompanyId());
            userToken.setUserId(userLogin.getUserId());
            Date currentDate = new Date();
            userToken.setLoginDate(DateUtil.convertToUTC(currentDate));
            Date expirationDate = keepMeLogin ? new Date(currentDate.getTime() + Constant.DEFAULT_REMEMBER_LOGIN_MILISECONDS) : new Date(currentDate.getTime() + Constant.DEFAULT_SESSION_TIME_OUT);
            userToken.setExpirationDate(DateUtil.convertToUTC(expirationDate));

            userTokenRepository.save(userToken);
            return userToken;
        } catch (Exception e) {
            throw new ApplicationException(APIStatus.SQL_ERROR);
        }
    }
    
}
