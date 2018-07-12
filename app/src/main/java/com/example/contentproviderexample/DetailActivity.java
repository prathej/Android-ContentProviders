package com.example.contentproviderexample;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.contentproviderexample.adapters.EmployeeAdapter;
import com.example.contentproviderexample.data.ProviderContract;
import com.example.contentproviderexample.models.Employee;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int EMPLOYEE_LOADER = 1;

    @BindView(R.id.tv)
    TextView mTexrView;

    @BindView(R.id.empListRv)
    RecyclerView mRecycler;

    ArrayList<Employee> employeeList = null;

    EmployeeAdapter mAdapter;

    @BindView(R.id.eId)
    TextView mEIdTv;

    @BindView(R.id.eName)
    TextView mENameTv;

    @BindView(R.id.eNu)
    TextView mENuTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        employeeList = new ArrayList<Employee>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(layoutManager);
        getSupportLoaderManager().initLoader(EMPLOYEE_LOADER, null, this);

        mEIdTv.setText(R.string.emp_id);
        mENameTv.setText(R.string.emp_name);
        mENuTv.setText(R.string.emp_number);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new android.support.v4.content.CursorLoader(this,
                ProviderContract.EmployeesEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        ArrayList<Employee> empList = getDataFromCursor(cursor);
        mAdapter = new EmployeeAdapter(getApplicationContext(), empList);
        mRecycler.setAdapter(mAdapter);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private ArrayList<Employee> getDataFromCursor(Cursor cursor) {

        //if (retCursor != null) {
        int count = cursor.getCount();
        employeeList = new ArrayList<Employee>();
        for (int i = 0; i < count; i++) {
            employeeList.add(new Employee());
            if (cursor.moveToPosition(i)) {
                // do{
                String employeeId = cursor.getString(cursor.getColumnIndex(ProviderContract.EmployeesEntry.COLUMN_EMPLOYEEID));
                //  Timber.d("%s eventId is %s ", LOG_TAG, eventId);
                String employeeName = cursor.getString(cursor.getColumnIndex(ProviderContract.EmployeesEntry.COLUMN_EMPLOYEE_NAME));
                String employeeNumber = cursor.getString(cursor.getColumnIndex(ProviderContract.EmployeesEntry.COLUMN_EMPLOYEE_NUMBER));


                employeeList.get(i).setEID(employeeId);
                employeeList.get(i).setEName(employeeName);
                employeeList.get(i).setENumber(employeeNumber);


            }
        }

        return employeeList;
    }

}
