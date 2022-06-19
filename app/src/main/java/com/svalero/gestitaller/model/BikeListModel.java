package com.svalero.gestitaller.model;

import android.content.Context;

import androidx.room.Room;

import com.svalero.gestitaller.api.GestiTallerApi;
import com.svalero.gestitaller.api.GestiTallerApiInterface;
import com.svalero.gestitaller.contract.BikeListContract;
import com.svalero.gestitaller.database.AppDatabase;
import com.svalero.gestitaller.domain.Bike;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BikeListModel implements BikeListContract.Model {

    private AppDatabase db;
    private Context context;
    private GestiTallerApiInterface api;
    private List<Bike> bikes;

    @Override
    public void startDb(Context context) {
        this.context = context;
        bikes = new ArrayList<>();
        api = GestiTallerApi.buildInstance();
        db = Room.databaseBuilder(context,
                        AppDatabase.class, "bike").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
    }

    @Override
    public void loadAllBikes(OnLoadBikesListener listener) {
        bikes.clear();

        Call<List<Bike>> bikeCall = api.getBikes();

        loadBikesCallEnqueue(listener, bikeCall);
    }

    @Override
    public void loadBikesByBrand(OnLoadBikesListener listener, String query) {
        bikes.clear();

        Call<List<Bike>> bikeCall = api.getBikesByBrand(query);

        loadBikesCallEnqueue(listener, bikeCall);
    }

    @Override
    public void loadBikesByModel(OnLoadBikesListener listener, String query) {
        bikes.clear();

        Call<List<Bike>> bikeCall = api.getBikesByModel(query);

        loadBikesCallEnqueue(listener, bikeCall);
    }

    @Override
    public void loadBikesByLicensePlate(OnLoadBikesListener listener, String query) {
        bikes.clear();

        Call<List<Bike>> bikesCall = api.getBikesByLicense(query);

        loadBikesCallEnqueue(listener, bikesCall);
    }

    /**
     * Envía la solicitud de forma asíncrona y notifica la devolución de llamada de su respuesta
     * o si se produjo un error al hablar con el servidor, crear la solicitud o procesar la respuesta.
     *
     * @param listener OnLoadBikesListener
     * @param call     Lista de Bikes
     */
    private void loadBikesCallEnqueue(OnLoadBikesListener listener, Call<List<Bike>> call) {
        call.enqueue(new Callback<List<Bike>>() {
            @Override
            public void onResponse(Call<List<Bike>> call, Response<List<Bike>> response) {
                bikes = response.body();
                listener.onLoadBikesSuccess(bikes);
            }

            @Override
            public void onFailure(Call<List<Bike>> call, Throwable t) {
                listener.onLoadBikesError("Se ha producido un error");
                t.printStackTrace();
            }
        });
    }

    @Override
    public void delete(OnDeleteBikeListener listener, Bike bike) {
        Call<Void> bikeCall = api.deleteBike(bike.getId());
        // db.bikeDao().delete(bike);
        bikeCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                listener.onDeleteBikeSuccess("Moto eliminada correctamente");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                listener.onDeleteBikeError("No se ha podido eliminar la moto");
                t.printStackTrace();
            }
        });
    }
}
