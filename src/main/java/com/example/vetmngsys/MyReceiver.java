package com.example.vetmngsys;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals("android.intent.action.ACTION_POWER_CONNECTED")) {
                Toast.makeText(context, "power connected", Toast.LENGTH_SHORT).show();
            } else if (intent.getAction().equals("android.intent.action.BATTERY_LOW")) {
                Toast.makeText(context, "Battery low", Toast.LENGTH_SHORT).show();
            } else if (intent.getAction().equals("android.intent.action.ACTION_POWER_DISCONNECTED")) {
                Toast.makeText(context, "power disconnected", Toast.LENGTH_SHORT).show();
            }

        }

    }
