package pers.xjh.note.widget.dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Map;

import pers.xjh.note.R;
import pers.xjh.note.runtime.RtEnv;
import pers.xjh.note.utils.UiUtil;


/**
 * Map生成对话框
 * Created by xjh on 2017/1/16.
 */
public class MapDialog extends BaseDialog {

    /** 根布局 */
    private ViewGroup mRootContainer;
    /** 标题 */
    private TextView mTvTitle;
    /** 条目的容器 */
    private LinearLayout mItemContainer;
    /** 确认和添加按钮 */
    private TextView mBtnConfirm, mBtnNew;
    /** 构造器 */
    private Builder mBuilder;

    public MapDialog(Context context) {
        this(context, R.style.base_dialog);
    }

    public MapDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    /**
     * 对话框构建工具
     */
    public static class Builder {

        /** 对话框 */
        private MapDialog mDialog;
        /** 标题文字 */
        private String mTitleStr;
        /** 条目的数据 */
        private Map<String, String> mMap;
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
         * @param map
         * @return
         */
        public Builder setMap(Map<String, String> map) {
            mMap = map;
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
        public MapDialog build() {
            mDialog = new MapDialog(mContext);
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
        setContentView(R.layout.dialog_map);

        mRootContainer = (ViewGroup) findViewById(R.id.root_container);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mItemContainer = (LinearLayout) findViewById(R.id.item_container);
        mBtnNew = (TextView) findViewById(R.id.btn_new);
        mBtnConfirm = (TextView) findViewById(R.id.btn_confirm);

        mBtnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = LayoutInflater.from(getContext()).inflate(R.layout.item_dialog_map, mItemContainer, false);
                ImageView imgDelete = (ImageView) view.findViewById(R.id.img_delete);
                imgDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemContainer.removeView(view);
                    }
                });
                mItemContainer.addView(view);
            }
        });

        mBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //隐藏软键盘
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

                mBuilder.mMap.clear();
                int count = mItemContainer.getChildCount();
                for(int i=0; i<count; i++) {
                    View view = mItemContainer.getChildAt(i);
                    EditText etKey = (EditText) view.findViewById(R.id.et_key);
                    EditText etValue = (EditText) view.findViewById(R.id.et_value);
                    if(etKey != null) {
                        String key = etKey.getText().toString();
                        if(!TextUtils.isEmpty(key)) {
                            String value = etValue.getText().toString();
                            mBuilder.mMap.put(key, value);
                        }
                    }
                }
                dismiss();
            }
        });

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
            if(builder.mMap != null && builder.mMap.size() > 0) {
                for(final Map.Entry<String, String> entry : builder.mMap.entrySet()) {
                    final View view = LayoutInflater.from(getContext()).inflate(R.layout.item_dialog_map, mItemContainer, false);
                    EditText etKey = (EditText) view.findViewById(R.id.et_key);
                    EditText etValue = (EditText) view.findViewById(R.id.et_value);
                    ImageView imgDelete = (ImageView) view.findViewById(R.id.img_delete);
                    etKey.setText(entry.getKey());
                    etValue.setText(entry.getValue());
                    imgDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            builder.mMap.remove(entry.getKey());
                            mItemContainer.removeView(view);
                        }
                    });
                    mItemContainer.addView(view);
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
}
