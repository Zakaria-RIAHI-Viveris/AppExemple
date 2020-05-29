package com.viveris.appexemple;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppExempleApplication extends Application {

    public static final String BASE_URL = "https://api.stackexchange.com/2.2/";
    private Gson gson;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();
        if (gson == null) {
            initializeGson();
        }
        if (retrofit == null) {
            initializeRetrofit();
        }
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public Gson getGson() {
        return gson;
    }

    private void initializeGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
    }

    private OkHttpClient initilializeOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder().addInterceptor(interceptor).build();

    }

    private void initializeRetrofit() {
        retrofit = new Retrofit.Builder()
                .client(initilializeOkHttpClient())
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }
}
