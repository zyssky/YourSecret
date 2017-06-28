package com.example.administrator.yoursecret.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/31.
 */

public abstract class MultiRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int ITEM_TYPE = 0;
    public static final int TITLE_TYPE = 1;
    public static final int HEADER_TYPE = 2;
    public static final int FOOTER_TYPE = 3;

    private int hasHeader = 0;
    private int hasFooter = 0;
    
    private BaseRecyclerAdapter.OnItemClickListener itemListener;
    private BaseRecyclerAdapter.OnItemClickListener titleListener;

//    private int itemCounts = 0;

//    private String titleAfterGetItemType;
//    private T dataAfterGetItemType;

    private Map<String,List<T>> datas;
    private List<String> titles;
    protected String titleAfterGetItemType;
    protected T dataAfterGetItemType;

    public void setOnItemClickListener(BaseRecyclerAdapter.OnItemClickListener itemListener){
        this.itemListener = itemListener;
    }

    public void setOnTitleClickListener(BaseRecyclerAdapter.OnItemClickListener titleListener){
        this.titleListener = titleListener;
    }

    public void setDatas(Map<String,List<T>> datas , List<String> titles){
        this.datas = datas;
        this.titles = titles;
        notifyDataSetChanged();
    }

//    public void addDatasOnTitle(String title , List<T> itemDatas){
//        for (int i = 0; i < titles.size(); i++) {
//            if(title.equals(titles.get(i))){
//                datas.get(title).addAll(itemDatas);
//            }
//        }
//        if(!datas.containsKey(title)){
//            datas.put(title,itemDatas);
//        }
//        notifyDataSetChanged();
//    }

    public Object getDataAt(int position) {
        position = position - hasFooter - hasHeader;
        for (int i = 0; i < titles.size(); i++) {
            if (position == 0) {
                return titles.get(i);
            }
            position--;
            if (position < datas.get(titles.get(i)).size()) {
                return datas.get(titles.get(i)).get(position);
            }
            position -= datas.get(titles.get(i)).size();
        }
        return null;
    }

    public KV getLocation(int position){
        for (int i = 0; i < titles.size(); i++) {
            if (position == 0) {
                return new KV(titles.get(i),-1);
            }

            position--;

            if (position < datas.get(titles.get(i)).size()) {
                return new KV(titles.get(i),position);
            }

            position -= datas.get(titles.get(i)).size();
        }
        return null;
    }

    public void setHasHeader(int hasHeader) {
        this.hasHeader = hasHeader;
    }

    public void setHasFooter(int hasFooter) {
        this.hasFooter = hasFooter;
    }

    @Override
    public int getItemViewType(int position) {
        if(hasHeader == 1 && position == 0 ) {
            return HEADER_TYPE;
        }
        if(hasFooter == 1 && position == getItemCount()-1 ){
            return FOOTER_TYPE;
        }
        if(position < getItemCount()) {
            int targetType = position - hasHeader;
            for (int i = 0; i < titles.size(); i++) {
                titleAfterGetItemType = titles.get(i);
                targetType -= 1;
                if(targetType<0)
                    return TITLE_TYPE;
                targetType -= datas.get(titles.get(i)).size();
                if(targetType<0) {
                    targetType += datas.get(titles.get(i)).size();
                    dataAfterGetItemType = datas.get(titleAfterGetItemType).get(targetType);
                    return ITEM_TYPE;
                }
            }
        }
        return ITEM_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case ITEM_TYPE:
                return onCreateItemViewHolder(parent);
            case TITLE_TYPE:
                return onCreateTitleViewHolder(parent);
            case HEADER_TYPE:
                if(hasHeader>0)
                    return new mViewHolder(headerView);
                break;
            case FOOTER_TYPE:
                if(hasFooter>0)
                    return new mViewHolder(footerView);
                break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int viewType = getItemViewType(position);
        
        switch (viewType){
            case ITEM_TYPE:

                if(null != itemListener){
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            itemListener.onItemClick(position,null);
                        }
                    });
                }

                onBindItem(holder,dataAfterGetItemType);
                break;
            case TITLE_TYPE:

                if(null != titleListener){
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            titleListener.onItemClick(position,null);
                        }
                    });
                }

                onBindTitle(holder,titleAfterGetItemType);
                break;
            case HEADER_TYPE:
//                onBindHeader(holder);
                break;
            case FOOTER_TYPE:
//                onBindFooter(holder);
                break;
        }
    }

    @Override
    public int getItemCount() {
        int count = hasFooter+hasHeader;
        count+=titles.size();
        for (int i = 0; i < titles.size(); i++) {
            count+=datas.get(titles.get(i)).size();
        }
        return count;
    }

    private View headerView;
    private View footerView;

    public void addHeader(View view){
//        hasHeader = 1;
        headerView = view;
    }

    public void addFooter(View view){
        hasFooter = 1;
        footerView = view;
    }

    public void hideFooter(){
        hasFooter = 0;
        notifyItemRemoved(getItemCount()-1);
    }

//    public abstract RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent);
//    public abstract RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup parent);
    public abstract RecyclerView.ViewHolder onCreateTitleViewHolder(ViewGroup parent);
    public abstract RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent);

//    public abstract void onBindHeader(RecyclerView.ViewHolder holder);
//    public abstract void onBindFooter(RecyclerView.ViewHolder holder);
    public abstract void onBindTitle(RecyclerView.ViewHolder holder,String title);
    public abstract void onBindItem(RecyclerView.ViewHolder holder,T data );

    class mViewHolder extends RecyclerView.ViewHolder{

        public mViewHolder(View itemView) {
            super(itemView);
        }
    }

}
