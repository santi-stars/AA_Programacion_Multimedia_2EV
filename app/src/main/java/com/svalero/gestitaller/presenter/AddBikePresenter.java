package com.svalero.gestitaller.presenter;

import android.widget.Toast;

import com.svalero.gestitaller.R;
import com.svalero.gestitaller.contract.AddBikeContract;
import com.svalero.gestitaller.domain.Bike;
import com.svalero.gestitaller.model.AddBikeModel;
import com.svalero.gestitaller.view.AddBikeView;

public class AddBikePresenter implements AddBikeContract.Presenter {

    private AddBikeModel model;
    private AddBikeView view;

    public AddBikePresenter(AddBikeView view) {
        model = new AddBikeModel();
        this.view = view;
    }

    @Override
    public void loadClientsSpinner() {
        view.loadClientSpinner(model.loadAllClient(view.getApplicationContext()));
    }

    @Override
    public void addBike(Bike bike, Boolean modifyBike) {

        model.startDb(view.getApplicationContext());

        if ((bike.getBrand().equals("")) || (bike.getModel().equals("")) || (bike.getLicensePlate().equals(""))) {
            Toast.makeText(view.getApplicationContext(), R.string.complete_all_fields, Toast.LENGTH_SHORT).show();
        } else {

            if (modifyBike) {
                view.setModifyBike(false);
                view.getAddButton().setText(R.string.add_button);
                model.updateBike(bike);
                Toast.makeText(view.getApplicationContext(), R.string.modified_bike, Toast.LENGTH_SHORT).show();
            } else {
                bike.setId(0);
                model.insertBike(bike);
                Toast.makeText(view.getApplicationContext(), R.string.added_bike, Toast.LENGTH_SHORT).show();
            }
            view.cleanForm();
        }
    }
}
