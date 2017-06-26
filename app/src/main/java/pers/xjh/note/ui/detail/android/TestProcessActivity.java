package pers.xjh.note.ui.detail.android;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import pers.xjh.note.R;
import pers.xjh.note.database.TestDatabaseHelper;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.utils.Constant;
import pers.xjh.note.utils.SpUtil;

/**
 * Created by XJH on 2017/5/18.
 */

public class TestProcessActivity extends BaseActivity implements View.OnClickListener {

    private Button mBtnLockScreen;

    private TextView mTvDB;

    private boolean mIsRunning;

    private TestDatabaseHelper mTestDatabaseHelper;

    private SQLiteDatabase mDatabase;

    @Override
    protected int initContentView() {
        return R.layout.activity_process;
    }

    @Override
    protected void initView() {
        mBtnLockScreen = (Button) findViewById(R.id.btn_lock_screen);
        mBtnLockScreen.setOnClickListener(this);

        findViewById(R.id.btn_insert_db).setOnClickListener(this);
        findViewById(R.id.btn_delete_db).setOnClickListener(this);

        mTvDB = (TextView) findViewById(R.id.tv_db);

        mIsRunning = SpUtil.getBoolean(Constant.SP_SETTING, Constant.SP_LOCK_SCREEN_STATE);

        mTestDatabaseHelper = new TestDatabaseHelper(this);

        if(mIsRunning) {
            mBtnLockScreen.setText("关闭锁屏服务");
        } else {
            mBtnLockScreen.setText("开启锁屏服务");
        }
    }

    @Override
    protected void start() {
        readDB();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_lock_screen:
                if(mIsRunning) {
                    mIsRunning = false;
                    mBtnLockScreen.setText("开启锁屏服务");
                    SpUtil.putBoolean(Constant.SP_SETTING, Constant.SP_LOCK_SCREEN_STATE, false);
                } else {
                    mIsRunning = true;
                    mBtnLockScreen.setText("关闭锁屏服务");
                    SpUtil.putBoolean(Constant.SP_SETTING, Constant.SP_LOCK_SCREEN_STATE, true);
                }
                break;
            case R.id.btn_insert_db:
                insertDB();
                readDB();
                break;
            case R.id.btn_delete_db:
                deleteDB();
                readDB();
                break;
        }
    }

    /**
     * 向数据库插入一条记录
     */
    private void readDB() {
        mDatabase = mTestDatabaseHelper.getReadableDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM PERSON", null);
        StringBuilder dbContent = new StringBuilder();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int age = cursor.getInt(2);
            dbContent.append(id + ":" + name + ":" + age + "\n");
        }
        mTvDB.setText(dbContent.toString());
        cursor.close();
        mDatabase.close();
    }

    /**
     * 向数据库插入一条记录
     */
    private void insertDB() {
        mDatabase = mTestDatabaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", "张三");
        values.put("age", 25);
        long rowid = mDatabase.insert("PERSON", null, values);//返回新添记录的行号，与主键id无关
        mDatabase.close();
    }

    /**
     * 删除数据库所有记录
     */
    private void deleteDB() {
        mDatabase = mTestDatabaseHelper.getWritableDatabase();
        mDatabase.execSQL("DELETE FROM PERSON");
        mDatabase.close();
    }
}
