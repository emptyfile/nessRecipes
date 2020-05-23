package com.example.nessrecipes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "recipe.db";
    private static final String TABLE_NAME = "recipe_table";
    private static final String COL_ID = "ID";
    private static final String COL_NAME = "NAME";
    private static final String COL_INGREDIENTS = "INGREDIENTS_LIST";
    private static final String COL_ESTIMATED_TIME = "ESTIMATED_TIME";
    private static final String COL_CATEGORY = "CATEGORY";
    private static final String COL_TEXT = "TEXT";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_NAME + " TEXT, " + COL_INGREDIENTS + " TEXT, "
                + COL_ESTIMATED_TIME + " INTEGER, " + COL_CATEGORY + " INTEGER, "
                + COL_TEXT + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name, String ingredients, int estimatedTime, String category, String text) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, name);
        contentValues.put(COL_INGREDIENTS, ingredients);
        contentValues.put(COL_ESTIMATED_TIME, estimatedTime);
        contentValues.put(COL_CATEGORY, category.length()); //fix
        contentValues.put(COL_TEXT, text);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(String.format("SELECT * FROM %s", TABLE_NAME), null);
    }

    public Cursor getById(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(String.format("SELECT * FROM %s WHERE %s=%d", TABLE_NAME, COL_ID, id), null);
    }

    public boolean updateData(long recipeId, String name, String ingredients, int estimatedTime, String category, String text) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ID, recipeId);
        contentValues.put(COL_NAME, name);
        contentValues.put(COL_INGREDIENTS, ingredients);
        contentValues.put(COL_ESTIMATED_TIME, estimatedTime);
        contentValues.put(COL_CATEGORY, category.length()); //fix
        contentValues.put(COL_TEXT, text);
        long result = db.update(TABLE_NAME, contentValues, "ID = ?", new String[] {Long.toString(recipeId)});
        return result != -1;
    }
}
