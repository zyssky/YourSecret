package com.example.administrator.yoursecret.Record;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.yoursecret.R;

import java.util.List;

/**
 * Created by Administrator on 2017/4/14.
 */

public class RecordsAdapter extends RecyclerView.Adapter<RecordsAdapter.MyViewHolder> {

    private List<Record> mdatas;

    public RecordsAdapter(List<Record> mdatas){
        this.mdatas = mdatas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_record,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mood.setText(mdatas.get(position).getMood());
    }

    @Override
    public int getItemCount() {
        return mdatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView mood;

        public MyViewHolder(View itemView) {
            super(itemView);
            mood = (TextView) itemView.findViewById(R.id.mood);
        }
    }
}
