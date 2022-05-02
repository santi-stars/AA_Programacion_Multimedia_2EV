package com.svalero.gestitaller.presenter;

import com.svalero.gestitaller.contract.ClientListContract;
import com.svalero.gestitaller.domain.Client;
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

    @Override
    public void loadAllClients() {
        view.listClients(model.loadAllClients());
    }

    @Override
    public void loadClientsByName(String query) {
        view.listClients(model.loadClientsByName(query));
    }

    @Override
    public void loadClientsBySurname(String query) {
        view.listClients(model.loadClientsBySurname(query));
    }

    @Override
    public void loadClientsByDni(String query) {
        view.listClients(model.loadClientsByDni(query));
    }

    @Override
    public void deleteClient(Client client) {
        model.deleteClient(client);
    }
}
