package com.avgator.todamac.Driver;

public class Driver {
    private String fullName;
    private String licenseId;
    private String phoneNumber;
    private String address;
    private String plateNumber;
    private String email;

    public Driver(String fullName, String licenseId, String phoneNumber, String address, String plateNumber, String email) {
        this.fullName = fullName;
        this.licenseId = licenseId;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.plateNumber = plateNumber;
        this.email = email;
    }

    public String getName() {
        return fullName;
    }

    public void setName(String fullName) {
        this.fullName = fullName;
    }

    public String getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(String licenseId) {
        this.licenseId = licenseId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}