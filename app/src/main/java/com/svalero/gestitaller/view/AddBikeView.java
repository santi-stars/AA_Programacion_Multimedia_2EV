package com.svalero.gestitaller.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

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
import com.svalero.gestitaller.database.AppDatabase;
import com.svalero.gestitaller.domain.Bike;
import com.svalero.gestitaller.domain.Client;
import com.svalero.gestitaller.presenter.AddBikePresenter;
import com.svalero.gestitaller.util.ImageUtils;

import java.util.ArrayList;

public class AddBikeView extends AppCompatActivity implements AddBikeContract.View {

    private Bike bike;

    private Button addButton;
    private ImageView bikeImage;
    private Spinner clientSpinner;
    private EditText etBrand;
    private EditText etModel;
    private EditText etLicensePlate;
    private Intent intent;
    private AddBikePresenter presenter;

    private boolean modifyBike;
    public ArrayList<Client> clients;

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
        bike = new Bike();
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
    public void loadClientSpinner(ArrayList<Client> clients) {

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
        // Si se está editando la moto, obtiene los datos de la moto y los pinta en el formulario
        if (modifyBike) {
            bike.setId(intent.getIntExtra("id", 0));
            bike.setClientId(intent.getIntExtra("clientId", 0));

            if (intent.getByteArrayExtra("bike_image") != null) {
                bikeImage.setImageBitmap(ImageUtils.getBitmap(intent.getByteArrayExtra("bike_image")));
            }
            etBrand.setText(intent.getStringExtra("brand"));
            etModel.setText(intent.getStringExtra("model"));
            etLicensePlate.setText(intent.getStringExtra("license_plate"));

            addButton.setText(R.string.modify_capital);
        }
    }

    public void addBike(View view) {

        bike.setBrand(etBrand.getText().toString().trim());
        bike.setModel(etModel.getText().toString().trim());
        bike.setLicensePlate(etLicensePlate.getText().toString().trim());
        bike.setClientId(clients.get(clientSpinner.getSelectedItemPosition()).getId());
        bike.setBikeImage(ImageUtils.fromImageViewToByteArray(bikeImage));

        presenter.addBike(bike, modifyBike);

    }

    @Override
    public void cleanForm() {

        bikeImage.setImageResource(R.drawable.bike_default);
        etBrand.setText("");
        etModel.setText("");
        etLicensePlate.setText("");

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