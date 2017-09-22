package pers.xjh.note.ui.detail.function;

import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.MediaPlayer.PlayM4.Player;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;

/**
 * Created by xjh on 17-9-21.
 */

public class HikvisionActivity extends BaseActivity {

    private Button mBtnPlay, mBtnTake;
    private EditText mEtUrl;
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private ImageView mImg;

    private int mPort;
    private Player mPlayer;

    @Override
    protected int initContentView() {
        return R.layout.activity_hikvision;
    }

    @Override
    protected void initView() {
        mSurfaceView = (SurfaceView) findViewById(R.id.surface_view);
        mEtUrl = (EditText) findViewById(R.id.et_url);
        mImg = (ImageView) findViewById(R.id.img);
        mBtnPlay = (Button) findViewById(R.id.btn_play);
        mBtnTake = (Button) findViewById(R.id.btn_take);

        mSurfaceHolder = mSurfaceView.getHolder();

        mPlayer = Player.getInstance();
        mPort = mPlayer.getPort();
        mPlayer.setStreamOpenMode(mPort, Player.STREAM_REALTIME);
        mPlayer.openStream(mPort, null, 0, 1024*100000);

        mBtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.play(mPort, mSurfaceHolder);
            }
        });

        byte[] a = new byte[1024];
        InputStream is = new ByteArrayInputStream(a);

    }
}
