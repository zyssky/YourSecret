package com.example.administrator.yoursecret.Module.Account;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.example.administrator.yoursecret.AppManager.App;
import com.example.administrator.yoursecret.AppManager.UserManager;
import com.example.administrator.yoursecret.Module.Detail.DetailDataManager;
import com.example.administrator.yoursecret.R;
import com.example.administrator.yoursecret.utils.BitmapUtils;
import com.example.administrator.yoursecret.utils.FileUtils;
import com.example.administrator.yoursecret.utils.GlideImageLoader;

import java.io.File;

/**
 * Created by j on 2017/6/19.
 */

public class SetBasicActivity extends AppCompatActivity implements View.OnClickListener,SetBasicObserver {
    static final int RG_REQUEST = 0;
    private Context context;
    private ImageView touxiang;
    private LinearLayout nicheng;
    private TextView m_nic,m_zhanghao;
    String s ;
    String parent = FileUtils.toRootPath();
    String savepath = parent+File.separator+"icon.png" ;
    public static SetBasicActivity Instance;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DetailDataManager.onDestroy();
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Instance = this;
        setContentView(R.layout.account_set);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
        initView2();
        super.onCreate(savedInstanceState);

    }

    private void initView2() {

        UserManager user = App.getInstance().getUserManager();
        String nic = user.getNickName();
        String acc = user.getPhoneNum();
        m_zhanghao.setText(acc);
        m_nic.setText(nic);
    }


    private void initView() {
//        getView();
        touxiang=(ImageView) findViewById(R.id.touxiang2) ;
        nicheng=(LinearLayout)findViewById(R.id.nic);
        m_nic =(TextView)findViewById(R.id.set_basic_nic) ;
        m_zhanghao=(TextView)findViewById(R.id.set_basic_acc);
        touxiang.setOnClickListener(this);
        nicheng.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.touxiang2:
                ShowPickDialog();
                break;
            case R.id.nic:
                Intent intent1=new Intent();
                intent1.setClass(this,SetItemActivity.class);
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

                        intent.putExtra(MediaStore.EXTRA_OUTPUT, getImageUri());
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


                        startPhotoZoom(getImageUri());

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
        Intent intent = SetBasicDataManager.getInstance().PhotoZoom(uri);
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
            photo = BitmapUtils.createCircleImage(photo);
//            Drawable drawable = new BitmapDrawable(photo);
            touxiang.setImageBitmap(photo);
//            touxiang.setImageDrawable(drawable);
           FileUtils.saveAsPng(savepath,photo);
            SetBasicDataManager.getInstance().modifyImage();


        }
    }
    private Uri getImageUri()
    {
      UserManager userManager = App.getInstance().getUserManager();
        userManager.setIconLocalTempPath(parent+File.separator+"icon.jpg");
        String IconLocalTempath =userManager.getIconLocalTempPath();
        return Uri.fromFile(new File(IconLocalTempath));
    }

    //如果本地有,就不需要再去联网去请求

    private boolean readImage() {
        File file = SetBasicDataManager.getInstance().isFile();
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
     initView2();
    UserManager usermanager = App.getInstance().getUserManager();
    if(usermanager.hasLogin()){
        String iconPath = usermanager.getIconPath();
        GlideImageLoader.loadImageNail(this,iconPath,touxiang);
    }

        }




}
