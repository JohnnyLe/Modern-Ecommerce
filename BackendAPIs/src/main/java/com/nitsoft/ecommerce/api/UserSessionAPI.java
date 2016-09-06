/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.api;

import static com.nitsoft.ecommerce.api.APIUtil.mapper;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 */
@Controller
public class UserSessionAPI extends APIUtil {

//
//    @RequestMapping(value = APIName.LOGIN, method = RequestMethod.POST, produces = APIName.CHARSET)
//    @ResponseBody
//    public String login(
//            @RequestParam(value = "email_address", required = true) String emailAddress,
//            @RequestParam(value = "password", required = true) String password,
//            @RequestParam(value = "team_id", required = false) String teamId,
//            @RequestParam(value = "keep_me_login", required = true) boolean keepMeLogin) {
//        User user = userAdapter.findByTeamEmail(teamId,emailAddress, true);
//        if (user == null) {
//            return writeObjectToJson(new StatusResponse(APIStatus.USER_NOT_FOUND));
//        }
//
//        if (user.getPasswordHash().equals(password)) {
//            long keepTimeOut = Long.parseLong((appConfig.getValueOfKey("keep_login_timeout")).trim()) * 24 * 60 * 60 * 1000; // 7 days
//            long sessionTimeOut = Long.parseLong((appConfig.getValueOfKey("session_timeout")).trim()) * 60 * 1000; // 30 minutes
//            long timeOut = (!keepMeLogin) ? sessionTimeOut : keepTimeOut;
//            Date currentTime = new Date();
//            Date newExpirationDate = new Date(currentTime.getTime() + timeOut);
//
//            // create new session
//            Session session = new Session();
//            session.setSessionId(UniqueID.getUUID());
//            session.setTeamId(teamId);
//            session.setUserId(user.getUserId());
//            session.setLoginDate(DateUtil.convertToUTC(currentTime));
//            session.setExpirationDate(DateUtil.convertToUTC(newExpirationDate));
//            if (userSessionAdapter.createUserSession(session)) {
//                // create history
//                historyEventManager.userEvent.trackUserAuthen(user, true);
//            } else {
//                return writeObjectToJson(new StatusResponse(APIStatus.CREATE_SESSION_FAILED));
//            }
//            user.setLastActivity(DateUtil.convertToUTC(currentTime));
//            userAdapter.save(user);
//            HashMap<String, Object> map = new HashMap<String, Object>();
//            map.put("session_id", session.getSessionId());
//            map.put("user_id", user.getUserId());
//            map.put("email_address", user.getMailAddress());
//            map.put("first_name", user.getFirstName());
//            map.put("last_name", user.getLastName());
//            map.put("middle_name", user.getMiddleName());
//            map.put("role_id", user.getRoleId());
//            map.put("lang", user.getLang());
//            return writeObjectToJson(new StatusResponse<HashMap<String, Object>>(200, map));
//        } else {
//            return writeObjectToJson(new StatusResponse(APIStatus.ERR_USER_NOT_VALID));
//        }
//    }
//
//    @RequestMapping(value = APIName.LOGOUT, method = RequestMethod.POST, produces = APIName.CHARSET)
//    @ResponseBody
//    public String logout(@RequestParam(value = "auth_token", required = true) String sessionId) {
//        Session session = userSessionAdapter.findByID(Session.class, sessionId);
//        User user = userSessionAdapter.getUserBySessionID(sessionId);
//        if (session == null) {
//            return writeObjectToJson(new StatusResponse(APIStatus.USER_NOT_FOUND));
//        }
//        userSessionAdapter.delete(session);
//
//        // Create history
//        History history = new History(user.getTeamId(), user.getUserId(), Constant.HistoryCategory.USER_TYPE.getValue(), Constant.HistoryType.USER_LOGOUT.getValue());
//        if (!historyAdapter.createHistory(history)) {
//            return writeObjectToJson(new StatusResponse(APIStatus.CREATE_HISTORY_FAILED));
//        }
//
//        return writeObjectToJson(new StatusResponse(APIStatus.OK));
//    }
//
//    @RequestMapping(value = APIName.ADMIN_LOGIN, method = RequestMethod.POST, produces = APIName.CHARSET)
//    @ResponseBody
//    public String adminLogin(
//            @RequestParam(value = "email_address", required = true) String emailAddress,
//            @RequestParam(value = "password", required = true) String password,
//            @RequestParam(value = "keep_me_login", required = true) boolean keepMeLogin) {
//        User user = userAdapter.findByTeamEmail(null, emailAddress, true);
//        if (user == null) {
//            return writeObjectToJson(new StatusResponse(APIStatus.USER_NOT_FOUND));
//        }
//        
//        if (user.getPasswordHash().equals(password)) {
//            if(user.getRoleId() == Constant.SystemRoleType.SuperAdmin.getValue()){
//            long keepTimeOut = Long.parseLong((appConfig.getValueOfKey("keep_login_timeout")).trim()) * 24 * 60 * 60 * 1000; // 7 days
//            long sessionTimeOut = Long.parseLong((appConfig.getValueOfKey("session_timeout")).trim()) * 60 * 1000; // 30 minutes
//            long timeOut = (!keepMeLogin) ? sessionTimeOut : keepTimeOut;
//            Date currentTime = new Date();
//            Date newExpirationDate = new Date(currentTime.getTime() + timeOut);
//
//            // create new session
//            Session session = new Session();
//            session.setSessionId(UniqueID.getUUID());            
//            session.setUserId(user.getUserId());
//            session.setTeamId(user.getTeamId());
//            session.setLoginDate(DateUtil.convertToUTC(currentTime));
//            session.setExpirationDate(DateUtil.convertToUTC(newExpirationDate));
//            if (userSessionAdapter.createUserSession(session)) {
//                // create history
//                historyEventManager.userEvent.trackUserAuthen(user, true);
//            } else {
//                return writeObjectToJson(new StatusResponse(APIStatus.CREATE_SESSION_FAILED));
//            }
//            user.setLastActivity(DateUtil.convertToUTC(currentTime));
//            userAdapter.save(user);
//            HashMap<String, Object> map = new HashMap<String, Object>();
//            map.put("session_id", session.getSessionId());
//            map.put("user_id", user.getUserId());
//            map.put("email_address", user.getMailAddress());
//            map.put("first_name", user.getFirstName());
//            map.put("last_name", user.getLastName());
//            map.put("middle_name", user.getMiddleName());
//            map.put("role_id", user.getRoleId());
//            map.put("lang", user.getLang());
//            return writeObjectToJson(new StatusResponse<HashMap<String, Object>>(200, map));
//            }else{
//                return writeObjectToJson(new StatusResponse(APIStatus.ERR_USER_ROLE_NOT_VALID));
//            }
//        } else {
//            return writeObjectToJson(new StatusResponse(APIStatus.ERR_USER_NOT_VALID));
//        }        
//    }
//    
}
