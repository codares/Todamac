package com.avgator.todamac.Customer;

import java.io.Serializable;

public class DestinationModel implements Serializable {

    private String desName;
    private String desCode;

    public DestinationModel(String desName, String desCode) {
        this.desName = desName;
        this.desCode = desCode;
    }

    public String getDesName() {
        return desName;
    }

    public void setDesName(String desName) {
        this.desName = desName;
    }


    public String getDesCode() {
        return desCode;
    }

    public void setDesCode(String desPhone) {
        this.desCode = desCode;
    }
}

