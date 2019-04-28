package com.pratilipi.contacts.Model;

import java.io.Serializable;

public class Contacts implements Serializable {

    public String name;
    public  String id;
    public String number;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

}
