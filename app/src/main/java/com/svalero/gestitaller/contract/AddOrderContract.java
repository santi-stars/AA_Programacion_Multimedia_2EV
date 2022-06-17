package com.svalero.gestitaller.contract;

import android.content.Context;

import com.svalero.gestitaller.domain.Bike;
import com.svalero.gestitaller.domain.Client;
import com.svalero.gestitaller.domain.WorkOrder;

import java.util.ArrayList;

public interface AddOrderContract {
    interface Model {
        void startDb(Context context);

        void insertOrder(WorkOrder workOrder);

        void updateOrder(WorkOrder workOrder);

        ArrayList<Bike> clientBikes(int clientId, Context context);

        ArrayList<Client> clients(Context context);
    }

    interface View {
        void fillBikeSpinner(ArrayList<Bike> bikes);

        void fillClientSpinner(ArrayList<Client> clients);

        void cleanForm();
    }

    interface Presenter {
        void addOrder(WorkOrder workOrder, Boolean modifyOrder);

        void fillBikeSpinner(int clientId);

        void fillClientSpinner();
    }
}
