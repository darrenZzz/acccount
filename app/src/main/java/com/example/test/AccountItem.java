package com.example.test;

public class AccountItem {
    private int id;
    private String type;
    private double value;
    private String remarks;

    public AccountItem() {
        super();
        type = "";
        value = 0.0;
        remarks = "";
    }
    public AccountItem(String type, double value, String remarks) {
        super();
        this.type = type;
        this.value = value;
        this.remarks = remarks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


}
