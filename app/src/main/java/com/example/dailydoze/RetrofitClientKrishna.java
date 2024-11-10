package com.example.dailydoze;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientKrishna {

    private static String BASE_URL = "https://dailydoseapi.onrender.com/api/";
    private static RetrofitClientKrishna retrofitClientKrishna;
    private static Retrofit retrofit;

    private RetrofitClientKrishna() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClientKrishna getInstance() {
        if (retrofitClientKrishna == null) {
            retrofitClientKrishna = new RetrofitClientKrishna();
        }
        return retrofitClientKrishna;
    }

    public ApiKrishna getApiKrishna() {
        return retrofit.create(ApiKrishna.class);
    }
}
