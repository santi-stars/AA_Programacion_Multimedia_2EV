package com.svalero.gestitaller.model;

import android.content.Context;

import androidx.room.Room;

import com.svalero.gestitaller.contract.AddBikeContract;
import com.svalero.gestitaller.database.AppDatabase;

public class AddBikeModel implements AddBikeContract.Model {

    private AppDatabase db;

    @Override
    public void startDb(Context context) {
        db = Room.databaseBuilder(context,
                AppDatabase.class, "bike").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
    }
}
