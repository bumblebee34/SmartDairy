package com.example.smartdairy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private LinearLayout milk,profile,cattlefeed,loan,billing,medical;
    private String userID;
    private Boolean aBoolean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(SaveSharedPreference.getUserName(MainActivity.this).length() == 0)
        {
            SendUserToLoginActivity();
        }
        userID = SaveSharedPreference.getUserName(MainActivity.this);
        Log.i("jdbc",userID);
        InitializeFields();
        milk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUserToMilkActivity();
            }
        });
        cattlefeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUserToCattleFeedActivity();
            }
        });
        loan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUserToLoanActivity();
            }
        });
        billing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUserToBillingActivity();
            }
        });
        medical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUserToMedicalActivity();
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUserToProfileActivity();
            }
        });
        aBoolean = isOnline();
        if(aBoolean.equals(false))
        {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("No Internet")
                    .setMessage("Please connect to internet and try again")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .show();
        }
    }

    private void InitializeFields() {
        milk = findViewById(R.id.milk_log);
        cattlefeed = findViewById(R.id.cattle_feed);
        loan = findViewById(R.id.loan);
        billing = findViewById(R.id.billing);
        medical = findViewById(R.id.medical);
        profile = findViewById(R.id.profile);
    }

    private void SendUserToLoginActivity() {
        Intent intent = new Intent(MainActivity.this,Login.class);
        startActivity(intent);
    }

    private void SendUserToMilkActivity() {
        Intent intent = new Intent(MainActivity.this,Milk.class);
        startActivity(intent);
    }

    private void SendUserToCattleFeedActivity() {
        Intent intent = new Intent(MainActivity.this,CattleFeed.class);
        startActivity(intent);
    }

    private void SendUserToLoanActivity() {
        Intent intent = new Intent(MainActivity.this,Loan.class);
        startActivity(intent);
    }

    private void SendUserToBillingActivity() {
        Intent intent = new Intent(MainActivity.this,Billing.class);
        startActivity(intent);
    }

    private void SendUserToMedicalActivity() {
        Intent intent = new Intent(MainActivity.this,Medical.class);
        startActivity(intent);
    }

    private void SendUserToProfileActivity() {
        Intent intent = new Intent(MainActivity.this,Profile.class);
        startActivity(intent);
    }

    private Boolean isOnline()	{
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if(ni != null && ni.isConnected())
            return true;

        return false;
    }
}
