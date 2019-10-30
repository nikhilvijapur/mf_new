package com.mf.mf_new.repo;

public class MfCompany {
    public String getAmc() {
        return amc;
    }

    public MfCompany(String amc, int noOfMf) {
        this.amc = amc;
        this.noOfMf = noOfMf;
    }

    private String amc;
    private int noOfMf;

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof MfCompany) {
            return ((MfCompany) obj).getAmc().equals(this.amc);
        }
        return super.equals(obj);
    }

    public int getNoOfMf() {
        return noOfMf;
    }

    @Override
    public String toString() {
        return "MfCompany{" +
                "amc='" + amc + '\'' +
                ", noOfMf=" + noOfMf +
                '}';
    }
}
