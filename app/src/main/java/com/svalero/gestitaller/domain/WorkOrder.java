package com.svalero.gestitaller.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.svalero.gestitaller.database.TimestampConverter;

import java.time.LocalDate;

@Entity
public class WorkOrder implements Comparable<WorkOrder> {

    @PrimaryKey(autoGenerate = true)
    private int id;     // TODO en la API es LONG!!!
    @ColumnInfo
    @TypeConverters({TimestampConverter.class})
    private LocalDate orderDate;
    @ColumnInfo
    private String description;
    @Ignore
    private Client client;   // TODO en API contiene un objeto cliente entero, sacar el ID
    @Ignore
    private Bike bike; // TODO en API contiene un objeto bike entero, sacar el ID

    public WorkOrder() {
    }

    @Ignore
    public WorkOrder(int id, LocalDate date, String description, Client client, Bike bike) {
        this.id = id;
        this.orderDate = date;
        this.description = description;
        this.client = client;
        this.bike = bike;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Bike getBike() {
        return bike;
    }

    public void setBike(Bike bike) {
        this.bike = bike;
    }

    @Override
    public String toString() {
        return "WorkOrder{" +
                "id=" + id +
                ", date=" + orderDate +
                ", clientId=" + client +
                ", bikeId=" + bike +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public int compareTo(WorkOrder o) {
        return orderDate.compareTo(o.orderDate);
    }
}
