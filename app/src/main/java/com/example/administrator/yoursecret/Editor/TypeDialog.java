package com.example.administrator.yoursecret.Editor;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.widget.Button;
import android.widget.RadioGroup;

import com.example.administrator.yoursecret.R;
import com.example.administrator.yoursecret.View.MyButton;
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

        buttons.add((Button) findViewById(R.id.scenery));
        buttons.add((Button) findViewById(R.id.person));
        buttons.add((Button) findViewById(R.id.thing));
        buttons.add((Button) findViewById(R.id.interest));

        buttons.get(choosed).setSelected(true);

    }

    public static void setChoosed(String articalType){
        if(articalType.equals(AppContants.ARTICAL_TYPE_SCENERY))
            choosed = 0;
        if(articalType.equals(AppContants.ARTICAL_TYPE_PERSON))
            choosed = 1;
        if (articalType.equals(AppContants.ARTICAL_TYPE_THING))
            choosed = 2;
        if (articalType.equals(AppContants.ARTICAL_TYPE_INTEREST))
            choosed = 3;
    }

    public void clearSelected(){
        for (Button button :
                buttons) {
            button.setSelected(false);
        }
    }


}
