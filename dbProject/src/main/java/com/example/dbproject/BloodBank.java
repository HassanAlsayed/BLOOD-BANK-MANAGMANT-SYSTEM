package com.example.dbproject;

public class BloodBank {

    String bloodBank_name ,bloodType;


    public BloodBank(String bloodBank_name, String bloodType) {
        this.bloodBank_name = bloodBank_name;
        this.bloodType = bloodType;
    }


    public String getBloodBank_name() {
        return bloodBank_name;
    }

    public void setBloodBank_name(String bloodBank_name) {
        this.bloodBank_name = bloodBank_name;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

}
