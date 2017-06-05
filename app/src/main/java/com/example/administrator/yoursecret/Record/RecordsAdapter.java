package com.example.administrator.yoursecret.Record;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.yoursecret.MetaData.Record;
import com.example.administrator.yoursecret.R;
import com.example.administrator.yoursecret.utils.MultiRecyclerAdapter;

/**
 * Created by Administrator on 2017/4/14.
 */

public class RecordsAdapter extends MultiRecyclerAdapter<Record>{

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_record_title,parent,false);
        return new TitleViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message2,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindHeader(RecyclerView.ViewHolder holder) {

    }

    @Override
    public void onBindFooter(RecyclerView.ViewHolder holder) {

    }

    @Override
    public void onBindTitle(RecyclerView.ViewHolder holder, String title) {
        if(holder instanceof TitleViewHolder){
            TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
            titleViewHolder.textView.setText(title);
        }
    }

    @Override
    public void onBindItem(RecyclerView.ViewHolder holder, Record data) {
        if(holder instanceof ItemViewHolder){
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

        }
    }


    class TitleViewHolder extends RecyclerView.ViewHolder{
        TextView textView ;

        public TitleViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.record_title);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{

        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }
}
