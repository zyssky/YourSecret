package com.example.administrator.yoursecret.Module.Detail;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.yoursecret.AppManager.App;
import com.example.administrator.yoursecret.Entity.Artical;
import com.example.administrator.yoursecret.Module.Discover.DiscoverFragment;
import com.example.administrator.yoursecret.R;
import com.example.administrator.yoursecret.utils.AppContants;
import com.example.administrator.yoursecret.utils.DividerItemDecoration;

public class DetailActivity extends AppCompatActivity implements DetailObserver {

    private RecyclerView recyclerView;
    private WebView webView;
    private EditText editText;
    private View inputLayout;
    private View footerView;
    private TextView countComment;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DetailDataManager.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_detail_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.share:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,DetailDataManager.getInstance().getArticalShareDesc());
                intent.setType("text/plain");
                startActivity(intent);
                break;
            case R.id.write_comment:
                if(inputLayout.getVisibility() == View.VISIBLE)
                    inputLayout.setVisibility(View.GONE);
                else
                    inputLayout.setVisibility(View.VISIBLE);
                editText.setText("");
                break;
            case R.id.go:
                Artical artical = DetailDataManager.getInstance().getArtical();
                Bundle bundle = new Bundle();
                bundle.putParcelable(AppContants.KEY,artical);
                App.getInstance().startFragment(this,DiscoverFragment.class,bundle);
                App.getInstance().getDiscoverDataManager().setToShowArtical(artical);
                break;
        }
        return true;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DetailDataManager.onDestroy();
        setContentView(R.layout.content_detail);

        App.getInstance().setAppContext(this.getApplicationContext());

        inputLayout = findViewById(R.id.input_layout);
        editText =(EditText) findViewById(R.id.input_text);
        countComment = (TextView) findViewById(R.id.count_comment);

        webView = (WebView) findViewById(R.id.content_detail);
        setupWebview();

        recyclerView = (RecyclerView) findViewById(R.id.comment_recycler);
        setupRecyclerView();

        DetailDataManager.getInstance().setObserver(this);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            Artical artical = bundle.getParcelable(AppContants.KEY);
            if(artical!=null) {

                DetailDataManager.getInstance().setArtical(artical);

//                DetailDataManager.getInstance().getComments();
            }
        }
    }

    private void setupWebview(){
        WebSettings webSettings = webView.getSettings();
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().getPath());
                return true;
            }

        });

    }

    private void setupRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST,0,0,0,0));
        recyclerView.setNestedScrollingEnabled(false);

        CommentRecyclerAdapter adapter = DetailDataManager.getInstance().getAdapter();
        adapter.setContext(this);
        adapter.setFooterView(getFooterView());
        recyclerView.setAdapter(adapter);
    }

    private View getFooterView(){
        footerView = getLayoutInflater().inflate(R.layout.footer_more_comment,recyclerView,false);
        footerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailDataManager.getInstance().loadMoreComment();
            }
        });
        return footerView;
    }

    private final static int NORMAL = 0;
    private final static int LOADING = 1;
    private void setFooter(int status){
        switch (status){
            case NORMAL:
                footerView.findViewById(R.id.normal).setVisibility(View.VISIBLE);
                footerView.findViewById(R.id.loading).setVisibility(View.GONE);
                break;
            case LOADING:
                footerView.findViewById(R.id.normal).setVisibility(View.GONE);
                footerView.findViewById(R.id.loading).setVisibility(View.VISIBLE);
                break;

        }
    }

    public void sendComment(View view){
        if(!App.getInstance().getUserManager().hasLogin()){
            Toast.makeText(this,"请先登录！",Toast.LENGTH_LONG).show();
            return;
        }
        String content = editText.getText().toString();
        Log.d("DetailActivity ", "sendComment: "+content);
        DetailDataManager.getInstance().sendComment(content);

        inputLayout.setVisibility(View.GONE);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        editText.setText("");
    }


    @Override
    public void setLoading() {
        setFooter(LOADING);
    }

    @Override
    public void setNormal() {
        setFooter(NORMAL);
    }

    @Override
    public void loadUrl(String articalHref) {
        webView.loadUrl(articalHref);
    }
}
