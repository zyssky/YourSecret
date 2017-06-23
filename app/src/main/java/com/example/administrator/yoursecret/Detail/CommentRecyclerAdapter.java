package com.example.administrator.yoursecret.Detail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.yoursecret.Entity.Comment;
import com.example.administrator.yoursecret.R;
import com.example.administrator.yoursecret.utils.BaseRecyclerAdapter;

/**
 * Created by Administrator on 2017/5/27.
 */

public class CommentRecyclerAdapter extends BaseRecyclerAdapter<Comment> {
    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        if(viewType == TYPE_HEADER){
            View header = getHeaderView();
            if (null !=header)
                return new CommentRecyclerAdapter.ViewHolder(header);
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, Comment data) {

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView date;
        TextView nickName;
        TextView content;
        public ViewHolder(View itemView) {
            super(itemView);
//            imageView = (ImageView) itemView.findViewById()
        }
    }
}
