package pers.xjh.note.ui.detail.opengl.render;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import java.nio.ByteBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import pers.xjh.note.R;
import pers.xjh.note.ui.detail.opengl.ShaderHelper;
import pers.xjh.note.utils.FileUtil;

/**
 * 渲染器
 * Created by xjh on 17-11-13.
 */

public class AirHockeyRender implements GLSurfaceView.Renderer {

    private Context mContext;

    public AirHockeyRender(Context context) {
        mContext = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f, 1.0f, 0.0f, 0.0f);

        String vertexShaderSource = FileUtil.readTextFileFromResourse(mContext, R.raw.simple_vertex_shader);
        String fragmentShaderSource = FileUtil.readTextFileFromResourse(mContext, R.raw.simple_fragment_shader);

        int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource);
        int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource);

        int program = ShaderHelper.linkProgram(vertexShader, fragmentShader);

        GLES20.glUseProgram(program);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

    }
}
