package pers.xjh.note.ui.detail.android;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.utils.ToastUtil;
import pers.xjh.note.widget.dialog.DifferentDisplay;

/**
 * 多屏显示
 * Created by xjh on 17-9-28.
 */

public class DualviewActivity extends BaseActivity {

    private DisplayManager mDisplayManager;//屏幕管理类

    private Display[]  mDisplays;//屏幕数组

    @Override
    protected int initContentView() {
        return R.layout.activity_dualview;
    }

    @Override
    protected void initView() {
        mDisplayManager = (DisplayManager) getSystemService(Context.DISPLAY_SERVICE);
        mDisplays = mDisplayManager.getDisplays();

        findViewById(R.id.btn_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDisplays.length < 2) {
                    ToastUtil.show("只有一个屏幕！");
                } else {
                    DifferentDisplay mPresentation = new DifferentDisplay(DualviewActivity.this, mDisplays[1]);//displays[1]是副屏
                    mPresentation.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                    mPresentation.show();
                }
            }
        });
    }
}
