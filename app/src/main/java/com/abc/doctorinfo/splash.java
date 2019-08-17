package com.abc.doctorinfo;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread background=new Thread(){
            public void run()
            {
                try {

                    Thread.sleep(3*1000);

                    startActivity(new Intent(getApplicationContext(),signInActivity.class));
                    finish();

                }
                catch(Exception e)
                {
                    //should not do anything
                }

            }



        };

        background.start();




    }
}
