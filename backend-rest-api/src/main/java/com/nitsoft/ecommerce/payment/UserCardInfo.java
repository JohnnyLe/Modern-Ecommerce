/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.payment;

import com.stripe.model.Card;
import com.stripe.model.Customer;


public class UserCardInfo {
    
    private Customer customer;
    private Card card;
    
    public UserCardInfo(){}
    
     public UserCardInfo(Customer cus, Card card)
     {
         this.customer=cus;
         this.card=card;
     }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
    
    
}
