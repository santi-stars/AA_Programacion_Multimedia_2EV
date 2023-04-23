package com.svalero.gestitaller.presenter;

import android.content.Context;

import com.svalero.gestitaller.R;
import com.svalero.gestitaller.contract.AddClientContract;
import com.svalero.gestitaller.domain.Client;
import com.svalero.gestitaller.model.AddClientModel;
import com.svalero.gestitaller.view.AddClientView;

public class AddClientPresenter implements AddClientContract.Presenter, AddClientContract.Model.OnAddClientListener, AddClientContract.Model.OnModifyClientListener {

    private AddClientModel model;
    private AddClientView view;
    private Context context;

    public AddClientPresenter(AddClientView view) {
        this.view = view;
        this.context = view.getApplicationContext();

        model = new AddClientModel();
        model.startDb(context);
    }

    @Override
    public void addOrModifyClient(Client client, Boolean modifyClient) {
        if ((client.getName().equals("")) || (client.getSurname().equals("")) || (client.getDni().equals(""))) {
            view.showMessage(R.string.complete_all_fields);
        } else if (client.getLatitude() == 0 && client.getLongitude() == 0) {
            view.showMessage(R.string.select_map_position);
        } else {

            if (modifyClient) {
                view.setModifyClient(false);
                view.getAddButton().setText(R.string.add_button);
                model.modifyClient(this, client);
            } else {
                client.setId(0);
                model.addClient(this, client);
            }
        }
    }

    @Override
    public void onAddClientSuccess(int message) {
        view.showMessage(message);
        view.cleanForm();
    }

    @Override
    public void onAddClientError(int message) {
        view.showMessage(message);
    }

    @Override
    public void onModifyClientSuccess(int message) {
        view.showMessage(message);
        view.cleanForm();
    }

    @Override
    public void onModifyClientError(int message) {
        view.showMessage(message);
    }
}
