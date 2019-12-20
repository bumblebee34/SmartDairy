package com.example.smartdairy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.xml.sax.DTDHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Profile extends AppCompatActivity {

    private TextView uid,name,address,mobile,idtype,idnumber,date,loan,medical,cattlefeed;
    private Button logout;
    private String ConnectionResult = "";
    private Boolean isSuccess = false;
    private Connection connect;
    private String uid1,name1,address1,mobile1,idtype1,idnumber1,date1,userID,loan1,cattlefeed1,medical1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        InitializeFields();
        userID = SaveSharedPreference.getUserName(Profile.this);
        AddUserData();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUserToLoginActivity();
            }
        });
    }

    private void AddUserData() {
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
                String query = "select UID,Name,Address,Mobile,DateOfReg,IDType,IDNumber from dbo.Farmer_Profiles where UID="+userID;
                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()){
                    Log.i("jdbc","yes it is connected");
                    uid1 = rs.getString(1);
                    name1 = rs.getString(2);
                    address1 = rs.getString(3);
                    mobile1 = rs.getString(4);
                    date1 = rs.getString(5);
                    idtype1 = rs.getString(6);
                    idnumber1 = rs.getString(7);
                }
                String query1 = "select Loan,Cattlefeed,Medical from dbo.bank where UID="+userID;
                Statement stmt1 = connect.createStatement();
                ResultSet rs1 = stmt1.executeQuery(query1);
                while (rs1.next()){
                    Log.i("jdbc","yes it is connected");
                    loan1 = rs1.getString(1);
                    cattlefeed1 = rs1.getString(2);
                    medical1 = rs1.getString(3);
                }
                ConnectionResult = " successful";
                isSuccess=true;
                uid.setText(uid1);
                name.setText(name1);
                address.setText(address1);
                mobile.setText(mobile1);
                date.setText(date1);
                idtype.setText(idtype1);
                idnumber.setText(idnumber1);
                loan.setText("Rs. "+loan1);
                cattlefeed.setText("Rs. "+cattlefeed1);
                medical.setText("Rs. "+medical1);
                connect.close();
            }
        }
        catch (Exception ex)
        {
            isSuccess = false;
            ConnectionResult = ex.getMessage();
            Log.i("jdbc",ConnectionResult);
        }
    }

    private void InitializeFields() {
        uid = findViewById(R.id.profile_user_id_edit_text);
        name = findViewById(R.id.profile_name_edit_text);
        address = findViewById(R.id.profile_address_edit_text);
        mobile = findViewById(R.id.profile_mobile_edit_text);
        idtype = findViewById(R.id.profile_idtype_edit_text);
        idnumber = findViewById(R.id.profile_idnumber_edit_text);
        date = findViewById(R.id.profile_date_edit_text);
        loan = findViewById(R.id.profile_loan_edit_text);
        cattlefeed = findViewById(R.id.profile_cattle_feed_edit_text);
        medical = findViewById(R.id.profile_medical_edit_text);
        logout = findViewById(R.id.logout_button);
    }

    private void SendUserToLoginActivity() {
        SaveSharedPreference.setUserName(Profile.this,"");
        Intent intent = new Intent(Profile.this,Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
