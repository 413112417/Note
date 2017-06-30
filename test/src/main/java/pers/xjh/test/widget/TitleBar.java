package pers.xjh.test.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import pers.xjh.test.R;
import pers.xjh.test.util.UiUtil;

/**
 * Created by XJH on 2017/4/5.
 */

public class TitleBar extends FrameLayout {

    //背景颜色
    private int mTitleBackground;
    //标题
    private String mTitle;
    //标题文本大小
    private float mTitleTextSize;
    //标题文本颜色
    private int mTitleTextColor;
    //左边背景
    private int mLeftBackground;
    //右边文本
    private String mRightText;
    //右边文本大小
    private float mRightTextSize;
    //右边文本颜色
    private int mRightTextColor;
    //标题根布局
    private LinearLayout mTitleContainer;
    //左边标题
    private LinearLayout mLeftTitleContainer;
    //左边标题图片
    private ImageView mImgTitleLeft;
    //标题文本
    private TextView mTvTitle;
    //右边标题文本
    private TextView mTvTitleRight;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if(attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TestTitleBar);
            mTitleBackground = ta.getColor(R.styleable.TestTitleBar_titleBackground, Color.rgb(105, 105, 105));
            mTitle = ta.getString(R.styleable.TestTitleBar_title);
            mTitleTextSize = ta.getDimension(R.styleable.TestTitleBar_titleTextSize, 16);
            mTitleTextColor = ta.getColor(R.styleable.TestTitleBar_titleTextColor, Color.rgb(255, 255, 255));
            mLeftBackground = ta.getResourceId(R.styleable.TestTitleBar_leftBackground, R.drawable.ic_back_arrow);
            mRightText = ta.getString(R.styleable.TestTitleBar_rightText);
            mRightTextSize = ta.getDimension(R.styleable.TestTitleBar_rightTextSize, 12);
            mRightTextColor = ta.getColor(R.styleable.TestTitleBar_titleTextColor, Color.WHITE);
            ta.recycle();
        }
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        //添加布局
        LayoutInflater.from(getContext()).inflate(R.layout.test_view_title_bar, this, true);

        mTitleContainer = (LinearLayout) findViewById(R.id.ll_title);
        mLeftTitleContainer = (LinearLayout) findViewById(R.id.ll_title_left);
        mImgTitleLeft = (ImageView) findViewById(R.id.img_title_left);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitleRight = (TextView) findViewById(R.id.tv_title_right);

        mTitleContainer.setBackgroundColor(mTitleBackground);
        mImgTitleLeft.setBackgroundResource(mLeftBackground);

        mTvTitle.setText(mTitle);
        mTvTitle.setTextColor(mTitleTextColor);
        mTvTitle.setTextSize(mTitleTextSize);

        mTvTitleRight.setText(mRightText);
        mTvTitleRight.setTextColor(mRightTextColor);
        mTvTitleRight.setTextSize(mRightTextSize);
    }

    /**
     * 设置左边标题的点击事件
     * @param listener
     */
    public void setLeftTitleClickListener(OnClickListener listener) {
        if(listener != null) {
            mLeftTitleContainer.setOnClickListener(listener);
        }
    }

    /**
     * 设置右边标题的点击事件
     * @param listener
     */
    public void setRightTitleClickListener(OnClickListener listener) {
        if(listener != null) {
            mTvTitleRight.setOnClickListener(listener);
        }
    }

    /**
     * 设置左边是否可见
     * @param visibility
     */
    public void setLeftTitleVisibility(boolean visibility) {
        if(visibility) {
            mLeftTitleContainer.setVisibility(View.VISIBLE);
        } else {
            mLeftTitleContainer.setVisibility(View.INVISIBLE);
            mLeftTitleContainer.setOnClickListener(null);
        }
    }

    /**
     * 设置标题
     * @param title
     */
    public void setTitle(String title) {
        mTvTitle.setText(title);
    }

    /**
     * 设置右边的字
     * @param titleRight
     */
    public void setTitleRight(String titleRight) {
        mTvTitleRight.setText(titleRight);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(measureHeight(heightMeasureSpec), MeasureSpec.EXACTLY));
    }

    /**
     * 测量高度
     */
    private int measureHeight(int heightMeasureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);

        if(specMode == MeasureSpec.UNSPECIFIED || specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else if(specMode == MeasureSpec.AT_MOST) {
            result = Math.min(UiUtil.dp2px(getContext(), 40), specSize);
        }
        return result;
    }
}
