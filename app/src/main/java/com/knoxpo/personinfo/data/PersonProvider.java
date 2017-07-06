package com.knoxpo.personinfo.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Tejas Sherdiwala on 12/10/2016.
 * &copy; Knoxpo
 */

public class PersonProvider extends ContentProvider {
    private static final UriMatcher sUriMather = builtUriMatcher();
    private PersonDBHelper mPersonDBHelper;
    public static final int PERSON = 100;
    public static final int PERSON_WITH_ID = 101;

    @Override
    public boolean onCreate() {
        mPersonDBHelper = new PersonDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = sUriMather.match(uri);
        switch (match){
            case PERSON:
                return PersonContract.PersonEntry.CONTENT_TYPE;
            case PERSON_WITH_ID:
                return PersonContract.PersonEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown Uri :" + uri);
        }

    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch (sUriMather.match(uri)){
            case PERSON:
                retCursor = mPersonDBHelper.getReadableDatabase()
                        .query(PersonContract.PersonEntry.TABLE_NAME,
                                projection,
                                selection,
                                selectionArgs,
                                null,
                                null,
                                sortOrder);
                break;
            case PERSON_WITH_ID:
                retCursor = mPersonDBHelper.getReadableDatabase()
                        .query(PersonContract.PersonEntry.TABLE_NAME,
                                projection,
                                PersonContract.PersonEntry._ID+"=?",
                                new String[]{uri.getLastPathSegment()},
                                null,
                                null,
                                null);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri :" + uri);
        }
        if(getContext()!=null){
            retCursor.setNotificationUri(getContext().getContentResolver(),uri);
        }
        return retCursor;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final SQLiteDatabase db = mPersonDBHelper.getWritableDatabase();
        final int match = sUriMather.match(uri);
        Uri retUri;

        switch(match){
            case PERSON:
                long _id = db.insert(PersonContract.PersonEntry.TABLE_NAME,null,contentValues);
                if(_id > 0){
                    retUri = PersonContract.PersonEntry.builtPersonUri(_id);
                }else{
                    throw new android.database.SQLException("Fail to insert Row" + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if(getContext()!=null){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return retUri;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        final SQLiteDatabase db = mPersonDBHelper.getWritableDatabase();
        final int match = sUriMather.match(uri);
        int rowUpdate=0;
        switch (match){
            case PERSON:
                //noting to do
                break;
            case PERSON_WITH_ID:
                rowUpdate = db.update(
                        PersonContract.PersonEntry.TABLE_NAME,
                        contentValues,
                        PersonContract.PersonEntry._ID + "=?",
                        new String[] {String.valueOf(uri.getLastPathSegment())}
                );

        }
        if(getContext()!=null){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rowUpdate;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        final SQLiteDatabase db = mPersonDBHelper.getWritableDatabase();
        final int match = sUriMather.match(uri);
        int rowDeleted=0;
        switch (match){
            case PERSON:
                //
                break;
            case PERSON_WITH_ID:
                rowDeleted = db.delete(PersonContract.PersonEntry.TABLE_NAME,
                        PersonContract.PersonEntry._ID + "=?",
                        new String[] {String.valueOf(uri.getLastPathSegment())}
                );
                break;
        }
        if(getContext()!=null){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rowDeleted;
    }

    public static UriMatcher builtUriMatcher(){
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = PersonContract.CONTENT_AUTHORITY;

        matcher.addURI(authority,PersonContract.PATH_PERSON,PERSON);
        matcher.addURI(authority,PersonContract.PATH_PERSON + "/#", PERSON_WITH_ID);

        return matcher;
    }
}
