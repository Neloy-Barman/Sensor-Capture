package com.example.sensorcapture;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;

public class SensorDB extends SQLiteOpenHelper {
    public SensorDB(@Nullable Context context) {
        super(context, "SensorDatabase", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Queries.create_light);
        db.execSQL(Queries.create_proximity);

        db.execSQL(Queries.create_acc_x);
        db.execSQL(Queries.create_acc_y);
        db.execSQL(Queries.create_acc_z);

        db.execSQL(Queries.create_gyro_x);
        db.execSQL(Queries.create_gyro_y);
        db.execSQL(Queries.create_gyro_z);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Queries.drop_light);
        db.execSQL(Queries.drop_proximity);

        db.execSQL(Queries.drop_acc_x);
        db.execSQL(Queries.drop_acc_y);
        db.execSQL(Queries.drop_acc_z);

        db.execSQL(Queries.drop_gyro_x);
        db.execSQL(Queries.drop_gyro_y);
        db.execSQL(Queries.drop_gyro_z);
    }

    public ArrayList<Entry> retrieveTable( String table_name)
    {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<Entry> dataVals = new ArrayList<>();
        String[] columns = {Queries.x_value, Queries.y_value};
        Cursor cursor = sqLiteDatabase.query(table_name, columns, null, null, null, null, null);
        for(int i=0;i<cursor.getCount();i++)
        {
            cursor.moveToNext();
            dataVals.add(new Entry(cursor.getFloat(0), cursor.getFloat(1)));
        }
        return dataVals;
    }

//    Dynamicically insertion into table.
    public boolean insertData(String table_name, float x, float y) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Queries.x_value, x);
        contentValues.put(Queries.y_value, y);
        sqLiteDatabase.insert(table_name, null, contentValues);
        return true;
    }

//    Contant insertion into one table.
//    public boolean insertData(float x_value, float y_value) {
//        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("x_values", x_value);
//        contentValues.put("y_values", y_value);
//        sqLiteDatabase.insert("mytable", null, contentValues);
//        return true;
//    }

}
