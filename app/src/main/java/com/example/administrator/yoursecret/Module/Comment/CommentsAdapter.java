package com.example.administrator.yoursecret.Module.Comment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.yoursecret.Entity.Comment;
import com.example.administrator.yoursecret.R;
import com.example.administrator.yoursecret.utils.GlideImageLoader;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/6/23.
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    private List<Comment> datas;

    public void setDatas(List<Comment> datas){
        this.datas = datas;
    }

    private Context context;
    public void setContext(Context context){
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_comment,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Comment comment = datas.get(position);
        holder.articalIntroduction.setText(comment.introduction);
        holder.articalTitle.setText(comment.title);
        holder.content.setText(comment.content);
        holder.nickName.setText(comment.nickName);
        GlideImageLoader.loadImageNail(context,comment.iconPath,holder.publishIcon);
        GlideImageLoader.loadImageNail(context,comment.imageUri,holder.articalImageUri);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String date = dateFormat.format(new Date(comment.date));
        holder.date.setText(date);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView publishIcon;
        ImageView articalImageUri;
        TextView nickName;
        TextView content;
        TextView articalTitle;
        TextView articalIntroduction;
        TextView date;


        public ViewHolder(View itemView) {
            super(itemView);
            publishIcon = (ImageView) itemView.findViewById(R.id.publisher_user_icon);
            articalImageUri = (ImageView) itemView.findViewById(R.id.artical_icon_comment);
            nickName = (TextView) itemView.findViewById(R.id.publisher_user_nickname);
            content = (TextView) itemView.findViewById(R.id.content_user_comment);
            articalTitle = (TextView) itemView.findViewById(R.id.artical_title_comment);
            articalIntroduction = (TextView) itemView.findViewById(R.id.artical_introduction_comment);
            date = (TextView) itemView.findViewById(R.id.date_user_comment);
        }
    }
}
