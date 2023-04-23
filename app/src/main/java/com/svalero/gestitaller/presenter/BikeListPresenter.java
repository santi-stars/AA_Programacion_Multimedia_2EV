package com.svalero.gestitaller.presenter;

import com.svalero.gestitaller.contract.BikeListContract;
import com.svalero.gestitaller.domain.Bike;
import com.svalero.gestitaller.model.BikeListModel;
import com.svalero.gestitaller.view.BikeListView;

import java.util.List;

public class BikeListPresenter implements BikeListContract.Presenter, BikeListContract.Model.OnLoadBikesListener, BikeListContract.Model.OnDeleteBikeListener {

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
        model.loadBikesByBrand(this, query);
    }

    @Override
    public void loadBikesByModel(String query) {
        model.loadBikesByModel(this, query);
    }

    @Override
    public void loadBikesByLicensePlate(String query) {
        model.loadBikesByLicensePlate(this, query);
    }

    @Override   // OnLoadBikesListener SUCCESS
    public void onLoadBikesSuccess(List<Bike> bikes) {
        view.listBikes(bikes);
    }

    @Override   // OnLoadBikesListener ERROR
    public void onLoadBikesError(String message) {
        // TODO view.showMessage(message);
    }

    @Override
    public void deleteBike(Bike bike) {
        model.delete(this, bike);
    }

    @Override   // OnDeleteBikesListener SUCCESS
    public void onDeleteBikeSuccess(String message) {
        // TODO view.showMessage(message);
    }

    @Override   // OnDeleteBikesListener ERROR
    public void onDeleteBikeError(String message) {
        // TODO view.showMessage(message);
    }
}
