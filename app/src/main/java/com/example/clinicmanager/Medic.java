package com.example.clinicmanager;

public class Medic extends Person {
    private int id;
    private String crm;

    public void setId(int id) {
        this.id = id;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public int getId() {
        return id;
    }

    public String getCrm() {
        return crm;
    }

    @Override
    public String toString() {
        return getName() + " | " + getCrm() + " | " + getPhone();
    }

    public Medic(String name, String phone, String fix_phone, int id, String crm) {
        super(name, phone, fix_phone);
        this.id = id;
        this.crm = crm;
    }
}
