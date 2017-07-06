package com.knoxpo.personinfo.misc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.knoxpo.personinfo.data.PersonContract;
import com.knoxpo.personinfo.model.Person;

import java.util.Date;

/**
 * Created by Tejas Sherdiwala on 12/13/2016.
 * &copy; Knoxpo
 */

public class Util {

    public static Uri savePerson(Context context, Person person){
        ContentValues cv = new ContentValues();
        cv.put(PersonContract.PersonEntry.COLUMN_PERSON_FNAME,person.getFname());
        cv.put(PersonContract.PersonEntry.COLUMN_PERSON_LNAME,person.getLname());
        cv.put(PersonContract.PersonEntry.COLUMN_PERSON_DOB,person.getDob().getTime());
        Uri uri = context.getContentResolver().insert(PersonContract.PersonEntry.CONTENT_URI,cv);
        return uri;
    }

    public static int updatePerson(Context context,Uri uri,Person person){
        ContentValues cv = new ContentValues();
        cv.put(PersonContract.PersonEntry.COLUMN_PERSON_FNAME,person.getFname());
        cv.put(PersonContract.PersonEntry.COLUMN_PERSON_LNAME,person.getLname());
        cv.put(PersonContract.PersonEntry.COLUMN_PERSON_DOB,person.getDob().getTime());
        int updatedRow = context.getContentResolver().update(uri,cv,null,null);
        return updatedRow;
    }
    public static Person getPerson(Cursor cursor){
        Person person = new Person();

        if(cursor != null) {
            person.setId(cursor.getLong(cursor.getColumnIndex(PersonContract.PersonEntry._ID)));
            person.setFname(cursor.getString(cursor.getColumnIndex(PersonContract.PersonEntry.COLUMN_PERSON_FNAME)));
            person.setLname(cursor.getString(cursor.getColumnIndex(PersonContract.PersonEntry.COLUMN_PERSON_LNAME)));
            long date = cursor.getLong(cursor.getColumnIndex(PersonContract.PersonEntry.COLUMN_PERSON_DOB));
            person.setDob(new Date(date));
        }
        return person;
    }
    public static int deletePerson(Context context,long id){
        Uri uri = PersonContract.PersonEntry.builtPersonUri(id);
        int rowDeleted = context.getContentResolver().delete(uri,null,null);
        return rowDeleted;
    }
}
