package com.example.administrator.yoursecret.Module.Recieve;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.yoursecret.Entity.Artical;
import com.example.administrator.yoursecret.R;
import com.example.administrator.yoursecret.utils.AppContants;
import com.example.administrator.yoursecret.utils.GlideImageLoader;
import com.example.administrator.yoursecret.utils.MultiRecyclerAdapter;

/**
 * Created by Administrator on 2017/4/16.
 */

public class RecieveRecyclerAdapter extends MultiRecyclerAdapter<Artical> {

    public RecieveRecyclerAdapter(){};

    @Override
    public RecyclerView.ViewHolder onCreateTitleViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_receive_title,parent,false);
        return new RecieveRecyclerAdapter.TitleViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_artical,parent,false);
        return new RecieveRecyclerAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindTitle(RecyclerView.ViewHolder holder, String title) {
        if(holder instanceof RecieveRecyclerAdapter.TitleViewHolder){
            RecieveRecyclerAdapter.TitleViewHolder titleViewHolder = (RecieveRecyclerAdapter.TitleViewHolder) holder;
//            if(title.equals(AppContants.RECIEVE_FIRST_CATOGORY)){
//                titleViewHolder.title_divider.setVisibility(View.GONE);
//            }
//            else {
//                titleViewHolder.title_divider.setVisibility(View.VISIBLE);
//            }
            titleViewHolder.textView.setText(title);
            if(title.equals(AppContants.ARTICAL_CATOGORY_HOT)){
                titleViewHolder.itemView.setBackgroundColor(titleViewHolder.itemView.getContext().getResources().getColor(R.color.HOT));
            }
            if(title.equals(AppContants.ARTICAL_CATOGORY_PUSH)){
                titleViewHolder.itemView.setBackgroundColor(titleViewHolder.itemView.getContext().getResources().getColor(R.color.PUSH));
            }
            if(title.equals(AppContants.ARTICAL_CATOGORY_GOOD)){
                titleViewHolder.itemView.setBackgroundColor(titleViewHolder.itemView.getContext().getResources().getColor(R.color.ARTICLE));
            }
            if(title.equals(AppContants.ARTICAL_CATOGORY_OUTSIDE)){
                titleViewHolder.itemView.setBackgroundColor(titleViewHolder.itemView.getContext().getResources().getColor(R.color.OUTSIDE));
            }
        }
    }

    @Override
    public void onBindItem(RecyclerView.ViewHolder holder, Artical data) {
        if(holder instanceof RecieveRecyclerAdapter.ItemViewHolder){
            RecieveRecyclerAdapter.ItemViewHolder itemViewHolder = (RecieveRecyclerAdapter.ItemViewHolder) holder;
            itemViewHolder.title.setText(data.title);
            itemViewHolder.introduction.setText(data.introduction);
            itemViewHolder.locationDesc.setText(data.locationDesc);
            GlideImageLoader.loadImageNail(itemViewHolder.itemView.getContext(),data.imageUri,itemViewHolder.imageView);
        }
    }


    class TitleViewHolder extends RecyclerView.ViewHolder{
        TextView textView ;
//        View title_divider;
        public TitleViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.receive_title);
//            title_divider = itemView.findViewById(R.id.title_divider);
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
