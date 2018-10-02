package com.example.user.rssnewcomposite;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // The default Realm file is "default.realm" in Context.getFilesDir();

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .build();
        Realm.setDefaultConfiguration(config);
    }
}
