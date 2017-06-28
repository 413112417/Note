package pers.xjh.note.widget.dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import pers.xjh.note.R;
import pers.xjh.note.utils.UiUtil;


/**
 * 选择对话框
 * Created by xjh on 2017/1/16.
 */
public class PickDialog extends BaseDialog {

    /** 根布局 */
    private ViewGroup mRootContainer;
    /** 标题 */
    private TextView mTvTitle;
    /** 条目的容器 */
    private LinearLayout mItemContainer;
    /** 构造器 */
    private Builder mBuilder;

    public PickDialog(Context context) {
        this(context, R.style.base_dialog);
    }

    public PickDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    /**
     * 对话框构建工具
     */
    public static class Builder {

        /** 对话框 */
        private PickDialog mDialog;
        /** 标题文字 */
        private String mTitleStr;
        /** 条目的数据 */
        private String[] mItemDatas;
        /** 条目点击事件 */
        private OnItemClickListener mOnItemClickListener;
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
         * 设置条目数据
         * @param datas
         * @return
         */
        public Builder setItemDatas(String... datas) {
            mItemDatas = datas;
            return this;
        }

        /**
         * 设置条目点击事件
         * @param listener
         * @return
         */
        public Builder setItemClickListener(OnItemClickListener listener) {
            mOnItemClickListener = listener;
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
        public PickDialog build() {
            mDialog = new PickDialog(mContext);
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
        setContentView(R.layout.dialog_pick);

        mRootContainer = (ViewGroup) findViewById(R.id.root_container);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mItemContainer = (LinearLayout) findViewById(R.id.item_container);

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

            //设置条目数据
            if(builder.mItemDatas != null && builder.mItemDatas.length > 0) {
                for(int i=0; i<builder.mItemDatas.length; i++) {
                    final TextView tv = new TextView(getContext());
                    tv.setPadding(0, UiUtil.dp2px(6), 0, UiUtil.dp2px(6));
                    tv.setTextSize(16);
                    tv.setTextColor(getContext().getResources().getColor(R.color.black));
                    tv.setText(builder.mItemDatas[i]);
                    tv.setBackground(getContext().getResources().getDrawable(R.drawable.bg_press_1));
                    tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    tv.setGravity(Gravity.CENTER);
                    //设置条目点击事件
                    if(builder.mOnItemClickListener != null) {
                        final int finalI = i;
                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(builder.mOnItemClickListener != null) {
                                    builder.mOnItemClickListener.onItemClicked(PickDialog.this, finalI);
                                }
                            }
                        });
                    }
                    mItemContainer.addView(tv);
                }
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
     * 条目的点击回调
     */
    public interface OnItemClickListener {
        void onItemClicked(PickDialog dialog, int position);
    }
}
