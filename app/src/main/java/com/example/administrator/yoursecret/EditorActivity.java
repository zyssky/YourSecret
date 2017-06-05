package com.example.administrator.yoursecret;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.administrator.yoursecret.View.MyImageButton;
import com.example.administrator.yoursecret.Write.BitmapUtil;
import com.example.administrator.yoursecret.Write.WriteImagesAdapter;
import com.example.administrator.yoursecret.utils.AppContants;
import com.example.administrator.yoursecret.utils.BaseRecyclerAdapter;
import com.example.administrator.yoursecret.utils.FunctionUtil;
import com.example.administrator.yoursecret.utils.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jp.wasabeef.richeditor.RichEditor;

public class EditorActivity extends AppCompatActivity {

    private RichEditor editor;
    private RichEditor title_editor;

    private RecyclerView recyclerView;

    private View text_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        title_editor = (RichEditor) findViewById(R.id.title_html);
        title_editor.setPadding(15,15,15,15);
        title_editor.setPlaceholder("请输入标题");
        title_editor.setEditorFontSize(25);

        editor = (RichEditor) findViewById(R.id.editor);
        editor.setPadding(10, 10, 10, 10);
        editor.setPlaceholder("请输入正文内容......");

        text_toolbar = findViewById(R.id.text_toolbar);

        List<Object> bitmaps = new ArrayList<>();
        bitmaps.add(R.drawable.sample);
        bitmaps.add(R.drawable.sample);
        bitmaps.add(R.drawable.sample);
        bitmaps.add(R.drawable.sample);

        WriteImagesAdapter.initDatas(bitmaps);

        recyclerView = (RecyclerView) findViewById(R.id.insert_gallery);

        //to change to fit the mvp architecture


        WriteImagesAdapter adapter = WriteImagesAdapter.getInstance();
        adapter.setContext(this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.addItemDecoration(new SpaceItemDecoration(10,10,10,10));
        recyclerView.setLayoutManager(layoutManager);
    }

    public void insertImage(View view){
        if(view instanceof MyImageButton){

            MyImageButton button = ((MyImageButton) view);
            button.click();
            if(button.isChoosed()){
                recyclerView.setVisibility(View.VISIBLE);
            }else {
                recyclerView.setVisibility(View.GONE);
            }
        }
    }

    public void setText(View view){
        if(view instanceof MyImageButton){

            MyImageButton button = ((MyImageButton) view);
            button.click();
            if(button.isChoosed()){
                text_toolbar.setVisibility(View.VISIBLE);
            }else {
                text_toolbar.setVisibility(View.GONE);
            }
        }

    }

    public void setLink(View view){
        click(view);
//        editor.insertLink();
    }

    public void setUndo(View view){
        editor.undo();
    }

    public void setSave(View view){
        click(view);
        SaveDialog dialog = new SaveDialog(this,R.style.MyDialog);
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.RIGHT | Gravity.BOTTOM);
        lp.x = FunctionUtil.dip2px(this,10);
        lp.y = FunctionUtil.dip2px(this,50);
        dialogWindow.setAttributes(lp);
        dialog.show();
    }

    public void setItalic(View view){
        click(view);
        editor.setItalic();
    }

    public void setBold(View view){
        click(view);
        editor.setBold();
    }

    public void setStrikeThrough(View view){
        click(view);
        editor.setStrikeThrough();
    }

    public void setHeading1(View view){
        click(view);
        editor.setHeading(1);
    }

    public void setHeading2(View view){
        click(view);
        editor.setHeading(2);
    }

    public void setHeading3(View view){
        click(view);
        editor.setHeading(3);
    }

    public void setBlockQuote(View view){
        click(view);
        editor.setBlockquote();
    }


    public void checkPhotos(View view){
        Intent intent = new Intent(this,PhotosActivity.class);
        this.startActivity(intent);
    }

    public void click(View view){
        if(view instanceof MyImageButton){
             ((MyImageButton) view).click();
        }
    }


}
