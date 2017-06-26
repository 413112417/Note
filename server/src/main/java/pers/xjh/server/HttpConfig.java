package pers.xjh.server;

/**
 * http配置类
 * Created by XJH on 2017/5/25.
 */

public class HttpConfig {
    private int port;//端口号
    private int maxParallels;//最大连接数

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getMaxParallels() {
        return maxParallels;
    }

    public void setMaxParallels(int maxParallels) {
        this.maxParallels = maxParallels;
    }
}
