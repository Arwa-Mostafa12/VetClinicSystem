package com.example.vetmngsys;

import static java.lang.Class.forName;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class connection {
    Connection con;
    public Connection conclass(){
        String ip="127.0.0.1",port="3306",db="VETMNGSYS",username="root",password="ar12345";
        StrictMode.ThreadPolicy a= new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(a);
        String ConnectionURL=null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL="jtds.jdbc.Driver://"+ip+":"+port+";"+"databasename="+db+";user="+username+";"+"password="+password+";";
            con = DriverManager.getConnection(ConnectionURL);

        }
        catch(Exception e)
        {
            Log.e("error is ", e.getMessage());
        }
        return con;
    }



}
