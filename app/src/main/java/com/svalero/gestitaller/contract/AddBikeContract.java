package com.svalero.gestitaller.contract;

import android.content.Context;

import com.svalero.gestitaller.domain.Bike;
import com.svalero.gestitaller.domain.Client;
import com.svalero.gestitaller.domain.dto.BikeDTO;

import java.util.ArrayList;
import java.util.List;

public interface AddBikeContract {
    interface Model {
        // CLIENTS
        interface OnLoadClientsListener {
            void onLoadClientsSuccess(List<Client> clients);

            void onLoadClientsError(int message);
        }

        // BIKES
        interface OnAddBikeListener {
            void onAddBikeSuccess(int message);

            void onAddBikeError(int message);
        }

        interface OnModifyBikeListener {
            void onModifyBikeSuccess(int message);

            void onModifyBikeError(int message);
        }

        void startDb(Context context);

        void addBike(OnAddBikeListener listener, BikeDTO bikeDTO);

        void modifyBike(OnModifyBikeListener listener, BikeDTO bikeDTO);

        void loadAllClient(OnLoadClientsListener listener);
    }

    interface View {
        void loadClientSpinner(List<Client> clients);

        void addBike(android.view.View view);

        void cleanForm();

        void showMessage(int message);
    }

    interface Presenter {
        void loadClientsSpinner();

        void addOrModifyBike(BikeDTO bikeDTO, Boolean modifyBike);
    }
}
