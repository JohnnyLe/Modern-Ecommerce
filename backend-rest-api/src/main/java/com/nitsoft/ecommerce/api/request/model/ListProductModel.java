/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.api.request.model;

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
public class ListProductModel {
    private long categoryId;
    private long attributeId;
    private long companyId;
    private String searchKey;
    private Double minPrice;
    private Double maxPrice;
    private int minRank;
    private int maxRank;
    private int sortCase;
    private Boolean ascSort;
    private int pageNumber;
    private int pageSize;
}
