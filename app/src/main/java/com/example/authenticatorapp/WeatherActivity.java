package com.example.authenticatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class WeatherActivity extends AppCompatActivity {

    TextView t1_temp, t2_city, t3_description, t4_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        t1_temp = (TextView)findViewById(R.id.temperature);
        t2_city = (TextView)findViewById(R.id.city);
        t3_description = (TextView)findViewById(R.id.description);
        t4_date = (TextView)findViewById(R.id.Date);

        find_weather();

    }

    public void find_weather(){
        String url = "http://api.openweathermap.org/data/2.5/weather?q=Seremban&appid=40c8b827fee1df191d901f08362a092b&units=metric";
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject main_Object = response.getJSONObject("main");
                    JSONArray array = response.getJSONArray("weather");
                    JSONObject object = array.getJSONObject(0); //first index
                    String temp = String.valueOf(main_Object.getDouble("temp"));
                    String description = object.getString("description");
                    String city = response.getString("country");

                    t1_temp.setText(temp);
                    t2_city.setText(city);
                    t3_description.setText(description);

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' hh:mm:ss a zzz");
                    String formatted_data = sdf.format(calendar.getTime());

                    t4_date.setText(formatted_data);

                    /*double temp_int = Double.parseDouble(temp);
                    double centi = (temp_int - 32)/1.8000;
                    centi = Math.round(centi);
                    int i = (int)centi;
                    t1_temp.setText(String.valueOf(i));*/
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }

        );
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jor);
    }
}
