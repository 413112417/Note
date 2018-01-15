package pers.xjh.note.ui.detail.android;

import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;

/**
 * 执行shell命令
 * Created by xjh on 18-1-15.
 */
public class ShellCommendActivity extends BaseActivity {

    @Override
    protected int initContentView() {
        return R.layout.activity_shell_commend;
    }

    @Override
    protected void initView() {
//        try {
//            Process exeEcho = Runtime.getRuntime().exec("su");
//            exeEcho.getOutputStream().write("ip address add 192.168.0.55/24 dev eth0\n".getBytes());
//            exeEcho.getOutputStream().flush();
//        } catch (IOException e) {
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
//        }

        String apkRoot="chmod 777 " + getPackageCodePath();
        RootCommand(apkRoot);
    }


    /**
     * 应用程序运行命令获取 Root权限，设备必须已破解(获得ROOT权限)
     * @param command 命令：String apkRoot="chmod 777 "+getPackageCodePath(); RootCommand(apkRoot);
     * @return 应用程序是/否获取Root权限
     */
    public static boolean RootCommand(String command) {
        Process process = null;
        DataOutputStream os = null;
        try {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(command + "\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                process.destroy();
            } catch (Exception e) {

            }
        }
        return true;
    }
}
