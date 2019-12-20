package com.example.smartdairy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ChangePassword extends AppCompatActivity {

    private String userID,IDType,IDNumber,newPassword;
    private EditText userid,idnumber,newpass;
    private Button changePassword;
    private Spinner dropdown;
    private String ConnectionResult = "";
    private Boolean isSuccess = false;
    private Connection connect;
    private String checknumber,checktype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        InitializeFields();
        dropdown = findViewById(R.id.id_type_spinner_view);
        String[] items = new String[]{"Pan Card", "Adhar Card", "Voter ID"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IDType = dropdown.getSelectedItem().toString();
                IDNumber = idnumber.getText().toString();
                userID = userid.getText().toString();
                newPassword = newpass.getText().toString();
                GetUserData();
                CheckData();
            }
        });
    }

    private void InitializeFields() {
        userid = findViewById(R.id.change_password_login_user_id);
        idnumber = findViewById(R.id.id_number_edit_text);
        newpass = findViewById(R.id.new_password_edit_text);
        changePassword = findViewById(R.id.change_password_button);
    }

    private void GetUserData() {
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
                String query = "select IDType,IDNumber from dbo.Farmer_Profiles where UID="+userID;
                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()){
                    Log.i("jdbc","yes it is connected");
                    checktype = rs.getString(1);
                    checknumber = rs.getString(2);
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
    }

    private void CheckData() {
        if(IDType.equals(checktype) && IDNumber.equals(checknumber))
        {
            if(TextUtils.isEmpty(newPassword)){
                new AlertDialog.Builder(ChangePassword.this)
                        .setTitle("Failed to change password")
                        .setMessage("New password field is empty. Please fill it.")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
            }
            else
            {
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
                        String query = "Update dbo.Farmer_Profiles Set Password='"+newPassword+"' where UID="+userID;
                        Statement stmt = connect.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
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
                SendUserToLoginActivity();
            }
        }
        else
        {
            new AlertDialog.Builder(ChangePassword.this)
                    .setTitle("Failed to change password")
                    .setMessage("Invalid credentials. Please enter valid credentials")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
        }
    }

    private void SendUserToLoginActivity() {
        Intent intent = new Intent(ChangePassword.this,Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
