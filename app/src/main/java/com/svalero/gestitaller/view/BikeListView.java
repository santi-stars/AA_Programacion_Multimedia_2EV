package com.svalero.gestitaller.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;

import com.svalero.gestitaller.R;
import com.svalero.gestitaller.adapters.BikeAdapter;
import com.svalero.gestitaller.contract.BikeListContract;
import com.svalero.gestitaller.domain.Bike;
import com.svalero.gestitaller.presenter.BikeListPresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BikeListView extends AppCompatActivity implements BikeListContract.View, AdapterView.OnItemClickListener {

    public List<Bike> bikes;
    public BikeAdapter bikeArrayAdapter;
    public Spinner findSpinner;
    private String orderBy;
    private final String[] FIND_SPINNER_OPTIONS = new String[]{"Marca", "Modelo", "Matricula"};
    private final String DEFAULT_STRING = "";
    private BikeListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bike);
        findSpinner = findViewById(R.id.find_spinner_view_bike);
        bikes = new ArrayList<>();
        presenter = new BikeListPresenter(this);
        bikeArrayAdapter = new BikeAdapter(this, bikes);

        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, FIND_SPINNER_OPTIONS);
        findSpinner.setAdapter(adapterSpinner);

        orderBy = DEFAULT_STRING;

        findBikesBy(DEFAULT_STRING);
    }

    @Override
    protected void onResume() {
        super.onResume();

        findBikesBy(DEFAULT_STRING);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bike_actionbar, menu);
        final MenuItem searchItem = menu.findItem(R.id.app_bar_bike_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                findBikesBy(query.trim());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                findBikesBy(newText.trim());
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void listBikes(List<Bike> bikes) {

        ListView bikesListView = findViewById(R.id.bike_lisview);
        registerForContextMenu(bikesListView);
        this.bikes = bikes;

        bikeArrayAdapter = new BikeAdapter(this, this.bikes);

        bikesListView.setAdapter(bikeArrayAdapter);
        bikesListView.setOnItemClickListener(this);

    }

    @Override
    public void showMessage(String message) {

    }

    /**
     * Busca las motos según la consulta en la barra de búsqueda del ActionBar y el parámetro de
     * búsqueda seleccionado en el Spinner.
     * Si no hay nada escrito en la barra de búsqueda carga todas las motos.
     * Finalmente llama a OrderBy para ordenarlas según lo indicado en las opciones del ActionBar
     *
     * @param query
     */
    private void findBikesBy(String query) {
        bikes.clear();

        if (query.equalsIgnoreCase(DEFAULT_STRING)) {
            presenter.loadAllBikes();
        } else {
            switch (findSpinner.getSelectedItemPosition()) {
                case 0:
                    presenter.loadBikesByBrand(query);
                    break;
                case 1:
                    presenter.loadBikesByModel(query);
                    break;
                case 2:
                    presenter.loadBikesByLicensePlate(query);
                    break;
            }
        }
        orderBy(orderBy);
    }

    /**
     * Ordena el ArrayList de motos según la opción seleccionada en el ActionBar
     *
     * @param orderBy String por el cuál ordenar las motos
     */
    private void orderBy(String orderBy) {
        this.orderBy = orderBy;

        Collections.sort(bikes, new Comparator<Bike>() {
            @Override
            public int compare(Bike o1, Bike o2) {
                switch (orderBy) {
                    case "brand":
                        return o1.getBrand().compareToIgnoreCase(o2.getBrand());
                    case "model":
                        return o1.getModel().compareToIgnoreCase(o2.getModel());
                    case "license_plate":
                        return o1.getLicensePlate().compareToIgnoreCase(o2.getLicensePlate());
                    default:
                        return String.valueOf(o1.getId()).compareTo(String.valueOf(o2.getId()));
                }
            }
        });
        bikeArrayAdapter.notifyDataSetChanged();
    }

    /**
     * Método para cuando se crea el menu contextual, infle el menu con las opciones
     *
     * @param menu
     * @param v
     * @param menuInfo
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.listview_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.add_menu);
        menuItem.setTitle("➕ AÑADIR");
    }

    /**
     * Opciones del menú ActionBar
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.order_by_default_item:
                orderBy("");
                return true;
            case R.id.order_by_brand_item:
                orderBy("brand");
                return true;
            case R.id.order_by_model_item:
                orderBy("model");
                return true;
            case R.id.order_by_license_plate_item:
                orderBy("license_plate");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Opciones del menu contextual
     *
     * @param item
     * @return
     */
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        Intent intent = new Intent(this, AddBikeView.class);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final int itemSelected = info.position;

        switch (item.getItemId()) {
            case R.id.modify_menu:                      // Modificar moto
                Bike bike = bikes.get(itemSelected);
                intent.putExtra("modify_bike", true);

                intent.putExtra("id", bike.getId());
                intent.putExtra("bike_image", bike.getBikeImage());
                intent.putExtra("brand", bike.getBrand());
                intent.putExtra("model", bike.getModel());
                intent.putExtra("license_plate", bike.getLicensePlate());
                intent.putExtra("clientId", bike.getClient().getId());

                startActivity(intent);
                return true;

            case R.id.detail_menu:                      // Detalles de la moto

                // Todo FALTA usar un fragment para mostrar una ficha con los detalles de la moto

                return true;

            case R.id.add_menu:                         // Añadir moto
                startActivity(intent);
                return true;

            case R.id.delete_menu:                      // Eliminar moto
                deleteBike(info);
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    private void deleteBike(AdapterView.AdapterContextMenuInfo info) {
        Bike bike = bikes.get(info.position);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.are_you_sure_delete_bike)
                .setPositiveButton(R.string.yes_capital, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.deleteBike(bike);
                        findBikesBy(DEFAULT_STRING);
                    }
                })
                .setNegativeButton(R.string.no_capital, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    public void addBike(View view) {
        Intent intent = new Intent(this, AddBikeView.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}