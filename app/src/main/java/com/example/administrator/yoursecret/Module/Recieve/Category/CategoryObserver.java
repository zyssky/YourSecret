package com.example.administrator.yoursecret.Module.Recieve.Category;

/**
 * Created by Administrator on 2017/7/4.
 */

public interface CategoryObserver {
    void removeLoading();

    void removeFooterLoading();

    void setFooterLoading();

    void showNoMsgToast();

    void showNewestToast();

    void showErrorToast();
}
