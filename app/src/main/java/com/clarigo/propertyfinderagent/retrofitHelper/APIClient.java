package com.clarigo.propertyfinderagent.retrofitHelper;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static Retrofit retrofit = null, retro = null;

    public static String BASE_URL = "http://clarigoinfotech.co.in/property/Api/";

    public final APIInterface apiInterface;

    public APIClient(APIInterface apiInterface) {
        this.apiInterface = apiInterface;
    }

    public static APIInterface getInterface() {
        return getClient().create(APIInterface.class);
    }

    public static Retrofit getClient() {
        try {
            OkHttpClient.Builder client = new OkHttpClient.Builder();
            client.connectTimeout(10, TimeUnit.SECONDS);
            client.readTimeout(10, TimeUnit.SECONDS);
            client.writeTimeout(10, TimeUnit.SECONDS);
            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(BASE_URL)
                        .client(client.build())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retrofit;
    }

    public static Retrofit getClient_with_header() {
        try {
            OkHttpClient.Builder client = new OkHttpClient.Builder();
            client.connectTimeout(15, TimeUnit.SECONDS);
            client.readTimeout(15, TimeUnit.SECONDS);
            client.writeTimeout(15, TimeUnit.SECONDS);
            if (retro == null) {
                retro = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl("https://api.simplyrets.com/")
                        .client(client.build())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retro;

    }


    private static OkHttpClient.Builder getHttpLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder();
    }

}

