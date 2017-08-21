package pers.xjh.note.widget.dialog;

import android.content.Context;
import android.os.Bundle;

import pers.xjh.note.R;


/**
 * 进度条对话框
 * Created by xjh on 2016/12/28.
 */
public class ProcessDialog extends BaseDialog {

    public ProcessDialog(Context context) {
        this(context, R.style.base_dialog);
    }

    public ProcessDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_process);
    }
}
