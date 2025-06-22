package com.example.finalsemproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class ProfilePage extends AppCompatActivity {
    TextView idfromdatabase,namefromdatabase,phonenumberfromdatabase,emailfromdatabase,profiledate;
    String yourid,yourname,yourPhoneno,yourEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        idfromdatabase=findViewById(R.id.idfromdatabase);
        namefromdatabase=findViewById(R.id.namefromdatabase);
        phonenumberfromdatabase=findViewById(R.id.phonenumberfromdatabase);
        emailfromdatabase=findViewById(R.id.emailfromdatabase);
        profiledate=findViewById(R.id.profiledate);


        yourid= Objects.requireNonNull(getIntent().getExtras()).getString("userId");
        yourname=getIntent().getExtras().getString("name");
        yourPhoneno=getIntent().getExtras().getString("email");
        yourEmail=getIntent().getExtras().getString("phoneNumber");

        idfromdatabase.setText(yourid);
        namefromdatabase.setText(yourname);
        phonenumberfromdatabase.setText(yourPhoneno);
        emailfromdatabase.setText(yourEmail);


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String todayDate = dateFormat.format(calendar.getTime());
        profiledate.setText(todayDate);
    }
}