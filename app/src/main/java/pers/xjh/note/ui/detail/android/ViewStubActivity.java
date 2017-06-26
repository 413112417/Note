package pers.xjh.note.ui.detail.android;

import android.view.View;
import android.view.ViewStub;
import android.widget.Button;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;

/**
 * Created by XJH on 2017/5/5.
 */

public class ViewStubActivity extends BaseActivity {

    private Button mBtnShow;

    private ViewStub mViewStub;

    private View mInflateView;

    @Override
    protected int initContentView() {
        return R.layout.activity_view_stub;
    }

    @Override
    protected void initView() {
        mBtnShow = (Button) findViewById(R.id.btn_show);
        mViewStub = (ViewStub) findViewById(R.id.view_stub);

        mBtnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mViewStub.setVisibility(View.VISIBLE);
                //两种方法都可以使ViewStub显示，第二种可以拿到view进行操作，但是多次调用会出错
                //ViewStub一旦可见就不存在了，取而代之的是加载的View，所以要判断加载的view是否可见
                if(mInflateView == null) {
                    mInflateView = mViewStub.inflate();
                }
            }
        });
    }
}
