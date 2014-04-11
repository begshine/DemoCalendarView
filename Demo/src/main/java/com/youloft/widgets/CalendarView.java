package com.youloft.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youloft.demo.R;

/**
 * 日历控件
 * <p/>
 * <p/>
 * Created by javen on 14-4-11.
 */
public class CalendarView extends View {


    private static final int COLS = 7;//有多少列
    private static final int ROWS = 6;//有多少行

    private int hspace = 3;//横向的间隔
    private int vspace = 3;//纵向的间隔

    private int itemHeight = 0;//子项高度
    private int itemWidth = 0;//子项宽度


    public CalendarView(Context context) {
        super(context);
        initView();
    }


    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private DayView mChilds[] = new DayView[42];

    private void initView() {
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = width / COLS * ROWS;
        setMeasuredDimension(width, height);
        itemWidth = (width - getTotalHSpace() - getPaddingLeft() - getPaddingRight()) / COLS;
        itemHeight = (height - getTotalVSpace() - getPaddingTop() - getPaddingBottom()) / ROWS;
        for (int i = 0; i < mChilds.length; i++) {
            if (mChilds[i] == null) {
                mChilds[i] = new DayView(getContext());
            }
            mChilds[i].measure(MeasureSpec.makeMeasureSpec(itemWidth, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(itemHeight, MeasureSpec.EXACTLY));
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        for (int i = 0; i < mChilds.length; i++) {
            DayItem item = new DayItem();
            item.lunarDate = "初" + i;
            item.solarDate = i + "";
            mChilds[i].setData(item);
            mChilds[i].layout(0, 0, itemWidth, itemHeight);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int restoreCount = canvas.save();
        canvas.translate(getPaddingLeft(), getPaddingTop());
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                int itemRestore = canvas.save();
                canvas.translate((float) (col * itemWidth + col * hspace * 1.5), row * itemHeight + row * vspace);
                DayView child = mChilds[row * COLS + col];
                if (child != null)
                    child.draw(canvas);
                canvas.restoreToCount(itemRestore);
            }
        }
        canvas.restoreToCount(restoreCount);
    }

    /**
     * 根据数据获取高度
     *
     * @return
     */
    public int getHeightByData() {
        return 0;
    }

    /**
     * 按行获取高度
     *
     * @param row
     * @return
     */
    public int getHeightAtRow(int row) {
        return row * itemHeight + (row - 1) * vspace;
    }

    /**
     * 获取横向总共有多少水平间隔
     *
     * @return
     */
    private int getTotalHSpace() {
        return (COLS - 1) * hspace;
    }


    /**
     * 获取垂直间隔
     *
     * @return
     */
    private int getTotalVSpace() {
        return (ROWS - 1) * vspace;
    }


    /**
     * 日历数据
     * <p/>
     * 每一天的用户数据
     */
    public static class DayItem {
        public int ladyLevel;
        public String solarDate;
        public String lunarDate;
        public int extraFlag;
        public String fest;
        public int eventType;
    }


    /**
     * 每一天的View
     */
    public static class DayView extends RelativeLayout {
        private View dayviewLady;
        private TextView dayviewSolar;
        private TextView dayviewLunar;
        private View dayviewEvent;
        private TextView dayviewFestive;
        private ImageView dayviewExtra;

        private void assignViews() {
            dayviewLady = findViewById(R.id.dayview_lady);
            dayviewSolar = (TextView) findViewById(R.id.dayview_solar);
            dayviewLunar = (TextView) findViewById(R.id.dayview_lunar);
            dayviewEvent = findViewById(R.id.dayview_event);
            dayviewFestive = (TextView) findViewById(R.id.dayview_festive);
            dayviewExtra = (ImageView) findViewById(R.id.dayview_extra);
        }

        public DayView(Context context) {
            super(context);
            inflate(context, R.layout.dayview_template, this);
            assignViews();
        }

        /**
         * 绑定数据
         *
         * @param dayItem
         */
        public void setData(DayItem dayItem) {
            dayviewSolar.setText(dayItem.solarDate);
            dayviewLunar.setText(dayItem.lunarDate);
        }


    }


}
