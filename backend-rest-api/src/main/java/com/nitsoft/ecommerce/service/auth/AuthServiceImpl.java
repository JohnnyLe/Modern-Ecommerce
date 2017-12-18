/*
 * Copyright (c) NIT-Software. All Rights Reserved.
 * This software is the confidential and proprietary information of NIT-Software,
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with NIT-Software.
 */
package com.nitsoft.ecommerce.service.auth;

import com.nitsoft.ecommerce.api.request.AuthRequestModel;
import com.nitsoft.ecommerce.api.response.APIResponse;
import com.nitsoft.ecommerce.api.response.APIStatus;
import com.nitsoft.ecommerce.api.response.StatusResponse;
import com.nitsoft.ecommerce.auth.AuthUser;
import com.nitsoft.ecommerce.auth.AuthUserFactory;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    
    @Autowired
    AuthUserFactory authUserFactory;
    
    @Override
    public ResponseEntity<APIResponse> adminLogin(Long companyId, AuthRequestModel authRequestModel) {
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
                            || adminUser.getRoleId() == Constant.USER_ROLE.STORE_ADMIN.getRoleId()){
                        
                        // TODO login
                        UserToken userToken = createUserToken(adminUser, authRequestModel.isKeepMeLogin());
                        // Create Auth User -> Set to filter config
                        // Perform the security
                        Authentication authentication = new UsernamePasswordAuthenticationToken(
                                adminUser.getEmail(),
                                adminUser.getPasswordHash()
                        );
                        //final Authentication authentication = authenticationManager.authenticate();
                        SecurityContextHolder.getContext().setAuthentication(authentication);
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
            AuthUser authUser = authUserFactory.createAuthUser(userLogin);
            // Set authUser to session data...
            userToken.setSessionData(gson.toJson(authUser));
            userTokenRepository.save(userToken);
            return userToken;
        } catch (Exception e) {
            throw new ApplicationException(APIStatus.SQL_ERROR);
        }
    }

    @Override
    public ResponseEntity<APIResponse> getUserData(Long companyId, String userId) {
        
        if (userId != null && !"".equals(userId)){
            User user = userRepository.findByUserIdAndCompanyIdAndStatus(userId ,companyId, Constant.USER_STATUS.ACTIVE.getStatus());
            if(user != null){
                return responseUtil.successResponse(user);
            }else{
                throw new ApplicationException(APIStatus.ERR_USER_NOT_EXIST);
            }
        }else{
            throw new ApplicationException(APIStatus.INVALID_PARAMETER);
        }
    }

    @Override
    public ResponseEntity<APIResponse> logout(String tokenId) {
        
        if (tokenId != null && !"".equals(tokenId)){
            UserToken userToken = userTokenRepository.findOne(tokenId);
            if (userToken != null){
                userTokenRepository.delete(userToken);
                return responseUtil.successResponse("OK");
            }else{
                throw new ApplicationException(APIStatus.ERR_SESSION_NOT_FOUND);
            }
        }else{
            throw new ApplicationException(APIStatus.INVALID_PARAMETER);
        }
    }
    
}
