package com.svalero.gestitaller.contract;

import android.content.Context;

import com.svalero.gestitaller.domain.Client;

public interface AddClientContract {
    interface Model {
        interface OnAddClientListener {
            void onAddClientSuccess(int message);

            void onAddClientError(int message);
        }

        interface OnModifyClientListener {
            void onModifyClientSuccess(int message);

            void onModifyClientError(int message);
        }

        void startDb(Context context);

        void addClient(OnAddClientListener onAddClientListener, Client client);

        void modifyClient(OnModifyClientListener onModifyClientListener, Client client);
    }

    interface View {
        void addClient(android.view.View view);

        void cleanForm();

        void showMessage(int message);
    }

    interface Presenter {
        void addOrModifyClient(Client client, Boolean modifyClient);
    }
}
