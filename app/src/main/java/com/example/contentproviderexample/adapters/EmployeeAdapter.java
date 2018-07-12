package com.example.contentproviderexample.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.contentproviderexample.R;
import com.example.contentproviderexample.models.Employee;

import java.util.ArrayList;


public class EmployeeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String LOG_TAG = EmployeeAdapter.class.getSimpleName();
    private final Context mContext;
    private final ArrayList<Employee> mEmployees;


    EmployeeAdapter adapter = this;


    public EmployeeAdapter(Context context, ArrayList<Employee> employees) {
        mContext = context;
        mEmployees = employees;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.employee_view, parent, false);

        viewHolder = new MyItemHolder(mContext, v);


        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        ((MyItemHolder) holder).textView1.setText(mEmployees.get(position).getEID());

        ((MyItemHolder) holder).textView2.setText(mEmployees.get(position).getEName());
        ((MyItemHolder) holder).textView3.setText(mEmployees.get(position).getENumber());
        ((MyItemHolder)holder).mDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEmployees.get(position).removeFromDatabase(mContext);
            }
        });


    }


    @Override
    public int getItemCount() {
        return mEmployees.size();
    }


    public static class MyItemHolder extends RecyclerView.ViewHolder {
        TextView textView1, textView2,textView3;
        ImageButton mDeleteBtn;

        Context cntxt;


        public MyItemHolder(Context context, View itemView) {
            super(itemView);
            cntxt = context;
            textView1 = (TextView) itemView.findViewById(R.id.tv1);
            textView2 = (TextView) itemView.findViewById(R.id.tv2);
            textView3 = (TextView) itemView.findViewById(R.id.tv3);
            mDeleteBtn = (ImageButton) itemView.findViewById(R.id.delete_btn);

        }

    }
}


