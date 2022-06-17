package com.svalero.gestitaller.presenter;

import com.svalero.gestitaller.contract.OrderListContract;
import com.svalero.gestitaller.domain.Bike;
import com.svalero.gestitaller.domain.Client;
import com.svalero.gestitaller.domain.WorkOrder;
import com.svalero.gestitaller.domain.dto.OrderDTO;
import com.svalero.gestitaller.model.OrderListModel;
import com.svalero.gestitaller.view.OrderListView;

import java.util.ArrayList;

public class OrderListPresenter implements OrderListContract.Presenter {

    private OrderListModel model;
    private OrderListView view;
    private ArrayList<WorkOrder> ordersArrayList;
    private ArrayList<OrderDTO> ordersDTOArrayList;

    public OrderListPresenter(OrderListView view) {
        model = new OrderListModel(view.getApplicationContext());

        this.view = view;
    }

    @Override
    public void loadAllOrders() {
        ordersArrayList = new ArrayList<>();
        ordersDTOArrayList = new ArrayList<>();

        model.startDb(view.getApplicationContext());
        ordersArrayList = model.loadAllOrders();

        for (WorkOrder workOrder : ordersArrayList) {
            Client client = model.loadClientById(workOrder.getClient().getId());
            Bike bike = model.loadBikeById(workOrder.getBike().getId());
            OrderDTO orderDTO = new OrderDTO();

            orderDTO.setId(workOrder.getId());
            orderDTO.setDate(workOrder.getOrderDate());
            orderDTO.setClientNameSurname(client.getName() + " " + client.getSurname());
            orderDTO.setBikeBrandModel(bike.getBrand() + " " + bike.getModel());
            orderDTO.setBikeLicensePlate(bike.getLicensePlate());
            orderDTO.setBikeImageOrder(bike.getBikeImage());
            orderDTO.setDescription(workOrder.getDescription());

            ordersDTOArrayList.add(orderDTO);
        }
        view.listClients(ordersDTOArrayList);
    }

    @Override
    public void deleteOrder(WorkOrder workOrder) {
        model.startDb(view.getApplicationContext());
        model.deleteOrder(workOrder);
    }
}
