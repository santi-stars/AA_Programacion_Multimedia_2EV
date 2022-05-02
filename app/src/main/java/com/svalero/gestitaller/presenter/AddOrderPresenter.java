package com.svalero.gestitaller.presenter;

import com.svalero.gestitaller.contract.AddOrderContract;
import com.svalero.gestitaller.domain.Order;
import com.svalero.gestitaller.model.AddOrderModel;
import com.svalero.gestitaller.view.AddOrderView;

public class AddOrderPresenter implements AddOrderContract.Presenter {

    private AddOrderModel model;
    private AddOrderView view;

    public AddOrderPresenter(AddOrderView view) {
        model = new AddOrderModel();
        this.view = view;
    }

    @Override
    public void insertOrder(Order order) {
        model.startDb(view.getApplicationContext());
        model.insertOrder(order);
    }

    @Override
    public void updateOrder(Order order) {
        model.startDb(view.getApplicationContext());
        model.updateOrder(order);
    }

    @Override
    public void fillBikeSpinner(int clientId) {
        view.fillBikeSpinner(model.clientBikes(clientId, view.getApplicationContext()));
    }

    @Override
    public void fillClientSpinner() {
        view.fillClientSpinner(model.clients(view.getApplicationContext()));
    }
}
