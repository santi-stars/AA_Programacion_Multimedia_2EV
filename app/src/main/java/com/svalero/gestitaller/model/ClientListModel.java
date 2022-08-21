package com.svalero.gestitaller.model;

import android.content.Context;

import androidx.room.Room;

import com.svalero.gestitaller.R;
import com.svalero.gestitaller.api.GestiTallerApi;
import com.svalero.gestitaller.api.GestiTallerApiInterface;
import com.svalero.gestitaller.contract.ClientListContract;
import com.svalero.gestitaller.database.AppDatabase;
import com.svalero.gestitaller.domain.Client;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientListModel implements ClientListContract.Model {

    private AppDatabase db;
    private GestiTallerApiInterface api;
    private List<Client> clients;

    @Override
    public void startDb(Context context) {
        clients = new ArrayList<>();
        api = GestiTallerApi.buildInstance();
        db = Room.databaseBuilder(context,
                        AppDatabase.class, "client").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
    }

    @Override
    public void loadAllClients(OnLoadClientsListener listener) {
        clients.clear();

        Call<List<Client>> clientCall = api.getClients();

        loadClientsCallEnqueue(listener, clientCall);
    }

    @Override
    public void loadClientsByName(OnLoadClientsListener listener, String query) {
        clients.clear();

        Call<List<Client>> clientCall = api.getClientsByName(query);

        loadClientsCallEnqueue(listener, clientCall);
    }

    @Override
    public void loadClientsBySurname(OnLoadClientsListener listener, String query) {
        clients.clear();

        Call<List<Client>> clientCall = api.getClientsBySurname(query);

        loadClientsCallEnqueue(listener, clientCall);
    }

    @Override
    public void loadClientsByDni(OnLoadClientsListener listener, String query) {
        clients.clear();

        Call<List<Client>> clientCall = api.getClientsByDni(query);

        loadClientsCallEnqueue(listener, clientCall);
    }

    /**
     * Envía la solicitud de forma asíncrona y notifica la devolución de llamada de su respuesta
     * o si se produjo un error al hablar con el servidor, crear la solicitud o procesar la respuesta.
     *
     * @param listener OnLoadComputersListener
     * @param call     Lista de Computers
     */
    private void loadClientsCallEnqueue(OnLoadClientsListener listener, Call<List<Client>> call) {
        call.enqueue(new Callback<List<Client>>() {
            @Override
            public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                clients = response.body();
                listener.onLoadClientsSuccess(clients);
            }

            @Override
            public void onFailure(Call<List<Client>> call, Throwable t) {
                listener.onLoadClientsError(R.string.error_ocurred);
                t.printStackTrace();
            }
        });
    }

    @Override
    public void deleteClient(OnDeleteClientListener listener, Client client) {
        Call<Void> clientCall = api.deleteClients(client.getId());

        clientCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                listener.onDeleteClientSuccess(R.string.client_deleted_successfully);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                listener.onDeleteClientError(R.string.client_delete_error);
                t.printStackTrace();
            }
        });
    }
}
