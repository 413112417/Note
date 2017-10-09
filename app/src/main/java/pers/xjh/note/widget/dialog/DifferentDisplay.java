package pers.xjh.note.widget.dialog;

import android.app.Presentation;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;

import pers.xjh.note.R;

/**
 * Created by xjh on 17-9-28.
 */

public class DifferentDisplay extends Presentation {

    public DifferentDisplay(Context outerContext, Display display) {
        super(outerContext, display);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.another_display);
    }
}
