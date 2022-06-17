package com.svalero.gestitaller.api;

import static com.svalero.gestitaller.api.Constants.BASE_URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GestiTallerApi {

    public static GestiTallerApiInterface buildInstance() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(GestiTallerApiInterface.class);
    }
}