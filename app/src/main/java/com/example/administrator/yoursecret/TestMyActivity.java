package com.example.administrator.yoursecret;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.amap.api.maps.MapView;
import com.example.administrator.yoursecret.AppManager.ApplicationDataManager;
import com.example.administrator.yoursecret.AppManager.FoundationManager;
import com.example.administrator.yoursecret.Entity.UserResponse;
import com.example.administrator.yoursecret.utils.FileUtils;
import com.example.administrator.yoursecret.utils.FunctionUtils;

import java.io.File;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TestMyActivity extends AppCompatActivity {
    public static String TAG = TestMyActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_my);
        WebView webView = (WebView) findViewById(R.id.testwebview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
//        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
//        webSettings.setAllowFileAccess(true);
//        webSettings.setBlockNetworkImage(false);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                return super.shouldOverrideUrlLoading(view, request);
                view.loadUrl(request.getUrl().getPath());
                return true;
            }





        });


        webView.loadUrl("http://geek.csdn.net/news/detail/203046");




        webView.loadUrl("http://geek.csdn.net/news/detail/203046");


//        FoundationManager.setup(this);
//        String path = FileUtils.toRootPath()+ File.separator+"test.jpg";
//        Bitmap bitmap = BitmapFactory.decodeFile(path);
//        Bitmap target = FunctionUtils.createCircleImage(bitmap);
//        String targetPath = FileUtils.toRootPath()+ File.separator+"circle.jpg";
//        boolean finish = FileUtils.saveAsPng(targetPath,target);
//        Log.d(TAG, "onCreate: "+finish);
//
//        ApplicationDataManager.getInstance().getNetworkManager().register()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<UserResponse>() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//                        Log.d(TAG, "onSubscribe: ");
//                    }
//
//                    @Override
//                    public void onNext(@NonNull UserResponse userResponse) {
//                        //设置ui，保存对应数据
//                        //注册,登录，修改 成功后返回的数据不一样的，有的会没有，需要的就跟我说
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        e.printStackTrace();
//                        Log.d(TAG, "onError: ");
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        //如果有什么想在完成后做的可以在这里做
//                        Log.d(TAG, "onComplete: ");
//                    }
//                });
    }
}
