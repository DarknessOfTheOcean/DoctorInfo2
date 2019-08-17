package com.abc.doctorinfo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.ContentValues.TAG;

public class doctorsFragment extends Fragment {
    public doctorAdapter adapter;
    DatabaseReference userReference ,rootReference;
    //a list to store all the doctors
    List<doctor> doctorList;
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        doctorList = new ArrayList<>();
        //creating recyclerview adapter
        adapter = new doctorAdapter(getActivity(), doctorList);
        //getting the recyclerview from xml
        View view=inflater.inflate(R.layout.fragment_doctors,null);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        //database reference pointing to root of database
        rootReference = database.getReference();
        //database reference pointing to doctors node
        userReference = rootReference.child("doctors");
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               String name,speciality,location,email;
               int Rating;
               int id=0;
               for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    id++;
                    name=(String)childDataSnapshot.child("name").getValue();
                    speciality=(String)childDataSnapshot.child("speciality").getValue();
                    location=(String)childDataSnapshot.child("location").getValue();
                    email=(String)childDataSnapshot.child("email").getValue();
                   Log.i(TAG, "onDataChange: "+location + GlobalVariable.selectedCity);
                   if(!GlobalVariable.selectedCity.equals("all"))
                   {
                       if (GlobalVariable.selectedCity.equals(location))
                       {
                           doctorList.add(new doctor(id,name,speciality,4.3,email,R.drawable.docto,location));
                       }
                   }
                   else {
                       doctorList.add(new doctor(id, name, speciality, 4.3, email, R.drawable.docto, location));
                   }
                   adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);
         adapter.notifyDataSetChanged();
        return view;
    }
}
