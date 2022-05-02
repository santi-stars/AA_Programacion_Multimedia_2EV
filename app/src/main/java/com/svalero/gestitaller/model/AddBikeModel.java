package com.svalero.gestitaller.model;

import android.content.Context;

import androidx.room.Room;

import com.svalero.gestitaller.contract.AddBikeContract;
import com.svalero.gestitaller.database.AppDatabase;
import com.svalero.gestitaller.domain.Bike;
import com.svalero.gestitaller.domain.Client;

import java.util.ArrayList;

public class AddBikeModel implements AddBikeContract.Model {

    private AppDatabase db;

    @Override
    public void startDb(Context context) {
        db = Room.databaseBuilder(context,
                AppDatabase.class, "bike").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
    }

    @Override
    public void insertBike(Bike bike) {
        db.bikeDao().insert(bike);
    }

    @Override
    public void updateBike(Bike bike) {
        db.bikeDao().update(bike);
    }

    @Override
    public ArrayList<Client> loadAllClient(Context context) {
        db = Room.databaseBuilder(context,
                AppDatabase.class, "client").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
        return (ArrayList<Client>) db.clientDao().getAll();
    }
}
