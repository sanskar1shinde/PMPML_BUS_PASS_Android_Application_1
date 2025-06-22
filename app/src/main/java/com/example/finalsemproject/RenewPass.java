package com.example.finalsemproject;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.Objects;

public class RenewPass extends AppCompatActivity {

    TextView tvRenewPassName, tvRenewPassPassID, tvRouteRenewPass, tvPassTypeRenewPass, tvStartDaterenewPass, tvEndDaterenewPass, tvPassAmountRenewPass;
    Button btnSubmitRenewPass;
    String PassAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renew_pass);

        // Initialize TextViews and Button
        tvRenewPassName = findViewById(R.id.tvrenewpassname);
        tvRenewPassPassID = findViewById(R.id.tvrenwpasspassID);
        tvRouteRenewPass = findViewById(R.id.tvrouterenewPass);
        tvPassTypeRenewPass = findViewById(R.id.tvpasstyperenewpass);
        tvStartDaterenewPass = findViewById(R.id.tvStartDaterenewpass);
        tvEndDaterenewPass = findViewById(R.id.tvEndDaterenewpass);
        tvPassAmountRenewPass = findViewById(R.id.tvPassAmountrenewpass);
        btnSubmitRenewPass = findViewById(R.id.btnSubmitrenewpass);

        String mobileNumber = Objects.requireNonNull(getIntent().getExtras()).getString("phoneNumber");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("PMPMLPASSDATA");

        userRef.orderByChild("mobileNumber").equalTo(mobileNumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        PMPMLTable userData = snapshot.getValue(PMPMLTable.class);
                        if (userData != null) {
                            tvRenewPassName.setText(userData.getName());
                            String aadharNumber = userData.getAadharNumber();
                            String lastFourDigits = aadharNumber.substring(Math.max(0, aadharNumber.length() - 4));
                            tvRenewPassPassID.setText(lastFourDigits);
                            tvRouteRenewPass.setText(userData.getBusRoute());
                            tvPassTypeRenewPass.setText(userData.getPassType());
                            tvStartDaterenewPass.setText(userData.getStartDate());
                            tvEndDaterenewPass.setText(userData.getEndDate());
                            tvPassAmountRenewPass.setText(String.valueOf(userData.getPassAmount()));

                            PassAmount= String.valueOf(userData.getPassAmount());

                            btnSubmitRenewPass.setOnClickListener(v -> {
                                Intent i = new Intent(getApplicationContext(), PaymentActivity.class);
                                i.putExtra("Amount",PassAmount);
                                startActivity(i);
                            });
                        }
                    }
                } else {
                    Toast.makeText(RenewPass.this, "No user data found for the provided phone number", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(RenewPass.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
