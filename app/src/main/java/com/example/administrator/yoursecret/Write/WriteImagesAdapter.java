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

import java.util.List;

/**
 * Created by Administrator on 2017/4/16.
 */

public class WriteImagesAdapter extends BaseRecyclerAdapter<Object> {


    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public WriteImagesAdapter(List<Object> mDatas){
        addDatas(mDatas);
    }



    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, Object data) {
        if(viewHolder instanceof WriteImagesAdapter.MyViewHolder)
            if(context!=null)
                Glide.with(context).load(data).into(((MyViewHolder) viewHolder).imageView);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView;
        }
    }
}
