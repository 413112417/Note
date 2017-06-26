package pers.xjh.test.listener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XJH on 2017/5/4.
 */

public class MemoryListener {

    public static List<OnMemoeySizeChangedListener> listeners;

    public static void addMemoeySizeChangedListener(OnMemoeySizeChangedListener listener) {
        if(listeners == null) {
            listeners = new ArrayList<>();
        }
        listeners.add(listener);
    }

    interface OnMemoeySizeChangedListener {
        void onMemoeySizeChanged(int memorySize);
    }
}
