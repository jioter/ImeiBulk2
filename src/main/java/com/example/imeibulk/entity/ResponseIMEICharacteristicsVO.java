package com.example.imeibulk.entity;

public class ResponseIMEICharacteristicsVO {
    public DeviceCharacteristics characteristics;
    public String imei;
    public String model;
    public String brand;


    public DeviceCharacteristics getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(DeviceCharacteristics characteristics) {
        this.characteristics = characteristics;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }


    @Override
    public String toString() {
        return  imei + "," + brand + "," + model + "," +
                characteristics.generation2G.replaceAll("yes", "true") + "," +
                characteristics.generation3G.replaceAll("yes", "true") + "," +
                characteristics.generation4G.replaceAll("yes", "true");
    }
}
