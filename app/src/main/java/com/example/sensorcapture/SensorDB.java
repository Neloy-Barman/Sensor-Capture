package com.example.sensorcapture;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SensorDB extends SQLiteOpenHelper {
    public SensorDB(Context context) {
        super(context, "SensorDatabase", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "create table mytable(x_values REAL, y_values REAL);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertData(float x_value, float y_value) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("x_values", x_value);
        contentValues.put("y_values", y_value);
        sqLiteDatabase.insert("mytable", null, contentValues);
        return true;
    }
}
