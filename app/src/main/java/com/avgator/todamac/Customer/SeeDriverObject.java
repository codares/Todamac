package com.avgator.todamac.Customer;

public class SeeDriverObject {
    private String driverName;
    private String driverPhone;
    private float ratingBar;

    public SeeDriverObject(String driverName, String driverPhone, float ratingBar) {
        this.driverName = driverName;
        this.driverPhone = driverPhone;
        this.ratingBar = ratingBar;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public float getRatingBar() {
        return ratingBar;
    }

    public void setRatingBar(float ratingBar) {
        this.ratingBar = ratingBar;
    }
}

