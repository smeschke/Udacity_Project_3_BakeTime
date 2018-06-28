package com.example.stephen.projectfour.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    // Some of the code (like the onUpgrade method) is adapted from lesson T07.06 (guest list)
    private static final String DATABASE_NAME = "recipes.db";
    private static final int DATABASE_VERSION = 4;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String create_table =
                "CREATE TABLE " + Contract.listEntry.TABLE_NAME + " (" +
                        Contract.listEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        Contract.listEntry.COLUMN_IS_STEP + " TEXT NOT NULL, " +
                        Contract.listEntry.COLUMN_STEP_ID + " TEXT NOT NULL, " +
                        Contract.listEntry.COLUMN_STEP_VIDEO_URL + " TEXT NOT NULL, " +
                        Contract.listEntry.COLUMN_STEP_VERBOSE_DESCRIPTION + " TEXT NOT NULL, " +
                        Contract.listEntry.COLUMN_STEP_THUMBNAIL_URL + " TEXT NOT NULL, " +
                        Contract.listEntry.COLUMN_STEP_SHORT_DESCRIPTION + " TEXT NOT NULL, " +
                        Contract.listEntry.COLUMN_IS_INGREDIENT + " TEXT NOT NULL, " +
                        Contract.listEntry.COLUMN_RECIPE_IMAGE_URL + " TEXT NOT NULL, " +
                        Contract.listEntry.COLUMN_RECIPE_ID + " TEXT NOT NULL, " +
                        Contract.listEntry.COLUMN_INGREDIENT_QUANTITY + " TEXT NOT NULL, " +
                        Contract.listEntry.COLUMN_INGREDIENT_NAME + " TEXT NOT NULL, " +
                        Contract.listEntry.COLUMN_RECIPE_NAME + " TEXT NOT NULL, " +
                        Contract.listEntry.COLUMN_INGREDIENT_MEASURE + " TEXT NOT NULL, " +
                        Contract.listEntry.COLUMN_RECIPE_SERVINGS + " TEXT NOT NULL, " +
                        Contract.listEntry.COLUMN_INGREDIENT_ID + " TEXT NOT NULL, " +
                        Contract.listEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                        "); ";

        db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Contract.listEntry.TABLE_NAME);
        onCreate(db);
    }
}
