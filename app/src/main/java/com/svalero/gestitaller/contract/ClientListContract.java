package com.svalero.gestitaller.contract;

import android.content.Context;

import com.svalero.gestitaller.domain.Client;

import java.util.List;

public interface ClientListContract {
    interface Model {
        interface OnLoadClientsListener {
            void onLoadClientsSuccess(List<Client> clients);

            void onLoadClientsError(int message);
        }

        interface OnDeleteClientListener {
            void onDeleteClientSuccess(int message);

            void onDeleteClientError(int message);
        }

        void startDb(Context context);

        void loadAllClients(OnLoadClientsListener listener);

        void loadClientsByName(OnLoadClientsListener listener, String query);

        void loadClientsBySurname(OnLoadClientsListener listener, String query);

        void loadClientsByDni(OnLoadClientsListener listener, String query);

        void deleteClient(OnDeleteClientListener listener, Client client);
    }

    interface View {
        void listClients(List<Client> clients);

        void showMessage(int message);
    }

    interface Presenter {
        void loadAllClients();

        void loadClientsByName(String query);

        void loadClientsBySurname(String query);

        void loadClientsByDni(String query);

        void deleteClient(Client client);
    }
}
