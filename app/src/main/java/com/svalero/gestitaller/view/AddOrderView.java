package com.svalero.gestitaller.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.svalero.gestitaller.R;
import com.svalero.gestitaller.contract.AddOrderContract;
import com.svalero.gestitaller.database.AppDatabase;
import com.svalero.gestitaller.domain.Bike;
import com.svalero.gestitaller.domain.Client;
import com.svalero.gestitaller.domain.Order;
import com.svalero.gestitaller.presenter.AddOrderPresenter;
import com.svalero.gestitaller.util.DateUtils;

import java.time.LocalDate;
import java.util.ArrayList;

public class AddOrderView extends AppCompatActivity implements AddOrderContract.View {

    private Spinner clientSpinner;
    private Spinner bikeSpinner;
    private EditText etDescription;
    private TextView tvDate;
    private Button addButton;
    private Intent intent;

    private Order order;
    private ArrayList<Client> clients;
    private ArrayList<Bike> bikes;
    private boolean modifyOrder;
    private AddOrderPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);

        clientSpinner = findViewById(R.id.client_spinner_add_order);
        bikeSpinner = findViewById(R.id.bike_spinner_add_order);
        etDescription = findViewById(R.id.description_edittext_add_order);
        tvDate = findViewById(R.id.date_textlabel_add_order);
        addButton = findViewById(R.id.add_order_button);

        order = new Order(0, null, 0, 0, "");

        presenter = new AddOrderPresenter(this);
        clients = new ArrayList<>();
        bikes = new ArrayList<>();
        fillSpinners(0);
        intent();

        clientSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fillSpinners(clients.get(clientSpinner.getSelectedItemPosition()).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        fillSpinners(0);
    }

    public void selectDate(View view) {
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String stringMonth = String.valueOf(month + 1);
                if ((month + 1) < 10) {
                    stringMonth = "0" + stringMonth;
                }
                tvDate.setText(dayOfMonth + "/" + stringMonth + "/" + year);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private void intent() {

        intent = getIntent();
        modifyOrder = intent.getBooleanExtra("modify_order", false);

        if (modifyOrder) {  // Si se edita una moto, obtiene sus datos y los pinta en el formulario
            order.setId(intent.getIntExtra("id", 0));
            tvDate.setText(DateUtils.fromLocalDateToMyDateFormatString
                    (LocalDate.parse(intent.getStringExtra("date"))));
            etDescription.setText(intent.getStringExtra("description"));

            addButton.setText(R.string.modify_capital);
        }
    }

    public void addOrder(View view) {

        order.setDate(DateUtils.fromMyDateFormatStringToLocalDate(tvDate.getText().toString().trim()));
        order.setDescription(etDescription.getText().toString().trim());

        if (bikeSpinner.getCount() == 0) {
            Toast.makeText(this, R.string.select_bike, Toast.LENGTH_SHORT).show();
        } else if ((order.getDescription().equals("")) || (String.valueOf(order.getDate()).equals(""))) {
            Toast.makeText(this, R.string.complete_all_fields, Toast.LENGTH_SHORT).show();
        } else {
            order.setClientId(clients.get(clientSpinner.getSelectedItemPosition()).getId());
            order.setBikeId(bikes.get(bikeSpinner.getSelectedItemPosition()).getId());

            if (modifyOrder) {
                modifyOrder = false;
                addButton.setText(R.string.add_button);
                presenter.updateOrder(order);
                Toast.makeText(this, R.string.modified_order, Toast.LENGTH_SHORT).show();
            } else {
                order.setId(0);
                presenter.insertOrder(order);
                Toast.makeText(this, R.string.added_order, Toast.LENGTH_SHORT).show();
            }

            etDescription.setText("");
            tvDate.setText("");

        }
    }

    /**
     * Rellena los spinner para añadirlos a la orden
     *
     * @param idClient 0 rellena el spinner de clients con todos ellos,
     *                 > 0 rellena el spinner de bikes con las motos del id enviado por parametro
     */
    private void fillSpinners(int idClient) {

        if (idClient == 0) {    // CLIENT
            presenter.fillClientSpinner();
        } else {    // BIKE
            presenter.fillBikeSpinner(idClient);
        }

    }

    @Override
    public void fillBikeSpinner(ArrayList<Bike> bikes) {

        this.bikes.clear();
        this.bikes.addAll(bikes);

        String[] arrayBikeSpinner = new String[bikes.size()];

        for (int i = 0; i < bikes.size(); i++) {
            arrayBikeSpinner[i] = bikes.get(i).getBrand() + " " + bikes.get(i).getModel();
        }

        ArrayAdapter<String> adapterBikeSpinner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayBikeSpinner);
        bikeSpinner.setAdapter(adapterBikeSpinner);

    }

    @Override
    public void fillClientSpinner(ArrayList<Client> clients) {

        this.clients.clear();
        this.clients.addAll(clients);

        String[] arrayClientSpinner = new String[clients.size()];

        for (int i = 0; i < clients.size(); i++) {
            arrayClientSpinner[i] = clients.get(i).getName() + " " + clients.get(i).getSurname();
        }

        ArrayAdapter<String> adapterClientSpinner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayClientSpinner);
        clientSpinner.setAdapter(adapterClientSpinner);

    }
}
