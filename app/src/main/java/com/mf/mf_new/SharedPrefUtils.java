package com.mf.mf_new;

import android.content.Context;
import android.content.SharedPreferences;

import com.mf.mf_new.repo.network.Api;

public class SharedPrefUtils {
    private static final String DEFAULT_SHARED_PREF = "config";

    public static String getBaseUrl(Context context) {
        SharedPreferences pref = context.getSharedPreferences(DEFAULT_SHARED_PREF, Context.MODE_PRIVATE);
        return pref.getString("default_url", Api.BASE_URL);
    }

    public static void setBaseUrl(Context context, String url) {
        SharedPreferences pref = context.getSharedPreferences(DEFAULT_SHARED_PREF, Context.MODE_PRIVATE);
        pref.edit().putString("default_url", url).apply();
    }

}
