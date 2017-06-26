package pers.xjh.note.widget;

/**
 * RecylerView分割线
 * Created by XJH on 2017/3/28.
 */

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class RecyclerViewDivider extends RecyclerView.ItemDecoration {

    int left, top, right, bottom;

    public RecyclerViewDivider(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(left, top, right, bottom);
    }
}
