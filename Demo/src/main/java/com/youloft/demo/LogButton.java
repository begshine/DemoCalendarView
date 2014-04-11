package com.youloft.demo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * Created by javen on 14-4-10.
 */
public class LogButton extends Button {
    public LogButton(Context context) {
        super(context);
    }

    public LogButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LogButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("LogButton", "LogButton TouchAction" + LogUtil.logTouchEventAction(event));
        return super.onTouchEvent(event);
    }
}
