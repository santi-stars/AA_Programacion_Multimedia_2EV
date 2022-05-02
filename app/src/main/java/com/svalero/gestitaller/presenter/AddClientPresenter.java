package com.svalero.gestitaller.presenter;

import com.svalero.gestitaller.contract.AddClientContract;
import com.svalero.gestitaller.domain.Client;
import com.svalero.gestitaller.model.AddClientModel;
import com.svalero.gestitaller.view.AddClientView;

public class AddClientPresenter implements AddClientContract.Presenter {

    private AddClientModel model;
    private AddClientView view;

    public AddClientPresenter(AddClientView view) {
        model = new AddClientModel();
        model.startDb(view.getApplicationContext());

        this.view = view;
    }

    @Override
    public void insertClient(Client client) {
        model.insertClient(client);
    }

    @Override
    public void updateClient(Client client) {
        model.updateClient(client);
    }
}
