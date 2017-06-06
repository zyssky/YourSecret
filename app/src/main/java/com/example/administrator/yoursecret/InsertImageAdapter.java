package com.example.administrator.yoursecret;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.yoursecret.Write.WriteImagesAdapter;
import com.example.administrator.yoursecret.utils.BaseRecyclerAdapter;
import com.example.administrator.yoursecret.utils.GlideImageLoader;

/**
 * Created by Administrator on 2017/6/6.
 */

public class InsertImageAdapter extends RecyclerView.Adapter<InsertImageAdapter.MyViewHolder> {
    private Context context;

    private BaseRecyclerAdapter.OnItemClickListener listener;

    public void setOnItemClickListener(BaseRecyclerAdapter.OnItemClickListener listener){
        this.listener = listener;
    }


    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_insert_image,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(position,null);
            }
        });
        if(context!=null)
            GlideImageLoader.loadImage(context,WriteImagesAdapter.getInstance().getmDatas().get(position),holder.imageView);
    }

    @Override
    public int getItemCount() {
        return WriteImagesAdapter.getInstance().getmDatas().size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.insert_image);
        }
    }
}
