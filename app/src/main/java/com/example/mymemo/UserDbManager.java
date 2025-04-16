package com.example.mymemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class UserDbManager {

    private SQLiteDatabase database;
    private UserDbHelper dbHelper;

    public UserDbManager(Context context) {
        dbHelper = new UserDbHelper(context);
    }

    // Membuka database untuk operasi
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    // Menutup database
    public void close() {
        dbHelper.close();
    }

    // Menambahkan pengguna baru ke database
    public long insertUser(User user) {
        ContentValues values = new ContentValues();
        values.put(UserDbHelper.COLUMN_EMAIL, user.getEmail());
        values.put(UserDbHelper.COLUMN_PASSWORD, user.getPassword());

        return database.insert(UserDbHelper.TABLE_USER, null, values);
    }

    // Mengecek apakah pengguna sudah terdaftar dengan email dan password
    public boolean checkLogin(String email, String password) {
        String[] columns = {UserDbHelper.COLUMN_ID};
        String selection = UserDbHelper.COLUMN_EMAIL + "=? AND " + UserDbHelper.COLUMN_PASSWORD + "=?";
        String[] selectionArgs = {email, password};

        Cursor cursor = database.query(UserDbHelper.TABLE_USER, columns, selection, selectionArgs, null, null, null);

        int cursorCount = cursor.getCount();
        cursor.close();

        return cursorCount > 0;
    }
}
