package com.example.administrator.yoursecret.Module.Account;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.yoursecret.AppManager.App;
import com.example.administrator.yoursecret.AppManager.UserManager;
import com.example.administrator.yoursecret.Entity.UserResponse;
import com.example.administrator.yoursecret.utils.FileUtils;

import java.io.File;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by j on 2017/6/19.
 */

public class SetBasicDataManager {

    private String TAG =SetBasicDataManager.class.getSimpleName();
    private static SetBasicDataManager instance;
    String parent = FileUtils.toRootPath();
    String savepath = parent+ File.separator+"icon.png" ;


    private SetBasicDataManager(){}

    public static SetBasicDataManager getInstance(){
        if(instance==null){
            instance = new SetBasicDataManager();
        }
        return instance;
    }

    public static void onDestroy(){
        instance = null;
    }


    public Intent PhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
// 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
// aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
// outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        return intent;
    }
    public File isFile(){
        String filesDir;
        filesDir = FileUtils.toRootPath();
        File file = new File(filesDir,"icon.png");
        return file;
    }

    public void modifyImage(){

       App.getInstance().getNetworkManager().modify(null,savepath)
               .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<UserResponse>() {
        @Override
        public void onSubscribe(@NonNull Disposable d) {

        }

        @Override
        public void onNext(@NonNull UserResponse userResponse) {
            if(userResponse.code==200)
            {
                UserManager usermanager = App.getInstance().getUserManager();
                usermanager.setIconLocalPath(savepath);
                usermanager.setIconPath(userResponse.userIconPath);
                Toast.makeText(App.getInstance().getAppContext(),"修改成功",Toast.LENGTH_LONG).show();
                SetBasicActivity.Instance.onResume();
            }
            else
            {
                Toast.makeText(App.getInstance().getAppContext(),"修改失败",Toast.LENGTH_LONG).show();
            }

        }

        @Override
        public void onError(@NonNull Throwable e) {
            Log.d(TAG, "onError: ");
        }

        @Override
        public void onComplete() {
            Log.d(TAG, "onComplete: ");
        }
    });

}
}
