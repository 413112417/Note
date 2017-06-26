package pers.xjh.note.ui.detail.android;

import android.view.View;
import android.widget.Button;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.widget.dispatchEvent.MyView;
import pers.xjh.note.widget.dispatchEvent.MyViewGroupA;
import pers.xjh.note.widget.dispatchEvent.MyViewGroupB;

/**
 * 事件分发
 * Created by XJH on 2017/4/13.
 */

public class DispatchEventActivity extends BaseActivity implements View.OnClickListener {

    private Button mBtnMyViewGroupAIntercept, mBtnMyViewGroupBIntercept, mBtnMyViewGroupADisposed, mBtnMyViewGroupBDisposed, mBtnMyViewDisposed;

    private boolean mMyViewGroupAIntercept, mMyViewGroupBIntercept, mMyViewGroupADisposed, mMyViewGroupBDisposed, mMyViewDisposed;

    private MyViewGroupA mMyViewGroupA;

    private MyViewGroupB mMyViewGroupB;

    private MyView mMyView;

    @Override
    protected int initContentView() {
        return R.layout.activity_dispatch_event;
    }

    @Override
    protected void initView() {
        mBtnMyViewGroupAIntercept = (Button) findViewById(R.id.btn_my_view_group_a_intercept);
        mBtnMyViewGroupBIntercept = (Button) findViewById(R.id.btn_my_view_group_b_intercept);
        mBtnMyViewGroupADisposed = (Button) findViewById(R.id.btn_my_view_group_a_disposed);
        mBtnMyViewGroupBDisposed = (Button) findViewById(R.id.btn_my_view_group_b_disposed);
        mBtnMyViewDisposed = (Button) findViewById(R.id.btn_my_view_disposed);

        mMyViewGroupA = (MyViewGroupA) findViewById(R.id.my_view_group_a);
        mMyViewGroupB = (MyViewGroupB) findViewById(R.id.my_view_group_b);
        mMyView = (MyView) findViewById(R.id.my_view);

        mBtnMyViewGroupAIntercept.setOnClickListener(this);
        mBtnMyViewGroupBIntercept.setOnClickListener(this);
        mBtnMyViewGroupADisposed.setOnClickListener(this);
        mBtnMyViewGroupBDisposed.setOnClickListener(this);
        mBtnMyViewDisposed.setOnClickListener(this);

        setBtnText();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_my_view_group_a_intercept:
                mMyViewGroupAIntercept = !mMyViewGroupAIntercept;
                break;
            case R.id.btn_my_view_group_b_intercept:
                mMyViewGroupBIntercept = !mMyViewGroupBIntercept;
                break;
            case R.id.btn_my_view_group_a_disposed:
                mMyViewGroupADisposed = !mMyViewGroupADisposed;
                break;
            case R.id.btn_my_view_group_b_disposed:
                mMyViewGroupBDisposed = !mMyViewGroupBDisposed;
                break;
            case R.id.btn_my_view_disposed:
                mMyViewDisposed = !mMyViewDisposed;
                break;
        }
        setBtnText();
        reset();
    }

    /**
     * 设置按钮文本
     */
    private void setBtnText() {
        mBtnMyViewGroupAIntercept.setText("传递" + mMyViewGroupAIntercept);
        mBtnMyViewGroupBIntercept.setText("传递" + mMyViewGroupBIntercept);
        mBtnMyViewGroupADisposed.setText("处理" + mMyViewGroupADisposed);
        mBtnMyViewGroupBDisposed.setText("处理" + mMyViewGroupBDisposed);
        mBtnMyViewDisposed.setText("处理" + mMyViewDisposed);
    }

    /**
     * 重新设置属性
     */
    private void reset() {
        mMyViewGroupA.setIntercept(mMyViewGroupAIntercept);
        mMyViewGroupA.setDisposed(mMyViewGroupADisposed);
        mMyViewGroupB.setIntercept(mMyViewGroupBIntercept);
        mMyViewGroupB.setDisposed(mMyViewGroupBDisposed);
        mMyView.setDisposed(mMyViewDisposed);
    }
}
