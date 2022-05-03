package com.svalero.gestitaller.contract;

import android.content.Context;

import com.svalero.gestitaller.domain.Bike;
import com.svalero.gestitaller.domain.Client;
import com.svalero.gestitaller.domain.Order;

import java.util.ArrayList;

public interface AddOrderContract {
    interface Model {
        void startDb(Context context);

        void insertOrder(Order order);

        void updateOrder(Order order);

        ArrayList<Bike> clientBikes(int clientId, Context context);

        ArrayList<Client> clients(Context context);
    }

    interface View {
        void fillBikeSpinner(ArrayList<Bike> bikes);

        void fillClientSpinner(ArrayList<Client> clients);

        void cleanForm();
    }

    interface Presenter {
        void addOrder(Order order, Boolean modifyOrder);

        void fillBikeSpinner(int clientId);

        void fillClientSpinner();
    }
}
