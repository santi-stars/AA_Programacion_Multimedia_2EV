package com.svalero.gestitaller.contract;

import android.content.Context;

import com.svalero.gestitaller.domain.Client;

import java.util.ArrayList;

public interface ClientListContract {
    interface Model {
        void startDb(Context context);

        ArrayList<Client> loadAllClients();

        ArrayList<Client> loadClientsByName(String query);

        ArrayList<Client> loadClientsBySurname(String query);

        ArrayList<Client> loadClientsByDni(String query);

        void deleteClient(Client client);
    }

    interface View {
        void listClients(ArrayList<Client> clients);
    }

    interface Presenter {
        void loadAllClients();

        void loadClientsByName(String query);

        void loadClientsBySurname(String query);

        void loadClientsByDni(String query);

        void deleteClient(Client client);
    }
}
