package com.example.authenticatorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

public class UserInputForm extends AppCompatActivity {


    private EditText moisture, temperature, humidity;
    private Button send;
    long id;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_input_form);

        databaseReference = FirebaseDatabase.getInstance().getReference("data");

        moisture = (EditText)findViewById(R.id.SoilMoisture);
        temperature = (EditText)findViewById(R.id.Temperature);
        humidity = (EditText)findViewById(R.id.Humidity);

        send = (Button)findViewById(R.id.sendButton);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                    id = (dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }
        });
    }

    public void addData(){
        String soilMoisture = moisture.getText().toString();
        String temp = temperature.getText().toString();
        String humid = humidity.getText().toString();

        if(!TextUtils.isEmpty(soilMoisture) && !TextUtils.isEmpty(temp) && !TextUtils.isEmpty(humid)){

            Data data = new Data(soilMoisture, temp, humid);
            moisture.setText("");
            temperature.setText("");
            humidity.setText("");
            databaseReference.child(String.valueOf(id + 1)).setValue(data);
        }
        else {
            Toast.makeText(UserInputForm.this, "Please type correct data", Toast.LENGTH_LONG).show();
        }
    }
}
