package com.example.administrator.yoursecret.Editor.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.yoursecret.utils.BaseRecyclerAdapter;
import com.example.administrator.yoursecret.R;
import com.example.administrator.yoursecret.utils.GlideImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2017/4/16.
 */

public class WriteImagesAdapter extends BaseRecyclerAdapter<Uri> {

    private Context context;

    private OnItemClickListener listener;

    public void setContext(Context context) {
        this.context = context;
    }

    public WriteImagesAdapter(List<Uri> mDatas){
        super(mDatas);
    }

    public WriteImagesAdapter(){}


    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, final int RealPosition, final Uri data) {
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
