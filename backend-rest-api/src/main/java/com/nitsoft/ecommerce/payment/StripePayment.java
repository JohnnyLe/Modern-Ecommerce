/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.payment;

import com.nitsoft.ecommerce.configs.AppConfig;
import com.nitsoft.ecommerce.tracelogged.EventLogManager;
import com.stripe.Stripe;
import com.stripe.model.Card;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StripePayment extends PaymentMethod{
    
    @Autowired
    AppConfig appConfig;
    
    @Override
    public String getAPIKey()
    {
        return appConfig.getValueOfKey("stripe.secretkey");
    }
    
    @Override
    public TransmissitionInfo processPayment(String teamId, Integer amount, Integer currencyType, String userCardId) {
        Stripe.apiKey = this.getAPIKey();
        TransmissitionInfo transmissitionInfo=new TransmissitionInfo();
        EventLogManager.getInstance().info("Make payment for teamId=" + teamId);
        HashMap<String, Object> defaultChargeParams = new HashMap<String, Object>();
        //Put params to map
//        int _amount=Integer.parseInt(amount.toString());
//         System.out.println(_amount);
        
        defaultChargeParams.put("amount", PaymentManager.convertPriceByCurrency(amount, currencyType));
        defaultChargeParams.put("currency", PaymentManager.getCurrencyByType(currencyType));
        defaultChargeParams.put("customer", userCardId);
                        //Create object stripe
        //PaymentManager.getInstance();
       
        Charge createdCharge = null;
        try {
            createdCharge = Charge.create(defaultChargeParams);
            if (createdCharge != null) {
                //1. Create new record for stored session of this transmission payment
                String transmissitionId = createdCharge.getId();
                transmissitionInfo.setTrasmissitionId(transmissitionId);
                String cardInfo = "";
                Customer customer = Customer.retrieve(userCardId);
                Card card = customer.getCards().retrieve(customer.getDefaultCard());
                cardInfo = card.getType() + "-" + card.getLast4();
                transmissitionInfo.setCardInfo(cardInfo);
            }
        } catch (Exception ex) {
            
            EventLogManager.getInstance().error(ex);
            return null;
        }
        return transmissitionInfo;
    }

    @Override
    public PaymentType getJobType() {
        return PaymentType.STRIPE;
    }
}
