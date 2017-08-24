package pers.xjh.note.ui.detail.android;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.View;
import java.util.HashMap;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;

/**
 * Created by xjh on 17-8-23.
 */

public class SoundPoolActivity extends BaseActivity implements View.OnClickListener {

    private SoundPool mSoundPool;

    private HashMap<Integer, Integer> mSoundId = new HashMap<>();

    @Override
    protected int initContentView() {
        return R.layout.activity_sound_pool;
    }

    @Override
    protected void initView() {
        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
        findViewById(R.id.btn_4).setOnClickListener(this);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_VOICE_COMMUNICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .build();

            mSoundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .build();

        }//当系统的SDK版本小于21时
        else {//设置最多可容纳2个音频流，音频的品质为5
            mSoundPool = new SoundPool(2, AudioManager.STREAM_SYSTEM, 5);
        }

        mSoundId.put(1, mSoundPool.load(this, R.raw.failed, 1));
        mSoundId.put(2, mSoundPool.load(this, R.raw.success, 1));
        mSoundId.put(3, mSoundPool.load(this, R.raw.manual, 1));
        mSoundId.put(4, mSoundPool.load(this, R.raw.verifing, 1));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                mSoundPool.play(mSoundId.get(1), 1, 1, 0, 0, 1);
                break;
            case R.id.btn_2:
                mSoundPool.play(mSoundId.get(2), 1, 1, 0, 0, 1);
                break;
            case R.id.btn_3:
                mSoundPool.play(mSoundId.get(3), 1, 1, 0, 0, 1);
                break;
            case R.id.btn_4:
                mSoundPool.play(mSoundId.get(4), 1, 1, 0, 0, 1);
                break;
        }
    }
}
