package com.example.administrator.yoursecret.Editor.Photo;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.example.administrator.yoursecret.Editor.AdapterManager;
import com.example.administrator.yoursecret.Editor.DataManager;
import com.example.administrator.yoursecret.Editor.PhotoManager;
import com.example.administrator.yoursecret.R;
import com.example.administrator.yoursecret.utils.BitmapUtil;
import com.example.administrator.yoursecret.Editor.WriteImagesAdapter;
import com.example.administrator.yoursecret.utils.AppContants;
import com.example.administrator.yoursecret.utils.BaseRecyclerAdapter;
import com.example.administrator.yoursecret.utils.SpaceItemDecoration;

public class PhotosActivity extends AppCompatActivity {
    private Activity activity;

    private RecyclerView recyclerView;

    public final static int TAKE_PHOTO_REQUEST = 0;

    public ServiceConnection connection;

    public LocationService.MyBinder myBinder;

    private WriteImagesAdapter adapter;

    private Uri curImageUri;

    private PhotoManager photoManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        activity = this;

        recyclerView = (RecyclerView) findViewById(R.id.photos);

        GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        recyclerView.addItemDecoration(new SpaceItemDecoration(10,10,10,10));
        recyclerView.setLayoutManager(layoutManager);

        initModel();
        initAdapter();


        recyclerView.setAdapter(adapter);

        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                myBinder = (LocationService.MyBinder) service;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                myBinder = null;
            }
        };

        Intent intent = new Intent(this,LocationService.class);
        this.bindService(intent,connection, Service.BIND_AUTO_CREATE);
    }

    public void initModel(){
        photoManager = DataManager.getInstance().getPhotoManager();

    }

    public void initAdapter(){
        adapter = AdapterManager.getInstance().getWriteImagesAdapter();
        adapter.setContext(this);
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object data) {
                Intent intent = new Intent(activity, ViewPagerActivity.class);
                intent.putExtra(AppContants.POSITION,position);
                activity.startActivity(intent);
            }
        });
        View footer = LayoutInflater.from(this).inflate(R.layout.footer_write_image,recyclerView,false);

        footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                curImageUri= Uri.fromFile(BitmapUtil.getTempImage());
                intent.putExtra(MediaStore.EXTRA_OUTPUT,curImageUri);
                startActivityForResult(intent,TAKE_PHOTO_REQUEST);

            }
        });
        adapter.setFooterView(footer);
        adapter.setScaleFooterView(false);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case TAKE_PHOTO_REQUEST:
                if(resultCode==RESULT_OK && null!=curImageUri){
                    photoManager.addPhoto(curImageUri);
                    myBinder.getLocation();
                    adapter.notifyDataSetChanged();
                }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }
}
