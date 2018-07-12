package com.example.contentproviderexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.contentproviderexample.models.Employee;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tvid1)
    TextView mTV1;

    @BindView(R.id.tvid2)
    TextView mTV2;

    @BindView(R.id.tvid3)
    TextView mTV3;

    @BindView(R.id.editText1)
    EditText mEditText1;

    @BindView(R.id.editText2)
    EditText mEditText2;

    @BindView(R.id.editText3)
    EditText mEditText3;

    @BindView(R.id.DbButton)
    Button mButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mTV1.setText(R.string.tv1);
        mTV2.setText(R.string.employee_name);
        mTV3.setText(R.string.employee_number);
        mButton.setText(R.string.add_to_database);


        //  employee.saveToDatabase(MainActivity.this);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String et1 = mEditText1.getText().toString();
                String et2 = mEditText2.getText().toString();
                String et3 = mEditText3.getText().toString();

                if(et1 != null && !et1.isEmpty() ||
                        et2 != null && !et2.isEmpty() ||
                        et3 != null && !et3.isEmpty()) {
                    Employee employee = saveEmployee(et1, et2, et3);
                    employee.saveToDatabase(MainActivity.this);
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    startActivity(intent);
                    mEditText1.setText(null);
                    mEditText2.setText(null);
                    mEditText3.setText(null);
                }
                else{
                    Toast.makeText(MainActivity.this,"Id, Name and Phone Number cannot be blank",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private Employee saveEmployee(String et1, String et2, String et3) {
        Employee employee = new Employee();

        employee.setEID(et1);
        employee.setEName(et2);
        employee.setENumber(et3);
        return employee;

    }
}
