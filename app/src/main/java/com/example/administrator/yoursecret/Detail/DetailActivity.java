package com.example.administrator.yoursecret.Detail;

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
import android.widget.Toast;

import com.example.administrator.yoursecret.AppManager.ApplicationDataManager;
import com.example.administrator.yoursecret.Entity.Artical;
import com.example.administrator.yoursecret.R;
import com.example.administrator.yoursecret.utils.AppContants;
import com.example.administrator.yoursecret.utils.DividerItemDecoration;
import com.example.administrator.yoursecret.utils.EndlessOnScrollListener;
import com.example.administrator.yoursecret.utils.KV;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DetailActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private WebView webView;
    private EditText editText;
    private View inputLayout;

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
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_detail);

        inputLayout = findViewById(R.id.input_layout);
        editText =(EditText) findViewById(R.id.input_text);

        webView = (WebView) findViewById(R.id.content_detail);
        WebSettings webSettings = webView.getSettings();
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
//        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
//        webSettings.setAllowFileAccess(true);
//        webSettings.setBlockNetworkImage(false);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                return super.shouldOverrideUrlLoading(view, request);
                view.loadUrl(request.getUrl().getPath());
                return true;
            }

        });

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){

            Artical artical = bundle.getParcelable(AppContants.KEY);
            if(artical!=null) {
                DetailDataManager.getInstance().artical = artical;
                webView.loadUrl(artical.articalHref);
                DetailDataManager.getInstance().getComments();
            }

        }



        recyclerView = (RecyclerView) findViewById(R.id.comment_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST,0,0,0,0));
        recyclerView.setNestedScrollingEnabled(false);

        CommentRecyclerAdapter adapter = DetailDataManager.getInstance().getAdapter();
        adapter.setContext(this);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new EndlessOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                Log.d("DetailActivity", "onLoadMore: ");
//                DetailDataManager.getInstance().test();
            }
        });

    }

    public void sendComment(View view){
        if(!ApplicationDataManager.getInstance().getUserManager().hasLogin()){
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
}
