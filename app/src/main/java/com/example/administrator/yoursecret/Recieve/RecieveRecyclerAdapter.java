package com.example.administrator.yoursecret.Recieve;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.yoursecret.utils.BaseRecyclerAdapter;
import com.example.administrator.yoursecret.R;

/**
 * Created by Administrator on 2017/4/16.
 */

public class RecieveRecyclerAdapter extends BaseRecyclerAdapter<PushMessage> {

//    public RecieveRecyclerAdapter(){
//
//    }

    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        if(viewType == TYPE_HEADER) {
            View header = getHeaderView();
            if (null !=header)
                return new MyViewHolder(header);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, PushMessage data) {
        if(viewHolder instanceof MyViewHolder)
            ((MyViewHolder) viewHolder).tv_title.setText(data.getTitle());
    }

    class MyViewHolder extends BaseRecyclerAdapter.Holder{

        public TextView tv_title;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.message_title);
        }
    }
}
