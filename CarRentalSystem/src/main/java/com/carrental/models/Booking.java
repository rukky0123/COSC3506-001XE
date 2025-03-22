package com.carrental.models;

import java.sql.Timestamp;

public class Booking {
    private int bookingId;
    private int userId;
    private int carId;
    private Timestamp startDate;
    private Timestamp endDate;
    private String status;
    private String username;
    private String carModel;
    private String carMake;
    private String carYear;

    public Booking(int bookingId, int userId, int carId, Timestamp startDate, Timestamp endDate, String status, String username, String carMake, String carModel, String carYear) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.carId = carId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.username = username;
        this.carMake = carMake;
        this.carModel = carModel;
        this.carYear = carYear;
    }

    // Getters and Setters
    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getCarId() { return carId; }
    public void setCarId(int carId) { this.carId = carId; }

    public Timestamp getStartDate() { return startDate; }
    public void setStartDate(Timestamp startDate) { this.startDate = startDate; }

    public Timestamp getEndDate() { return endDate; }
    public void setEndDate(Timestamp endDate) { this.endDate = endDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getCarModel() { return carModel; }
    public void setCarModel(String carModel) { this.carModel = carModel; }

    public String getCarMake() { return carMake; }
    public void setCarMake(String carMake) { this.carMake = carMake; }

    public String getCarYear() { return carYear; }
    public void setCarYear(String carYear) { this.carYear = carYear; }
}
