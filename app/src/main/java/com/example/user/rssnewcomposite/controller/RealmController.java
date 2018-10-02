package com.example.user.rssnewcomposite.controller;

import com.example.user.rssnewcomposite.model.RealmModel;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmController {

    private Realm realm;

    //добавил констурктор
    public RealmController(Realm realm) {
        this.realm = realm;
    }

    public RealmController() {

    }

    /*public RealmController(Context context) {
        RealmConfiguration config = new RealmConfiguration.Builder(context).build();
        realm.setDefaultConfiguration(config);
        realm = Realm.getDefaultInstance();
    }*/

    public void addInfo(String title, String description) {
        realm.beginTransaction();

        RealmModel realmObject = realm.createObject(RealmModel.class);
        int id = getNextKey();
        realmObject.setId(id);
        realmObject.setTitle(title);


        realm.commitTransaction();
    }

    public RealmResults<RealmModel> getInfo() {
        return realm.where(RealmModel.class).findAll();
    }

    public void updateInfo(int id, String title, String description) {
        realm.beginTransaction();

        RealmModel realmObject = realm.where(RealmModel.class).equalTo("id", id).findFirst();
        realmObject.setTitle(title);


        realm.commitTransaction();
    }

    public void removeItemById(int id) {
        realm.beginTransaction();

        RealmResults<RealmModel> rows = realm.where(RealmModel.class).equalTo("id", id).findAll();
        rows.clear();

        realm.commitTransaction();
    }

    private int getNextKey() {
        return realm.where(RealmModel.class).max("id").intValue() + 1;
    }
}
