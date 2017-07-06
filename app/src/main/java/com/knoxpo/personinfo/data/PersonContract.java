package com.knoxpo.personinfo.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Tejas Sherdiwala on 12/10/2016.
 * &copy; Knoxpo
 */

public class PersonContract {

    public static final String CONTENT_AUTHORITY = "com.knoxpo.personinfo";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);

    public static final String PATH_PERSON = "person";

    public static final class PersonEntry implements BaseColumns{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_PERSON).build();
        public static final String
                CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PERSON,
                CONTENT_ITEM_TYPE =
                        ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PERSON;

        public static final String TABLE_NAME = "person";
        public static final String
                COLUMN_PERSON_FNAME = "fname",
                COLUMN_PERSON_LNAME = "lname",
                COLUMN_PERSON_DOB = "dob";

        public static Uri builtPersonUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI,id);
        }

        public static long getIdFromUri(Uri uri){
            return Long.parseLong(uri.getLastPathSegment());
        }



    }


}
