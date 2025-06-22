package com.example.finalsemproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class AdminDash extends AppCompatActivity {

    CardView viewPassessCard1,userscountdisplay1,passesscountdisplay1,viewUsersCard1;
    TextView AdminnameDisplay,date,userscountdisplay,passesscountdisplay;
    ImageButton profileB,logOutB;
    String yourid,yourname,yourPhoneno,yourEmail;
    DatabaseReference databaseReference,databaseReference1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dash);

        viewPassessCard1=findViewById(R.id.viewPassessCard1);
        userscountdisplay1=findViewById(R.id.userscountdisplay1);
        passesscountdisplay1=findViewById(R.id.passesscountdisplay1);
        viewUsersCard1=findViewById(R.id.viewUsersCard1);

        AdminnameDisplay=findViewById(R.id.AdminnameDisplay);
        date=findViewById(R.id.date);
        userscountdisplay=findViewById(R.id.userscountdisplay);
        passesscountdisplay=findViewById(R.id.passesscountdisplay);

        profileB=findViewById(R.id.profileB);
        logOutB=findViewById(R.id.logOutB);

        yourid= Objects.requireNonNull(getIntent().getExtras()).getString("userId");
        yourname=getIntent().getExtras().getString("name");
        yourEmail=getIntent().getExtras().getString("email");
        yourPhoneno=getIntent().getExtras().getString("phoneNumber");

        viewUsersCard1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), ViewUSERS.class);
                intent.putExtra("userId",yourid);
                intent.putExtra("name", yourname);
                intent.putExtra("email", yourEmail);
                intent.putExtra("phoneNumber", yourPhoneno);
                startActivity(intent);
            }
        });
        viewPassessCard1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), AdminViewPassess.class);
                intent.putExtra("userId",yourid);
                intent.putExtra("name", yourname);
                intent.putExtra("email", yourEmail);
                intent.putExtra("phoneNumber", yourPhoneno);
                startActivity(intent);
            }
        });
        profileB.setOnClickListener(v -> {
            Intent intent=new Intent(getApplicationContext(), AdminProfilePage.class);
            intent.putExtra("userId",yourid);
            intent.putExtra("name", yourname);
            intent.putExtra("email", yourEmail);
            intent.putExtra("phoneNumber", yourPhoneno);
            startActivity(intent);
        });

        logOutB.setOnClickListener(view -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AdminDash.this);
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

        AdminnameDisplay.setText(yourname);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String todayDate = dateFormat.format(calendar.getTime());
        date.setText(todayDate);

        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = 0;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Object userIdObj = ds.child("userId").getValue();
                    if (userIdObj != null) {
                        count++;
                    }
                }
                userscountdisplay.setText(String.valueOf(count));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled if needed
            }
        });

        databaseReference1 = FirebaseDatabase.getInstance().getReference("PMPMLPASSDATA");
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = 0;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Object userIdObj = ds.child("mobileNumber").getValue();
                    if (userIdObj != null) {
                        count++;
                    }
                }
                passesscountdisplay.setText(String.valueOf(count));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled if needed
            }
        });

    }
}