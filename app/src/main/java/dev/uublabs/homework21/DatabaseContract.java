package dev.uublabs.homework21;

import android.provider.BaseColumns;

/**
 * Created by Admin on 11/13/2017.
 */

public class DatabaseContract
{
    public static class Entry implements BaseColumns
    {
        public static final String TABLE_NAME = "Celebs";
        public static final String COLUMN_FIRST_NAME = "First";
        public static final String COLUMN_LAST_NAME = "Last";
        public static final String COLUMN_FAVORITE = "Favorite";
    }
}
