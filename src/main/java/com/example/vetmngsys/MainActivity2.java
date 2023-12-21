package com.example.vetmngsys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.WindowDecorActionBar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity2 extends AppCompatActivity {
    EditText user_name;
    EditText email_address;
    EditText phone_number;
    EditText password;
    EditText passConf;
    EditText ID_s;
    int ID;
    EditText address;
    EditText role;
    Button Sign_up;
    Connection connect;
    Statement stmt;
    SharedPreferences sp;
    String EMAIL,PASS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        user_name=findViewById(R.id.editTextText3);
        email_address=findViewById(R.id.editTextText4);
        phone_number=findViewById(R.id.editTextText5);
        password=findViewById(R.id.editTextText6);
        passConf=findViewById(R.id.editTextText7);
        role=findViewById(R.id.editTextText10);
        ID_s=findViewById(R.id.editTextText8);
        ID=Integer.parseInt(ID_s.getText().toString());
        address=findViewById(R.id.editTextText9);
        sp=getSharedPreferences("ADMININFO", Context.MODE_PRIVATE);


        Sign_up=findViewById(R.id.button3);

        Sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EMAIL=email_address.getText().toString();
                PASS=password.getText().toString();
                SharedPreferences.Editor editor=sp.edit();
                if(role.getText().toString()=="admin"){
                    editor.putString("email",EMAIL);
                    editor.putString("password",PASS);
                    editor.commit();
                    Toast.makeText(MainActivity2.this, "Admin info Saved", Toast.LENGTH_SHORT).show();
                }
                new registeruser().execute("");
                Intent i = new Intent(MainActivity2.this,MainActivity3.class);
                startActivity(i);
                finish();
            }
        });

    }
    public class registeruser extends AsyncTask<String, String , String> {

        String z = "";
        Boolean isSuccess = false;

        @SuppressLint("RestrictedApi")
        @Override
        protected void onPreExecute() {
            WindowDecorActionBar.TabImpl status;
            Toast.makeText(MainActivity2.this, "sending data to database", Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(MainActivity2.this, "Registration successful", Toast.LENGTH_LONG).show();
            user_name.setText("");
            email_address.setText("");
            password.setText("");
            address.setText("");
            role.setText("");
            phone_number.setText("");
            ID_s.setText("");
            passConf.setText("");

        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                connect = (Connection) connectionClass("127.0.0.1", "ar12345", "VETMNGSYS", "3306");
                if(connect == null){
                    z = "Check Your Internet Connection";
                }
                else{
                    String sql = "INSERT INTO users (ID, username, userrole, contactinfo_phone, contactinfo_address, email, userpassword) VALUES ('"+user_name+"','"+ID+"','"+role.getText()+"','"+phone_number.getText()+"','"+address.getText()+"','"+email_address.getText()+"','"+password.getText()+"')";
                    Statement stmt = connect.createStatement();
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
