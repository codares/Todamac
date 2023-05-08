package com.avgator.todamac.Customer;

public class Report {
    private String reporterId, driverName, date, plateNumber, reportType, mediaUrl;

    public Report() {
        // Required empty public constructor
    }

    public Report(String reporterId, String driverName, String date, String plateNumber, String reportType) {
        this.reporterId = reporterId;
        this.driverName = driverName;
        this.date = date;
        this.plateNumber = plateNumber;
        this.reportType = reportType;
    }

    public Report(String reporterId, String driverName, String date, String plateNumber, String reportType, String mediaUrl) {
        this.reporterId = reporterId;
        this.driverName = driverName;
        this.date = date;
        this.plateNumber = plateNumber;
        this.reportType = reportType;
        this.mediaUrl = mediaUrl;
    }

    public String getReporterId() {
        return reporterId;
    }

    public void setReporterId(String reporterId) {
        this.reporterId = reporterId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }
}