package com.example.administrator.yoursecret;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * Created by Administrator on 2017/6/22.
 */

public class TitleBarWebView extends WebView {
    private View mTitleBar;
    private LayoutParams mTitleBarLayoutParams;
    private Matrix mMatrix = new Matrix();
    private Rect mClipBounds = new Rect();

    public TitleBarWebView(Context context) {
        super(context);

    }

    public TitleBarWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TitleBarWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setEmbeddedTitleBar(View v) {
        if(mTitleBar == v) return;
        if(mTitleBar != null) {
            removeView(mTitleBar);
        }
        if(null != v) {
            mTitleBarLayoutParams = new LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, 0, 0);
            addView(v, mTitleBarLayoutParams);
            setInitialScale(100);
        }
        mTitleBar = v;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();

        if(mTitleBar != null) {
            final int sy = getScrollY();
            final int sx = getScrollX();
            mClipBounds.top = sy;
            mClipBounds.left = sx;
            mClipBounds.right = mClipBounds.left + getWidth();
            mClipBounds.bottom = mClipBounds.top + getHeight();
            canvas.clipRect(mClipBounds);
            mMatrix.set(canvas.getMatrix());
            int titleBarOffs = mTitleBar.getHeight() - sy;
            if(titleBarOffs < 0) titleBarOffs = 0;
            mMatrix.postTranslate(0, titleBarOffs);
            canvas.setMatrix(mMatrix);
        }

        super.onDraw(canvas);
        canvas.restore();
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        if(child == mTitleBar) {
            mClipBounds.top = 0;
            mClipBounds.left = 0;
            mClipBounds.right = mClipBounds.left + child.getWidth();
            mClipBounds.bottom = child.getHeight();
            canvas.save();
            child.setDrawingCacheEnabled(true);
            mMatrix.set(canvas.getMatrix());
            mMatrix.postTranslate(getScrollX(), -getScrollY());
            canvas.setMatrix(mMatrix);
            canvas.clipRect(mClipBounds);
            child.draw(canvas);
            canvas.restore();

            return false;
        }

        return super.drawChild(canvas, child, drawingTime);
    }
}
