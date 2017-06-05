package com.example.administrator.yoursecret.View;

import android.content.Context;
import android.support.v7.widget.AppCompatImageButton;
import android.util.AttributeSet;
/**
 * Created by Administrator on 2017/6/4.
 */

public class MyImageButton extends AppCompatImageButton {
    public MyImageButton(Context context) {
        super(context);
    }

    public MyImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private boolean choosed = false;

    public void click(){
        choosed = !choosed;
        setSelected(choosed);
    }

    public boolean isChoosed(){
        return choosed;
    }
}
