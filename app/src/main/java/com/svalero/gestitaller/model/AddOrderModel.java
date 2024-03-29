package com.svalero.gestitaller.model;

import android.content.Context;

import androidx.room.Room;

import com.svalero.gestitaller.contract.AddOrderContract;
import com.svalero.gestitaller.database.AppDatabase;
import com.svalero.gestitaller.domain.Bike;
import com.svalero.gestitaller.domain.Client;
import com.svalero.gestitaller.domain.WorkOrder;

import java.util.ArrayList;

public class AddOrderModel implements AddOrderContract.Model {

    private AppDatabase db;

    @Override
    public void startDb(Context context) {
        db = Room.databaseBuilder(context,
                AppDatabase.class, "order").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
    }

    @Override
    public void insertOrder(WorkOrder workOrder) {
        db.orderDao().insert(workOrder);
    }

    @Override
    public void updateOrder(WorkOrder workOrder) {
        db.orderDao().update(workOrder);
    }

    @Override
    public ArrayList<Bike> clientBikes(int clientId, Context context) {
        db = Room.databaseBuilder(context,
                AppDatabase.class, "bike").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
        //return (ArrayList<Bike>) db.bikeDao().getBikesByClientId(clientId);
        return null;
    }

    @Override
    public ArrayList<Client> clients(Context context) {
        db = Room.databaseBuilder(context,
                AppDatabase.class, "client").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
        return (ArrayList<Client>) db.clientDao().getAll();
    }
}
