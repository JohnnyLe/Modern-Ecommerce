/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.api.request.model;

/**
 *
 * @author acer
 */
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

    public ListProductModel(long categoryId, long attributeId, long companyId, String searchKey, Double minPrice, Double maxPrice, int minRank, int maxRank, int sortCase, Boolean ascSort, int pageNumber, int pageSize) {
        this.categoryId = categoryId;
        this.attributeId = attributeId;
        this.companyId = companyId;
        this.searchKey = searchKey;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.minRank = minRank;
        this.maxRank = maxRank;
        this.sortCase = sortCase;
        this.ascSort = ascSort;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public ListProductModel() {
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public long getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(long attributeId) {
        this.attributeId = attributeId;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public int getMinRank() {
        return minRank;
    }

    public void setMinRank(int minRank) {
        this.minRank = minRank;
    }

    public int getMaxRank() {
        return maxRank;
    }

    public void setMaxRank(int maxRank) {
        this.maxRank = maxRank;
    }

    public int getSortCase() {
        return sortCase;
    }

    public void setSortCase(int sortCase) {
        this.sortCase = sortCase;
    }

    public Boolean getAscSort() {
        return ascSort;
    }

    public void setAscSort(Boolean ascSort) {
        this.ascSort = ascSort;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    
    
}
