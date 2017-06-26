package pers.xjh.note.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import pers.xjh.note.utils.Constant;

/**
 * Created by XJH on 2017/5/18.
 */

public class TestDatabaseHelper extends SQLiteOpenHelper {

    public TestDatabaseHelper(Context context) {
        super(context, Constant.DB_TEST, null, Constant.DB_TEST_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS PERSON (ID integer primary key autoincrement, name varchar(20), age INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
