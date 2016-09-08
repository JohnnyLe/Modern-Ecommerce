/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.payment;

import org.springframework.stereotype.Component;

@Component
public abstract class PaymentMethod {
    
    public abstract PaymentType getJobType();
    public abstract String getAPIKey();
    public abstract TransmissitionInfo processPayment(String teamId, Integer amount, Integer currencyType, String userCardId);
    
    public static enum PaymentType {
        // Allow pay with Stripe (credit card)
        STRIPE(1,"STRIPE"), 
        // Allow pay with pay with paypal account
        PAYPAL(2,"PAYPAL");
       private int type;
       private String value;

        private PaymentType(int type, String value) {
            this.type = type;
            this.value = value;
        }

        public int getType() {
            return type;
        }

        public String getValue() {
            return value;
        }

        public void setType(int type) {
            this.type = type;
        }

        public void setValue(String value) {
            this.value = value;
        }  
    }
    
     // Define Currency Payment type
    public static enum currencyPaymentType {

        USD(1, "USD");
        private final int value;
        private final String type;
        private currencyPaymentType(int value, String type) {
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
}
