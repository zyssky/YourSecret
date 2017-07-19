package com.example.administrator.yoursecret.Module.Editor;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.widget.Button;

import com.example.administrator.yoursecret.R;
import com.example.administrator.yoursecret.utils.AppContants;

import java.util.ArrayList;
import java.util.List;

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

    private List<Button> buttons;

    public static int choosed = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.type_dialog);

        buttons = new ArrayList<>();

        buttons.add((Button) findViewById(R.id.hot_type));
        buttons.add((Button) findViewById(R.id.notice_type));
        buttons.add((Button) findViewById(R.id.article_type));

        buttons.get(choosed).setSelected(true);

    }

    public static void setChoosed(String articalType){
        if(articalType.equals(AppContants.ARTICLE_TYPE_HOT))
            choosed = 0;
        if(articalType.equals(AppContants.ARTICLE_TYPE_NOTICE))
            choosed = 1;
        if (articalType.equals(AppContants.ARTICLE_TYPE_ARTICLE))
            choosed = 2;
    }

    public void clearSelected(){
        for (Button button :
                buttons) {
            button.setSelected(false);
        }
    }
}
