package com.example.administrator.yoursecret.Record;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.yoursecret.Entity.Artical;
import com.example.administrator.yoursecret.R;
import com.example.administrator.yoursecret.utils.AppContants;
import com.example.administrator.yoursecret.utils.BaseRecyclerAdapter;
import com.example.administrator.yoursecret.utils.GlideImageLoader;
import com.example.administrator.yoursecret.utils.MultiRecyclerAdapter;

/**
 * Created by Administrator on 2017/4/14.
 */

public class RecordsAdapter extends MultiRecyclerAdapter<Artical>{

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_artical,parent,false);
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
    public void onBindItem(RecyclerView.ViewHolder holder, Artical data) {
        if(holder instanceof ItemViewHolder){
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.title.setText(data.title);
            itemViewHolder.introduction.setText(data.introduction);
            if(data.locationDesc==null || data.locationDesc.isEmpty()){
                itemViewHolder.locationDesc.setText("亲，还没有拍过照片来定位哦！");
            }
            else{
                itemViewHolder.locationDesc.setText(data.locationDesc);
            }
            Log.d("test image in record ", "onBindItem: "+data.imageUri);
            GlideImageLoader.loadImageNail(itemViewHolder.itemView.getContext(),data.imageUri,itemViewHolder.imageView);

        }
    }

    private BaseRecyclerAdapter.OnItemClickListener tempItemListener;


    public void setOnTempItemClickListener(BaseRecyclerAdapter.OnItemClickListener listener){
        tempItemListener = listener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        if(position!=0 && titleAfterGetItemType.equals(AppContants.RECORD_CATOGORY_TEMP) && tempItemListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tempItemListener.onItemClick(position,null);
                }
            });
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
