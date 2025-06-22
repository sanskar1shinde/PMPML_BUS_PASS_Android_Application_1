package com.example.finalsemproject;

import android.annotation.SuppressLint;
 import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

 import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class ViewPass extends AppCompatActivity {

    TextView tvViewPassName, tvViewPassID, tvViewPassAge, tvViewPassGender, tvViewPassRoute,
            tvViewPassPassType, tvViewPassStartDate, tvViewPassEndDate, tvViewPassAmount;
    Button btnDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pass);

        try {
            tvViewPassName = findViewById(R.id.tvViewPassName);
            tvViewPassID = findViewById(R.id.tvViewPassID);
            tvViewPassAge = findViewById(R.id.tvViewPassAge);
            tvViewPassGender = findViewById(R.id.tvViewPassGender);
            tvViewPassRoute = findViewById(R.id.tvViewPassRoute);
            tvViewPassPassType = findViewById(R.id.tvViewPassPassType);
            tvViewPassStartDate = findViewById(R.id.tvViewPassStartDate);
            tvViewPassEndDate = findViewById(R.id.tvViewEndDate);
            tvViewPassAmount = findViewById(R.id.tvViewPassAmount);
            btnDownload = findViewById(R.id.btnDownload);

            String mobileNumber = Objects.requireNonNull(getIntent().getExtras()).getString("phoneNumber");
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference userRef = database.getReference("PMPMLPASSDATA");

            userRef.orderByChild("mobileNumber").equalTo(mobileNumber).addListenerForSingleValueEvent(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            PMPMLTable userData = snapshot.getValue(PMPMLTable.class);
                            if (userData != null) {
                                tvViewPassName.setText(userData.getName());
                                String aadharNumber = userData.getAadharNumber();
                                String lastFourDigits = aadharNumber.substring(aadharNumber.length() - 4);
                                tvViewPassID.setText(lastFourDigits);
                                tvViewPassRoute.setText(userData.getBusRoute());
                                tvViewPassPassType.setText(userData.getPassType());
                                tvViewPassStartDate.setText(userData.getStartDate());
                                tvViewPassEndDate.setText(userData.getEndDate());
                                tvViewPassAmount.setText(String.valueOf(userData.getPassAmount()));
                                tvViewPassGender.setText(userData.getGender());
                                tvViewPassAge.setText(String.valueOf(userData.getAge()));

                                btnDownload.setOnClickListener(v -> captureScreenshot());
                            }
                        }
                    } else {
                        tvViewPassName.setText("None");
                        tvViewPassID.setText("None");
                        tvViewPassRoute.setText("None");
                        tvViewPassPassType.setText("None");
                        tvViewPassStartDate.setText("None");
                        tvViewPassEndDate.setText("None");
                        tvViewPassAmount.setText("None");
                        tvViewPassGender.setText("None");
                        tvViewPassAge.setText("None");
                        Toast.makeText(ViewPass.this, "You Haven't Issued Any Pass, Please Issue A Pass", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(ViewPass.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
        }
    }

    private void captureScreenshot() {
        try {
            View rootView = getWindow().getDecorView().getRootView();
            rootView.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(rootView.getDrawingCache());
            rootView.setDrawingCacheEnabled(false);

            File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = "Screenshot_" + timeStamp + ".jpg";

            File file = new File(directory, fileName);

            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();

            Toast.makeText(this, "Screenshot saved to: " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error capturing screenshot", Toast.LENGTH_SHORT).show();
        }
    }
}
