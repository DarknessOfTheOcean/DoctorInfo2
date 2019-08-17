package com.abc.doctorinfo;

import android.app.Application;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseHandler extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase database=FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true);
        //database reference pointing to root of database
        DatabaseReference rootReference = database.getReference();
        rootReference.keepSynced(true);
    }
}
