package com.svalero.gestitaller.presenter;

import com.svalero.gestitaller.contract.ClientListContract;
import com.svalero.gestitaller.domain.Client;
import com.svalero.gestitaller.model.ClientListModel;
import com.svalero.gestitaller.view.ClientListView;

import java.util.List;

public class ClientListPresenter implements ClientListContract.Presenter, ClientListContract.Model.OnLoadClientsListener, ClientListContract.Model.OnDeleteClientListener {

    private ClientListModel model;
    private ClientListView view;

    public ClientListPresenter(ClientListView view) {
        model = new ClientListModel();
        model.startDb(view.getApplicationContext());

        this.view = view;
    }

    @Override
    public void loadAllClients() {
        model.loadAllClients(this);
    }

    @Override
    public void loadClientsByName(String query) {
        model.loadClientsByName(this, query);
    }

    @Override
    public void loadClientsBySurname(String query) {
        model.loadClientsBySurname(this, query);
    }

    @Override
    public void loadClientsByDni(String query) {
        model.loadClientsByDni(this, query);
    }

    @Override   // OnLoadClientsListener SUCCESS
    public void onLoadClientsSuccess(List<Client> clients) {
        view.listClients(clients);
    }

    @Override      // OnLoadClientsListener ERROR
    public void onLoadClientsError(int message) {
        view.showMessage(message);
    }

    @Override
    public void deleteClient(Client client) {
        model.deleteClient(this, client);
    }


    @Override   // OnDeleteClientsListener SUCCESS
    public void onDeleteClientSuccess(int message) {
        view.showMessage(message);
        view.refreshList();
    }

    @Override   // OnDeleteClientsListener ERROR
    public void onDeleteClientError(int message) {
        view.showMessage(message);
    }
}
