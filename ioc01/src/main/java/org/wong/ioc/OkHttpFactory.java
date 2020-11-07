package org.wong.ioc;

import okhttp3.OkHttpClient;

public class OkHttpFactory {
    private OkHttpClient okHttpClient;
    public OkHttpClient getInstance(){
        if(okHttpClient == null){
            okHttpClient = new OkHttpClient.Builder().build();
        }
        return okHttpClient;
    }
}
