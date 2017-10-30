package pers.xjh.note.ui.detail.android;

import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaFormat;
import android.util.Log;
import android.view.SurfaceView;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.SocketChannel;
import java.util.List;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.utils.ThreadPool;
import pers.xjh.note.widget.CameraPreview;
import pers.xjh.zxing.camera.PreviewCallback;

/**
 * Created by xjh on 17-10-18.
 */

public class MediaCodecActivity extends BaseActivity {

    private SurfaceView mSurfaceView;

    private boolean isSupportHardWare;

    @Override
    protected int initContentView() {
        return R.layout.activity_mediacodec;
    }

    @Override
    protected void initView() {
        mSurfaceView = (SurfaceView) findViewById(R.id.surface_view);

        checkMediaDecoder();
    }

    private void checkMediaDecoder() {
        int numCodecs = MediaCodecList.getCodecCount();
        MediaCodecInfo mediaCodecInfo = null;
        for (int i = 0; i < numCodecs && mediaCodecInfo == null; i++) {
            MediaCodecInfo info = MediaCodecList.getCodecInfoAt(i);
            if (info.isEncoder()) {
                continue;
            }
            String[] types = info.getSupportedTypes();
            boolean found = false;
            for (int j = 0; j < types.length && !found; j++) {
                if (types[j].equals("video/avc")) {
                    Log.d("asd", "found");
                    found = true;
                    isSupportHardWare = found;
                    return;
                }
            }
            if (!found) {
                continue;
            }
            mediaCodecInfo = info;
        }
        mediaCodecInfo.getCapabilitiesForType("video/avc");
    }
}
