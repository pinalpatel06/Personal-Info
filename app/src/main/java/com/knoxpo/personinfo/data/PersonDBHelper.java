package com.knoxpo.personinfo.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Tejas Sherdiwala on 12/10/2016.
 * &copy; Knoxpo
 */

public class PersonDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME="person.db";


    public PersonDBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_PERSON_TABLE = "create table " + PersonContract.PersonEntry.TABLE_NAME +
                "(" + PersonContract.PersonEntry._ID + " integer primary key autoincrement," +
                PersonContract.PersonEntry.COLUMN_PERSON_FNAME + " varchar(100) not null," +
                PersonContract.PersonEntry.COLUMN_PERSON_LNAME + " varchar(100) not null," +
                PersonContract.PersonEntry.COLUMN_PERSON_DOB + " integer"+
                ");";

        sqLiteDatabase.execSQL(SQL_CREATE_PERSON_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
