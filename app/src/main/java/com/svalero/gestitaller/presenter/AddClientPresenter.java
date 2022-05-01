package com.svalero.gestitaller.presenter;

import com.svalero.gestitaller.contract.AddClientContract;
import com.svalero.gestitaller.model.ClientListModel;
import com.svalero.gestitaller.view.ClientListView;

public class AddClientPresenter implements AddClientContract.Presenter {

    private ClientListModel model;
    private ClientListView view;

    public AddClientPresenter(ClientListView view) {
        model = new ClientListModel();
        model.startDb(view.getApplicationContext());

        this.view = view;
    }
}
