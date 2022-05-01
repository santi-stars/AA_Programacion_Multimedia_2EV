package com.svalero.gestitaller.model;

import android.content.Context;

import androidx.room.Room;

import com.svalero.gestitaller.contract.OrderListContract;
import com.svalero.gestitaller.database.AppDatabase;

public class OrderListModel implements OrderListContract.Model {

    private AppDatabase db;

    @Override
    public void startDb(Context context) {
        db = Room.databaseBuilder(context,
                AppDatabase.class, "order").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
    }
}
