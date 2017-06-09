package com.example.administrator.yoursecret.Editor;

/**
 * Created by Administrator on 2017/6/8.
 */

public class AdapterManager {
    private static AdapterManager instance;

    private AdapterManager(){}

    public static AdapterManager getInstance(){
        if(instance==null){
            instance = new AdapterManager();
        }
        return instance;
    }

    public static void onDestroy(){
        instance = null;
    }

    //the resources
    private WriteImagesAdapter writeImagesAdapter;

    public WriteImagesAdapter getWriteImagesAdapter() {
        return writeImagesAdapter;
    }

    public void setWriteImagesAdapter(WriteImagesAdapter writeImagesAdapter) {
        this.writeImagesAdapter = writeImagesAdapter;
    }
}
