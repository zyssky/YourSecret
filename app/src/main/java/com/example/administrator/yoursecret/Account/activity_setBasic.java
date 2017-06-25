package com.example.administrator.yoursecret.Account;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.yoursecret.AppManager.ApplicationDataManager;
import com.example.administrator.yoursecret.AppManager.UserManager;
import com.example.administrator.yoursecret.Entity.UserResponse;
import com.example.administrator.yoursecret.R;
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

public class activity_setBasic extends AppCompatActivity implements View.OnClickListener {
    static final int RG_REQUEST = 0;
    private Context context;
    private ImageView touxiang;
    private LinearLayout nicheng,click_touxiang;
    private TextView m_nic,m_zhanghao;
    String s ;
    String parent = FileUtils.toRootPath();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.account_set);
        initView();
        super.onCreate(savedInstanceState);

    }

    private void initView() {
        getView();
        touxiang=(ImageView) findViewById(R.id.touxiang2) ;
        nicheng=(LinearLayout)findViewById(R.id.nic);
        m_nic =(TextView)findViewById(R.id.set_basic_nic) ;
        m_zhanghao=(TextView)findViewById(R.id.set_basic_acc);
        touxiang.setOnClickListener(this);
        nicheng.setOnClickListener(this);
        UserManager user = ApplicationDataManager.getInstance().getUserManager();
        String nic = user.getNickName();
        String acc = user.getPhoneNum();
        m_zhanghao.setText(acc);
        m_nic.setText(nic);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.touxiang2:
                ShowPickDialog();
                break;
            case R.id.nic:
                Intent intent1=new Intent();
                intent1.setClass(this,activity_setItem.class);
                intent1.putExtra("title","昵称");
                startActivity(intent1);
                break;

        }

    }



    private void ShowPickDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.set_head_portrait)
                .setNegativeButton(R.string.ablum, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(Intent.ACTION_PICK, null);
                        intent.setDataAndType(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                "image/*");
                        startActivityForResult(intent, 1);
                    }
                })
                .setPositiveButton(R.string.photograph, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                        Intent intent = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
// 下面这句指定调用相机拍照后的照片存储的路径

                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                                .fromFile(new File(parent,
                                        "icon.jpg")));
                        ApplicationDataManager.getInstance().getUserManager().setIconLocalTempPath(parent+"/icon.jpg");
                        startActivityForResult(intent, 2);
                    }
                }).show();
    }
   @Override
   public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != RESULT_CANCELED) {

            switch (requestCode) {
// 如果是直接从相册获取
                case 1:
                    startPhotoZoom(data.getData());
                    break;
// 如果是调用相机拍照时
                case 2:
                    File temp = new File(ApplicationDataManager.getInstance().getUserManager().getIconLocalTempPath());
                    startPhotoZoom(Uri.fromFile(temp));
                    break;
// 取得裁剪后的图片
                case 3:
                    if (data != null) {
                        setPicToView(data);
                    }
                    break;
                default:
                    break;

                }

            }

        super.onActivityResult(requestCode, resultCode, data);
    }




    public void startPhotoZoom(Uri uri) {
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
        startActivityForResult(intent, 3);
    }
    /**
     * 保存裁剪之后的图片数据
     *
     * @param picdata
     */
    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(photo);
            touxiang.setImageDrawable(drawable);
            String path = parent+File.separator+"icon.png" ;
            FileUtils.saveAsPng(path,photo);
            ApplicationDataManager.getInstance().getNetworkManager().modify("",path)
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
                                Toast.makeText(activity_setBasic.this,"successful",Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(activity_setBasic.this,"fail",Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
            //saveImage(photo);


        }
    }
    /*private void saveImage(Bitmap bitmap) {
        File filesDir;
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){//判断sd卡是否挂载
            //路径1：storage/sdcard/Android/data/包名/files
            filesDir = getApplicationContext().getExternalFilesDir("");

        }else{//手机内部存储
            //路径：data/data/包名/files
            filesDir = getApplicationContext().getFilesDir();

        }
        FileOutputStream fos = null;
        try {
            File file = new File(filesDir,"icon.png");
            fos = new FileOutputStream(file);

            bitmap.compress(Bitmap.CompressFormat.PNG, 100,fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally{
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }*/

    //如果本地有,就不需要再去联网去请求

    private boolean readImage() {
        String filesDir;
        filesDir = FileUtils.toRootPath();
        /*if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){//判断sd卡是否挂载
            //路径1：storage/sdcard/Android/data/包名/files
            filesDir = getApplicationContext().getExternalFilesDir("");

        }else{//手机内部存储
            //路径：data/data/包名/files
            filesDir = getApplicationContext().getFilesDir();

        }*/
        File file = new File(filesDir,"icon.png");
        if(file.exists()){
            //存储--->内存
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            touxiang.setImageBitmap(bitmap);
            return true;
        }
        else
            return false;

    }
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }


    /**
     * 判断本地是否有该图片,没有则去联网请求
     * */
@Override
    public void onResume() {
        super.onResume();
        if(readImage()){
            return;
        }

    }

    public void getView() {


    }
}
