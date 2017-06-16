package com.example.administrator.yoursecret.Recieve.Category;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.yoursecret.Entity.Artical;
import com.example.administrator.yoursecret.R;
import com.example.administrator.yoursecret.utils.MultiRecyclerAdapter;

/**
 * Created by Administrator on 2017/6/4.
 */

public class CategoryAdapter extends MultiRecyclerAdapter<Artical> {
    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public RecyclerView.ViewHolder onCreateTitleViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.date_divider,parent,false);
        return new CategoryAdapter.ItemViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_artical,parent,false);
        return new CategoryAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindHeader(RecyclerView.ViewHolder holder) {

    }

    @Override
    public void onBindFooter(RecyclerView.ViewHolder holder) {

    }

    @Override
    public void onBindTitle(RecyclerView.ViewHolder holder, String title) {

    }

    @Override
    public void onBindItem(RecyclerView.ViewHolder holder, Artical data) {

    }

    class TitleViewHolder extends RecyclerView.ViewHolder{
        TextView day ;
        TextView year;
        public TitleViewHolder(View itemView) {
            super(itemView);
            day = (TextView) itemView.findViewById(R.id.day_text);
            year = (TextView) itemView.findViewById(R.id.year_text);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{

        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }
}
