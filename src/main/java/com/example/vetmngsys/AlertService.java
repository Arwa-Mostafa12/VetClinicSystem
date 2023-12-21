package com.example.vetmngsys;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class AlertService extends Service {

    public AlertService() {
    }


    private static final String TAG = "StockAlertService";
    private Handler handler;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Schedule periodic checks using Handler
        handler.postDelayed(stockCheckRunnable, 10000); // 10 seconds delay, adjust as needed
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Remove any callbacks when the service is stopped
        handler.removeCallbacks(stockCheckRunnable);
    }

    // Runnable to check stock levels
    private final Runnable stockCheckRunnable = new Runnable() {




        @Override
        public void run() {
            Connection
            connect = connectionClass("127.0.0.1", "ar12345", "VETMNGSYS", "3306");
            String str="Medicine A";
            String str2="Bandages";
            String str3="Vaccines";
            String str4="Surgical Tools";
            String str5="Antiseptic Solution";

            int currentStock = Integer.parseInt("SELECT quantity FROM Inventory WHERE INVname = '"+str);
            int currentStock2 = Integer.parseInt("SELECT quantity FROM Inventory WHERE INVname = '"+str2);
            int currentStock3 = Integer.parseInt("SELECT quantity FROM Inventory WHERE INVname = '"+str3);
            int currentStock4 = Integer.parseInt("SELECT quantity FROM Inventory WHERE INVname = '"+str4);
            int currentStock5 = Integer.parseInt("SELECT quantity FROM Inventory WHERE INVname = '"+str5);

            if (currentStock < 20) {
                // Trigger an alert, for example, by showing a notification
                showStockAlertNotification();
            }else if (currentStock2 < 20){
                showStockAlertNotification();

            } else if (currentStock3 < 20) {
                showStockAlertNotification();

            } else if (currentStock4 < 20) {
                showStockAlertNotification();

            } else if (currentStock5 < 20) {
                showStockAlertNotification();
            }

            // Schedule the next check after a delay
            handler.postDelayed(this, 10000); // Repeat every 10 seconds, adjust as needed
        }
    };

    private int getCurrentStock() {
        // Replace this with your actual method to get the current stock level
        return 15; // Example value, replace with your logic
    }

    private void showStockAlertNotification() {
        // Replace this with your logic to show an alert, e.g., a notification
        Log.d(TAG, "Stock is under 20! Triggering alert.");
    }


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

