/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nitsoft.ecommerce.api.response;

/**
 *
 */

public class OKStatus<T> {

    private final int statusCode = 200;
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public OKStatus () {
    }

    public OKStatus (T data) {
        this.data = data;
    }
}

