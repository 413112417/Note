package pers.xjh.note.ui.detail.android;
import java.util.List;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.utils.ToastUtil;
import pers.xjh.note.widget.patternlock.PatternLockView;
import pers.xjh.note.widget.patternlock.listener.PatternLockViewListener;
import pers.xjh.note.widget.patternlock.utils.PatternLockUtils;

/**
 * Created by xjh on 17-7-4.
 */

public class UnlockActivity extends BaseActivity {

    private PatternLockView mPatternLockView;

    @Override
    protected int initContentView() {
        return R.layout.activity_unlock;
    }

    @Override
    protected void initView() {
        mPatternLockView = (PatternLockView) findViewById(R.id.pattern_lock_view);
        mPatternLockView.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {
                PatternLockUtils.patternToString(mPatternLockView, progressPattern);
            }

            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {
                ToastUtil.show(PatternLockUtils.patternToString(mPatternLockView, pattern));
            }

            @Override
            public void onCleared() {

            }
        });
    }
}
