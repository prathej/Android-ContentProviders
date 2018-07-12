package com.example.contentproviderexample.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import timber.log.Timber;

public class EmployeesDbHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = EmployeesDbHelper.class.getSimpleName();
    private static final String DB_NAME = "employees";
    private static final int DB_VERSION = 1;

    public EmployeesDbHelper(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_EMPLOYEES_TABLE = "CREATE TABLE " + ProviderContract.EmployeesEntry.TABLE_NAME + "(" +
                ProviderContract.EmployeesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ProviderContract.EmployeesEntry.COLUMN_EMPLOYEEID + " TEXT NOT NULL," +
                ProviderContract.EmployeesEntry.COLUMN_EMPLOYEE_NAME + " TEXT," +
                ProviderContract.EmployeesEntry.COLUMN_EMPLOYEE_NUMBER + " TEXT NOT NULL" +

                ");";



        Timber.d("%s employees query = %s", LOG_TAG, SQL_CREATE_EMPLOYEES_TABLE);


        sqLiteDatabase.execSQL(SQL_CREATE_EMPLOYEES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ProviderContract.EmployeesEntry.TABLE_NAME );

        onCreate(sqLiteDatabase);
    }

}
