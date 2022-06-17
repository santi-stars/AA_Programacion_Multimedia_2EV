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
    public ArrayList<Bike> loadAllBikesArray() {
        return (ArrayList<Bike>) db.bikeDao().getAll();
    }

    @Override
    public void loadAllBikes(OnLoadBikesListener listener) {
        bikes.clear();

        Call<List<Bike>> callBikes = api.getBikes();

        callBikes.enqueue(new Callback<List<Bike>>() {
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
    public ArrayList<Bike> loadBikesByBrand(String query) {
        return (ArrayList<Bike>) db.bikeDao().getByBrandString(query);
    }

    @Override
    public ArrayList<Bike> loadBikesByModel(String query) {
        return (ArrayList<Bike>) db.bikeDao().getByModelString(query);
    }

    @Override
    public ArrayList<Bike> loadBikesByLicensePlate(String query) {
        return (ArrayList<Bike>) db.bikeDao().getByLicensePlateString(query);
    }

    @Override
    public void delete(Bike bike) {
        db.bikeDao().delete(bike);
    }
}
