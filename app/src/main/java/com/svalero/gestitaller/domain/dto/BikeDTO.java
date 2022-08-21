package com.svalero.gestitaller.domain.dto;

import androidx.room.Ignore;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

public class BikeDTO {

    private int id;
    private String brand;
    private String model;
    private String licensePlate;
    private byte[] bikeImage;
    private int client;

    @Ignore
    public BikeDTO() {
    }

    public BikeDTO(int id, String brand, String model, String licensePlate, byte[] bikeImage, int client) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.licensePlate = licensePlate;
        this.bikeImage = bikeImage;
        this.client = client;
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

    public byte[] getBikeImage() {
        return bikeImage;
    }

    public void setBikeImage(byte[] bikeImage) {
        this.bikeImage = bikeImage;
    }

    public int getClient() {
        return client;
    }

    public void setClient(int client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "BikeDTO{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", licensePlate='" + licensePlate + '\'' +
                ", bikeImage=" + Arrays.toString(bikeImage) +
                ", client=" + client +
                '}';
    }
}
