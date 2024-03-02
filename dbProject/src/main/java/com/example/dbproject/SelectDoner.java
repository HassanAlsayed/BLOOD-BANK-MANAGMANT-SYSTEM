package com.example.dbproject;

import javafx.scene.chart.PieChart;

import java.util.Date;

public class SelectDoner {

    private String fn,ln,Address,Phone,bloodpressure,weight,gender,sufer_from_disease,bloodType,bankName;
    private Date Birth;
    int id;

    public SelectDoner(int id,String fn, String ln, String Address, String Phone, String bloodpressure, String weight,
                       Date Birth,String gender,String sufer_from_disease,String bloodType,String bankName) {
        this.id=id;
        this.fn = fn;
        this.ln = ln;
        this.Address = Address;
        this.Phone = Phone;
        this.bloodpressure = bloodpressure;
        this.weight = weight;
        this.Birth = Birth;
        this.gender=gender;
        this.bloodType = bloodType;
        this.sufer_from_disease=sufer_from_disease;
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

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public Date getBirth() {
        return Birth;
    }

    public void setBirth(Date Birth) {
        this.Birth = Birth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSufer() {
        return sufer_from_disease;
    }

    public void setSufer(String sufer_from_disease) {
        this.sufer_from_disease = sufer_from_disease;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSufer_from_disease() {
        return sufer_from_disease;
    }

    public void setSufer_from_disease(String sufer_from_disease) {
        this.sufer_from_disease = sufer_from_disease;
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
