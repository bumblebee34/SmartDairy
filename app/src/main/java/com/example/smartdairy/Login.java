package com.example.smartdairy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Login extends AppCompatActivity {

    private TextView userID,password,forgotpassword;
    private Button loginButton;
    private String username,pass,passcheck,pointname;
    private Connection connect;
    private String ConnectionResult = "";
    private Boolean isSuccess = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        InitializeFields();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserLogin();
            }
        });
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUserToChangePasswordActivity();
            }
        });
    }


    private void InitializeFields() {
        userID = findViewById(R.id.login_user_id);
        password = findViewById(R.id.login_user_password);
        loginButton = findViewById(R.id.change_password_button);
        forgotpassword = findViewById(R.id.forgot_password_text_view);
    }

    private void UserLogin() {
        username = userID.getText().toString();
        pass = password.getText().toString();
        Log.i("jdbc",username);
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
                Log.i("jdbc"," yes connected");
                String query = "select Password,CollectionPointID from dbo.Farmer_Profiles where UID="+username;
                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()){
                    passcheck = rs.getString(1);
                }
                Log.i("jdbc",passcheck);
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
        if(pass.equals(passcheck))
        {
            SaveSharedPreference.setUserName(Login.this,username);
            Log.i("jdbc",username);
            SendUserToMainActivity();
        }
        else {
            Toast.makeText(Login.this, "Wrong Password", Toast.LENGTH_SHORT).show();
        }
    }

    private void SendUserToMainActivity() {
        Intent intent = new Intent(Login.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    private void SendUserToChangePasswordActivity() {
        Intent intent = new Intent(Login.this,ChangePassword.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
