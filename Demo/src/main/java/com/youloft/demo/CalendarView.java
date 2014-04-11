//package com.youloft.demo;
//
//import android.content.Context;
//import android.util.AttributeSet;
//import android.widget.ImageView;
//
///**
// * Created by javen on 14-4-10.
// */
//public class CalendarView extends ImageView {
//
//    public CalendarView(Context context) {
//        super(context);
//        initCalendarView();
//    }
//
//    public CalendarView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        initCalendarView();
//
//    }
//
//    public CalendarView(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//        initCalendarView();
//    }
//
//    /**
//     * 初始化CalenadrView
//     */
//    private void initCalendarView() {
//
//    }
//
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        if (getBackground() != null) {
//            setMeasuredDimension(getMeasuredWidth(), getBackground().getIntrinsicHeight());
//        }
//    }
//}
