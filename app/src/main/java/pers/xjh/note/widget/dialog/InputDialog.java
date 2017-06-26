package pers.xjh.note.widget.dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import pers.xjh.note.R;


/**
 * 输入对话框
 * Created by xjh on 2017/1/16.
 */
public class InputDialog extends BaseDialog {

    /** 根布局 */
    private ViewGroup mRootContainer;
    /** 标题 */
    private TextView mTvTitle;
    /** 输入框 */
    private EditText mEditText;
    /** 确认和添加按钮 */
    private TextView mBtnPositive, mBtnNegative;
    /** 构造器 */
    private Builder mBuilder;

    public InputDialog(Context context) {
        this(context, R.style.base_dialog);
    }

    public InputDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    /**
     * 对话框构建工具
     */
    public static class Builder {

        /** 对话框 */
        private InputDialog mDialog;
        /** 标题文字 */
        private String mTitleStr;
        /** 确定按钮文本 */
        private String mBtnPositiveStr;
        /** 取消按钮文本 */
        private String mBtnNegativeStr;
        /** 确定按钮点击事件 */
        private OnClickPositiveListener mBtnPositiveClickListener;
        /** 取消按钮点击事件 */
        private OnClickNegativeListener mBtnNegativeClickListener;
        /** 上下文 */
        private Context mContext;
        /** 进入动画资源id */
        private int mInAnimationId;
        /** 消失动画资源id */
        private int mOutAnimationId;
        /** 对话框对齐方式 */
        private int mGravity;
        /** 宽度百分比 */
        private float mWidthPercent;
        /** 是否禁止圆角 */
        private boolean mAvoidCorner;

        public Builder(Context context) {
            mContext = context;
        }

        /**
         * 设置标题
         * @param titleStr
         * @return
         */
        public Builder setTitle(String titleStr) {
            mTitleStr = titleStr;
            return this;
        }

        /**
         * 设置确定按钮
         * @param btnStr
         * @param listener
         * @return
         */
        public Builder setPositiveButton(String btnStr, final OnClickPositiveListener listener) {
            mBtnPositiveStr = btnStr;
            mBtnPositiveClickListener = listener;
            return this;
        }

        /**
         * 设置取消按钮
         * @param btnStr
         * @param listener
         * @return
         */
        public Builder setNegativeButton(String btnStr, final OnClickNegativeListener listener) {
            mBtnNegativeStr = btnStr;
            mBtnNegativeClickListener = listener;
            return this;
        }

        /**
         * 设置进入时动画
         * @param animationId
         * @return
         */
        public Builder setInAnimation(int animationId) {
            mInAnimationId = animationId;
            return this;
        }

        /**
         * 设置消失时动画
         * @param animationId
         * @return
         */
        public Builder setOutAnimation(int animationId) {
            mOutAnimationId = animationId;
            return this;
        }

        /**
         * 设置对齐方式
         * @param gravity
         * @return
         */
        public Builder setGravity(int gravity) {
            mGravity = gravity;
            return this;
        }

        /**
         * 设置宽度百分比
         * @param percent
         * @return
         */
        public Builder setWidthPercent(float percent) {
            mWidthPercent = percent;
            return this;
        }

        /**
         * 设置是否需要圆角
         * @param avoidCorner
         * @return
         */
        public Builder setAvoidCorner(boolean avoidCorner) {
            mAvoidCorner = avoidCorner;
            return this;
        }

        /**
         * 生成对话框
         * @return
         */
        public InputDialog build() {
            mDialog = new InputDialog(mContext);
            mDialog.setBuilder(this);
            return mDialog;
        }
    }

    private void setBuilder(Builder builder) {
        mBuilder = builder;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_input);

        mRootContainer = (ViewGroup) findViewById(R.id.root_container);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mEditText = (EditText) findViewById(R.id.et_input);
        mBtnPositive = (TextView) findViewById(R.id.btn_positive);
        mBtnNegative = (TextView) findViewById(R.id.btn_negative);

        setParams(mBuilder);
    }

    /**
     * 设置参数
     */
    private void setParams(final Builder builder) {
        if(builder != null) {

            //设置标题文字
            if(!TextUtils.isEmpty(builder.mTitleStr)) {
                mTvTitle.setText(builder.mTitleStr);
            }

            //按钮文本
            mBtnPositive.setText(TextUtils.isEmpty(builder.mBtnPositiveStr) ? "确定" : builder.mBtnPositiveStr);
            mBtnNegative.setText(TextUtils.isEmpty(builder.mBtnNegativeStr) ? "取消" : builder.mBtnNegativeStr);

            //设置按钮一点击事件
            if(builder.mBtnPositiveClickListener != null) {
                mBtnPositive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        builder.mBtnPositiveClickListener.onClick(mEditText.getText().toString(), InputDialog.this);
                    }
                });
            }

            //设置按钮二点击事件
            if(builder.mBtnNegativeClickListener != null) {
                mBtnNegative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        builder.mBtnNegativeClickListener.onClick(InputDialog.this);
                    }
                });
            }

            //设置进入动画
            if(builder.mInAnimationId != 0) {
                setInAnimation(AnimationUtils.loadAnimation(getContext(), builder.mInAnimationId));
            }

            //设置消失动画
            if(builder.mOutAnimationId != 0) {
                setOutAnimation(AnimationUtils.loadAnimation(getContext(), builder.mOutAnimationId));
            }

            //设置圆角
            if(builder.mAvoidCorner) {
                mRootContainer.setBackgroundResource(0);
                mRootContainer.setBackgroundColor(getContext().getResources().getColor(R.color.white));
            }

            //设置对齐方式
            if(builder.mGravity != 0) {
                getWindow().setGravity(builder.mGravity);
            }

            //设置宽度，默认为屏幕宽度的80%
            WindowManager m = getWindow().getWindowManager();
            Display d = m.getDefaultDisplay();
            WindowManager.LayoutParams p = getWindow().getAttributes();
            if(builder.mWidthPercent != 0) {
                p.width = (int) (d.getWidth() * builder.mWidthPercent);
            } else {
                p.width = (int) (d.getWidth() * 0.8);
            }

            getWindow().setAttributes(p);
        }
    }

    /**
     * 确认按钮点击的回调
     */
    public interface OnClickPositiveListener {
        void onClick(String inputString, InputDialog dialog);
    }

    /**
     * 取消按钮点击的回调
     */
    public interface OnClickNegativeListener {
        void onClick(InputDialog dialog);
    }
}
