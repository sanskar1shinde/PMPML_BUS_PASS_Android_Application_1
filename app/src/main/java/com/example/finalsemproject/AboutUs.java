package com.example.finalsemproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class AboutUs extends AppCompatActivity {
    ViewFlipper viewFlipper;
    TextView websitelink,phonelink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        viewFlipper = findViewById(R.id.viewFlipper);
        websitelink=findViewById(R.id.website);
        phonelink=findViewById(R.id.phonelink);
        // Set the animation for view flipper
        viewFlipper.setInAnimation(this, android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this, android.R.anim.slide_out_right);

        // Set flip interval in milliseconds
        viewFlipper.setFlipInterval(2000);

        // Start flipping
        viewFlipper.startFlipping();

        websitelink.setOnClickListener(v -> {
            String url ="https://pmpml.org/";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            try {
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),"Something Wrong...",Toast.LENGTH_LONG).show();
            }
        });

        phonelink.setOnClickListener(v -> {
            String phoneNumber = phonelink.getText().toString();
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(intent);
        });
    }
    public void onPress(View view) {
        String pdfFile = "https://pmpml.org/assets/media/organization_structure.pdf";

        // Create an intent to open the PDF file
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(pdfFile), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // Handle the case where a PDF viewer app is not installed
            Toast.makeText(getApplicationContext(), "No PDF viewer app installed", Toast.LENGTH_SHORT).show();
        }
    }
}