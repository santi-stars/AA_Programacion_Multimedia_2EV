package com.svalero.gestitaller.presenter;

import android.content.Context;
import android.widget.Toast;

import com.svalero.gestitaller.R;
import com.svalero.gestitaller.contract.AddBikeContract;
import com.svalero.gestitaller.domain.Bike;
import com.svalero.gestitaller.domain.Client;
import com.svalero.gestitaller.domain.dto.BikeDTO;
import com.svalero.gestitaller.model.AddBikeModel;
import com.svalero.gestitaller.view.AddBikeView;

import java.util.List;

public class AddBikePresenter implements AddBikeContract.Presenter, AddBikeContract.Model.OnLoadClientsListener, AddBikeContract.Model.OnAddBikeListener, AddBikeContract.Model.OnModifyBikeListener {

    private AddBikeModel model;
    private AddBikeView view;
    private Context context;

    public AddBikePresenter(AddBikeView view) {
        this.context = view.getApplicationContext();
        this.view = view;

        model = new AddBikeModel();
        model.startDb(context);
    }

    @Override
    public void loadClientsSpinner() {
        model.loadAllClient(this);
    }

    @Override
    public void addOrModifyBike(BikeDTO bikeDTO, Boolean modifyBike) {

        if ((bikeDTO.getBrand().equals("")) || (bikeDTO.getModel().equals("")) || (bikeDTO.getLicensePlate().equals(""))) {
            view.showMessage(R.string.complete_all_fields);
        } else {
            if (modifyBike) {
                view.setModifyBike(false);
                view.getAddButton().setText(R.string.add_button);
                model.modifyBike(this, bikeDTO);
            } else {
                bikeDTO.setId(0);
                model.addBike(this, bikeDTO);
            }
        }

    }

    @Override
    public void onLoadClientsSuccess(List<Client> clients) {
        view.loadClientSpinner(clients);
    }

    @Override
    public void onLoadClientsError(int message) {
        view.showMessage(message);
    }

    @Override
    public void onAddBikeSuccess(int message) {
        view.showMessage(message);
        view.cleanForm();
    }

    @Override
    public void onAddBikeError(int message) {
        view.showMessage(message);
    }

    @Override
    public void onModifyBikeSuccess(int message) {
        view.showMessage(message);
        view.cleanForm();
    }

    @Override
    public void onModifyBikeError(int message) {
        view.showMessage(message);
    }
}
