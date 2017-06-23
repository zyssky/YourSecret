package com.example.administrator.yoursecret.Detail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

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
            KV kv1 = bundle.getParcelable(AppContants.FROM_RECORD);
            KV kv2 = bundle.getParcelable(AppContants.FROM_RECIEVE);
            Artical artical = null;
            if(kv1!=null){
                artical = ApplicationDataManager.getInstance().getRecordDataManager().getArtical(kv1);
            }
            if(kv2!=null){
                artical = ApplicationDataManager.getInstance().getRecieveDataManager().getArtical(kv2);
            }
            if(artical!=null) {
                DetailDataManager.getInstance().artical = artical;
                webView.loadUrl(artical.articalHref);
                ApplicationDataManager.getInstance().getNetworkManager().getComments(artical.articalHref)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(DetailDataManager.getInstance().getCommentsObserver());
            }

        }



        recyclerView = (RecyclerView) findViewById(R.id.comment_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST,0,0,0,0));
        recyclerView.setNestedScrollingEnabled(false);

        CommentRecyclerAdapter adapter = DetailDataManager.getInstance().getAdapter();
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new EndlessOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                Log.d("DetailActivity", "onLoadMore: ");
                DetailDataManager.getInstance().test();
            }
        });

    }

    public void sendComment(View view){
        String content = editText.getText().toString();
        Log.d("DetailActivity ", "sendComment: "+content);
        DetailDataManager.getInstance().sendComment(content);

    }
}
