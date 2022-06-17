package com.svalero.gestitaller.contract;

import android.content.Context;

import com.svalero.gestitaller.domain.Bike;

import java.util.ArrayList;
import java.util.List;

public interface BikeListContract {
    interface Model {
        interface OnLoadBikesListener {
            void onLoadBikesSuccess(List<Bike> bikes);

            void onLoadBikesError(String message);
        }

        void startDb(Context context);

        void loadAllBikes(OnLoadBikesListener listener);

        ArrayList<Bike> loadAllBikesArray();

        ArrayList<Bike> loadBikesByBrand(String query);

        ArrayList<Bike> loadBikesByModel(String query);

        ArrayList<Bike> loadBikesByLicensePlate(String query);

        void delete(Bike bike);
    }

    interface View {
        void listBikes(List<Bike> bikes);

        void showMessage(String message);
    }

    interface Presenter {
        void loadAllBikes();

        void loadBikesByBrand(String query);

        void loadBikesByModel(String query);

        void loadBikesByLicensePlate(String query);

        void deleteBike(Bike bike);
    }
}
