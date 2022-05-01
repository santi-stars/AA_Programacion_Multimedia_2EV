package com.svalero.gestitaller.model;

import android.content.Context;

import androidx.room.Room;

import com.svalero.gestitaller.contract.AddOrderContract;
import com.svalero.gestitaller.database.AppDatabase;

public class AddOrderModel implements AddOrderContract.Model {

    private AppDatabase db;

    @Override
    public void startDb(Context context) {
        db = Room.databaseBuilder(context,
                AppDatabase.class, "order").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
    }
}
