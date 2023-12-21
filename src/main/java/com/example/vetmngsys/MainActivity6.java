package com.example.vetmngsys;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class MainActivity6 extends AppCompatActivity {
    TextView t1;
    TextView t2;
    TextView t3;
    TextView t4;
    TextView t5;
    TextView text1;
    TextView text2;
    TextView text3;
    TextView text4;
    TextView text5;
    Button show;
    connection connect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        t1 = findViewById(R.id.textView21);
        t2 = findViewById(R.id.textView22);
        t3 = findViewById(R.id.textView23);
        t4 = findViewById(R.id.textView24);
        t5 = findViewById(R.id.textView25);
        text1 = findViewById(R.id.textView14);
        text2 = findViewById(R.id.textView15);
        text3 = findViewById(R.id.textView16);
        text4 = findViewById(R.id.textView17);
        text5 = findViewById(R.id.textView18);
        show = findViewById(R.id.button5);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MainActivity6.showing().execute("");


            }
        });
    }
        public class showing extends AsyncTask<String, String , String> {

            String z = "";
            Boolean isSuccess = false;

            @SuppressLint("RestrictedApi")
            @Override
            protected void onPreExecute() {

            }

            @Override
            protected void onPostExecute(String s) {
                Toast.makeText(MainActivity6.this, "successfully showed", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected String doInBackground(String... strings) {
                try{
                    connect = (connection) connectionClass("127.0.0.1", "ar12345", "VETMNGSYS", "3306");
                    if(connect == null){
                        z = "Check Your Internet Connection";
                    }
                    else{
                        t1.setText("SELECT quantity FROM Inventory WHERE INVname = '"+text1.getText());
                        t2.setText("SELECT quantity FROM Inventory WHERE INVname = '"+text2.getText());
                        t3.setText("SELECT quantity FROM Inventory WHERE INVname = '"+text3.getText());
                        t4.setText("SELECT quantity FROM Inventory WHERE INVname = '"+text4.getText());
                        t5.setText("SELECT quantity FROM Inventory WHERE INVname = '"+text5.getText());

                    }

                }catch (Exception e){
                    isSuccess = false;
                    z = e.getMessage();
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




