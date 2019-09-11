package com.abc.doctorinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URLEncoder;

public class DoctorProfileDisplay extends AppCompatActivity {
     private TextView nameTextView,clinicTextView,timingsTextView,cityTextView,phoneTextView,emailTextView,specialityTextView;
     private String name, clinic, timings,city,phone,email,speciality;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile_display);
        nameTextView=findViewById(R.id.DoctorName);
        clinicTextView=findViewById(R.id.textViewDoctorClinic);
        timingsTextView=findViewById(R.id.textViewDoctorTimings);
        cityTextView=findViewById(R.id.textViewDoctorCity);
        phoneTextView=findViewById(R.id.textViewDoctorPhone);
        emailTextView=findViewById(R.id.textViewDoctorEmail);
        specialityTextView=findViewById(R.id.textViewDoctorSpeciality);
        //getting the intent data

        name=getIntent().getStringExtra("name");
        clinic=getIntent().getStringExtra("clinic");
        timings=getIntent().getStringExtra("timing");
        city=getIntent().getStringExtra("city");
        phone=getIntent().getStringExtra("phone");
        email=getIntent().getStringExtra("email");
        speciality=getIntent().getStringExtra("speciality");


        //set the data to the text views
        nameTextView.setText(name);
        clinicTextView.setText(clinic);
        timingsTextView.setText(timings);
        cityTextView.setText(city);
        phoneTextView.setText(phone);
        emailTextView.setText(email);
        specialityTextView.setText(speciality);



        phoneTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+phone));
                startActivity(intent);
            }
        });
        emailTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mBody="Looking forward to have an appointment with the doctor ";
                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Appointment latter");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, mBody);
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, email);
                emailIntent.setType("plain/text");
                startActivity(Intent.createChooser(emailIntent, "Choose an app"));

            }
        });

        findViewById(R.id.whatsAppChatBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Context ctx=getApplicationContext();
                    PackageManager packageManager = ctx.getPackageManager();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    String message="Hi sir can i have an appointment on:";
                    String url = "https://wa.me/91"+ phone +"?text=" + URLEncoder.encode(message, "UTF-8");
                    i.setPackage("com.whatsapp");
                    i.setData(Uri.parse(url));
                    if (i.resolveActivity(packageManager) != null) {
                        ctx.startActivity(i);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }
}
