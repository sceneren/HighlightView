package com.flyjingfish.highlightviewlib;


import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public class HighlightImageView extends AppCompatImageView implements HighlightView, HighlightDrawView, AnimHolder {
    private final HighlightAnimHolder mHighlightAnimHolder = new HighlightAnimHolder(this, this);
    private final HighlightDraw mHighlightDraw = new HighlightDraw(this);

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            try {
                invalidate();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };


    public HighlightImageView(@NonNull Context context) {
        this(context, null);
    }

    public HighlightImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HighlightImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        InitAttrs.init(context, attrs, mHighlightAnimHolder);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mHighlightDraw.onDraw(canvas);
        if (mHandler != null) {
            mHandler.sendEmptyMessage(100);
        }


    }

    @Override
    public View thisView() {
        return this;
    }

    @Override
    public HighlightAnimHolder getHighlightAnimHolder() {
        return mHighlightAnimHolder;
    }

    @Override
    public HighlightDraw getHighlightDraw() {
        return mHighlightDraw;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mHighlightAnimHolder.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        mHighlightAnimHolder.drawableStateChanged();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;
    }
}
