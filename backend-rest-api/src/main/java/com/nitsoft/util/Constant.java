package com.nitsoft.util;

/**
 * @Class Name: Constant.java
 *
 * @brief: Define constant
 */
public class Constant {

    // API FOMAT DATE
    public static final String API_FORMAT_DATE = "yyyy/MM/dd HH:mm:ss";

    // LANG
    public static final String LANG_DEFAULT = "en"; // Lang default
    public static final String LANG_AUTO = "auto";

    public static final int BILLING_CYCLE = 30;

    public static enum TIME_REPORT {
        LASTWEEK,
        LAST2WEEKS,
        LASTMONTH,
        LAST3MONTHS,
        LAST6MONTHS,
        LASTYEAR
    }

    public static final int LASTWEEK_CODE = 0;
    public static final int LAST2WEEKS_CODE = 1;
    public static final int LASTMONTH_CODE = 2;
    public static final int LAST3MONTHS_CODE = 3;
    public static final int LAST6MONTHS_CODE = 4;
    public static final int LASTYEAR_CODE = 5;

    public static enum STATUS {
        ACTIVE_STATUS(0, "Active"),
        DELETED_STATUS(1, "Deleted"),
        REVOKE_STATUS(2, "Revoke"),
        DISABLED_STATUS(3, "Disable"),
        DELETED_FOREVER_STATUS(4, "Deleted forever"),
        PENDING(5, "Pending"),
        TRIAL_ACCOUNT_STATUS(6, "Trial");

        private final int value;
        private final String type;

        private STATUS(int value, String type) {
            this.value = value;
            this.type = type;
        }

        public int getValue() {
            return value;
        }

        public String getType() {
            return type;
        }
    }

    public static enum MESSAGE_STATUS {
        UNREAD(0, "unread"),
        READED(1, "readed");

        private final int value;
        private final String type;

        private MESSAGE_STATUS(int value, String type) {
            this.value = value;
            this.type = type;
        }

        public int getValue() {
            return value;
        }

        public String getType() {
            return type;
        }
    }

    public static enum USER_ROLE {
        SYS_ADMIN(1, "System Admin"),
        STORE_ADMIN(2, "Store Admin"),
        STORE_MANAGER(3, "Store Manager"),
        NORMAL_USER(4, "Normal User"),
        GUEST(5, "Guest");
        

        private final int roleId;
        private final String roleName;

        private USER_ROLE(int id, String name) {
            this.roleId = id;
            this.roleName = name;
        }

        public int getRoleId() {
            return roleId;
        }

        public String getRoleName() {
            return roleName;
        }
    }
    
    public static enum USER_STATUS {
        INACTIVE(-1),
        PENDING(0),
        ACTIVE(1);
        
        private final int status;
        
        private USER_STATUS(int status) {
            this.status = status;
        }
        
        public int getStatus() {
            return status;
        }
    }
    
    public static enum ORDER_STATUS {
        PENDING(0),
        SHIPPING(1),
        COMPLETED(2);
        
        private final int status;
        
        private ORDER_STATUS(int status) {
            this.status = status;
        }
        
        public int getStatus() {
            return status;
        }
    }
    
    public static enum PRODUCT_ATTRIBUTE {
        DETAIL_IMAGES(1, "Detail images");
        
        private final int id;
        private final String name;
        
        private PRODUCT_ATTRIBUTE(int id, String name) {
            this.id = id;
            this.name = name;
        }
        
        public int getId() {
            return id;
        }
        
        public String getName() {
            return name;
        }
    }

    public static final long ONE_MINUTE_IN_MILLIS = 60000;
    public static final long ONE_SECOND_IN_MILLIS = 1000;
    
    public static final long DEFAULT_REMEMBER_LOGIN_MILISECONDS = 1296000000; // 15 days
    public static final long DEFAULT_SESSION_TIME_OUT = 1800000; // 30 minutes

    // define paging results, use for default value of @RequestParam, so type of data is String
    public static final String DEFAULT_PAGE_SIZE = "25";
    public static final String DEFAULT_PAGE_NUMBER = "0";

    // define sort key value
    public static final int SORT_BY_PRODUCT_NAME = 1;
    public static final int SORT_BY_PRODUCT_PRICE= 2;
    public static final int SORT_BY_PRODUCT_QUANTITY = 3;
    public static final int SORT_BY_PRODUCT_CREATE_DATE = 4;
    public static final int SORT_BY_FIRST_NAME = 1;
    public static final int SORT_BY_LAST_NAME = 2;
    public static final int SORT_BY_EMAIL_ADDRESS = 3;
    public static final int SORT_BY_CREATE_DATE = 4;
    
    // define sort key value  
    public static final int SORT_BY_ITEMS_QUANTITY = 4;
    public static final int SORT_BY_GRAND_TOTAL = 2;
    
    // Custom token header
    public static final String HEADER_TOKEN = "X-Access-Token";
    
    public enum ParamError {

        MISSING_USERNAME_AND_EMAIL("accountName", "Missing both user name and email address"),
        USER_NAME("userName", "Invalid user name"),
        EMAIL_ADDRESS("email", "Invalid email address"),
        PASSWORD("passwordHash", "Invalid password hash"),
        PHONE_NUMBER("phone", "Invalid phone number"),
        FIRST_NAME("firstName", "Invalid first name"),
        LAST_NAME("lastName", "Invalid last name"),
        APP_NAME("appName", "Invalid app name"),
        APP_DOMAIN("appDomain", "Invalid app domain"),
        SERVER_KEY("serverKey", "Invalid server key"),
        TOKEN_EXPIRE_DURATION("tokenExpireDuration", "Invalid token expiry duration"),
        REDIRECT_URL("redirectUrl", "Invalid redirect URL"),
        ROLE_NAME("roleName", "Invalid role name"),
        ROLE_DESC("roleDescription", "Invalid role description");

        private final String name;
        private final String desc;

        private ParamError(String name, String desc) {
            this.name = name;
            this.desc = desc;
        }

        public String getName() {
            return name;
        }

        public String getDesc() {
            return desc;
        }
    }
}
