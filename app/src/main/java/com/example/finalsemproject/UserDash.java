package com.example.finalsemproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class UserDash extends AppCompatActivity {
    CardView aboutusCard, createpassCard, renewpassCard, updatepassCard, viewpassCard, passinfoCard;
    TextView usernameDisplay, datetext;
    ImageButton Profile, logOut;
    String yourid,yourname,yourPhoneno,yourEmail;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dash);
        logOut = findViewById(R.id.logOutB);
        Profile = findViewById(R.id.profileB);

        usernameDisplay = findViewById(R.id.usernameDisplay);
        datetext = findViewById(R.id.date);

        aboutusCard = findViewById(R.id.aboutuscard);
        createpassCard = findViewById(R.id.createpassCard);
        renewpassCard = findViewById(R.id.renewpassCard);
        updatepassCard = findViewById(R.id.updatepassCard);
        viewpassCard = findViewById(R.id.viewpassCard);
        passinfoCard = findViewById(R.id.passinfoCard);

        yourid= Objects.requireNonNull(getIntent().getExtras()).getString("userId");
        yourname=getIntent().getExtras().getString("name");
        yourEmail=getIntent().getExtras().getString("email");
        yourPhoneno=getIntent().getExtras().getString("phoneNumber");

        usernameDisplay.setText("Hello!  "+yourname);

        logOut.setOnClickListener(view -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UserDash.this);
            alertDialogBuilder.setTitle("Are you Sure! You want to logout ?");
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setNegativeButton("No", (dialogInterface, i) -> {
            });
            alertDialogBuilder.setNeutralButton("", (dialogInterface, i) -> {
            });
            alertDialogBuilder.setPositiveButton("Yes", (dialogInterface, which) -> {
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        });

        Profile.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ProfilePage.class);
            intent.putExtra("userId",yourid);
            intent.putExtra("name", yourname);
            intent.putExtra("email", yourEmail);
            intent.putExtra("phoneNumber", yourPhoneno);
            startActivity(intent);
        });

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String todayDate = dateFormat.format(calendar.getTime());
        datetext.setText(todayDate);

        aboutusCard.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), AboutUs.class);
            startActivity(i);
        });

        createpassCard.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), CreatePass.class);
            i.putExtra("name", yourname);
            i.putExtra("email", yourEmail);
            i.putExtra("phoneNumber", yourPhoneno);
            startActivity(i);
        });

        renewpassCard.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), RenewPass.class);
            i.putExtra("phoneNumber", yourPhoneno);
            startActivity(i);
        });

        updatepassCard.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), UpdatePass.class);
            i.putExtra("phoneNumber", yourPhoneno);
            startActivity(i);
        });

        viewpassCard.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), ViewPass.class);
            i.putExtra("phoneNumber", yourPhoneno);
            startActivity(i);
        });

        passinfoCard.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), PassInformation.class);
            startActivity(i);
        });

    }
}