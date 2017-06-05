package com.example.administrator.yoursecret.Detail;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Administrator on 2017/5/27.
 */

public interface DetailContract {
    interface View{
        void setCommentAdapter(RecyclerView.Adapter adapter);
    }

    interface Presenter{
        void setCommentAdapter();
    }
}
