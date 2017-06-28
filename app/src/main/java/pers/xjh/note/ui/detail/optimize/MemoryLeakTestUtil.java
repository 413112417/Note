package pers.xjh.note.ui.detail.optimize;

import android.content.Context;

/**
 * Created by xjh on 17-6-28.
 */
public class MemoryLeakTestUtil {

    private static MemoryLeakTestUtil mInstance;

    private Context mContext;

    private MemoryLeakTestUtil(Context context){
        this.mContext = context;
    }

    public static MemoryLeakTestUtil getInstance(Context context) {
        if(mInstance == null) {
            mInstance = new MemoryLeakTestUtil(context);
        }
        return mInstance;
    }
}
