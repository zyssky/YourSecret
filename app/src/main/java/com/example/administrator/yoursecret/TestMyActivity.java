package com.example.administrator.yoursecret;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.amap.api.maps.MapView;

public class TestMyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_my);
        MapView mapView = (MapView) findViewById(R.id.test);
        mapView.onCreate(savedInstanceState);
    }
}
