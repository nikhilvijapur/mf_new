package com.mf.mf_new.repo;

public class MfCategory {

    private String category;
    private int noOfMf;

    public MfCategory(String category, int noOfMf) {
        this.category = category;
        this.noOfMf = noOfMf;
    }

    public String getCategory() {
        return category;
    }

    public int getNoOfMf() {
        return noOfMf;
    }
}
