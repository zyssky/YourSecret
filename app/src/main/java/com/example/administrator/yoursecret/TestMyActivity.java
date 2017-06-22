package com.example.administrator.yoursecret;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.amap.api.maps.MapView;
import com.example.administrator.yoursecret.AppManager.FoundationManager;
import com.example.administrator.yoursecret.utils.FileUtils;
import com.example.administrator.yoursecret.utils.FunctionUtils;

import java.io.File;

public class TestMyActivity extends AppCompatActivity {
    public static String TAG = TestMyActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_my);
        FoundationManager.setup(this);
        String path = FileUtils.toRootPath()+ File.separator+"test.jpg";
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        Bitmap target = FunctionUtils.createCircleImage(bitmap);
        String targetPath = FileUtils.toRootPath()+ File.separator+"circle.jpg";
        boolean finish = FileUtils.saveAsPng(targetPath,target);
        Log.d(TAG, "onCreate: "+finish);
    }
}
