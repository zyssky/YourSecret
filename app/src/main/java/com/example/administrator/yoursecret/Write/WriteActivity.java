package com.example.administrator.yoursecret.Write;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.example.administrator.yoursecret.R;
import com.example.administrator.yoursecret.utils.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class WriteActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private static int nextBitmapIndex = 0;

    public final static int TAKE_PHOTO_REQUEST = 0;

    private List<Object> bitmaps;

    private WriteImagesAdapter adapter;

    private Uri curImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

//        getSupportActionBar().setTitle(R.string.Write_name);

        recyclerView = (RecyclerView) findViewById(R.id.images);

        //to change to fit the mvp architecture
        bitmaps = new ArrayList<>();
        bitmaps.add(R.drawable.sample);
        bitmaps.add(R.drawable.sample);
        bitmaps.add(R.drawable.sample);
        bitmaps.add(R.drawable.sample);

        adapter = new WriteImagesAdapter(bitmaps);
        adapter.setContext(this);
        View footer = LayoutInflater.from(this).inflate(R.layout.footer_write_image,null);
        footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                curImageUri= Uri.fromFile(BitmapUtil.getTempImage(nextBitmapIndex++));
                intent.putExtra(MediaStore.EXTRA_OUTPUT,curImageUri);
                startActivityForResult(intent,TAKE_PHOTO_REQUEST);
            }
        });
        adapter.setFooterView(footer);
        adapter.setScaleFooterView(false);
        recyclerView.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        recyclerView.addItemDecoration(new SpaceItemDecoration(10,10,10,10));
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case TAKE_PHOTO_REQUEST:
                if(resultCode==RESULT_OK && null!=curImageUri){
                    adapter.addData(curImageUri);
                }
        }
    }
}
