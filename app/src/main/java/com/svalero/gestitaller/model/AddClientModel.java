package com.svalero.gestitaller.model;

import android.content.Context;

import androidx.room.Room;

import com.svalero.gestitaller.contract.AddClientContract;
import com.svalero.gestitaller.database.AppDatabase;
import com.svalero.gestitaller.domain.Client;

public class AddClientModel implements AddClientContract.Model {

    private AppDatabase db;

    @Override
    public void startDb(Context context) {
        db = Room.databaseBuilder(context,
                AppDatabase.class, "client").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
    }

    @Override
    public void insertClient(Client client) {
        db.clientDao().insert(client);
    }

    @Override
    public void updateClient(Client client) {
        db.clientDao().update(client);
    }
}
