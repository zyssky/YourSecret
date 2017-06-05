package com.example.administrator.yoursecret;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;

/**
 * Created by Administrator on 2017/6/5.
 */

public class TypeDialog extends Dialog {
    public TypeDialog(@NonNull Context context) {
        super(context);
    }

    public TypeDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected TypeDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.type_dialog);
    }
}
