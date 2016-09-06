/*
 */
package com.nitsoft.ecommerce.auth.login;
import org.springframework.stereotype.Component;

/**
 * @Class Name: UserActionServices.java
 * @brief: Define services for user action like login, log out
 */
@Component
public class UserAuthenticationService implements Authenticator{

    @Override
    public AuthResponse authenticate(long teamId, String email, String password, boolean isKeepMeLogin) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
//
//    
//    @Autowired
//    private UserSessionImpl sessionDao;
//    @Autowired
//    private HistoryDaoImpl historyDao;
//    @Autowired
//    private UserImpl userDao;
//    
//    // defaultTokenAge
//    long keepLoginTokenAge=7*24*60*60*1000;// 1 Week
//    long defaultTokenAge=30*60*1000;// 30 minutes
//    
//    @Override
//    public AuthResponse authenticate(String teamId, String email, String password, boolean isKeepMeLogin) {
//        User user=userDao.getUser(teamId, email, password, Constant.ACTIVE_STATUS);
//        AuthResponse response=null;
//        Date currentDate = new Date();
//        if(user!=null)
//        { 
//                    Session sessionEntity = new Session();
//                    //Create new session
//                    sessionEntity.setSessionId(UniqueID.getUUID());
//                    sessionEntity.setTeamId(teamId);
//                    sessionEntity.setUserId(user.getUserId());
//                    sessionEntity.setLoginDate(DateUtil.convertToUTC(currentDate));
//                    if(isKeepMeLogin)
//                       sessionEntity.setExpirationDate(DateUtil.convertToUTC(new Date(currentDate.getTime() + keepLoginTokenAge)));
//                    else
//                       sessionEntity.setExpirationDate(DateUtil.convertToUTC(new Date(currentDate.getTime() + defaultTokenAge)));
//                    if (sessionDao.createUserSession(sessionEntity)) {
//                        //Create a history record
//                        History history = new History();
//                        history.setHistoryId(UniqueID.getUUID());
//                        history.setCategory(Constant.TYPE_USER_ACTION);
//                        history.setCreateDate(DateUtil.convertLocalToUTC(currentDate, user.getTimeZone()));
//                        history.setHistoryType(Constant.LOGIN_TYPE); //403
//                        history.setTeamId(teamId);
//                        history.setStatus(Constant.ACTIVE_STATUS);
//                        history.setUserId(user.getUserId());
//                        if (historyDao.createHistory(history)) {
//                             //Set attribute login
//                            response=new AuthResponse(user, sessionEntity.getSessionId());
//                        } else {
//                            EventLogManager.getInstance().error("Cannot create History ");
//                        }
//                    } else {
//                       
//                    }
//                    //Update lastLogin of user
//                    user.setLastActivity(DateUtil.convertLocalToUTC(currentDate, user.getTimeZone()));
//                    userDao.merge(user);
//        }      
//        return response;
//    }

}
