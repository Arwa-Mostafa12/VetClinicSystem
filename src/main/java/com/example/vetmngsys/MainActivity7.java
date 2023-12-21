package com.example.vetmngsys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity7 extends AppCompatActivity {
    TextView date;
    TextView time;
    TextView patientName;
    TextView drname;
    connection connect;
    Button booking;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);
        date=findViewById(R.id.textview_dateLabel);
        patientName=findViewById(R.id.textview_pat_label);
        drname=findViewById(R.id.textview_dr_label);
        time=findViewById(R.id.textview_timeLabel);
        date.setText("2023-01-01");
        time.setText("10:00 AM");
        patientName.setText("Fluffy");
        drname.setText("JaneSmith");
        booking=findViewById(R.id.bookbutton);
        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity7.this,MainActivity8.class);
                startActivity(i);
            }
        });


    }
}