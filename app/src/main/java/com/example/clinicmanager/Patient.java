package com.example.clinicmanager;

public class Patient extends Person {
    private int id;
    private boolean grp_blood;

    @Override
    public String toString() {
        return getName() + " | " + getPhone() + " | " + (isGrp_blood()?"Positivo":"Negativo" );
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isGrp_blood() {
        return grp_blood;
    }

    public void setGrp_blood(boolean grp_blood) {
        this.grp_blood = grp_blood;
    }

    public Patient(String name, String phone, String fix_phone, int id, boolean grp_blood) {
        super(name, phone, fix_phone);
        this.id = id;
        this.grp_blood = grp_blood;
    }
}
