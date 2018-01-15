package pers.xjh.note.ui.detail.jni.usbCamera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import pers.xjh.note.R;
import pers.xjh.note.model.bean.ImageProc;
import pers.xjh.note.utils.FileUtil;

public class CameraView2 extends SurfaceView implements SurfaceHolder.Callback, Runnable, ImageProc.RecordCallback {

	private static final String TAG = "CameraView";
	protected Context mContext;
	private SurfaceHolder mSurfaceHolder;

	private boolean mCameraExists = false;
	private boolean mShouldStop = false;

	private ImageProc mUsbCameraNative = new ImageProc();
	private boolean mIsVideoRecording = false;

	private boolean mCaptureFlag = false;

	private MediaMuxerCore mediaMuxerCore;

	private static final int RAW_AUDIO_SHUTTER = 1;
	private static final int RAW_AUDIO_VIDEO_RECORD = 2;
	private SoundPool mSoundPool;
	private int mCurrStreamId;
	private HashMap<Integer,Integer> mSoundMap = new HashMap<Integer, Integer>();

	private int index = 1;

	public CameraView2(Context context) {
		super(context);
		initCameraView(context);
	}

	public CameraView2(Context context, AttributeSet attrs) {
		super(context, attrs);
		initCameraView(context);
	}

	private void initCameraView(Context context) {
		this.mContext = context;
		setFocusable(true);
		mSurfaceHolder = getHolder();
		mSurfaceHolder.addCallback(this);
		mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {

		Log.d(TAG, "surfaceCreated");

		mSoundPool = new SoundPool(2, AudioManager.STREAM_SYSTEM, 0);
		mSoundMap.put(RAW_AUDIO_SHUTTER, mSoundPool.load(mContext, R.raw.camera_shutter, 1));
		mSoundMap.put(RAW_AUDIO_VIDEO_RECORD, mSoundPool.load(mContext, R.raw.video_record, 1));

		int ret = mUsbCameraNative.nativePrepareCamera(
				index,
				ImageProc.IMG_WIDTH,
				ImageProc.IMG_HEIGHT,
				ImageProc.CAMERA_PIX_FMT_MJPEG);

		if(ret != -1) {
			mCameraExists = true;
			new Thread(this).start();
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		Log.d(TAG, "surfaceChanged");
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {

		mShouldStop = true;
		Log.d(TAG, "surfaceDestroyed");
		stopVideoRecord(false);

		if(mCameraExists){
			while(mShouldStop){
				try{
					Thread.sleep(100);
				}catch(Exception e){}
			}
		}
		mUsbCameraNative.nativeStopCamera(index);
		mCameraExists = false;

		if (mSoundPool != null) {
			mSoundPool.release();
			mSoundPool = null;
		}
	}

	@Override
	public void onDataEncode(byte[] data) {
		if (null == data) {
			return;
		}

		if(mediaMuxerCore != null){
			mediaMuxerCore.addVideoFrameData(data);
		}
	}

	public boolean isCameraReady(){
		return mCameraExists;
	}

	public void startVideoRecord(){
		if(!mCameraExists ||isVideoRecording()){
			return;
		}
		playSound(RAW_AUDIO_VIDEO_RECORD);
		mIsVideoRecording = true;
		try {
			mediaMuxerCore = new MediaMuxerCore(
					ImageProc.IMG_WIDTH,
					ImageProc.IMG_HEIGHT,
					MediaMuxerCore.RecordType.RECORD_AUDIO_AND_VIDDEO);

			mediaMuxerCore.startMuxer();
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
		mUsbCameraNative.setRecordCallback(this);
		mUsbCameraNative.nativeStartRecord(index);
	}

	public void stopVideoRecord(boolean soundEffect){
		Log.d(TAG, "stopVideoRecord");
		if(soundEffect){
			playSound(RAW_AUDIO_VIDEO_RECORD);
		}
		mUsbCameraNative.nativeStopRecord(0);

		if(mediaMuxerCore != null){
			mediaMuxerCore.release();
			mediaMuxerCore = null;
		}

		mIsVideoRecording = false;
	}

	public boolean isVideoRecording(){
		return mIsVideoRecording;
	}

	public void capturePicture(){
		if(!mCameraExists || mCaptureFlag){
			return;
		}
		mCaptureFlag = true;
		playSound(RAW_AUDIO_SHUTTER);
	}

	private void playSound(int index){

		final int soundId = index;

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				mCurrStreamId = mSoundPool.play(mSoundMap.get(soundId), 1.0f, 1.0f, 0, 0, 1.0f);
			}
		}, 500);
	}

	private void savePictureFile(Bitmap bitmap, String fileName){

		String defaultFloder = FileUtil.getImageDir().getAbsolutePath();

		File f = new File(defaultFloder + fileName);

		FileOutputStream fOut = null;

		try {
			fOut = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);

		try {
			fOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    @Override
    public void run() {

		int dw, dh;
		Rect rect = null;
		int winWidth = 0;
		int winHeight = 0;

		Bitmap bmp = Bitmap.createBitmap(
				ImageProc.IMG_WIDTH,
				ImageProc.IMG_HEIGHT,
				Bitmap.Config.ARGB_8888);

        while (mCameraExists) {

			mUsbCameraNative.nativeProcessCamera(index);
			mUsbCameraNative.nativePixelToBmp(index, bmp);

			if(winWidth == 0) {
				winWidth = CameraView2.this.getWidth();
				winHeight = CameraView2.this.getHeight();

				if(winWidth * 3 / 4 <= winHeight){
					dw = 0;
					dh = (winHeight - winWidth * 3 / 4) / 2;
					rect = new Rect(dw, dh, dw + winWidth - 1, dh + winWidth * 3 / 4 - 1);
				}else{
					dw = (winWidth - winHeight * 4 / 3) / 2;
					dh = 0;
					rect = new Rect(dw, dh, dw + winHeight * 4 / 3 - 1, dh + winHeight - 1);
				}
			}
			Canvas canvas = getHolder().lockCanvas();
			if (canvas != null) {
				canvas.drawBitmap(bmp, null, rect, null);
				getHolder().unlockCanvasAndPost(canvas);
			}

			if(mCaptureFlag){
				savePictureFile(bmp, System.currentTimeMillis() + ".jpg");
				mCaptureFlag = false;
			}

            if(mShouldStop){
				mShouldStop = false;
            	break;
            }	        
        }
    }
}
