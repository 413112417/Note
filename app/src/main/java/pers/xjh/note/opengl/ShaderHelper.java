package pers.xjh.note.opengl;

import android.opengl.GLES20;

/**
 * Created by xjh on 17-11-14.
 */

public class ShaderHelper {

    public static int compileVertexShader(String shaderCode) {
        return compileShader(GLES20.GL_VERTEX_SHADER, shaderCode);
    }

    public static int compileFragmentShader(String shaderCode) {
        return compileShader(GLES20.GL_FRAGMENT_SHADER, shaderCode);
    }

    /**
     * 编译着色器
     * @param type
     * @param shaderCode
     * @return
     */
    private static int compileShader(int type, String shaderCode) {
        //创建一个新的着色器对象
        final int shaderObjectId = GLES20.glCreateShader(type);
        //把着色器代码上传到着色器对象里
        GLES20.glShaderSource(shaderObjectId, shaderCode);
        //编译之前上传的代码
        GLES20.glCompileShader(shaderObjectId);

        return shaderObjectId;
    }

    public static int linkProgram(int vertexShaderId, int fragmentShaderId) {
        //新建程序对象
        final int programObjectId = GLES20.glCreateProgram();
        //把顶点着色器对象和片段着色器对象附加到程序对象上
        GLES20.glAttachShader(programObjectId, vertexShaderId);
        GLES20.glAttachShader(programObjectId, fragmentShaderId);
        //连接着色器
        GLES20.glLinkProgram(programObjectId);

        return programObjectId;
    }
}
