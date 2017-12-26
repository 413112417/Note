package pers.xjh.note.ui.detail.android;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;

import java.io.FileOutputStream;
import java.io.IOException;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.utils.Constant;
import pers.xjh.note.utils.FileUtil;
import pers.xjh.note.widget.ClipImageView;
import pers.xjh.note.widget.TitleBar;

/**
 * Created by xjh on 17-7-26.
 */

public class ImageClipActivity extends BaseActivity {

    private ClipImageView mClipImageView;

    @Override
    protected int initContentView() {
        return R.layout.activity_image_clip;
    }

    @Override
    protected void initTitle(TitleBar titleBar) {
        titleBar.setTitleRight("确定");
        titleBar.setRightTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //保存图片到本地
                    FileOutputStream fos = new FileOutputStream(FileUtil.getImageFile("clipImage"));
                    Bitmap bitmap = mClipImageView.clip();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
                    fos.flush();
                    fos.close();

                    //跳转到图片详情
                    Intent intent = new Intent(ImageClipActivity.this, ImageDetailActivity.class);
                    intent.putExtra(Constant.KEY_IMAGE_URL, new String[] {FileUtil.getImageFile("clipImage").getAbsolutePath()});
                    intent.putExtra(Constant.KEY_SKIP_CACHE, true);
                    startActivity(intent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void initView() {
        mClipImageView = (ClipImageView) findViewById(R.id.clip_image_view);
    }
}
