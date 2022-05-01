package com.svalero.gestitaller.presenter;

import com.svalero.gestitaller.contract.AddOrderContract;
import com.svalero.gestitaller.model.OrderListModel;
import com.svalero.gestitaller.view.OrderListView;

public class AddOrderPresenter implements AddOrderContract.Presenter {

    private OrderListModel model;
    private OrderListView view;

    public AddOrderPresenter(OrderListView view) {
        model = new OrderListModel();
        model.startDb(view.getApplicationContext());

        this.view = view;
    }
}
