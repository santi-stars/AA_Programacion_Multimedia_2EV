package com.svalero.gestitaller.model;

import android.content.Context;

import androidx.room.Room;

import com.svalero.gestitaller.R;
import com.svalero.gestitaller.api.GestiTallerApi;
import com.svalero.gestitaller.api.GestiTallerApiInterface;
import com.svalero.gestitaller.contract.AddClientContract;
import com.svalero.gestitaller.database.AppDatabase;
import com.svalero.gestitaller.domain.Client;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddClientModel implements AddClientContract.Model {

    private AppDatabase db;
    private GestiTallerApiInterface api;

    @Override
    public void startDb(Context context) {
        api = GestiTallerApi.buildInstance();
        db = Room.databaseBuilder(context,
                AppDatabase.class, "client").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
    }

    @Override
    public void addClient(OnAddClientListener onAddClientListener, Client client) {

        Call<Client> clientCall = api.addClient(client);

        clientCall.enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {
                onAddClientListener.onAddClientSuccess(R.string.added_client_success);
            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {
                onAddClientListener.onAddClientError(R.string.added_client_error);
                t.printStackTrace();
            }
        });
    }

    @Override
    public void modifyClient(OnModifyClientListener onModifyClientListener, Client client) {

        Call<Client> clientCall = api.modifyClient(client.getId(), client);

        clientCall.enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {
                onModifyClientListener.onModifyClientSuccess(R.string.modified_client_success);
            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {
                onModifyClientListener.onModifyClientError(R.string.modified_client_error);
                t.printStackTrace();
            }
        });
    }
}
