package com.svalero.gestitaller.contract;

import android.content.Context;

import com.svalero.gestitaller.domain.Bike;
import com.svalero.gestitaller.domain.Client;
import com.svalero.gestitaller.domain.Order;
import com.svalero.gestitaller.domain.dto.OrderDTO;

import java.util.ArrayList;

public interface OrderListContract {
    interface Model {
        void startDb(Context context);

        ArrayList<Order> loadAllOrders();

        void deleteOrder(Order order);

        Client loadClientById(int id);

        Bike loadBikeById(int id);
    }

    interface View {
        void listClients(ArrayList<OrderDTO> ordersDTOArrayList);
    }

    interface Presenter {
        void loadAllOrders();

        void deleteOrder(Order order);
    }
}
