package com.example.administrator.yoursecret.Write;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.yoursecret.utils.BaseRecyclerAdapter;
import com.example.administrator.yoursecret.R;
import com.example.administrator.yoursecret.utils.GlideImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2017/4/16.
 */

public class WriteImagesAdapter extends BaseRecyclerAdapter<Object> {

    private static WriteImagesAdapter instance;


    private Context context;

    private OnItemClickListener listener;

    public void setContext(Context context) {
        this.context = context;
    }

    private WriteImagesAdapter(List<Object> mDatas){
        addDatas(mDatas);
    }

    public static void initDatas(List<Object> mDatas){
        instance = new WriteImagesAdapter(mDatas);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public static WriteImagesAdapter getInstance(){
        return instance;
    }



    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, final int RealPosition, final Object data) {
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(RealPosition,data);
            }
        });
        if(viewHolder instanceof WriteImagesAdapter.MyViewHolder)
            if(context!=null)
                GlideImageLoader.loadImage(context,data,((MyViewHolder) viewHolder).imageView);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView;
        }
    }


}
