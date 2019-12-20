package com.example.smartdairy;

import android.content.Context;
import android.util.Log;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GetData {

    Connection connect;
    String ConnectionResult = "";
    Boolean isSuccess = false;
    String userID;

    public List<ArrayList<String>> getData(Context context){
        userID = SaveSharedPreference.getUserName(context);
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
                String query = "select Date from dbo.Table_of_24_cp where UID="+userID;
                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()){
                    Log.i("jdbc","yes it is connected");
                    ArrayList<String> datanum = new ArrayList<String>();
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

        return data;
    }
}
