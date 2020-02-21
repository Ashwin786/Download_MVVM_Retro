package com.rk.download_mvvm_retro.repository.remote;

import com.rk.download_mvvm_retro.GlobalApp;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class RetroServices {
    static Retrofit retrofit;


    protected static Retrofit getClient() {
        OkHttpClient okHttp = new OkHttpClient();
        retrofit = new Retrofit.Builder().baseUrl(GlobalApp.baseUrl).client(okHttp).build();
        return retrofit;
    }
}
