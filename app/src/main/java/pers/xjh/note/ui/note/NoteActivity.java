package pers.xjh.note.ui.note;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pers.xjh.note.R;
import pers.xjh.note.model.bean.Note;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.utils.Constant;
import pers.xjh.note.utils.PermissionUtil;
import pers.xjh.note.utils.ToastUtil;
import pers.xjh.note.widget.RecyclerViewDivider;
import pers.xjh.note.widget.TitleBar;

/**
 * 笔记列表页
 */
public class NoteActivity extends BaseActivity implements NoteContract.NoteView {

    private RecyclerView mRecyclerView;
    //适配器
    private NoteAdapter mNoteAdapter;
    //笔记列表
    private List<Note> mNoteList = new ArrayList<>();
    //笔记事物处理类
    private NotePresenter mNotePresenter;
    //笔记类型（默认所有笔记）
    private int noteType = Constant.NOTE_ALL;
    //被权限问题阻挡的意图
    private Intent mBlockByPermissionIntent;
    //记录按下返回键的时间
    private long mPressBackBottomTime;

    @Override
    protected int initContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void getIntentData(Intent intent) {
        if (intent != null) {
            noteType = intent.getIntExtra(Constant.KEY_NOTE_TYPE, Constant.NOTE_ALL);
        }
    }

    /**
     * 初始化标题
     */
    @Override
    protected void initTitle(TitleBar titleBar) {
        if(Constant.NOTE_ALL == noteType) {
            titleBar.setTitle("学习笔记");
            titleBar.setLeftTitleVisibility(false);
        }
    }

    @Override
    protected void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new RecyclerViewDivider(0, 0, 0, 10));

        mNoteAdapter = new NoteAdapter();
        mRecyclerView.setAdapter(mNoteAdapter);

        mNotePresenter = new NotePresenter(this);
    }

    @Override
    protected void start() {
        mNotePresenter.loadNote(noteType);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mNotePresenter != null) {
            mNotePresenter.detachView();
        }
    }

    @Override
    public void onBackPressed() {
        if(Constant.NOTE_ALL == noteType) {
            if (System.currentTimeMillis() - mPressBackBottomTime < 2000) {
                super.onBackPressed();
            } else {
                mPressBackBottomTime = System.currentTimeMillis();
                ToastUtil.show("再按一次退出", Toast.LENGTH_SHORT, Gravity.CENTER);
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //检查权限是否授予，只要有一项权限没有授予，就不进行跳转
        for(int result : grantResults) {
            if(result != PackageManager.PERMISSION_GRANTED) {
                mBlockByPermissionIntent = null;
                return;
            }
        }

        if(requestCode == PermissionUtil.getRequestCode()) {
            //权限全部授予后，执行被阻挡的跳转意图
            String permission = permissions[0];
            if(permission != null) {
                if(mBlockByPermissionIntent != null) {
                    startActivity(mBlockByPermissionIntent);
                    mBlockByPermissionIntent = null;
                }
            }
        }
    }

    @Override
    public void onNoteLoadComplete(List<Note> noteList) {
        mNoteList = noteList;
        mNoteAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void hideLoadingView() {

    }

    @Override
    public void showToast(String text) {

    }

    private class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> {

        @Override
        public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new NoteViewHolder(LayoutInflater.from(NoteActivity.this).inflate(R.layout.item_note, parent, false));
        }

        @Override
        public void onBindViewHolder(NoteViewHolder holder, int position) {
            holder.updateUi(mNoteList.get(position));
        }

        @Override
        public int getItemCount() {
            return mNoteList == null ? 0 : mNoteList.size();
        }
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {

        private TextView tvNoteName;

        public NoteViewHolder(View itemView) {
            super(itemView);
            tvNoteName = (TextView) itemView.findViewById(R.id.tv_note_name);
        }

        public void updateUi(final Note note) {
            tvNoteName.setText(note.getNoteName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (note != null && note.getAction() != null) {
                        if(note.getPermission() != null) {
                            if(PermissionUtil.checkPermission(note.getPermission())) {
                                Intent intent = new Intent(NoteActivity.this, note.getAction());
                                intent.putExtra(Constant.KEY_TITLE, note.getNoteName());
                                intent.putExtra(Constant.KEY_NOTE_TYPE, note.getNoteType());
                                startActivity(intent);
                            } else {
                                //将被权限问题阻挡的Intent记录下来，授予权限后重新跳转
                                mBlockByPermissionIntent = new Intent(NoteActivity.this, note.getAction());
                                mBlockByPermissionIntent.putExtra(Constant.KEY_TITLE, note.getNoteName());
                                mBlockByPermissionIntent.putExtra(Constant.KEY_NOTE_TYPE, note.getNoteType());
                                PermissionUtil.requestPermission(NoteActivity.this, note.getPermission());
                            }
                        } else {
                            Intent intent = new Intent(NoteActivity.this, note.getAction());
                            intent.putExtra(Constant.KEY_TITLE, note.getNoteName());
                            intent.putExtra(Constant.KEY_NOTE_TYPE, note.getNoteType());
                            startActivity(intent);
                        }
                    } else if(note != null) {
                        Intent intent = new Intent(NoteActivity.this, NoteTextActivity.class);
                        intent.putExtra(Constant.KEY_TITLE, note.getNoteName());
                        intent.putExtra(Constant.KEY_STRING, note.getStringId());
                        intent.putExtra(Constant.KEY_IMAGE_URL, note.getImageResourceId());
                        startActivity(intent);
                    }
                }
            });
        }
    }
}
