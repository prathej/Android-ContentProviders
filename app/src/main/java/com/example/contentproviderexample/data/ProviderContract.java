package com.example.contentproviderexample.data;


import android.net.Uri;
import android.provider.BaseColumns;

public class ProviderContract {

    public ProviderContract(){

    }

    public static final String AUTHORITY = "com.example.contentproviderexample";
    public static final Uri BASE_CONTENT_URI= Uri.parse("content://"+AUTHORITY);

    public static final String PATH_EMPLOYEES = "employees";

    public static class EmployeesEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_EMPLOYEES)
                .build();

        // events table and column names
        public static final String TABLE_NAME = "employees";

        //columns
        public static final String COLUMN_EMPLOYEEID="EmployeeId";
       public static final String COLUMN_EMPLOYEE_NUMBER="EmployeeNumber";
        public static final String COLUMN_EMPLOYEE_NAME="EmployeeName";

    }




}
