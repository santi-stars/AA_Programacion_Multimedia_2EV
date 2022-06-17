package com.svalero.gestitaller.presenter;

import com.svalero.gestitaller.contract.BikeListContract;
import com.svalero.gestitaller.domain.Bike;
import com.svalero.gestitaller.model.BikeListModel;
import com.svalero.gestitaller.view.BikeListView;

import java.util.List;

public class BikeListPresenter implements BikeListContract.Presenter, BikeListContract.Model.OnLoadBikesListener {

    private BikeListModel model;
    private BikeListView view;

    public BikeListPresenter(BikeListView view) {
        model = new BikeListModel();
        model.startDb(view.getApplicationContext());

        this.view = view;
    }

    @Override
    public void loadAllBikes() {
        model.loadAllBikes(this);
    }

    @Override
    public void loadBikesByBrand(String query) {
        view.listBikes(model.loadBikesByBrand(query));
    }

    @Override
    public void loadBikesByModel(String query) {
        view.listBikes(model.loadBikesByModel(query));
    }

    @Override
    public void loadBikesByLicensePlate(String query) {
        view.listBikes(model.loadBikesByLicensePlate(query));
    }

    @Override
    public void deleteBike(Bike bike) {
        model.delete(bike);
    }

    @Override   // RETROFIT
    public void onLoadBikesSuccess(List<Bike> bikes) {
        view.listBikes(bikes);
    }

    @Override   // RETROFIT
    public void onLoadBikesError(String message) {

    }
}
