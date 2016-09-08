/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.util;

/**
 *
 * @author VS9 X64Bit
 */
public class ValueConverter {

    public static int convertStringToInt(String strVal, int defaultVal) {
        try {
            return Integer.parseInt(strVal);
        } catch (Exception ex) {
            return defaultVal;
        }
    }

    public static boolean convertStringToBoolean(String strVal, boolean defaultVal) {
        try {
            if (strVal.equalsIgnoreCase("true") || strVal.equals("1")) {
                return true;
            } else if (strVal.equalsIgnoreCase("false") || strVal.equals("0")) {
                return false;
            }
        } catch (Exception ex) {
        }

        return defaultVal;
    }

    public static double convertStringToDouble(String strVal, double defaultVal) {
        try {
            return Double.parseDouble(strVal);
        } catch (Exception ex) {
            return defaultVal;
        }
    }

}
