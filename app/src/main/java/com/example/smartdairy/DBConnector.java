package com.example.smartdairy;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

    String ID,DB,DBUSerName,DBPassword;
    public Connection connection(){
        ID="dairymanagement.ccb2r2jg0qf7.ap-south-1.rds.amazonaws.com";
        DB="bhagyaraj";
        DBUSerName="bhagyaraj34";
        DBPassword="bhagyaraj34";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        java.sql.Connection connection = null;
        String ConnectionURL = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL = "jdbc:jtds:sqlserver://" + ID +";databaseName="+ DB + ";user=" + DBUSerName+ ";password=" + DBPassword + ";";
            connection = DriverManager.getConnection(ConnectionURL);
        }
        catch (SQLException se)
        {
            Log.e("error here 1 : ", se.getMessage());
        }
        catch (ClassNotFoundException e)
        {
            Log.e("error here 2 : ", e.getMessage());
        }
        catch (Exception e)
        {
            Log.e("error here 3 : ", e.getMessage());
        }
        return connection;
    }

}
