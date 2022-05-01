package com.svalero.gestitaller.presenter;

import com.svalero.gestitaller.contract.AddBikeContract;
import com.svalero.gestitaller.model.BikeListModel;
import com.svalero.gestitaller.view.BikeListView;

public class AddBikePresenter implements AddBikeContract.Presenter {

    private BikeListModel model;
    private BikeListView view;

    public AddBikePresenter(BikeListView view) {
        model = new BikeListModel();
        model.startDb(view.getApplicationContext());

        this.view = view;
    }
}
