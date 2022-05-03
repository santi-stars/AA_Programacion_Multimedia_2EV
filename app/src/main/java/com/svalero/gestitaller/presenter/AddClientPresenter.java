package com.svalero.gestitaller.presenter;

import android.content.Context;
import android.widget.Toast;

import com.svalero.gestitaller.R;
import com.svalero.gestitaller.contract.AddClientContract;
import com.svalero.gestitaller.domain.Client;
import com.svalero.gestitaller.model.AddClientModel;
import com.svalero.gestitaller.view.AddClientView;

public class AddClientPresenter implements AddClientContract.Presenter {

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
    public void addClient(Client client, Boolean modifyClient) {
        if ((client.getName().equals("")) || (client.getSurname().equals("")) || (client.getDni().equals(""))) {
            Toast.makeText(context, R.string.complete_all_fields, Toast.LENGTH_SHORT).show();
        } else if (client.getLatitude() == 0 && client.getLongitude() == 0) {
            Toast.makeText(context, R.string.select_map_position, Toast.LENGTH_SHORT).show();
        } else {

            if (modifyClient) {
                view.setModifyClient(false);
                view.getAddButton().setText(R.string.add_button);
                model.updateClient(client);
                Toast.makeText(context, R.string.modified_client, Toast.LENGTH_SHORT).show();
            } else {
                client.setId(0);
                model.insertClient(client);
                Toast.makeText(context, R.string.added_client, Toast.LENGTH_SHORT).show();
            }
            view.cleanForm();
        }
    }
}
