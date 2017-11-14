package pers.xjh.note.ui.detail.opengl;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;

import pers.xjh.note.ui.detail.opengl.render.AirHockeyRender;

/**
 * Created by xjh on 17-11-13.
 */

public class AirHockeyActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GLSurfaceView glSurfaceView = new GLSurfaceView(this);
        glSurfaceView.setRenderer(new AirHockeyRender(this));
        setContentView(glSurfaceView);
    }
}
