package com.svalero.gestitaller.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.svalero.gestitaller.R;
import com.svalero.gestitaller.adapters.ClientAdapter;
import com.svalero.gestitaller.contract.ClientListContract;
import com.svalero.gestitaller.database.AppDatabase;
import com.svalero.gestitaller.domain.Client;
import com.svalero.gestitaller.presenter.ClientListPresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ClientListView extends AppCompatActivity implements ClientListContract.View,
        AdapterView.OnItemClickListener, DetailFragment.closeDetails {

    public List<Client> clients;
    public ClientAdapter clientArrayAdapter;
    public Spinner findSpinner;
    private boolean favorites;
    private String orderBy;
    private FrameLayout frameLayout;
    private final String[] FIND_SPINNER_OPTIONS = new String[]{"Nombre", "Apellido", "Dni"};
    private final String DEFAULT_STRING = "";
    private ClientListPresenter presenter;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_client);

        db = Room.databaseBuilder(this,
                        AppDatabase.class, "client").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();

        clients = new ArrayList<>();
        presenter = new ClientListPresenter(this);
        clientArrayAdapter = new ClientAdapter(this, clients);
        frameLayout = findViewById(R.id.frame_layout_client);
        findSpinner = findViewById(R.id.find_spinner_view_client);
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, FIND_SPINNER_OPTIONS);
        findSpinner.setAdapter(adapterSpinner);
        orderBy = DEFAULT_STRING;
        favorites = false;

        findClientsBy(DEFAULT_STRING);
    }

    @Override
    protected void onResume() {
        super.onResume();

        findClientsBy(DEFAULT_STRING);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.client_actionbar, menu);

        final MenuItem searchItem = menu.findItem(R.id.app_bar_client_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                findClientsBy(query.trim());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                findClientsBy(newText.trim());
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void listClients(List<Client> clients) {

        ListView clientsListView = findViewById(R.id.client_lisview);
        registerForContextMenu(clientsListView);
        this.clients = clients;

        clientArrayAdapter = new ClientAdapter(this, this.clients);

        clientsListView.setAdapter(clientArrayAdapter);
        clientsListView.setOnItemClickListener(this);

    }

    @Override
    public void showMessage(int message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void refreshList() {
        clientArrayAdapter.notifyDataSetChanged();
    }

    private void findClientsBy(String query) {
        clients.clear();

        if (query.equalsIgnoreCase(DEFAULT_STRING)) {
            presenter.loadAllClients();
        } else {

            switch (findSpinner.getSelectedItemPosition()) {
                case 0:
                    presenter.loadClientsByName(query);
                    break;
                case 1:
                    presenter.loadClientsBySurname(query);
                    break;
                case 2:
                    presenter.loadClientsByDni(query);
                    break;
            }

        }
        orderBy(orderBy);
    }

    private void orderBy(String orderBy) {
        this.orderBy = orderBy;

        if (orderBy.equalsIgnoreCase("FAVORITOS")) {

            if (favorites) {
                this.orderBy = DEFAULT_STRING;
                findClientsBy(DEFAULT_STRING);
            } else {
                clients.clear();
                clients.addAll(db.clientDao().getAll());
            }

        } else {

            Collections.sort(clients, new Comparator<Client>() {
                @Override
                public int compare(Client o1, Client o2) {
                    switch (orderBy) {
                        case "name":
                            return o1.getName().compareToIgnoreCase(o2.getName());
                        case "surname":
                            return o1.getSurname().compareToIgnoreCase(o2.getSurname());
                        case "dni":
                            return o1.getDni().compareToIgnoreCase(o2.getDni());
                        default:
                            return String.valueOf(o1.getId()).compareTo(String.valueOf(o2.getId()));
                    }
                }
            });

        }
        clientArrayAdapter.notifyDataSetChanged();
    }

    private void favorites() {

        // TODO crear función para añadir a favoritos
        // TODO booleana FAV para cambiar ELIMINAR por ELIMINAR FAV y modificar método deleteClient

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
            case R.id.order_by_favorite_item:
                orderBy("FAVORITOS");
                if (favorites) {
                    item.setTitle("\uD83D\uDC96 FAVORITOS \uD83D\uDC96");
                    favorites = false;
                } else {
                    item.setTitle("👨‍👩‍👧‍👧 TODOS 👨‍👩‍👧‍👧");
                    favorites = true;
                }
                return true;
            case R.id.order_by_name_item:
                orderBy("name");
                return true;
            case R.id.order_by_surname_item:
                orderBy("surname");
                return true;
            case R.id.order_by_dni_item:
                orderBy("dni");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Método para cuando se crea el menu contextual, infle el menu con las opciones
     *
     * @param menu
     * @param v
     * @param menuInfo
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo
            menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.listview_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.add_menu);
        if (favorites) {
            menuItem.setTitle("\uD83D\uDCA5 ELIMINAR de FAVORITOS");
        } else {
            menuItem.setTitle("\uD83D\uDC96 AÑADIR A FAVORITOS");
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

        Intent intent = new Intent(this, AddClientView.class);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.modify_menu:                      // Modificar cliente
                Client client = clients.get(info.position);

                intent.putExtra("modify_client", true);
                intent.putExtra("id", client.getId());
                intent.putExtra("client_image", client.getClientImage());
                intent.putExtra("name", client.getName());
                intent.putExtra("surname", client.getSurname());
                intent.putExtra("dni", client.getDni());
                intent.putExtra("vip", client.isVip());
                intent.putExtra("latitud", client.getLatitude());
                intent.putExtra("longitud", client.getLongitude());

                startActivity(intent);
                return true;
            case R.id.detail_menu:                      // Detalles del cliente
                showDetails(info.position);
                return true;
            case R.id.add_menu:
                if (favorites) {
                    db.clientDao().delete(clients.get(info.position));
                    showMessage(R.string.deleted_from_fav);
                    clients.remove(clients.get(info.position));
                    clientArrayAdapter.notifyDataSetChanged();
                } else {
                    try {
                        db.clientDao().insert(clients.get(info.position));
                        showMessage(R.string.added_to_fav_success);
                    } catch (Exception ex) {
                        showMessage(R.string.added_to_fav_error);
                    }
                }
                return true;
            case R.id.delete_menu:                      // Eliminar cliente
                deleteClient(info);
                return true;
            default:
                return super.onContextItemSelected(item);
        }   // End switch
    }

    private void deleteClient(AdapterView.AdapterContextMenuInfo info) {
        Client client = clients.get(info.position);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.are_you_sure_delete_client)
                .setPositiveButton(R.string.yes_capital, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.deleteClient(client);
                        findClientsBy(DEFAULT_STRING);
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

    private void showDetails(int position) {

        Client client = clients.get(position);

        Bundle datos = new Bundle();
        datos.putByteArray("client_image", client.getClientImage());
        datos.putString("name", client.getName());
        datos.putString("surname", client.getSurname());
        datos.putString("dni", client.getDni());
        datos.putBoolean("vip", client.isVip());
        datos.putFloat("latitude", client.getLatitude());
        datos.putFloat("longitude", client.getLongitude());

        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(datos);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.client_detail, detailFragment)
                .commit();

        frameLayout.setVisibility(View.VISIBLE);
    }

    public void addClient(View view) {
        Intent intent = new Intent(this, AddClientView.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        showDetails(position);
    }

    @Override
    public void hiddeDetails() {
        frameLayout.setVisibility(View.GONE);
    }
}