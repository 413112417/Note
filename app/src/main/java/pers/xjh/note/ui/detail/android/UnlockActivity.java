package pers.xjh.note.ui.detail.android;
import android.graphics.Color;
import android.widget.TextView;

import java.util.List;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.widget.patternlock.PatternLockView;
import pers.xjh.note.widget.patternlock.listener.PatternLockViewListener;
import pers.xjh.note.widget.patternlock.utils.PatternLockUtils;

/**
 * Created by xjh on 17-7-4.
 */

public class UnlockActivity extends BaseActivity {

    private TextView mTvMsg;

    private PatternLockView mPatternLockView;

    private int mState;

    private String mFirstPattern;

    private String mPattern;

    @Override
    protected int initContentView() {
        return R.layout.activity_unlock;
    }

    @Override
    protected void initView() {
        mTvMsg = (TextView) findViewById(R.id.tv_msg);
        mPatternLockView = (PatternLockView) findViewById(R.id.pattern_lock_view);
        mPatternLockView.setCorrectStateColor(Color.BLUE);
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
                if(mState == 0) {
                    mFirstPattern = PatternLockUtils.patternToString(mPatternLockView, pattern);
                    mState = 1;
                    mTvMsg.setText("请再次设置手势密码");
                    mPatternLockView.clearPattern();
                } else if(mState == 1) {
                    if(mFirstPattern.equals(PatternLockUtils.patternToString(mPatternLockView, pattern))) {
                        mTvMsg.setText("设置成功");
                        mPatternLockView.setViewMode(PatternLockView.PatternViewMode.CORRECT);
                        mPattern = mFirstPattern;
                        mState = 2;
                    } else {
                        mTvMsg.setText("两次密码不一致");
                        mPatternLockView.setViewMode(PatternLockView.PatternViewMode.WRONG);
                    }
                } else if(mState == 2) {
                    if(mPattern.equals(PatternLockUtils.patternToString(mPatternLockView, pattern))) {
                        mTvMsg.setText("解锁成功");
                        mPatternLockView.setViewMode(PatternLockView.PatternViewMode.CORRECT);
                    } else {
                        mTvMsg.setText("解锁失败");
                        mPatternLockView.setViewMode(PatternLockView.PatternViewMode.WRONG);
                    }
                }
            }

            @Override
            public void onCleared() {

            }
        });
    }
}
