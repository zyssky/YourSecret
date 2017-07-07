package com.example.administrator.yoursecret.Module.Account;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.yoursecret.R;

/**
 * Created by j on 2017/6/19.
 */

public class NavigationView extends RelativeLayout implements View.OnClickListener {
    public NavigationView(Context context){
        this(context, null);
    }
    private ImageView leftView;
    private TextView titleView;
    private ImageView rightView;
    public NavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.navigation, this, true);
        leftView = (ImageView) view.findViewById(R.id.iv_nav_back);
        leftView.setOnClickListener(this);
        titleView = (TextView) view.findViewById(R.id.tv_nav_title);
        rightView = (ImageView) view.findViewById(R.id.iv_nav_right);
        rightView.setOnClickListener(this);
    }

    /**
     * 获取返回按钮
     * @return
     */
    public ImageView getLeftView() {
        return leftView;
    }


    /**
     * 获取标题控件
     * @return
     */
    public TextView getTitleView() {
        return titleView;
    }

    /**
     * 设置标题
     * @param title
     */
    public void setTitle(String title){
        titleView.setText(title);
    }

    /**
     * 获取右侧按钮,默认不显示
     * @return
     */
    public ImageView getRightView() {
        return rightView;
    }

    private ClickCallback callback;
    /**
     * 设置按钮点击回调接口
     * @param callback
     */
    public void setClickCallback(ClickCallback callback) {
        this.callback = callback;
    }

    /**
     * 导航栏点击回调接口
     * </br>如若需要标题可点击,可再添加
     *
     */
    public static interface ClickCallback{
        /**
         * 点击返回按钮回调
         */
        void onBackClick();

        void onRightClick();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_nav_back) {
            callback.onBackClick();

        }
        if (id == R.id.iv_nav_right) {
            callback.onRightClick();

        }

    }


}