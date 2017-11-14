package dev.uublabs.homework21;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 11/13/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper
{
    public static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context)
    {
        super(context, DatabaseContract.Entry.TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_TABLE = "CREATE TABLE " + DatabaseContract.Entry.TABLE_NAME + "(" + DatabaseContract.Entry.COLUMN_FIRST_NAME
                + " TEXT PRIMARY KEY,"+
        DatabaseContract.Entry.COLUMN_LAST_NAME + " TEXT, " + DatabaseContract.Entry.COLUMN_FAVORITE + " TEXT "+
                ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.Entry.TABLE_NAME);
        onCreate(db);
    }

    public long savePerson(Celebrity celebrity)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.Entry.COLUMN_FIRST_NAME, celebrity.getFirstName());
        contentValues.put(DatabaseContract.Entry.COLUMN_LAST_NAME, celebrity.getLastName());
        contentValues.put(DatabaseContract.Entry.COLUMN_FAVORITE, celebrity.isFavorite()+"");
        long row = db.insert(DatabaseContract.Entry.TABLE_NAME, null,contentValues);
        return row;
    }
    public List<Celebrity> getCelebs()
    {
        List<Celebrity> celebList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String QUERY = "SELECT * FROM " + DatabaseContract.Entry.TABLE_NAME;
        Cursor cursor = db.rawQuery(QUERY, null);

        if(cursor.moveToFirst())
        {
            do
            {
                Celebrity celebrity = new Celebrity(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2));
                celebList.add(celebrity);
            }
            while (cursor.moveToNext());
        }
        return celebList;
    }
    public List<Celebrity> getFaveCelebs()
    {
        List<Celebrity> celebList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String QUERY = "SELECT * FROM " + DatabaseContract.Entry.TABLE_NAME + " WHERE Favorite='true'";
        Cursor cursor = db.rawQuery(QUERY, null);

        if(cursor.moveToFirst())
        {
            do
            {
                Celebrity celebrity = new Celebrity(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2));
                Log.d("FAVES", "getFaveCelebs: "+celebrity.toString());
                celebList.add(celebrity);
            }
            while (cursor.moveToNext());
        }
        return celebList;
    }

    public Celebrity getCeleb(String n)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Celebrity celebrity = new Celebrity("","");

        String QUERY = "SELECT * FROM " + DatabaseContract.Entry.TABLE_NAME + " WHERE First='"+n+"'";
        Cursor cursor = db.rawQuery(QUERY, null);

        if (cursor.moveToFirst())
        {
            do {
                        celebrity = new Celebrity(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2)
                );
            }while (cursor.moveToNext());
        }
        return celebrity;
    }

    public long upDateFavorites(Celebrity celebrity)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.Entry.COLUMN_FAVORITE, celebrity.isFavorite()+"");
        long row =  db.update(DatabaseContract.Entry.TABLE_NAME, contentValues, " First='"+celebrity.getFirstName()+"'", null);
        Log.d("CLICK", "upDateFavorites: "+celebrity.isFavorite());
        return row;
    }
    public long updateCeleb(Celebrity celebrity, String firstName)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.Entry.COLUMN_FIRST_NAME, celebrity.getFirstName());
        contentValues.put(DatabaseContract.Entry.COLUMN_LAST_NAME, celebrity.getLastName());
        long row =  db.update(DatabaseContract.Entry.TABLE_NAME, contentValues, " First='"+firstName+"'", null);
        return row;
    }

    public long deleteCeleb(Celebrity celebrity)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        long row = db.delete(DatabaseContract.Entry.TABLE_NAME, " First='"+celebrity.getFirstName()+"'", null);
        return row;
    }
}
