package com.svalero.gestitaller.contract;

import android.content.Context;

import com.svalero.gestitaller.domain.Bike;

import java.util.ArrayList;

public interface BikeListContract {
    interface Model {
        void startDb(Context context);

        ArrayList<Bike> loadAllBikes();

        ArrayList<Bike> loadBikesByBrand(String query);

        ArrayList<Bike> loadBikesByModel(String query);

        ArrayList<Bike> loadBikesByLicensePlate(String query);

        void delete(Bike bike);
    }

    interface View {
        void listBikes(ArrayList<Bike> bikes);

    }

    interface Presenter {
        void loadAllBikes();

        void loadBikesByBrand(String query);

        void loadBikesByModel(String query);

        void loadBikesByLicensePlate(String query);

        void deleteBike(Bike bike);
    }
}
