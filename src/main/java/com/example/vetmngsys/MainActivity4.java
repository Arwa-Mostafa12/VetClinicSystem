package com.example.vetmngsys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.WindowDecorActionBar;
import android.annotation.SuppressLint;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class MainActivity4 extends AppCompatActivity {
    EditText pID;
    int ID;
    EditText Pname;
    EditText age;
    int AGE;
    EditText species;
    EditText medical_history;
    EditText breed;
    EditText vacc;

    EditText user_id;
    int userID;
    Button button;
    ImageButton camera;
    connection connect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        pID=findViewById(R.id.pID);
        Pname=findViewById(R.id.pname);
        age=findViewById(R.id.age);
        species=findViewById(R.id.species);
        medical_history=findViewById(R.id.med_history);
        breed=findViewById(R.id.breed);
        vacc=findViewById(R.id.vacc);
        user_id=findViewById(R.id.user_id);
        camera=findViewById(R.id.imageButton);
        ID=Integer.parseInt(pID.getText().toString());
        AGE=Integer.parseInt(age.getText().toString());
        userID=Integer.parseInt(user_id.getText().toString());

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i,100);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MainActivity4.addingPatient().execute("");


            }
        });
    }

        public class addingPatient extends AsyncTask<String, String , String> {

            String z = "";
            Boolean isSuccess = false;

            @SuppressLint("RestrictedApi")
            @Override
            protected void onPreExecute() {
                WindowDecorActionBar.TabImpl status;
                Toast.makeText(MainActivity4.this, "sending data to database", Toast.LENGTH_LONG).show();
            }

            @Override
            protected void onPostExecute(String s) {
                Toast.makeText(MainActivity4.this, "successfully added ", Toast.LENGTH_LONG).show();
                pID.setText("");
                species.setText("");
                breed.setText("");
                age.setText("");
                medical_history.setText("");
                vacc.setText("");
                medical_history.setText("");
                user_id.setText("");
            }

            @Override
            protected String doInBackground(String... strings) {
                try{
                    connect = (connection) connectionClass("127.0.0.1", "ar12345", "VETMNGSYS", "3306");
                    if(connect == null){
                        z = "Check Your Internet Connection";
                    }
                    else{
                        String sql="INSERT INTO patient(ID, patientname, age, species, med_history, breed, vaccination, user_id) VALUES('"+ID+"','"+Pname.getText()+"','"+AGE+"','"+species.getText()+"','"+medical_history.getText()+"','"+breed.getText()+"','"+vacc.getText()+"','"+userID+"')";
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
