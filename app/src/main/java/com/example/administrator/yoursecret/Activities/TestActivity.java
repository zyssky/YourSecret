package com.example.administrator.yoursecret.Activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.administrator.yoursecret.R;
import com.example.administrator.yoursecret.client.ServiceManager;

public class TestActivity extends AppCompatActivity {

    final int MY_PERMISSION_READ_PHONE_STATE = 0;

    ServiceManager serviceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Settings
        Button okButton = (Button) findViewById(R.id.btn_settings);
        okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ServiceManager.viewNotificationSettings(TestActivity.this);
            }
        });

        serviceManager = new ServiceManager(this);

        //request the permission
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_PHONE_STATE}
                    ,MY_PERMISSION_READ_PHONE_STATE);
        }

        // Start the service

        serviceManager.setNotificationIcon(android.R.drawable.sym_def_app_icon);
        serviceManager.startService();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSION_READ_PHONE_STATE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    serviceManager.setDeviceIdByDevice();
                }
                else{
                    serviceManager.setDeviceIdByRandom();
                }
        }
    }
}