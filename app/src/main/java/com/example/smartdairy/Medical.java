package com.example.smartdairy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Medical extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String ConnectionResult = "";
    private Boolean isSuccess = false;
    private Connection connect;
    private String userID;
    private Integer count=0;
    private TextView countText;
    private SwipeRefreshLayout refreshLayout;
    private Calendar myCalendar;
    private String  searchdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical);
        refreshLayout = findViewById(R.id.medical_swipe_to_refresh);
        countText = findViewById(R.id.medical_count_text);
        userID = SaveSharedPreference.getUserName(Medical.this);
        recyclerView = findViewById(R.id.medical_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int topRowVerticalPosition =
                        (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                refreshLayout.setEnabled(topRowVerticalPosition >= 0);

            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        AddUserMilkData();
        refreshLayout.setColorSchemeResources(R.color.colorAccent,R.color.green, R.color.colorPrimaryDark);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run() {
                        AddUserMilkData();
                        refreshLayout.setRefreshing(false);
                    }
                },2000);
            }
        });
        myCalendar = Calendar.getInstance();
        ImageButton button= findViewById(R.id.medical_search_date);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(Medical.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void AddUserMilkData() {
        List<ArrayList<String>> data=null;
        data = new ArrayList<ArrayList<String>>();
        try {
            DBConnector connectionHelper = new DBConnector();
            connect = connectionHelper.connection();
            if (connect == null)
            {
                ConnectionResult = "Check Your Internet Access!";
            }
            else
            {
                // Change below query according to your own database.
                Log.i("jdbc","connected");
                Log.i("jdbc",userID);
                String query = "select MedicalCost,Date from dbo.medical_table where UID="+userID+" ORDER BY Ind DESC";
                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()){
                    count++;
                    Log.i("jdbc","yes it is connected");
                    ArrayList<String> datanum = new ArrayList<String>();
                    datanum.add(rs.getString("MedicalCost"));
                    datanum.add(rs.getString("Date"));
                    data.add(datanum);
                }
                ConnectionResult = " successful";
                isSuccess=true;
                connect.close();
            }
        }
        catch (Exception ex)
        {
            isSuccess = false;
            ConnectionResult = ex.getMessage();
            Log.i("jdbc",ConnectionResult);
        }
        mAdapter = new MyMedicalAdapter(data);
        recyclerView.setAdapter(mAdapter);

        countText.setText(count.toString()+" Items");
        if(count.equals(0))
        {
            new AlertDialog.Builder(Medical.this)
                    .setTitle("No Data")
                    .setMessage("No data found for selected category")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .show();
        }
        count = 0;
    }

    private void GetSearchedDateData() {
        List<ArrayList<String>> data=null;
        data = new ArrayList<ArrayList<String>>();
        try {
            DBConnector connectionHelper = new DBConnector();
            connect = connectionHelper.connection();
            if (connect == null)
            {
                ConnectionResult = "Check Your Internet Access!";
            }
            else
            {
                // Change below query according to your own database.
                Log.i("jdbc","connected");
                Log.i("jdbc",userID);
                String query = "select MedicalCost,Date from dbo.medical_table where UID="+userID+" and Date='"+searchdate+"' ORDER BY Ind DESC";
                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()){
                    count++;
                    Log.i("jdbc","yes it is connected");
                    ArrayList<String> datanum = new ArrayList<String>();
                    datanum.add(rs.getString("MedicalCost"));
                    datanum.add(rs.getString("Date"));
                    data.add(datanum);
                }
                ConnectionResult = " successful";
                isSuccess=true;
                connect.close();
            }
        }
        catch (Exception ex)
        {
            isSuccess = false;
            ConnectionResult = ex.getMessage();
            Log.i("jdbc",ConnectionResult);
        }
        mAdapter = new MyMedicalAdapter(data);
        recyclerView.setAdapter(mAdapter);

        countText.setText(count.toString()+" Items");
        if(count.equals(0))
        {
            new AlertDialog.Builder(Medical.this)
                    .setTitle("Search Result")
                    .setMessage("No data found for selected date")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            AddUserMilkData();
                        }
                    })
                    .show();
        }
        count = 0;
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        searchdate = sdf.format(myCalendar.getTime());
        Log.i("jdbc",searchdate);
        GetSearchedDateData();
    }
}
