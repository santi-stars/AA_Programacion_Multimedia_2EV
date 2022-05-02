package com.svalero.gestitaller.model;

import android.content.Context;

import androidx.room.Room;

import com.svalero.gestitaller.contract.ClientListContract;
import com.svalero.gestitaller.database.AppDatabase;
import com.svalero.gestitaller.domain.Client;

import java.util.ArrayList;

public class ClientListModel implements ClientListContract.Model {

    private AppDatabase db;

    @Override
    public void startDb(Context context) {
        db = Room.databaseBuilder(context,
                AppDatabase.class, "client").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
    }

    @Override
    public ArrayList<Client> loadAllClients() {
        return (ArrayList<Client>) db.clientDao().getAll();
    }

    @Override
    public ArrayList<Client> loadClientsByName(String query) {
        return (ArrayList<Client>) db.clientDao().getByNameString(query);
    }

    @Override
    public ArrayList<Client> loadClientsBySurname(String query) {
        return (ArrayList<Client>) db.clientDao().getBySurnameString(query);
    }

    @Override
    public ArrayList<Client> loadClientsByDni(String query) {
        return (ArrayList<Client>) db.clientDao().getByDniString(query);
    }

    @Override
    public void deleteClient(Client client) {
        db.clientDao().delete(client);
    }
}
