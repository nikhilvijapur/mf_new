package com.mf.mf_new.repo;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MfDetailItem  implements Parcelable {
    private String amc;
    @PrimaryKey
    @NonNull
    private String code;
    private String scheme_name;
    private String scheme_type;
    private String scheme_cat;
    private String scheme_nav_name;
    private String scheme_min_amt;
    private String scheme_launch_date;
    private String scheme_end_date;
    private String scheme_dev_payout;

    @Override
    public String toString() {
        return "MfDetailItem{" +
                "amc='" + amc + '\'' +
                ", code='" + code + '\'' +
                ", scheme_name='" + scheme_name + '\'' +
                ", scheme_type='" + scheme_type + '\'' +
                ", scheme_cat='" + scheme_cat + '\'' +
                ", scheme_nav_name='" + scheme_nav_name + '\'' +
                ", scheme_min_amt='" + scheme_min_amt + '\'' +
                ", scheme_launch_date='" + scheme_launch_date + '\'' +
                ", scheme_end_date='" + scheme_end_date + '\'' +
                ", scheme_dev_payout='" + scheme_dev_payout + '\'' +
                '}';
    }

    public MfDetailItem(String amc, String code, String scheme_name, String scheme_type, String scheme_cat, String scheme_nav_name, String scheme_min_amt, String scheme_launch_date, String scheme_end_date, String scheme_dev_payout) {
        this.amc = amc;
        this.code = code;
        this.scheme_name = scheme_name;
        this.scheme_type = scheme_type;
        this.scheme_cat = scheme_cat;
        this.scheme_nav_name = scheme_nav_name;
        this.scheme_min_amt = scheme_min_amt;
        this.scheme_launch_date = scheme_launch_date;
        this.scheme_end_date = scheme_end_date;
        this.scheme_dev_payout = scheme_dev_payout;
    }

    protected MfDetailItem(Parcel in) {
        amc = in.readString();
        code = in.readString();
        scheme_name = in.readString();
        scheme_type = in.readString();
        scheme_cat = in.readString();
        scheme_nav_name = in.readString();
        scheme_min_amt = in.readString();
        scheme_launch_date = in.readString();
        scheme_end_date = in.readString();
        scheme_dev_payout = in.readString();
    }

    public static final Creator<MfDetailItem> CREATOR = new Creator<MfDetailItem>() {
        @Override
        public MfDetailItem createFromParcel(Parcel in) {
            return new MfDetailItem(in);
        }

        @Override
        public MfDetailItem[] newArray(int size) {
            return new MfDetailItem[size];
        }
    };

    public String getAmc() {
        return amc;
    }

    public String getCode() {
        return code;
    }

    public String getScheme_name() {
        return scheme_name;
    }

    public String getScheme_type() {
        return scheme_type;
    }

    public String getScheme_cat() {
        return scheme_cat;
    }

    public String getScheme_nav_name() {
        return scheme_nav_name;
    }

    public String getScheme_min_amt() {
        return scheme_min_amt;
    }

    public String getScheme_launch_date() {
        return scheme_launch_date;
    }

    public String getScheme_end_date() {
        return scheme_end_date;
    }

    public String getScheme_dev_payout() {
        return scheme_dev_payout;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(amc);
        parcel.writeString(code);
        parcel.writeString(scheme_name);
        parcel.writeString(scheme_type);
        parcel.writeString(scheme_cat);
        parcel.writeString(scheme_nav_name);
        parcel.writeString(scheme_min_amt);
        parcel.writeString(scheme_launch_date);
        parcel.writeString(scheme_end_date);
        parcel.writeString(scheme_dev_payout);
    }
}
