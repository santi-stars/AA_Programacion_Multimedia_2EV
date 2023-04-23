package com.svalero.gestitaller.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.svalero.gestitaller.R;
import com.svalero.gestitaller.contract.AddBikeContract;
import com.svalero.gestitaller.domain.Bike;
import com.svalero.gestitaller.domain.Client;
import com.svalero.gestitaller.domain.dto.BikeDTO;
import com.svalero.gestitaller.presenter.AddBikePresenter;
import com.svalero.gestitaller.util.ImageUtils;

import java.util.ArrayList;
import java.util.List;

public class AddBikeView extends AppCompatActivity implements AddBikeContract.View {

    private BikeDTO bikeDTO;

    private Button addButton;
    private ImageView bikeImage;
    private Spinner clientSpinner;
    private EditText etBrand;
    private EditText etModel;
    private EditText etLicensePlate;
    private Intent intent;
    private AddBikePresenter presenter;

    private boolean modifyBike;
    public List<Client> clients;

    public Button getAddButton() {
        return addButton;
    }

    public void setModifyBike(boolean modifyBike) {
        this.modifyBike = modifyBike;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bike);

        bikeImage = findViewById(R.id.bike_imageView);
        etBrand = findViewById(R.id.brand_edittext_add_bike);
        etModel = findViewById(R.id.model_edittext_add_bike);
        etLicensePlate = findViewById(R.id.license_plate_edittext_add_bike);
        clientSpinner = findViewById(R.id.client_spinner_add_bike);
        addButton = findViewById(R.id.add_bike_button);

        presenter = new AddBikePresenter(this);
        bikeDTO = new BikeDTO();
        clients = new ArrayList<>();

        presenter.loadClientsSpinner(); //MVP
        intent();
    }


    @Override
    protected void onResume() {
        super.onResume();

        presenter.loadClientsSpinner(); //MVP
    }

    @Override
    public void loadClientSpinner(List<Client> clients) {

        this.clients.clear();
        this.clients.addAll(clients);

        String[] arraySpinner = new String[clients.size()];

        for (int i = 0; i < clients.size(); i++) {
            arraySpinner[i] = clients.get(i).getName() + " " + clients.get(i).getSurname();
        }

        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arraySpinner);
        clientSpinner.setAdapter(adapterSpinner);

    }

    private void intent() {

        intent = getIntent();
        modifyBike = intent.getBooleanExtra("modify_bike", false);

        if (modifyBike) {
            bikeDTO.setId(intent.getIntExtra("id", 0));
            bikeDTO.setClient(intent.getIntExtra("clientId", 0));

            etBrand.setText(intent.getStringExtra("brand"));
            etModel.setText(intent.getStringExtra("model"));
            etLicensePlate.setText(intent.getStringExtra("license_plate"));

            addButton.setText(R.string.modify_capital);
        }
    }

    @Override
    public void addBike(View view) {

        bikeDTO.setBrand(etBrand.getText().toString().trim());
        bikeDTO.setModel(etModel.getText().toString().trim());
        bikeDTO.setLicensePlate(etLicensePlate.getText().toString().trim());
        bikeDTO.setClient(clients.get(clientSpinner.getSelectedItemPosition()).getId());
        bikeDTO.setBikeImage(null);

        presenter.addOrModifyBike(bikeDTO, modifyBike);

    }

    @Override
    public void cleanForm() {

        bikeImage.setImageResource(R.drawable.bike_default);
        etBrand.setText("");
        etModel.setText("");
        etLicensePlate.setText("");

    }

    @Override
    public void showMessage(int stringRes) {
        Toast.makeText(this, stringRes, Toast.LENGTH_SHORT).show();
    }

    //Método para tomar foto
    public void takePhoto(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 1);
        }
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    // Muestra la vista previa en un imageWiev de la foto tomada
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            bikeImage.setImageBitmap(imageBitmap);
        }
    }

}