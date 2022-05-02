package com.svalero.gestitaller.presenter;

import com.svalero.gestitaller.contract.AddBikeContract;
import com.svalero.gestitaller.domain.Bike;
import com.svalero.gestitaller.model.AddBikeModel;
import com.svalero.gestitaller.view.AddBikeView;

public class AddBikePresenter implements AddBikeContract.Presenter {

    private AddBikeModel model;
    private AddBikeView view;

    public AddBikePresenter(AddBikeView view) {
        model = new AddBikeModel();
        this.view = view;
    }

    @Override
    public void loadClientsSpinner() {
        view.loadClientSpinner(model.loadAllClient(view.getApplicationContext()));
    }

    @Override
    public void insertBike(Bike bike) {
        model.startDb(view.getApplicationContext());
        model.insertBike(bike);
    }

    @Override
    public void updateBike(Bike bike) {
        model.startDb(view.getApplicationContext());
        model.updateBike(bike);
    }
}
