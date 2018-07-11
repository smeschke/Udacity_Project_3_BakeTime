package com.example.stephen.projectfour.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

import static com.example.stephen.projectfour.data.Contract.listEntry.TABLE_NAME;


/*
This is based on S09.05 - The Sunshine database lesson.
This exactly the same as the Provider I used in the last project
*/

// Extend ContentProvider
public class Provider extends ContentProvider {

    // Initialize mTaskDbHelper and context in onCreate
    private DbHelper mTaskDbHelper;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mTaskDbHelper = new DbHelper(context);
        return true;
    }

    // Insert a single new row of data
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        // Access database
        final SQLiteDatabase db = mTaskDbHelper.getWritableDatabase();
        // Uri to be returned
        Uri returnUri;
        // Insert the row
        long id = db.insert(TABLE_NAME, null, values);
        // Update Uri's
        if (id > 0) {
            returnUri = ContentUris.withAppendedId(
                    Contract.listEntry.CONTENT_URI, id);
        } else {
            throw new android.database.SQLException("Failed to insert row into " + uri);
        }
        // Notify
        getContext().getContentResolver().notifyChange(uri, null);
        // Return uri that points to newly inserted row
        return returnUri;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // Access db
        final SQLiteDatabase db = mTaskDbHelper.getReadableDatabase();
        // Query database
        Cursor retCursor = db.query(TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);
        // Notify - not sure what/who/why I am notifying here.
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        // Return cursor
        return retCursor;
    }

    // Not used
    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        // # of tasks deleted
        int moviesDeleted = 0;
        // Access db
        final SQLiteDatabase db = mTaskDbHelper.getWritableDatabase();
        // Get movie_json
        String movie_id = uri.getPathSegments().get(1);
        moviesDeleted = db.delete(TABLE_NAME,
                null,
                null);
        // Return the number of tasks deleted
        getContext().getContentResolver().notifyChange(uri, null);
        return moviesDeleted;
    }

    // Not used
    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        throw new UnsupportedOperationException("Not implemented");
    }

    // Not used
    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
