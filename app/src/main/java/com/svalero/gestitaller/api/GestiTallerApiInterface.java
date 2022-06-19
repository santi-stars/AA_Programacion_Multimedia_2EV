package com.svalero.gestitaller.api;

import com.svalero.gestitaller.domain.Bike;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GestiTallerApiInterface {

    // Bikes
    @GET("bikes?all=true")
    Call<List<Bike>> getBikes();
    @GET("bikes")
    Call<List<Bike>> getBikesByBrand(@Query("brand") String brand);
    @GET("bikes")
    Call<List<Bike>> getBikesByModel(@Query("model") String model);
    @GET("bikes")
    Call<List<Bike>> getBikesByLicense(@Query("license") String license);
    @DELETE("bike/{id}")
    Call<Void> deleteBike(@Path("id") long id);
}
