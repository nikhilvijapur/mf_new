package com.mf.mf_new.repo.network;

import com.mf.mf_new.repo.MfDetailItem;
import com.mf.mf_new.repo.MfGraphDetailItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Api {
    public static final String BASE_URL = "http://192.168.0.107:8000/mf/";

    @GET("getall")
    public Call<List<MfDetailItem>> getAll();

    @GET("getentries/{code}")
    public Call<List<MfDetailItem>> getEntries(@Path("code") String code);

    @POST("get_by_company_name")
    @FormUrlEncoded
    public Call<List<MfDetailItem>> getAllByCompany(@Field("company_name") String code);

    @GET("{code}")
    public Call<List<MfGraphDetailItem>> getGraphEntries(@Path("code") int code);

}
