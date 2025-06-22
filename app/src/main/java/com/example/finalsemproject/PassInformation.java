package com.example.finalsemproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class PassInformation extends AppCompatActivity {
    TextView punedarshanacbuses,punyadashambuses,passinfo,allbusestimetable,rainbowbusestimetable,nightbusestimetable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_information);

        passinfo=findViewById(R.id.passinfo);
        allbusestimetable=findViewById(R.id.allbusestimetable);
        rainbowbusestimetable=findViewById(R.id.rainbowbusestimetable);
        nightbusestimetable=findViewById(R.id.nightbusestimetable);
        punyadashambuses=findViewById(R.id.punyadashambuses);
        punedarshanacbuses=findViewById(R.id.punedarshanacbuses);


         punedarshanacbuses.setOnClickListener(v -> {
            String url="https://pmpml.org/assets/schedule/1676888650b08095da212d1eb9c10c00513f0f5b03.pdf";
            Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            try {
                startActivity(intent);
            }catch(Exception e){
                Toast.makeText(getApplicationContext(),"Something Wrong...",Toast.LENGTH_LONG).show();
            }
        });

        punyadashambuses.setOnClickListener(v -> {
            String url="https://pmpml.org/assets/schedule/1682329950250e84f6afa7f037df0290166eb28992.pdf";

            Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            try {
                startActivity(intent);
            }catch(Exception e){
                Toast.makeText(getApplicationContext(),"Something Wrong...",Toast.LENGTH_LONG).show();
            }
        });

        nightbusestimetable.setOnClickListener(v -> {
            String url="https://pmpml.org/assets/schedule/1676888650b08095da212d1eb9c10c00513f0f5b03.pdf";

            Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            try {
                startActivity(intent);
            }catch(Exception e){
                Toast.makeText(getApplicationContext(),"Something Wrong...",Toast.LENGTH_LONG).show();
            }
        });

        rainbowbusestimetable.setOnClickListener(v -> {
            String url="https://pmpml.org/assets/schedule/1676888650b08095da212d1eb9c10c00513f0f5b03.pdf";

            Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            try {
                startActivity(intent);
            }catch(Exception e){
                Toast.makeText(getApplicationContext(),"Something Wrong...",Toast.LENGTH_LONG).show();
            }
        });


        allbusestimetable.setOnClickListener(v -> {
            String url="https://pmpml.org/assets/schedule/171057019760a399ddecda6852710f17b0bbbe9629.pdf";

            Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            try {
                startActivity(intent);
            }catch(Exception e){
                Toast.makeText(getApplicationContext(),"Something Wrong...",Toast.LENGTH_LONG).show();
            }
        });


        passinfo.setOnClickListener(v -> {
            String url="https://pmpml.org/assets/media_center/169477922297baf38334451e72170bd0a462d06a6a.pdf";

            Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            try {
                startActivity(intent);
            }catch(Exception e){
                Toast.makeText(getApplicationContext(),"Something Wrong...",Toast.LENGTH_LONG).show();
            }
        });
    }
}