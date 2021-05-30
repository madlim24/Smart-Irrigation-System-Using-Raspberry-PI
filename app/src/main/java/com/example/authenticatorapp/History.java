package com.example.authenticatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class History extends AppCompatActivity {

    TextView dataView;
    DatabaseReference dref;
    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        dataView = (TextView) findViewById(R.id.textView3);

        dref= FirebaseDatabase.getInstance().getReference();
        dref.addValueEventListener(new ValueEventListener(){
            public void onDataChange(DataSnapshot dataSnapshot){
                data = dataSnapshot.child("data").getValue().toString();

                dataView.setText(data);
            }
            public void onCancelled(DatabaseError databaseError){
                Log.w("TAG", "Failed to read value.", databaseError.toException());

            }
        });
    }
}
