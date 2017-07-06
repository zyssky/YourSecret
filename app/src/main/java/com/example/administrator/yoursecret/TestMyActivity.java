package com.example.administrator.yoursecret;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TestMyActivity extends AppCompatActivity {
    public static String TAG = TestMyActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_my);
        Intent intent = new Intent(this,TestActivity.class);
        startActivity(intent);
    }
}
