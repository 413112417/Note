package pers.xjh.note.opengl.render;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import pers.xjh.note.R;
import pers.xjh.note.opengl.ShaderHelper;
import pers.xjh.note.utils.FileUtil;

/**
 * 渲染器
 * Created by xjh on 17-11-13.
 */

public class AirHockeyRender implements GLSurfaceView.Renderer {

    private Context mContext;

    private static final int POSITION_COMPONENT_COUNT = 2;

    private float[] tableVerticesWithTriangles = {
            //Triangle1
            -0.5f, -0.5f,
            0.5f, 0.5f,
            -0.5f, 0.5f,

            //Triangle2
            -0.5f, -0.5f,
            0.5f, -0.5f,
            0.5f, 0.5f,

            //Line1
            -0.5f, 0f,
            0.5f, 0f,

            //Mallets
            0f, -0.25f,
            0f, 0.25f,
    };

    private static final int BYTES_PER_FLOAT = 4;

    private FloatBuffer vertexData;

    private static final String U_COLOR = "u_Color";

    private int uColorLocation;

    private static final String A_POSITION = "a_Position";

    private int aPositionLocation;

    public AirHockeyRender(Context context) {
        mContext = context;

        vertexData = ByteBuffer
                .allocateDirect(tableVerticesWithTriangles.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();

        vertexData.put(tableVerticesWithTriangles);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //定义清除颜色
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        String vertexShaderSource = FileUtil.readTextFileFromResourse(mContext, R.raw.simple_vertex_shader);
        String fragmentShaderSource = FileUtil.readTextFileFromResourse(mContext, R.raw.simple_fragment_shader);

        int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource);
        int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource);

        int program = ShaderHelper.linkProgram(vertexShader, fragmentShader);

        GLES20.glUseProgram(program);

        //从程序中获取uniform的位置
        uColorLocation = GLES20.glGetUniformLocation(program, U_COLOR);
        //从程序中获取属性的位置
        aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);

        //关联属性与顶点数据的数组
        vertexData.position(0);
        GLES20.glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GLES20.GL_FLOAT, false, 0, vertexData);

        GLES20.glEnableVertexAttribArray(aPositionLocation);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        //设置视口尺寸
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        //用之前定义的clearColor来清除屏幕
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        GLES20.glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);

        GLES20.glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_LINES, 6, 2);

        GLES20.glUniform4f(uColorLocation, 0f, 0f, 1.0f, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_POINTS, 8, 1);

        GLES20.glUniform4f(uColorLocation, 1.0f, 0f, 0f, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_POINTS, 9, 1);
    }
}
