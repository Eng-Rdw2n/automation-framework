package com.radwanalbunni.automation.data.models.domain;

public class ProductDataModel {

    private String id;
    private String name;
    private String searchKeyword;
    private String category;
    private String subCategory;
    private String brand;

    public ProductDataModel(ProductDataModel other) {
        this.id = other.id;
        this.name = other.name;
        this.searchKeyword = other.searchKeyword;
        this.category = other.category;
        this.subCategory = other.subCategory;
        this.brand = other.brand;
    }

    public ProductDataModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
