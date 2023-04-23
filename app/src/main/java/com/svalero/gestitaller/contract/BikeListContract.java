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

        interface OnDeleteBikeListener{
            void onDeleteBikeSuccess(String message);

            void onDeleteBikeError(String message);
        }

        void startDb(Context context);

        void loadAllBikes(OnLoadBikesListener listener);

        void loadBikesByBrand(OnLoadBikesListener listener, String query);

        void loadBikesByModel(OnLoadBikesListener listener, String query);

        void loadBikesByLicensePlate(OnLoadBikesListener listener, String query);

        void delete(OnDeleteBikeListener listener, Bike bike);
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
