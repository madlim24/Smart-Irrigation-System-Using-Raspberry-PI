package com.example.authenticatorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    TextView humidityView, temperatureView, moistureView, waterLevelView;
    DatabaseReference dref;
    String humidity, temperature, moisture, waterLevel;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        humidityView = (TextView) findViewById(R.id.humidityText);
        temperatureView = (TextView) findViewById(R.id.temperatureText);
        moistureView = (TextView) findViewById(R.id.moistureText);
        waterLevelView = (TextView) findViewById(R.id.waterLevelText);

        dref= FirebaseDatabase.getInstance().getReference();
        dref.addValueEventListener(new ValueEventListener(){
            public void onDataChange(DataSnapshot dataSnapshot){
                humidity = dataSnapshot.child("humidity").getValue().toString();
                temperature = dataSnapshot.child("temperature").getValue().toString();
                moisture = dataSnapshot.child("moisture").getValue().toString();
                waterLevel = dataSnapshot.child("distance").getValue().toString();


                humidityView.setText(humidity);
                temperatureView.setText(temperature);
                moistureView.setText(moisture);
                waterLevelView.setText(waterLevel);

            }
            public void onCancelled(DatabaseError databaseError){
                Log.w("TAG", "Failed to read value.", databaseError.toException());

            }
        });

        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawerOpen, R.string.drawerClose);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.Dashboard:
                Toast.makeText(Dashboard.this, "This is already dashboard", Toast.LENGTH_SHORT).show();
                break;
            case R.id.Status:
                Intent intent = new Intent(Dashboard.this, IrrigationStatus.class);
                startActivity(intent);
                Toast.makeText(Dashboard.this, "Go To Irrigation Status", Toast.LENGTH_SHORT).show();
                break;
            case R.id.profile:
                Intent intent3 = new Intent(Dashboard.this, Profile.class);
                startActivity(intent3);
                Toast.makeText(Dashboard.this, "Profile Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.contact:
                Toast.makeText(Dashboard.this, "Go To Contact Section", Toast.LENGTH_SHORT).show();
                break;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();//logout
                startActivity(new Intent(getApplicationContext(),Login3.class));
                finish();
                Toast.makeText(Dashboard.this, "Successfully Logout", Toast.LENGTH_SHORT).show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
