package pers.xjh.note.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.support.v8.renderscript.Type;
import android.util.Log;

import pers.xjh.note.renderscript.ScriptC_magnifier;
import pers.xjh.note.renderscript.ScriptC_sketch;

/**
 * Created by xjh on 17-10-9.
 */

public class RenderScriptUtil {

    /**
     * 图片模糊
     * @param bitmap src
     * @param radius the radius of blur ,max is 25
     * @param context
     * @return a blur bitmap
     */
    public static Bitmap blurBitmap(Bitmap bitmap, float radius, Context context) {
        //Create renderscript
        RenderScript rs = RenderScript.create(context);
        //Create allocation from Bitmap
        Allocation allocation = Allocation.createFromBitmap(rs, bitmap);
        Type t = allocation.getType();
        //Create allocation with the same type
        Allocation blurredAllocation = Allocation.createTyped(rs, t);
        //Create blur script
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        //Set blur radius (maximum 25.0)
        blurScript.setRadius(radius);
        // Set input for script
        blurScript.setInput(allocation);
        // Call script for output allocation
        blurScript.forEach(blurredAllocation);
        // Copy script result into bitmap
        blurredAllocation.copyTo(bitmap);
        //Destroy everything to free memory allocation.destroy();
        blurredAllocation.destroy();
        blurScript.destroy();
        t.destroy();
        rs.destroy();
        return bitmap;
    }

    //将bitmap改为素描模式
    public static Bitmap sketchBitmap(Bitmap bitmap, Context context) {
        RenderScript renderScript = RenderScript.create(context);
        ScriptC_sketch sketchScript = new ScriptC_sketch(renderScript);
        Allocation in = Allocation.createFromBitmap(renderScript,bitmap);
        Allocation out = Allocation.createTyped(renderScript,in.getType());

        // call kernel
        sketchScript.forEach_invert(in,out);

        out.copyTo(bitmap);

        renderScript.destroy();
        sketchScript.destroy();
        in.destroy();
        out.destroy();
        return bitmap;
    }

    public static Bitmap magnifierBitmap(Bitmap bitmap, int x, int y, int radius, int scale, Context context) {

        Log.d("sc", x + ":" + y);

        RenderScript rs = RenderScript.create(context);
        Allocation in = Allocation.createFromBitmap(rs, bitmap);
        Allocation out = Allocation.createTyped(rs,in.getType());

        ScriptC_magnifier magnifier = new ScriptC_magnifier(rs);
        magnifier.set_inputAllocation(in);
        magnifier.set_atX(x);
        magnifier.set_atY(y);
        magnifier.set_radius(radius);
        magnifier.set_scale(scale);
        magnifier.forEach_magnify(in,out);

        out.copyTo(bitmap);
        rs.destroy();
        magnifier.destroy();
        in.destroy();
        out.destroy();
        return bitmap;
    }
}
