package com.mirea.kt.ribo;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBHelper  extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "login.db";
    private static final int DATABASE_VERSION = 5;
    public static final String TABLE_NAME = "logins";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_WEBSITE = "website";
    public static final String COLUMN_CATEGORY = "category";



    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String TABLE_CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_USERNAME + " TEXT, " +
                        COLUMN_PASSWORD + " TEXT, " +
                        COLUMN_WEBSITE + " TEXT, " +
                        COLUMN_CATEGORY + " TEXT" + ");";
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public void addPassword(String username, String password, String website, String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_WEBSITE, website);
        values.put(COLUMN_CATEGORY, category);


        long resualt = db.insert(TABLE_NAME, null, values);
        Log.d("DBHelper","Работает!:" + resualt);
        db.close();

    }


    public List<Password> getPasswordsByCategory(String category) {
        List<Password> passwordList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_CATEGORY + "=?", new String[]{category}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Password password = new Password(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WEBSITE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY))
                );
                passwordList.add(password);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return passwordList;
    }

    public boolean deletePassword(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rowsDeleted > 0;
    }
}
