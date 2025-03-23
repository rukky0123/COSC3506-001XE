package com.carrental.models;

public class Car {
    private int carId;
    private String make;
    private String model;
    private int year;
    private double pricePerDay;
    private boolean availability;
    private String imagePath;

    public Car(int carId, String make, String model, int year, double pricePerDay, boolean availability, String imagePath) {
        this.carId = carId;
        this.make = make;
        this.model = model;
        this.year = year;
        this.pricePerDay = pricePerDay;
        this.availability = availability;
        this.imagePath = imagePath;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    @Override
    public String toString() {
        return make + " " + model + " (" + year + ")";
    }
}
