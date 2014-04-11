package com.youloft.demo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ValueAnimator;
import com.youloft.widgets.CalendarView;

/**
 * 滑动Layout
 * <p/>
 * <p/>
 * Created by javen on 14-4-10.
 */
public class SwipeLayout extends FrameLayout implements SwipeEventHelper.SwipeEventListener {


    public SwipeLayout(Context context) {
        super(context);
        mSwipeHelper = new SwipeEventHelper(this);
    }

    public SwipeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mSwipeHelper = new SwipeEventHelper(this);
    }

    private SwipeEventHelper mSwipeHelper = null;

    public SwipeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mSwipeHelper = new SwipeEventHelper(this);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mSwipeHelper.onInterceptTouchEvent(this, ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return mSwipeHelper.onTouchEvent(ev);
    }

    boolean isAnimating = false;

    /**
     * Swipe动作后
     *
     * @param direction
     */
    @Override
    public void onSwipeEvent(int direction) {
        if (isAnimating) {
            return;
        }
        switch (direction) {
            case SwipeEventHelper.DIRECT_LEFT:
                onRight();
                break;
            case SwipeEventHelper.DIRECT_RIGHT:
                onLeft();
                break;
        }
    }

    int row = 6;

    /**
     * 向右滑动
     */
    private void onRight() {
        if (row == 1)
            row = 7;
        final View back = getChildAt(0);
        final View front = getChildAt(1);
        final ViewTreeObserver treeObserver = getViewTreeObserver();
        row--;
        treeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                                              @Override
                                              public boolean onPreDraw() {
                                                  if (treeObserver.isAlive()) {
                                                      treeObserver.removeOnPreDrawListener(this);
                                                  }
                                                  ValueAnimator hAnimator = ValueAnimator.ofInt(getHeight(), ((CalendarView) front.findViewWithTag("value")).getHeightAtRow(row));
                                                  hAnimator.setDuration(300);
                                                  hAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                                      @Override
                                                      public void onAnimationUpdate(ValueAnimator animation) {
                                                          Integer value = (Integer) animation.getAnimatedValue();
//                                                          layout(getLeft(), getTop(), getRight(), value);
                                                          getLayoutParams().height = value;
                                                          requestLayout();
                                                      }
                                                  });

                                                  ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
                                                  animator.setDuration(600);
                                                  bringChildToFront(back);
                                                  animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                                      @Override
                                                      public void onAnimationUpdate(ValueAnimator animation) {
                                                          Float value = (Float) animation.getAnimatedValue();
                                                          float w = getWidth() * value;
                                                          front.scrollTo((int) w, 0);
                                                          back.scrollTo((int) (-(getWidth() - w)), 0);
                                                      }
                                                  });
                                                  animator.addListener(new Animator.AnimatorListener() {
                                                      @Override
                                                      public void onAnimationStart(Animator animation) {
                                                          isAnimating = true;
                                                      }

                                                      @Override
                                                      public void onAnimationEnd(Animator animation) {
                                                          front.setVisibility(View.GONE);
                                                          isAnimating = false;
                                                      }

                                                      @Override
                                                      public void onAnimationCancel(Animator animation) {
                                                          isAnimating = false;

                                                      }

                                                      @Override
                                                      public void onAnimationRepeat(Animator animation) {

                                                      }
                                                  });


                                                  AnimatorSet as = new AnimatorSet();
                                                  as.playTogether(hAnimator, animator);
                                                  as.start();
                                                  return false;
                                              }
                                          }
        );
        back.setVisibility(View.VISIBLE);
    }

    /**
     * 向左滑动
     */
    private void onLeft() {

    }
}
