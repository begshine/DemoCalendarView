package com.youloft.demo;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

/**
 * Swipe事件助手
 * <p/>
 * Created by javen on 14-4-10.
 */
public class SwipeEventHelper {

    public SwipeEventHelper(SwipeEventListener listener) {
        this.mSwipeListener = listener;
    }


    public static interface SwipeEventListener {

        /**
         * Swipe动作后
         *
         * @param direction
         */
        void onSwipeEvent(int direction);
    }


    private SwipeEventListener mSwipeListener = null;


    /**
     * 设置滑动listener
     *
     * @param swipeListener
     */
    public void setSwipeEventListener(SwipeEventListener swipeListener) {
        this.mSwipeListener = swipeListener;
    }

    private float lastTouchX, lastTouchY;


    private boolean canSwipe = false;

    public boolean onInterceptTouchEvent(View v, MotionEvent ev) {
        final int action = ev.getActionMasked();
        int f = ViewConfiguration.getTouchSlop();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastTouchX = ev.getX();
                lastTouchY = ev.getY();
                canSwipe = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(ev.getX() - lastTouchX) < f) {
                    canSwipe = false;
                } else {
                    canSwipe = true;
                    if (v instanceof ViewGroup) {
                        ((ViewGroup) v).requestDisallowInterceptTouchEvent(false);
                    }
                }
                lastTouchX = ev.getX();
                lastTouchY = ev.getY();
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                canSwipe = false;
                break;
        }
        return canSwipe;

    }

    public boolean onTouchEvent(MotionEvent ev) {
        final int action = ev.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastTouchX = ev.getX();
                lastTouchY = ev.getY();
                canSwipe = true;
                break;
            case MotionEvent.ACTION_MOVE:
                if (canSwipe) {
                    float curX = ev.getX();
                    float curY = ev.getY();
                    float detalX = curX - lastTouchX;
                    float detalY = curY - lastTouchY;
                    int direction = getDirection(detalX, detalY);
                    if (direction != -1) {
                        canSwipe = false;
                        notifySwipe(direction);
                    }
                    lastTouchX = curX;
                    lastTouchY = curY;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                lastTouchX = ev.getX();
                lastTouchY = ev.getY();
                break;
        }
        return canSwipe;
    }

    public static final int DIRECT_UP = 0;
    public static final int DIRECT_DOWN = 1;
    public static final int DIRECT_LEFT = 2;
    public static final int DIRECT_RIGHT = 3;


    /**
     * 根据横纵向量决定滑动方向
     *
     * @param detalX
     * @param detalY
     * @return
     */
    private int getDirection(float detalX, float detalY) {
        float absDetalX = Math.abs(detalX);
        float absDetalY = Math.abs(detalY);
        int f = ViewConfiguration.getTouchSlop();
        if (absDetalX == absDetalY || absDetalX < f) {
            return -1;
        }

        if (absDetalX > absDetalY) {
            return detalX < 0 ? DIRECT_LEFT : DIRECT_RIGHT;
        } else if (absDetalX < absDetalY) {
            return detalY < 0 ? DIRECT_UP : DIRECT_DOWN;
        }
        return -1;
    }

    /**
     * 通知Swipe
     *
     * @param direction
     */
    private void notifySwipe(int direction) {
        if (mSwipeListener != null) {
            mSwipeListener.onSwipeEvent(direction);
        }

    }


}
