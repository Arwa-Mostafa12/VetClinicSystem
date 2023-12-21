package com.example.vetmngsys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.WindowDecorActionBar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class MainActivity5 extends AppCompatActivity {
    EditText added_inventory;
    ImageButton adding;
    ImageButton deleting;
    ImageButton checking;
    connection connect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        added_inventory=findViewById(R.id.textView11);
        adding=findViewById(R.id.imageButton14);
        checking=findViewById(R.id.imageButton13);
        deleting=findViewById(R.id.imageButton2);
        Intent serviceIntent = new Intent(this, AlertService.class);
        startService(serviceIntent);

        checking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity5.this, MainActivity6.class);
                startActivity(i);
                finish();
            }
        });
        adding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MainActivity5.addingINV().execute("");


            }
        });
        deleting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MainActivity5.deleting_inv().execute("");

            }
        });

    }
    public class addingINV extends AsyncTask<String, String , String> {

        String z = "";
        Boolean isSuccess = false;

        @SuppressLint("RestrictedApi")
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(MainActivity5.this, "successfully added inventory", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                connect = (connection) connectionClass("127.0.0.1", "ar12345", "VETMNGSYS", "3306");
                if(connect == null){
                    z = "Check Your Internet Connection";
                }
                else{
                    int count = Integer.parseInt("SELECT quantity FROM Inventory WHERE INVname = '"+added_inventory.getText().toString());

                    count=count++;
                    String sql= "UPDATE Inventory SET quantity = '" + count + "' WHERE INVname = '" + added_inventory.getText() + "'";
                    Statement stmt = connect.con.createStatement();
                    stmt.executeUpdate(sql);
                }

            }catch (Exception e){
                isSuccess = false;
                z = e.getMessage();
            }

            return z;
        }
    }
    public class deleting_inv extends AsyncTask<String, String , String> {

        String z = "";
        Boolean isSuccess = false;

        @SuppressLint("RestrictedApi")
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(MainActivity5.this, "successfully decremented inventory", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                connect = (connection) connectionClass("127.0.0.1", "ar12345", "VETMNGSYS", "3306");
                if(connect == null){
                    z = "Check Your Internet Connection";
                }
                else{
                    int count = Integer.parseInt("SELECT quantity FROM Inventory WHERE INVname = '"+added_inventory.getText().toString());

                    count=count--;
                    String sql= "UPDATE Inventory SET quantity = '" + count + "' WHERE INVname = '" + added_inventory.getText() + "'";
                    Statement stmt = connect.con.createStatement();
                    stmt.executeUpdate(sql);
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
