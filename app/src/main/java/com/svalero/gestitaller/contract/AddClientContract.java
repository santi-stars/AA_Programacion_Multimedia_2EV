package com.svalero.gestitaller.contract;

import android.content.Context;

import com.svalero.gestitaller.domain.Bike;
import com.svalero.gestitaller.domain.Client;

public interface AddClientContract {
    interface Model {
        void startDb(Context context);

        void insertClient(Client client);

        void updateClient(Client client);
    }

    interface View {
        void cleanForm();
    }

    interface Presenter {
        void addClient(Client client, Boolean modifyClient);
    }
}
