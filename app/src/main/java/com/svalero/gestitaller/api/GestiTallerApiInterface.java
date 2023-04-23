package com.svalero.gestitaller.api;

import com.svalero.gestitaller.domain.Bike;
import com.svalero.gestitaller.domain.Client;
import com.svalero.gestitaller.domain.dto.BikeDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GestiTallerApiInterface {

    // Clients
    @GET("clients?all=true")
    Call<List<Client>> getClients();

    @GET("clients")
    Call<List<Client>> getClientsByName(@Query("name") String name);

    @GET("clients")
    Call<List<Client>> getClientsBySurname(@Query("surname") String surname);

    @GET("clients")
    Call<List<Client>> getClientsByDni(@Query("dni") String dni);

    @DELETE("client/{id}")
    Call<Void> deleteClients(@Path("id") long id);

    @POST("client")
    Call<Client> addClient(@Body Client client);

    @PUT("client/{id}")
    Call<Client> modifyClient(@Path("id") long id, @Body Client client);

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

    @POST("bike")
    Call<Bike> addBike(@Body BikeDTO bikeDTO);

    @PUT("bike/{id}")
    Call<Bike> modifyBike(@Path("id") long id, @Body BikeDTO bikeDTO);
}
