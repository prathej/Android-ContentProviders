package com.example.contentproviderexample.models;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.contentproviderexample.data.ProviderContract;

import timber.log.Timber;

/**
 * Created by user on 09-07-2018.
 */

public class Employee implements Parcelable {

    private static final String LOG_TAG = Employee.class.getSimpleName();
    private String mEID;
    private String mEName;
    private String mENumber;


    public Employee() {
    }

    public void setEID(String eid) {
        mEID = eid;
    }

    public void setEName(String eName) {
        mEName = eName;
    }

    public void setENumber(String eNumber) {
        mENumber = eNumber;
    }



    public String getEID() {
        return mEID;
    }

    public String getEName() {
        return mEName;
    }


    public String getENumber() {
        return mENumber;
    }




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mEID);
        dest.writeString(mEName);
        dest.writeString(mENumber);


    }

    public Employee(Parcel in) {
        mEID = in.readString();
        mEName = in.readString();
        mENumber = in.readString();

    }

    public static final Creator<Employee> CREATOR = new Creator<Employee>() {
        public Employee createFromParcel(Parcel source) {
            return new Employee(source);
        }

        public Employee[] newArray(int size) {
            return new Employee[size];
        }
    };


  public void saveToDatabase(Context context) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ProviderContract.EmployeesEntry.COLUMN_EMPLOYEEID, mEID);
      contentValues.put(ProviderContract.EmployeesEntry.COLUMN_EMPLOYEE_NAME, mEName);

     // Double d = Double.parseDouble(mENumber);
      contentValues.put(ProviderContract.EmployeesEntry.COLUMN_EMPLOYEE_NUMBER, mENumber);


        Uri uri = context.getContentResolver().insert(ProviderContract.EmployeesEntry.CONTENT_URI, contentValues);
        if (uri != null) {

            Timber.d("%s Event added to Database = %s", LOG_TAG, uri.toString());
        } else {

            Timber.d("%s Error adding event to Database = %s ", LOG_TAG, uri.toString());
        }
    }


    public void removeFromDatabase(Context context) {
        int deletedRows = context.getContentResolver().delete(ProviderContract.EmployeesEntry.CONTENT_URI,
                ProviderContract.EmployeesEntry.COLUMN_EMPLOYEEID + "=?", new String[]{mEID});
        if (deletedRows > 0) {

            Timber.d(LOG_TAG + " event deleted from database");

        } else {

            Timber.d(LOG_TAG + "Error removing event from from database");

        }
    }
}
