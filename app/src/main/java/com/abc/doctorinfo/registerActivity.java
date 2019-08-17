package com.abc.doctorinfo;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class registerActivity extends AppCompatActivity {
    private EditText editTextName, editTextEmail,editTextCity;
    private Button btnSubmitData;
    private String userId,mobile;
    private DatabaseReference mDatabaseReference;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //initialize the progress bar
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Registering");
        progressDialog.setMessage("wait...");
      //  progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);





       //get the extras
        Intent intent=getIntent();
        userId = intent.getStringExtra("userId");
        mobile = intent.getStringExtra("mobile");

        editTextName=findViewById(R.id.edtName);
        editTextEmail=findViewById(R.id.edtEmail);
        editTextCity=findViewById(R.id.edtCity);
        btnSubmitData=findViewById(R.id.btnSubmitData);
        // get the database reference
        mDatabaseReference=FirebaseDatabase.getInstance().getReference();

        btnSubmitData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                String name=editTextName.getText().toString().trim();
                String email=editTextEmail.getText().toString().trim();
                String city=editTextCity.getText().toString().trim();
                writeNewUser(name,email,city,mobile);


                }
        });
    }


    private void writeNewUser(String name, String email, String city, String mobile) {
        Users user = new Users(name, email, city,mobile);

        mDatabaseReference.child("users").child(userId).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {    progressDialog.dismiss();
                    startActivity(new Intent(getApplicationContext(),profileActivity.class));
                    finish();
                    Toast.makeText(registerActivity.this, "Data was successfully submitted", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    progressDialog.dismiss();
                    Toast.makeText(registerActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
        }


}


