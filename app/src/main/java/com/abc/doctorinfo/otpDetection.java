package com.abc.doctorinfo;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class otpDetection extends AppCompatActivity {
String TAG="the parth here";
private Button codeVerificationBtn, codeResendBtn;
private EditText editTextCode;
private FirebaseAuth mAuth;
private ProgressBar progressBar;
private String mVerificationId;
private  PhoneAuthProvider phoneAuthProvider;
private ConstraintLayout myLayout;
private PhoneAuthProvider.ForceResendingToken myForceResendingToken;
private PhoneAuthProvider.OnVerificationStateChangedCallbacks callBacks;
private String mobile="";
private  DatabaseReference databaseReference;
private String myUserId;
private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_detection);


        //initialize the progress bar
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Verifying");
        progressDialog.setMessage("wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);




        //get all the reference
        phoneAuthProvider=PhoneAuthProvider.getInstance();
        databaseReference=FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        codeResendBtn=(Button)findViewById(R.id.resendCodeBtn);
        codeVerificationBtn=(Button)findViewById(R.id.codeVerificationBtn);
        editTextCode=(EditText) findViewById(R.id.editTextCode);
        myLayout=(ConstraintLayout)findViewById(R.id.rootLayout);





//setting up the call backs
            callBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override
                public void onVerificationCompleted(PhoneAuthCredential credential) {

                    signInWithPhoneAuthCredential(credential);
                    Toast.makeText(otpDetection.this, "Code detected", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onVerificationFailed(FirebaseException e) {

                    Toast.makeText(otpDetection.this, "invalid phone no"+e, Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "onVerificationFailed: "+e.getMessage());
                    Log.i(TAG, "onVerificationFailed: "+e);
                }

                @Override
                public void onCodeSent(String verificationId,
                                       PhoneAuthProvider.ForceResendingToken token) {


                    // Save verification ID and resending token so we can use them later
                    mVerificationId = verificationId;
                    myForceResendingToken = token;
                    Toast.makeText(otpDetection.this, "code has been sent", Toast.LENGTH_SHORT).show();

                }
            };





        Toast.makeText(this, "you will receive a code shortly ", Toast.LENGTH_SHORT).show();



        mAuth.setLanguageCode("en-GB");
        //get mobile no from the previous activity and send verification code

        Intent intent=getIntent();
         mobile = intent.getStringExtra("mobile");
        Log.i(TAG, "onCreate: "+mobile);

        if(!TextUtils.isEmpty(mobile))
        {
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    "+91"+mobile,
                    60,
                    TimeUnit.SECONDS,
                    otpDetection.this,
                    callBacks);


        }





        codeResendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91"+mobile,
                        60,
                        TimeUnit.SECONDS,
                        otpDetection.this,
                        callBacks);
            }
        });







        codeVerificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code=editTextCode.getText().toString().trim();
                if (TextUtils.isEmpty(code))
                {
                    Toast.makeText(otpDetection.this, "enter correct code", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    progressDialog.show();

                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

                    signInWithPhoneAuthCredential(credential);


                }
            }
        });







    }














    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user=task.getResult().getUser();
                            myUserId=user.getUid();





                           databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                               @Override
                               public void onDataChange(@androidx.annotation.NonNull DataSnapshot dataSnapshot) {
                                   if (dataSnapshot.exists())
                                   {

                                       databaseReference.child("users").child(myUserId).addListenerForSingleValueEvent(new ValueEventListener() {
                                           @Override
                                           public void onDataChange(@androidx.annotation.NonNull DataSnapshot dataSnapshot) {
                                               if (dataSnapshot.exists())
                                               {
                                                   progressDialog.dismiss();
                                                   startActivity(new Intent(getApplicationContext(),profileActivity.class));
                                                   finish();
                                                   return;
                                               }

                                               Intent intent=new Intent(getApplicationContext(),registerActivity.class);
                                               intent.putExtra("userId",myUserId);
                                               intent.putExtra("mobile",mobile);
                                               progressDialog.dismiss();
                                               startActivity(intent);
                                               finish();
                                           }

                                           @Override
                                           public void onCancelled(@androidx.annotation.NonNull DatabaseError databaseError) {

                                           }
                                       });
                                       return;
                                   }
                                   Intent intent=new Intent(getApplicationContext(),registerActivity.class);
                                   intent.putExtra("userId",myUserId);
                                   intent.putExtra("mobile",mobile);
                                   progressDialog.dismiss();
                                   startActivity(intent);
                                   finish();
                               }

                               @Override
                               public void onCancelled(@androidx.annotation.NonNull DatabaseError databaseError) {

                               }
                           });







                        }
                        else{
                            String errorMsg=task.getException().toString();
                            Toast.makeText(otpDetection.this, ""+errorMsg, Toast.LENGTH_SHORT).show();
                            Log.i(TAG, "onComplete: "+errorMsg);
                        }
                    }
                });
    }












}
