package com.example.administrator.yoursecret.Module.Editor;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.View;

import com.example.administrator.yoursecret.R;

/**
 * Created by Administrator on 2017/6/4.
 */

public class SaveDialog extends Dialog {
    public SaveDialog(@NonNull Context context) {
        super(context);
    }

    public SaveDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected SaveDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private View.OnClickListener listener;
    private View view1;
    private View view2;
    private View view3;
    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
        if(view1!=null){
            view1.setOnClickListener(listener);
        }
        if(view2!=null){
            view2.setOnClickListener(listener);
        }
        if(view3!=null){
            view3.setOnClickListener(listener);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_dialog);
        view1 = findViewById(R.id.save_delete);
        view2 = findViewById(R.id.save_private);
        view3 = findViewById(R.id.save_public);

        if(listener!=null){
            view1.setOnClickListener(listener);
            view2.setOnClickListener(listener);
            view3.setOnClickListener(listener);
        }
    }
}
