package pers.xjh.note.utils;

import android.view.Gravity;
import android.widget.Toast;

import pers.xjh.note.runtime.AppRuntime;

/**
 * Toast工具类
 */
public class ToastUtil {
	
	private static Toast mToast;

	/**
	 * @param msg 内容
	 */
	public static void show(String msg) {
		show(msg, Toast.LENGTH_SHORT);
	}

	/**
	 * @param msg 内容
	 * @param duration Toast 显示时间长短类型  如：Toast.LENGTH_SHORT
	 */
	public static void show(String msg, int duration) {
        show(msg, duration, Gravity.BOTTOM);
	}

    /**
     *
     * @param msg 内容
     * @param duration 显示时间长短类型  如：Toast.LENGTH_SHORT
     * @param gravity 对齐方式
     */
    public static void show(String msg, int duration, int gravity) {
        if(Gravity.BOTTOM == gravity) {
            //底部对齐方式默认距离底部60dp
            show(msg, duration, gravity, 0, UiUtil.dp2px(AppRuntime.getApplication(), 60));
        } else {
            show(msg, duration, gravity, 0, 0);
        }
    }

    /**
     *
     * @param msg 内容
     * @param duration 显示时间长短类型  如：Toast.LENGTH_SHORT
     * @param gravity 对齐方式
     * @param xOffset x轴偏移量
     * @param yOffset y轴偏移量
     */
    public synchronized static void show(String msg, int duration, int gravity, int xOffset, int yOffset) {
        if(mToast == null){
            mToast = Toast.makeText(AppRuntime.getApplication(), msg, duration);
        } else {
            mToast.setText(msg);
            mToast.setDuration(duration);
        }
        mToast.setGravity(gravity, xOffset, yOffset);
        mToast.show();
    }

	/**
	 * 取消toast
	 * @param
	 */
	public static void cancel() {
		if(mToast != null){
			mToast.cancel();
		}
	}
}
