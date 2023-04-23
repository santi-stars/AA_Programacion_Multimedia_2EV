package com.svalero.gestitaller.model;

import android.content.Context;

import androidx.room.Room;

import com.svalero.gestitaller.R;
import com.svalero.gestitaller.api.GestiTallerApi;
import com.svalero.gestitaller.api.GestiTallerApiInterface;
import com.svalero.gestitaller.contract.AddBikeContract;
import com.svalero.gestitaller.database.AppDatabase;
import com.svalero.gestitaller.domain.Bike;
import com.svalero.gestitaller.domain.Client;
import com.svalero.gestitaller.domain.dto.BikeDTO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBikeModel implements AddBikeContract.Model {

    private AppDatabase db;
    private GestiTallerApiInterface api;
    private List<Client> clients;

    @Override
    public void startDb(Context context) {
        clients = new ArrayList<>();
        api = GestiTallerApi.buildInstance();
        db = Room.databaseBuilder(context,
                        AppDatabase.class, "bike").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
    }

    @Override
    public void addBike(OnAddBikeListener listener, BikeDTO bikeDTO) {

        Call<Bike> bikeDTOCall = api.addBike(bikeDTO);

        bikeDTOCall.enqueue(new Callback<Bike>() {
            @Override
            public void onResponse(Call<Bike> call, Response<Bike> response) {
                listener.onAddBikeSuccess(R.string.added_bike_success);
            }

            @Override
            public void onFailure(Call<Bike> call, Throwable t) {
                listener.onAddBikeError(R.string.added_bike_error);
            }
        });
    }

    @Override
    public void modifyBike(OnModifyBikeListener listener, BikeDTO bikeDTO) {

        Call<Bike> bikeDTOCall = api.modifyBike(bikeDTO.getId(), bikeDTO);

        bikeDTOCall.enqueue(new Callback<Bike>() {
            @Override
            public void onResponse(Call<Bike> call, Response<Bike> response) {
                listener.onModifyBikeSuccess(R.string.modified_bike_success);
            }

            @Override
            public void onFailure(Call<Bike> call, Throwable t) {
                listener.onModifyBikeError(R.string.modified_bike_error);
            }
        });
    }

    @Override
    public void loadAllClient(OnLoadClientsListener listener) {
        clients.clear();

        Call<List<Client>> clientCall = api.getClients();

        clientCall.enqueue(new Callback<List<Client>>() {
            @Override
            public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                clients = response.body();
                listener.onLoadClientsSuccess(clients);
            }

            @Override
            public void onFailure(Call<List<Client>> call, Throwable t) {
                listener.onLoadClientsError(R.string.error_ocurred);
            }
        });
    }
}
