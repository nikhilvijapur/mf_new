package com.mf.mf_new.repo.network;

import android.content.Context;

import com.mf.mf_new.SharedPrefUtils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroFitUtil {
    private static RetroFitUtil sInstance=null;

    private Retrofit mRetrofit;
    private RetroFitUtil(Context context) {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(SharedPrefUtils.getBaseUrl(context))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RetroFitUtil getInstance(Context context) {
        if(sInstance == null)
            sInstance = new RetroFitUtil(context);
        return sInstance;
    }

    public static void dispose() {
        sInstance = null;
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

}
