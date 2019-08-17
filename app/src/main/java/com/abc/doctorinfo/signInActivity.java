package com.abc.doctorinfo;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;

public class signInActivity extends AppCompatActivity {
private Button signInBtn;
private EditText editTextMobile;
private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        signInBtn=(Button)findViewById(R.id.signInBtn);
        editTextMobile=(EditText)findViewById(R.id.editText);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mobile=editTextMobile.getText().toString().trim();

                if (mobile.isEmpty()||mobile.length()<10)
                {
                    editTextMobile.setError("Enter a valid phone number");
                    editTextMobile.requestFocus();
                    return;
                }
                Intent intent=new Intent(getApplicationContext(),otpDetection.class);
                intent.putExtra("mobile",mobile);
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser!=null)
        {


            startActivity(new Intent(getApplicationContext(),profileActivity.class));
            finish();
        }

    }
}
