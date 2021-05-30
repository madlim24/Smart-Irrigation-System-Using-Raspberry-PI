package com.example.authenticatorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.view.View;

public class IrrigationStatus extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    TextView irrigationTime ;
    DatabaseReference dref ;
    String time ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_irrigation_status);

        irrigationTime = (TextView) findViewById(R.id.textView5);
        dref = FirebaseDatabase.getInstance().getReference();
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                time = dataSnapshot.child("Last Irrigation Time").getValue().toString();
                irrigationTime.setText(time);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("TAG", "failed to read database", databaseError.toException() );
            }
        });

        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        navigationView = findViewById(R.id.navigationView);
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
                    Intent intent2 = new Intent(IrrigationStatus.this, Dashboard.class);
                    startActivity(intent2);
                    Toast.makeText(IrrigationStatus.this, "Dashboard Selected", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.Status:
                    Toast.makeText(IrrigationStatus.this, "This is already Irrigation Status page", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.profile:
                    Intent intent3 = new Intent(IrrigationStatus.this, Profile.class);
                    startActivity(intent3);
                    Toast.makeText(IrrigationStatus.this, "Profile Selected", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.contact:
                    Toast.makeText(IrrigationStatus.this, "Go To Contact Section", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.logout:
                    FirebaseAuth.getInstance().signOut();//logout
                    startActivity(new Intent(getApplicationContext(),Login3.class));
                    finish();
                    Toast.makeText(IrrigationStatus.this, "Successfully Logout", Toast.LENGTH_SHORT).show();
                    break;
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
}