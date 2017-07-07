package com.example.administrator.yoursecret.Module.Editor;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.widget.EditText;

import com.example.administrator.yoursecret.R;

/**
 * Created by Administrator on 2017/6/6.
 */

public class LinkDialog extends Dialog {
    public LinkDialog(@NonNull Context context) {
        super(context);
    }

    public LinkDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected LinkDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private EditText linkName;
    private EditText linkUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.link_dialog);

        linkName = (EditText) findViewById(R.id.link_name);
        linkUri = (EditText) findViewById(R.id.link_uri);
    }

    public String getLinkName(){
        return linkName.getText().toString();
    }

    public String getLinkUri(){
        return linkUri.getText().toString();
    }
}
