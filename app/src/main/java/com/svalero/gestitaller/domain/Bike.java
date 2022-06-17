package com.svalero.gestitaller.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Arrays;

@Entity
public class Bike implements Comparable<Bike> {

    @PrimaryKey(autoGenerate = true)
    private int id;     // TODO en la API es LONG!!!
    @ColumnInfo
    private String brand;
    @ColumnInfo
    private String model;
    @ColumnInfo
    private String licensePlate;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] bikeImage;
    @Ignore
    private Client client;   // TODO en API contiene un objeto cliente entero, sacar el ID


    public Bike() {
    }


    @Ignore
    public Bike(Bike bike) {
        this.id = bike.getId();
        this.brand = bike.getBrand();
        this.model = bike.getModel();
        this.licensePlate = bike.getLicensePlate();
        this.client = bike.getClient();
        this.bikeImage = bike.getBikeImage();
    }

    @Ignore
    public Bike(int id, String brand, String model, String licensePlate, Client client, byte[] bikeImage) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.licensePlate = licensePlate;
        this.client = client;
        this.bikeImage = bikeImage;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public byte[] getBikeImage() {
        return bikeImage;
    }

    public void setBikeImage(byte[] bikeImage) {
        this.bikeImage = bikeImage;
    }

    @Override
    public String toString() {
        return "Bike{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", licensePlate='" + licensePlate + '\'' +
                ", clientId=" + client +
                ", bikeImage=" + Arrays.toString(bikeImage) +
                '}';
    }

    @Override
    public int compareTo(Bike o) {
        return brand.compareTo(o.brand);
    }
}

