package com.example.smartdairy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Milk extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String ConnectionResult = "";
    private Boolean isSuccess = false;
    private Connection connect;
    private String userID,pointID;
    private Integer count=0;
    private TextView countText;
    private SwipeRefreshLayout refreshLayout;
    private Calendar myCalendar;
    private String  searchdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_milk);
        refreshLayout = findViewById(R.id.milk_swipe_to_refresh);
        countText = findViewById(R.id.milk_log_count_text);
        userID = SaveSharedPreference.getUserName(Milk.this);
        Log.i("jdbc",pointID+"Milk madhe ahe miii");
        recyclerView = findViewById(R.id.milk_log_recycler_view);
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
        ImageButton button= findViewById(R.id.milk_log_search_date);
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
                new DatePickerDialog(Milk.this, date, myCalendar
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
                String query1 = "select CollectionPointID from dbo.Farmer_Profiles where UID="+userID;
                Statement stmt1 = connect.createStatement();
                ResultSet rs1 = stmt1.executeQuery(query1);
                while (rs1.next()){
                    Log.i("jdbc","yes it is connected");
                    pointID = rs1.getString(1);
                }
                String query = "select Date,MilkType,Quantity,Fat,Rate,TotalCost from dbo.Table_of_"+pointID+"_cp where UID="+userID+" ORDER BY Ind DESC";
                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()){
                    count++;
                    Log.i("jdbc","yes it is connected");
                    ArrayList<String> datanum = new ArrayList<String>();
                    datanum.add(rs.getString("Date"));
                    datanum.add(rs.getString("MilkType"));
                    datanum.add(rs.getString("Quantity"));
                    datanum.add(rs.getString("Fat"));
                    datanum.add(rs.getString("Rate"));
                    datanum.add(rs.getString("TotalCost"));
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
        mAdapter = new MyAdapter(data);
        recyclerView.setAdapter(mAdapter);

        countText.setText(count.toString()+" Items");
        if(count.equals(0))
        {
            new AlertDialog.Builder(Milk.this)
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
                String query = "select Date,MilkType,Quantity,Fat,Rate,TotalCost from dbo.Table_of_"+pointID+"_cp where UID="+userID+" and Date='"+searchdate+"' ORDER BY Ind DESC";
                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()){
                    count++;
                    Log.i("jdbc","yes it is connected");
                    ArrayList<String> datanum = new ArrayList<String>();
                    datanum.add(rs.getString("Date"));
                    datanum.add(rs.getString("MilkType"));
                    datanum.add(rs.getString("Quantity"));
                    datanum.add(rs.getString("Fat"));
                    datanum.add(rs.getString("Rate"));
                    datanum.add(rs.getString("TotalCost"));
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
        mAdapter = new MyAdapter(data);
        recyclerView.setAdapter(mAdapter);

        countText.setText(count.toString()+" Items");
        if(count.equals(0))
        {
            new AlertDialog.Builder(Milk.this)
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
