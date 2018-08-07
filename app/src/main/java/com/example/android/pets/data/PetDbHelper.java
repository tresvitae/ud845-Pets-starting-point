package com.example.android.pets.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.pets.data.PetContract.PetEntry;

public class PetDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = PetDbHelper.class.getSimpleName();

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "shelter.db";

    public PetDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + PetContract.PetEntry.TABLE_NAME + " (" +
                        PetEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        PetEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                        PetEntry.COLUMN_BREED + " TEXT, " +
                        PetEntry.COLUMN_GENDER + " INTEGER NOT NULL, " +
                        PetEntry.COLUMN_WEIGHT + " INTEGER NOT NULL DEFAULT 0)";

        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
