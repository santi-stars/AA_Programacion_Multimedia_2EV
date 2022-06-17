package com.svalero.gestitaller.api;

import com.svalero.gestitaller.domain.Bike;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GestiTallerApiInterface {

    // Bikes
    @GET("bikes?all=true")
    Call<List<Bike>> getBikes();
}
