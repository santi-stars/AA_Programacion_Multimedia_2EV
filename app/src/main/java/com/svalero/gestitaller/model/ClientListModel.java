package com.svalero.gestitaller.model;

import android.content.Context;

import androidx.room.Room;

import com.svalero.gestitaller.contract.ClientListContract;
import com.svalero.gestitaller.database.AppDatabase;

public class ClientListModel implements ClientListContract.Model {

    private AppDatabase db;

    @Override
    public void startDb(Context context) {
        db = Room.databaseBuilder(context,
                AppDatabase.class, "client").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
    }
}
