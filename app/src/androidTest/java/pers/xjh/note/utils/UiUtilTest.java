package pers.xjh.note.utils;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by xjh on 17-12-21.
 */
public class UiUtilTest {
    @Test
    public void dp2px() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        int result = UiUtil.dp2px(appContext, 10);

        Log.d("result", result + "");
        assertEquals(26, result);
    }

    @Test
    public void px2dp() throws Exception {
    }

}