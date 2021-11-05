package com.example.clinicmanager;

public class Person {
    private String name;
    private String phone;
    private String fix_phone;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", fix_phone='" + fix_phone + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFix_phone() {
        return fix_phone;
    }

    public void setFix_phone(String fix_phone) {
        this.fix_phone = fix_phone;
    }

    public Person(String name, String phone, String fix_phone) {
        this.name = name;
        this.phone = phone;
        this.fix_phone = fix_phone;
    }
}
