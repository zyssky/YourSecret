package com.example.administrator.yoursecret.Recieve.Category;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.yoursecret.Entity.Artical;
import com.example.administrator.yoursecret.R;
import com.example.administrator.yoursecret.Recieve.RecieveRecyclerAdapter;
import com.example.administrator.yoursecret.Record.RecordsAdapter;
import com.example.administrator.yoursecret.utils.GlideImageLoader;
import com.example.administrator.yoursecret.utils.MultiRecyclerAdapter;

/**
 * Created by Administrator on 2017/6/4.
 */

public class CategoryAdapter extends MultiRecyclerAdapter<Artical> {

    private int dividerColor = Color.GRAY;

    public void setDividerColor(int color){
        dividerColor = color;
    }

    @Override
    public RecyclerView.ViewHolder onCreateTitleViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.date_divider,parent,false);
        return new CategoryAdapter.TitleViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_artical,parent,false);
        return new CategoryAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindTitle(RecyclerView.ViewHolder holder, String title) {
        if(holder instanceof TitleViewHolder){
            String[] times = title.split("-");
            if(times.length == 2) {
                TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
                titleViewHolder.day.setText(times[1]);
                titleViewHolder.year.setText(times[0]);
            }
            holder.itemView.setBackgroundColor(dividerColor);
        }
    }

    @Override
    public void onBindItem(RecyclerView.ViewHolder holder, Artical data) {
        if(holder instanceof CategoryAdapter.ItemViewHolder){
            CategoryAdapter.ItemViewHolder itemViewHolder = (CategoryAdapter.ItemViewHolder) holder;
            itemViewHolder.title.setText(data.title);
            itemViewHolder.introduction.setText(data.introduction);
            itemViewHolder.locationDesc.setText(data.locationDesc);
            GlideImageLoader.loadImageNail(itemViewHolder.itemView.getContext(),data.imageUri,itemViewHolder.imageView);
        }
    }


    class TitleViewHolder extends RecyclerView.ViewHolder{
        public TextView day ;
        public TextView year;
        public TitleViewHolder(View itemView) {
            super(itemView);
            day = (TextView) itemView.findViewById(R.id.day_text);
            year = (TextView) itemView.findViewById(R.id.year_text);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public TextView introduction;
        public ImageView imageView;
        public TextView locationDesc;

        public ItemViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title_msg);
            introduction = (TextView) itemView.findViewById(R.id.content_msg);
            imageView = (ImageView) itemView.findViewById(R.id.image_msg);
            locationDesc = (TextView) itemView.findViewById(R.id.location_msg);
        }
    }


}
