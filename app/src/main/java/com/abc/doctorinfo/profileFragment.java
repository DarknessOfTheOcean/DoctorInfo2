package com.abc.doctorinfo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;

import static android.content.ContentValues.TAG;

public class profileFragment extends Fragment {
     private TextView editTextName,editTextEmail,editTextCity,editTextMobile;
     DatabaseReference userReference ,rootReference;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view=inflater.inflate(R.layout.fragment_profile,null);
        //get all the references

        editTextName=view.findViewById(R.id.profileName);
        editTextCity=view.findViewById(R.id.textViewCity);
        editTextEmail=view.findViewById(R.id.textViewEmail);
        editTextMobile=view.findViewById(R.id.textViewPhone);


          FirebaseDatabase  database=FirebaseDatabase.getInstance();
            //database reference pointing to root of database
            rootReference = database.getReference();








        // frist get the courrent userid

        FirebaseUser currentUser=FirebaseAuth.getInstance().getCurrentUser();
        String userId= currentUser.getUid();





        //database reference pointing to user node
        userReference = rootReference.child("users");
        userReference.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 String name=dataSnapshot.child("name").getValue().toString();
                 String email=dataSnapshot.child("email").getValue().toString();
                 String city=dataSnapshot.child("city").getValue().toString();
                 String mobile=dataSnapshot.child("mobile").getValue().toString();


                 editTextName.setText(name);
                 editTextCity.setText(city);
                 editTextEmail.setText(email);
                 editTextMobile.setText(mobile);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        return view;


    }
}
