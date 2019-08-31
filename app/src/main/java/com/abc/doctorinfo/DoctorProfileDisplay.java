package com.abc.doctorinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

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






    }
}
