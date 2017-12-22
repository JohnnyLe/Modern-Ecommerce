/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.api.request.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author acer
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductModel {
    private long productId;
    private long companyId;
    private List<Long> listCategoriesId;
    private String name;
    private String browsingName;
    private double salePrice;
    private double listPrice;
    private String defaultImage;
    private String overview;
    private int quantity;
    private Boolean isStockControlled;
    private String description;
    private int rank;
    private String sku;
}
