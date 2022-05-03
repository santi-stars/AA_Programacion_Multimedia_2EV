package com.svalero.gestitaller.presenter;

import android.content.Context;
import android.widget.Toast;

import com.svalero.gestitaller.R;
import com.svalero.gestitaller.contract.AddOrderContract;
import com.svalero.gestitaller.domain.Bike;
import com.svalero.gestitaller.domain.Client;
import com.svalero.gestitaller.domain.Order;
import com.svalero.gestitaller.model.AddOrderModel;
import com.svalero.gestitaller.view.AddOrderView;

public class AddOrderPresenter implements AddOrderContract.Presenter {

    private AddOrderModel model;
    private AddOrderView view;
    private Context context;

    public AddOrderPresenter(AddOrderView view) {
        this.context = view.getApplicationContext();
        this.view = view;

        model = new AddOrderModel();
    }

    @Override
    public void addOrder(Order order, Boolean modifyOrder) {

        model.startDb(view.getApplicationContext());

        if (view.getBikeSpinner().getCount() == 0) {
            Toast.makeText(context, R.string.select_bike, Toast.LENGTH_SHORT).show();
        } else if ((order.getDescription().equals("")) || (order.getDate() == null)) {
            Toast.makeText(context, R.string.complete_all_fields, Toast.LENGTH_SHORT).show();
        } else {
            order.setClientId(view.getClients().get(view.getClientSpinner().getSelectedItemPosition()).getId());
            order.setBikeId(view.getBikes().get(view.getBikeSpinner().getSelectedItemPosition()).getId());

            if (modifyOrder) {
                view.setModifyOrder(false);
                view.getAddButton().setText(R.string.add_button);
                model.updateOrder(order);
                Toast.makeText(context, R.string.modified_order, Toast.LENGTH_SHORT).show();
            } else {
                order.setId(0);
                model.insertOrder(order);
                Toast.makeText(context, R.string.added_order, Toast.LENGTH_SHORT).show();
            }
            view.cleanForm();
        }
    }

    @Override
    public void fillBikeSpinner(int clientId) {
        view.fillBikeSpinner(model.clientBikes(clientId, view.getApplicationContext()));
    }

    @Override
    public void fillClientSpinner() {
        view.fillClientSpinner(model.clients(view.getApplicationContext()));
    }
}
