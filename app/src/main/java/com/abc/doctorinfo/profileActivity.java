package com.abc.doctorinfo;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class profileActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
private Button signBtn;
private Toolbar toolbar;
private FirebaseUser firebaseUser;
private List<String>cities;
private Spinner spinner;




private DatabaseReference userReference ,rootReference;

String TAG="something";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        cities = new ArrayList<String>();
        cities.add("all");

        FirebaseDatabase database=FirebaseDatabase.getInstance();
        //database reference pointing to root of database
        rootReference = database.getReference();

        //database reference pointing to doctors node
        userReference = rootReference.child("doctors");


        // get all the available cities


        getCities();




        toolbar=(Toolbar)findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);


        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        BottomNavigationView bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottomNavigation);
        loadFragment(new doctorsFragment());
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

    }

    private boolean loadFragment(Fragment fragment)
    {
        if(fragment!=null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolderId,fragment).commit();
            return true;
        }
        return false;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment=null;
        switch(item.getItemId())
        {
            case R.id.person_id:
                fragment=new profileFragment();
                break;
            case R.id.doctor_id:
                fragment=new doctorsFragment();
                break;

        }
        return loadFragment(fragment);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu, menu);

        MenuItem item = menu.findItem(R.id.spinner);
        spinner = (Spinner) item.getActionView();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                GlobalVariable var=new GlobalVariable();
                var.selectedCity = parent.getItemAtPosition(position).toString();
                loadFragment(new doctorsFragment());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });








        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.menuAbout:
                Toast.makeText(this, "You clicked about", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menuSettings:
                Toast.makeText(this, "You clicked settings", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menuLogout:

                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getApplicationContext(), "successfully signed out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),signInActivity.class));
                finish();




                Toast.makeText(this, "You clicked logout", Toast.LENGTH_SHORT).show();
                break;

        }
        return true;
    }


    private void getCities()
    {
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String city;
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    city=(String)childDataSnapshot.child("location").getValue();

                    if(!cities.contains(city))
                    {
                        cities.add(city);
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}
