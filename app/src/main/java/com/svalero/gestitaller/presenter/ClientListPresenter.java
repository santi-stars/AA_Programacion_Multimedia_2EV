package com.svalero.gestitaller.presenter;

import com.svalero.gestitaller.contract.ClientListContract;
import com.svalero.gestitaller.model.ClientListModel;
import com.svalero.gestitaller.view.ClientListView;

public class ClientListPresenter implements ClientListContract.Presenter {

    private ClientListModel model;
    private ClientListView view;

    public ClientListPresenter(ClientListView view) {
        model = new ClientListModel();
        model.startDb(view.getApplicationContext());

        this.view = view;
    }
}
