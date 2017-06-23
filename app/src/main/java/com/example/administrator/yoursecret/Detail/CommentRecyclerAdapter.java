package com.example.administrator.yoursecret.Detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.yoursecret.Entity.Comment;
import com.example.administrator.yoursecret.R;
import com.example.administrator.yoursecret.utils.BaseRecyclerAdapter;
import com.example.administrator.yoursecret.utils.GlideImageLoader;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/5/27.
 */

public class CommentRecyclerAdapter extends BaseRecyclerAdapter<Comment> {
    private Context context;
    public void setContext(Context context){
        this.context = context;
    }

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
        if(viewHolder instanceof ViewHolder){
            ViewHolder holder = (ViewHolder) viewHolder;
            holder.content.setText(data.content);
            holder.nickName.setText(data.nickName);
            GlideImageLoader.loadImageNail(context,data.iconPath,holder.imageView);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date = dateFormat.format(new Date(data.date));
            holder.date.setText(date);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView date;
        TextView nickName;
        TextView content;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.publisher_icon);
            date = (TextView) itemView.findViewById(R.id.date_comment);
            nickName = (TextView) itemView.findViewById(R.id.publisher_nickname);
            content = (TextView) itemView.findViewById(R.id.content_comment);
        }
    }
}
