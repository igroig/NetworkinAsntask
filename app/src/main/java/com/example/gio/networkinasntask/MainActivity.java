package com.example.gio.networkinasntask;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new GetJsonTask(this).execute("http://api.openweathermap.org/data/2.5/weather?q=London,uk&appid=b1b15e88fa797225412429c1c50c122a");


    }


    
    public void onTaskCompleted(String data) {
        TextView currentWeather = (TextView)findViewById(R.id.currentWeather);
        TextView maxWeather = (TextView)findViewById(R.id.maxWeather);
        TextView minWeather = (TextView)findViewById(R.id.minWeather);
        Log.d("data", data);

        JSONObject jObj = null;
        JSONObject mainObj = null;
        JSONArray vezerArr = null;


        // com imageview
        RequestQueue requestQueue= Volley.newRequestQueue(getBaseContext());
        ImageLoader imageLoader=new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {

            }
        });

        NetworkImageView weatherIcon=(NetworkImageView)findViewById(R.id.weatherIcon);


        try {
            jObj = new JSONObject(data);
            mainObj = jObj.getJSONObject("main");
            vezerArr = jObj.getJSONArray("weather");
            Log.d("sdsd", vezerArr.getJSONObject(0).getString("icon"));
            weatherIcon.setImageUrl("http://i.imgur.com/7spzG.png" + vezerArr.getJSONObject(0).getString("icon") + ".png", imageLoader);

            maxWeather.setText(Double.toString(Math.round(mainObj.getDouble("temp_max") - 273.15)) + " gradusi C");
            minWeather.setText(Double.toString(Math.round(mainObj.getDouble("temp_min") - 273.15)) + " gradusi C");
            currentWeather.setText(Double.toString(Math.round(mainObj.getDouble("temp") - 273.15)) + " gradusi C");


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
