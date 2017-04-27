package com.example.administrator.yoursecret.Write;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.administrator.yoursecret.R;

import java.util.ArrayList;
import java.util.List;

public class WriteActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

//        getSupportActionBar().setTitle(R.string.Write_name);

        recyclerView = (RecyclerView) findViewById(R.id.images);

        //to change to fit the mvp architecture
        List<Bitmap> bitmaps = new ArrayList<>();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.sample);
        bitmaps.add(bitmap);
        recyclerView.setAdapter(new WriteImagesAdapter(bitmaps));
        GridLayoutManager layoutManager = new GridLayoutManager(this,4);
        recyclerView.setLayoutManager(layoutManager);
    }
}
