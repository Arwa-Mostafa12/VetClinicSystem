package com.example.vetmngsys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.WindowDecorActionBar;

import android.annotation.SuppressLint;
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
import java.sql.Statement;

//(ID, timeA, dateA, record, patient_ID)
public class MainActivity8 extends AppCompatActivity {
    EditText ID;
    int appID;
    EditText timeA;
    EditText dateA;
    EditText record;
    EditText patientID;
    int patID;
    Button save;
    connection connect;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);
        ID = findViewById(R.id.editText);
        timeA = findViewById(R.id.editText4);
        dateA = findViewById(R.id.editText3);
        record = findViewById(R.id.editText2);
        patientID = findViewById(R.id.editText5);
        save = findViewById(R.id.button3);
        appID = Integer.parseInt(ID.getText().toString());
        patID = Integer.parseInt(patientID.getText().toString());
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MainActivity8.addingAPPOINTMENTS().execute("");


            }
        });
    }
        public class addingAPPOINTMENTS extends AsyncTask<String, String , String> {

            String z = "";
            Boolean isSuccess = false;

            @SuppressLint("RestrictedApi")
            @Override
            protected void onPreExecute() {
                WindowDecorActionBar.TabImpl status;
                Toast.makeText(MainActivity8.this, "sending data to database", Toast.LENGTH_LONG).show();
            }

            @Override
            protected void onPostExecute(String s) {
                Toast.makeText(MainActivity8.this, "successfully added ", Toast.LENGTH_LONG).show();
                ID.setText("");
                timeA.setText("");
                dateA.setText("");
                record.setText("");
                patientID.setText("");
            }

            @Override
            protected String doInBackground(String... strings) {
                try{
                    connect = (connection) connectionClass("127.0.0.1", "ar12345", "VETMNGSYS", "3306");
                    if(connect == null){
                        z = "Check Your Internet Connection";
                    }
                    else{
                        String sql="Appointments (ID, timeA, dateA, record, patient_ID) VALUES('"+ID+"','"+timeA.getText()+"','"+dateA.getText()+"','"+record.getText()+"','"+patientID.getText()+"')";
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