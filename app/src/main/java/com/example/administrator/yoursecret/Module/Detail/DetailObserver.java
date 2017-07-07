package com.example.administrator.yoursecret.Module.Detail;

/**
 * Created by Administrator on 2017/7/6.
 */

public interface DetailObserver {
    void setLoading();

    void setNormal();

    void loadUrl(String articalHref);
}
