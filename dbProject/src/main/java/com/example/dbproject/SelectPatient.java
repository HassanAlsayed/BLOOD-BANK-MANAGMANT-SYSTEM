package com.example.dbproject;

import java.util.Date;

public class SelectPatient {

    private String fn, ln, Address, Phone, bloodpressure, bloodType,bankName;
    private Date Birth;
    int id;

    public SelectPatient(int id, String fn, String ln, String Address, String Phone, String bloodpressure,
                         Date Birth, String bloodType,String bankName) {
        this.id = id;
        this.fn = fn;
        this.ln = ln;
        this.Address = Address;
        this.Phone = Phone;
        this.bloodpressure = bloodpressure;
        this.Birth = Birth;
        this.bloodType = bloodType;
        this.bankName = bankName;

    }

    public String getFn() {
        return fn;
    }

    public void setFn(String fn) {
        this.fn = fn;
    }

    public String getLn() {
        return ln;
    }

    public void setLn(String ln) {
        this.ln = ln;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getBloodpressure() {
        return bloodpressure;
    }

    public void setBloodpressure(String bloodpressure) {
        this.bloodpressure = bloodpressure;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getBirth() {
        return Birth;
    }

    public void setBirth(Date birth) {
        Birth = birth;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}


