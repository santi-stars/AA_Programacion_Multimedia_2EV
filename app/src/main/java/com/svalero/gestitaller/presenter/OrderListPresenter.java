package com.svalero.gestitaller.presenter;

import com.svalero.gestitaller.contract.OrderListContract;
import com.svalero.gestitaller.model.OrderListModel;
import com.svalero.gestitaller.view.OrderListView;

public class OrderListPresenter implements OrderListContract.Presenter {

    private OrderListModel model;
    private OrderListView view;

    public OrderListPresenter(OrderListView view) {
        model = new OrderListModel();
        model.startDb(view.getApplicationContext());

        this.view = view;
    }
}
