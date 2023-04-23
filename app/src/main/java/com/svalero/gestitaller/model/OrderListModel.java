package com.svalero.gestitaller.model;

import android.content.Context;

import androidx.room.Room;

import com.svalero.gestitaller.contract.OrderListContract;
import com.svalero.gestitaller.database.AppDatabase;
import com.svalero.gestitaller.domain.Bike;
import com.svalero.gestitaller.domain.Client;
import com.svalero.gestitaller.domain.WorkOrder;

import java.util.ArrayList;

public class OrderListModel implements OrderListContract.Model {

    private Context context;
    private AppDatabase db;

    public OrderListModel(Context context) {
        this.context = context;
    }


    @Override
    public void startDb(Context context) {
        db = Room.databaseBuilder(context,
                AppDatabase.class, "order").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
    }

    @Override
    public ArrayList<WorkOrder> loadAllOrders() {
        return (ArrayList<WorkOrder>) db.orderDao().getAll();
    }

    @Override
    public void deleteOrder(WorkOrder workOrder) {
        db.orderDao().delete(workOrder);
    }

    @Override
    public Client loadClientById(int id) {
        db = Room.databaseBuilder(context,
                AppDatabase.class, "client").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();

        return db.clientDao().getClientById(id);
    }

    @Override
    public Bike loadBikeById(int id) {
        db = Room.databaseBuilder(context,
                AppDatabase.class, "bike").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();

        return db.bikeDao().getBikeById(id);
    }
}
