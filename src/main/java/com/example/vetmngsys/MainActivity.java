package com.example.vetmngsys;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    Button sign_in;
    Button sign_up;
    Connection connect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BroadcastReceiver br = new MyReceiver();
        IntentFilter f1 = new IntentFilter(Intent.ACTION_POWER_DISCONNECTED);
        IntentFilter f2 = new IntentFilter(Intent.ACTION_POWER_CONNECTED);
        IntentFilter f3 = new IntentFilter(Intent.ACTION_BATTERY_LOW);

        registerReceiver(br, f1);
        registerReceiver(br, f2);
        registerReceiver(br,f3);
        username=findViewById(R.id.editTextText);
        password=findViewById(R.id.editTextText2);
        sign_in=findViewById(R.id.button);
        sign_up=findViewById(R.id.button2);

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new checkLogin().execute("");
                Intent i = new Intent(MainActivity.this, MainActivity3.class);
                startActivity(i);
                finish();

            }
        });
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(i);
                finish();
            }
        });


    }
    public class checkLogin extends AsyncTask<String,String,String> {
        String z = null;
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String s) {
        }

        @Override
        protected String doInBackground(String... strings) {
            connect = connectionClass("127.0.0.1", "ar12345", "VETMNGSYS", "3306");
            if (connect == null) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Check Internet Connection", Toast.LENGTH_LONG).show();
                    }
                });
                z = "on Internet connection";
            } else {

                try {
                    String sql = "SELECT * FROM USERS WHERE email = '" + username.getText() + "'AND userpassword = '" + password.getText();
                    Statement stmt = connect.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);

                    if (rs.next()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "login success", Toast.LENGTH_LONG).show();

                            }
                        });
                        z = "success";
                        Intent i = new Intent(MainActivity.this, MainActivity3.class);
                        startActivity(i);
                        finish();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "check email or password", Toast.LENGTH_LONG).show();

                            }
                        });
                        username.setText("");
                        password.setText("");
                    }

                } catch (Exception e) {
                    isSuccess = false;
                    Log.e("SQL ERROR : ", e.getMessage());
                }
            }
            return z;
        }
    }
    @SuppressLint("NewApi")
    public Connection connectionClass(String user, String password, String database, String server){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String connectionURL = null;
        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectionURL = "jdbc:jtds:sqlserver://" + server+"/" + database + ";user=" + user + ";password=" + password + ";";
            connection = DriverManager.getConnection(connectionURL);
        }catch (Exception e){
            Log.e("SQL Connection Error : ", e.getMessage());
        }

        return connection;
    }
}