package com.example.administrator.yoursecret.View;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by Administrator on 2017/6/6.
 */

public class MyButton extends AppCompatButton {
    public MyButton(Context context) {
        super(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
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
