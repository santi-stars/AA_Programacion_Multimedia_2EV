package com.svalero.gestitaller.model;

import android.content.Context;

import androidx.room.Room;

import com.svalero.gestitaller.contract.BikeListContract;
import com.svalero.gestitaller.database.AppDatabase;
import com.svalero.gestitaller.domain.Bike;

import java.util.ArrayList;

public class BikeListModel implements BikeListContract.Model {

    private AppDatabase db;

    @Override
    public void startDb(Context context) {
        db = Room.databaseBuilder(context,
                AppDatabase.class, "bike").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
    }

    @Override
    public ArrayList<Bike> loadAllBikes() {
        return (ArrayList<Bike>) db.bikeDao().getAll();
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
