package com.example.administrator.yoursecret.Detail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.administrator.yoursecret.AppManager.ApplicationDataManager;
import com.example.administrator.yoursecret.Entity.Artical;
import com.example.administrator.yoursecret.R;
import com.example.administrator.yoursecret.utils.AppContants;
import com.example.administrator.yoursecret.utils.DividerItemDecoration;
import com.example.administrator.yoursecret.utils.KV;

public class DetailActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
//    private View header;
    private WebView webView;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DetailDataManager.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_detail);

//        recyclerView = (RecyclerView) findViewById(R.id.comment_recycler);

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
            if(kv1!=null){
                Artical artical = ApplicationDataManager.getInstance().getRecordDataManager().getArtical(kv1);
                webView.loadUrl(artical.articalHref);
            }
            if(kv2!=null){
                Artical artical = ApplicationDataManager.getInstance().getRecieveDataManager().getArtical(kv2);
                webView.loadUrl(artical.articalHref);
            }
        }

//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST,0,0,0,0));
//        recyclerView.setNestedScrollingEnabled(false);
//
//        CommentRecyclerAdapter adapter = DetailDataManager.getInstance().getAdapter();
//        recyclerView.setAdapter(adapter);

    }
}
