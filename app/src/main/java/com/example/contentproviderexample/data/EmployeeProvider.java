package com.example.contentproviderexample.data;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class EmployeeProvider extends ContentProvider {

    private EmployeesDbHelper employeesDbHelper;

    public static final int EMPLOYEES = 100;
    public static final int EMPLOYEES_WITH_ID = 101;

    public static final UriMatcher sUriMatcher = buildUriMatcher();


    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(ProviderContract.AUTHORITY, ProviderContract.PATH_EMPLOYEES, EMPLOYEES);
        uriMatcher.addURI(ProviderContract.AUTHORITY, ProviderContract.PATH_EMPLOYEES + "/#", EMPLOYEES_WITH_ID);

        return uriMatcher;
    }


    @Override
    public boolean onCreate() {
        Context context = getContext();
        employeesDbHelper = new EmployeesDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        final SQLiteDatabase db = employeesDbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);

        Cursor retCursor;

        switch (match) {
            case EMPLOYEES:
                retCursor = db.query(ProviderContract.EmployeesEntry.TABLE_NAME,
                        projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            case EMPLOYEES_WITH_ID:
                String id = uri.getPathSegments().get(1);

                String mSelection = "id=?";
                String[] mSelectionArgs = new String[]{id};

                retCursor = db.query(ProviderContract.EmployeesEntry.TABLE_NAME,
                        projection,
                        mSelection,
                        mSelectionArgs,
                        null, null,
                        sortOrder);
                break;

            default:
                throw new UnsupportedOperationException("Unknown Uri:" + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = employeesDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);

        Uri returnUri;

        switch (match) {
            case EMPLOYEES: {
                long id = db.insert(ProviderContract.EmployeesEntry.TABLE_NAME, null, contentValues);

                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(ProviderContract.EmployeesEntry.CONTENT_URI, id);
                } else {
                    throw new SQLException("Failed to Insert into new row " + uri);
                }
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri:" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        final SQLiteDatabase db = employeesDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int deleteId;

        switch (match) {
            case EMPLOYEES:
                deleteId = db.delete(ProviderContract.EmployeesEntry.TABLE_NAME,
                        selection, selectionArgs);
                break;

            case EMPLOYEES_WITH_ID:
                String id = uri.getPathSegments().get(1);
                String mSelection = "id=?";
                String[] mSelectionArgs = new String[]{id};

                deleteId = db.delete(ProviderContract.EmployeesEntry.TABLE_NAME,
                        mSelection,
                        mSelectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri:" + uri);

        }

        if (deleteId > 0) {
            getContext().getContentResolver().notifyChange(uri, null);

        }
        return deleteId;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
