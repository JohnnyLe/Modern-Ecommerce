
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
    public static enum TIME_REPORT{
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
    
    public static enum STATUS{
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
    
    
      public static enum MESSAGE_STATUS{
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
    
    public static final long ONE_MINUTE_IN_MILLIS = 60000;
    public static final long ONE_SECOND_IN_MILLIS = 1000;   
      
}
