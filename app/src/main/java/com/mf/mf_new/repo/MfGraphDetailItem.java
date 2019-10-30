package com.mf.mf_new.repo;

public class MfGraphDetailItem {
    @Override
    public String toString() {
        return "MfGraphDetailItem{" +
                "data='" + data + '\'' +
                ", nav=" + nav +
                '}';
    }

    public MfGraphDetailItem(String mDate, float mNav) {
        this.data = mDate;
        this.nav = mNav;
    }

    public String getDate() {
        return data;
    }

    public void setDate(String mDate) {
        this.data = mDate;
    }

    public float getNav() {
        return nav;
    }

    public void setNav(float mNav) {
        this.nav = mNav;
    }

    private String data;
    private float nav;
}
