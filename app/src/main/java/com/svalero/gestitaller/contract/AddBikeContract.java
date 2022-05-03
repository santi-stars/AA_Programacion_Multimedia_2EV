package com.svalero.gestitaller.contract;

import android.content.Context;

import com.svalero.gestitaller.domain.Bike;
import com.svalero.gestitaller.domain.Client;

import java.util.ArrayList;

public interface AddBikeContract {
    interface Model {
        void startDb(Context context);

        void insertBike(Bike bike);

        void updateBike(Bike bike);

        ArrayList<Client> loadAllClient(Context context);
    }

    interface View {
        void loadClientSpinner(ArrayList<Client> clients);

        void cleanForm();
    }

    interface Presenter {
        void loadClientsSpinner();

        void addBike(Bike bike, Boolean modifyBike);
    }
}
