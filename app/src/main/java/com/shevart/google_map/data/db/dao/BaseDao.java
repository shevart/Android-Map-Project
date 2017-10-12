package com.shevart.google_map.data.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Date;

/***
 *  <h3>Developer description</h3>
 *  <br/><br/>
 *  Class which define base operations for SQLite
 *  <br/><br/>
 */
@SuppressWarnings("unused")
public abstract class BaseDao {
    protected static boolean parseBoolean(@NonNull Cursor cursor, @NonNull final String KEY) { // 0 (false) and 1 (true)
        return cursor.getInt(cursor.getColumnIndex(KEY)) != 0;
    }

    protected static int parseInt(@NonNull Cursor cursor, @NonNull final String KEY) {
        return cursor.getInt(cursor.getColumnIndex(KEY));
    }

    protected static double parseDouble(@NonNull Cursor cursor, @NonNull final String KEY) {
        return cursor.getDouble(cursor.getColumnIndex(KEY));
    }

    protected static long parseLong(@NonNull Cursor cursor, @NonNull final String KEY) {
        return cursor.getLong(cursor.getColumnIndex(KEY));
    }

    protected static String parseString(@NonNull Cursor cursor, @NonNull final String KEY) {
        return cursor.getString(cursor.getColumnIndex(KEY));
    }

    @Nullable
    protected static Date parseDate(@NonNull Cursor cursor, @NonNull final String KEY) {
        final long time = cursor.getLong(cursor.getColumnIndex(KEY));
        return time > 0 ? new Date(time) : null;
    }

    protected static void bindString(@NonNull SQLiteStatement sqLiteStatement, int index, @Nullable String value) {
        if (value != null) sqLiteStatement.bindString(index, value);
    }

    protected static void bindDate(@NonNull SQLiteStatement statement, int index, @Nullable Date date) {
        statement.bindLong(index, date != null ? date.getTime() : 0);
    }
}