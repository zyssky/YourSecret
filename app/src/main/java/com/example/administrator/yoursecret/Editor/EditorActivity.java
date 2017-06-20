package com.example.administrator.yoursecret.Editor;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.administrator.yoursecret.AppManager.ApplicationDataManager;
import com.example.administrator.yoursecret.Editor.Adapter.InsertImageAdapter;
import com.example.administrator.yoursecret.Editor.Adapter.WriteImagesAdapter;
import com.example.administrator.yoursecret.Editor.Manager.AdapterManager;
import com.example.administrator.yoursecret.Editor.Manager.ArticalManager;
import com.example.administrator.yoursecret.Editor.Manager.EditorDataManager;
import com.example.administrator.yoursecret.Editor.Manager.PhotoManager;
import com.example.administrator.yoursecret.Network.NetworkManager;
import com.example.administrator.yoursecret.Editor.Photo.PhotosActivity;
import com.example.administrator.yoursecret.AppManager.FoundationManager;
import com.example.administrator.yoursecret.Entity.Artical;
import com.example.administrator.yoursecret.Entity.ImageLocation;
import com.example.administrator.yoursecret.R;
import com.example.administrator.yoursecret.View.MyImageButton;
import com.example.administrator.yoursecret.utils.AppContants;
import com.example.administrator.yoursecret.utils.BaseRecyclerAdapter;
import com.example.administrator.yoursecret.utils.FunctionUtils;
import com.example.administrator.yoursecret.utils.KV;
import com.example.administrator.yoursecret.utils.SpaceItemDecoration;

import jp.wasabeef.richeditor.RichEditor;

public class EditorActivity extends AppCompatActivity {

//    private Context context;

    private RichEditor editor;

    private RecyclerView recyclerView;

    private View text_toolbar;

    private LinkDialog curLinkDialog;

    private TypeDialog curTypeDialog;

    private ArticalManager articalManager;

    private PhotoManager photoManager;


    private WriteImagesAdapter adapter;

    private InsertImageAdapter insertImageAdapter;

    private NetworkManager networkManager;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.type_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        articalManager.saveArtical(editor.getHtml());
        ApplicationDataManager.getInstance().getRecordDataManager().saveTempArtical(articalManager.getArtical());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.type:
                if(curTypeDialog==null) {
                    curTypeDialog = new TypeDialog(this, R.style.MyDialog);
                    setDialogPosition(curTypeDialog, Gravity.END, 10, Gravity.TOP, 60);
                }
                curTypeDialog.show();
                break;
        }
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        context = this;

        editor = (RichEditor) findViewById(R.id.editor);
        recyclerView = (RecyclerView) findViewById(R.id.insert_gallery);
        text_toolbar = findViewById(R.id.text_toolbar);

        initModels();

        Intent intent = getIntent();
        if(intent.getExtras()!=null) {
            KV kv = intent.getExtras().getParcelable(AppContants.FROM_RECORD);

            Artical artical = ApplicationDataManager.getInstance().getRecordDataManager().getArtical(kv);
            photoManager.setPhotos(artical.photos);
            articalManager.setArtical(artical);
            ApplicationDataManager.getInstance().getRecordDataManager().removeArtical(kv);
        }

        initAdapters();

        editor.setPadding(10, 10, 10, 10);
//        String hint = "<h1>请输入标题......</h1><hr>请输入内容......";
        editor.loadCSS(FoundationManager.getCssPath());
        editor.setHtml(articalManager.getArtical().html);

        recyclerView.setAdapter(insertImageAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.addItemDecoration(new SpaceItemDecoration(15,0,15,15));
        recyclerView.setLayoutManager(layoutManager);
    }

    private void initModels(){
        EditorDataManager editorDataManager = EditorDataManager.getInstance();


        articalManager = new ArticalManager();
        articalManager.setArticalType(AppContants.ARTICAL_TYPE_THING);

        photoManager = new PhotoManager();
        networkManager = new NetworkManager();

        editorDataManager.setNetworkManager(networkManager);
        editorDataManager.setArticalManager(articalManager);
        editorDataManager.setPhotoManager(photoManager);
    }

    private void initAdapters(){
        AdapterManager adapterManager = AdapterManager.getInstance();

        adapter = new WriteImagesAdapter();
        adapter.setmDatas(photoManager.getPhotos());

        insertImageAdapter = new InsertImageAdapter();
        insertImageAdapter.setContext(this);
        insertImageAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object data) {
                insertImageIntoEditor(position);
                recyclerView.setVisibility(View.GONE);
            }
        });

        adapterManager.setWriteImagesAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EditorDataManager.onDestroy();
        AdapterManager.onDestroy();
    }

//    private String getHtmlData(String bodyHTML) {
//        String head = "<head>" +
//                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
//                "<style>img{max-width: 100%; width:auto; height:auto;}</style>" +
//                "</head>";
//        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
//    }

    public void insertImageIntoEditor(int position){
        Uri url = adapter.getmDatas().get(position);
        editor.insertImage(url.getPath(),"sample");
        ImageLocation location = photoManager.getImageLocation(url);
        if(location!=null && !articalManager.hasLocation())
            articalManager.setLocation(location);
        if(url!=null && !articalManager.hasImageUri())
            articalManager.setImageUri(url);
    }


    //一级功能按钮的点击事件响应

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
        curLinkDialog = new LinkDialog(this,R.style.MyDialog);
        curLinkDialog.show();
    }

    public void setUndo(View view){
        editor.undo();
    }

    public void setDialogPosition(Dialog dialog,int gravity_x,int x, int gravity_y,int y){
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(gravity_x | gravity_y);
        lp.x = FunctionUtils.dip2px(this,x);
        lp.y = FunctionUtils.dip2px(this,y);
        dialogWindow.setAttributes(lp);
    }

    public void setSave(View view){
        click(view);
        final SaveDialog dialog = new SaveDialog(this,R.style.MyDialog);
        setDialogPosition(dialog,Gravity.END,10,Gravity.BOTTOM,50);
        dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.save_delete:
                        articalManager.deleteArtical();
                        break;
                    case R.id.save_public:
                        articalManager.setArticalSaveType(AppContants.PUBLIC);
                        articalManager.saveArtical(editor.getHtml());
                        ApplicationDataManager.getInstance().getRecordDataManager().saveFinishArtical(articalManager.getArtical());
                        networkManager.uploadArtical();
                        break;
                    case R.id.save_private:
                        articalManager.setArticalSaveType(AppContants.PRIVATE);
                        articalManager.saveArtical(editor.getHtml());
                        ApplicationDataManager.getInstance().getRecordDataManager().saveFinishArtical(articalManager.getArtical());
                        networkManager.uploadArtical();
                        break;
                }
                dialog.dismiss();
                finish();
            }
        });
        dialog.show();
    }

    public void checkPhotos(View view){
        Intent intent = new Intent(this,PhotosActivity.class);
        this.startActivity(intent);
    }

    //文字模式选择

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

    //用于按钮状态转换的，用于显示图标颜色

    public void click(View view){
        if(view instanceof MyImageButton ){
             ((MyImageButton) view).click();
        }
    }

    //类型选择按钮

    public void onSceneryClick(View view){
        articalManager.setArticalType(AppContants.ARTICAL_TYPE_SCENERY);
        curTypeDialog.clearSelected();
        view.setSelected(true);
    }

    public void onPersonClick(View view){
        articalManager.setArticalType(AppContants.ARTICAL_TYPE_PERSON);
        curTypeDialog.clearSelected();
        view.setSelected(true);
    }

    public void onThingClick(View view){
        articalManager.setArticalType(AppContants.ARTICAL_TYPE_THING);
        curTypeDialog.clearSelected();
        view.setSelected(true);
    }

    public void onInterest(View view){
        articalManager.setArticalType(AppContants.ARTICAL_TYPE_INTEREST);
        curTypeDialog.clearSelected();
        view.setSelected(true);
    }

    //插入链接按钮

    public void onLinkConfirm(View view){
        String title = curLinkDialog.getLinkName();
        String uri = curLinkDialog.getLinkUri();
        editor.insertLink(uri,title);
        curLinkDialog.dismiss();
        curLinkDialog = null;
    }

    public void onLinkCancle(View view){
        curLinkDialog.dismiss();
        curLinkDialog = null;
    }

}
