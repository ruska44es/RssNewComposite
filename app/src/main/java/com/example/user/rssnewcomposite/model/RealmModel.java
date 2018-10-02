package com.example.user.rssnewcomposite.model;

import io.realm.RealmObject;

public class RealmModel extends RealmObject {
    private int id;
    private String title;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
