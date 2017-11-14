//片段着色器
precision mediump float;

uniform vec4 u_Color;

void main() {
    gl_FragColor = u_Color;
}