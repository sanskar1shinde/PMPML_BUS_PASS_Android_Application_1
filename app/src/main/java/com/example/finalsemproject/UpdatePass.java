package com.example.finalsemproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UpdatePass extends AppCompatActivity {

    TextView tvEndDate, tvPassAmount, tvupdatepassname, UpdatePassID;
    Button btnSubmitUpdatePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pass);

        String mobileNumber = getIntent().getExtras().getString("phoneNumber");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("PMPMLPASSDATA");


        Spinner spinnerPassType = findViewById(R.id.spinnerPassTypeUpdatePass);
        TextView tvStartDate = findViewById(R.id.tvStartDateUpdatePass);
        tvPassAmount = findViewById(R.id.tvPassAmountUpdatePass);
        tvEndDate = findViewById(R.id.tvEndDateUpdatePass);
        btnSubmitUpdatePass = findViewById(R.id.btnSubmitUpdatePass);
        tvupdatepassname = findViewById(R.id.tvupdatepassname);
        UpdatePassID = findViewById(R.id.UpdatePassID);

        // Spinner configurations
        String[] routes = {
                "Within PCMC & PMC",
                "Within PMC",
                "Within PCMC",
                "All Route",
                "Bhosari to Nigdi",
                "Bhosari to Katraj",
                "Nigdi to Hadapsar via Visharantwadi",
                "Nigdi to Hadapsar",
                "Bhaktishakti to Manapa",
                "Bhaktishakti to Swargate",
                "Aalandi to Katraj",
                "Bhaktishakti to Lonavala",
                "Hadapsar to Alandi",
                "Manapa to Bhosari",
                "Manapa to Narayangaon",
                "Bhakti Shakti to Hinjewadi phase 3",
                "Hinjewadi to Alandi",
                "Narayangaon to Pune Station",
                "Swargate to Sinhagad",
                "Bhakti Shakti to Katraj",
                "Shivajinagar to Hadapsar",
                "Manapa to Swargate",
                "Hinjewadi Maan phase 3 to Katraj",
                "Chinchwad to Hadapsar",
                "Manapa to Chinchwad",
                "Alandi to Jambegaon"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, routes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinnerBusRoute = findViewById(R.id.spinnerBusRouteUpdatePass);
        spinnerBusRoute.setAdapter(adapter);


        // Set today's date as the start date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String todayDate = dateFormat.format(new Date());
        tvStartDate.setText(todayDate);

        String[] passTypes = {
                "Within PCMC & PMC",
                "Within PMC",
                "Within PCMC",
                "Monthly All Route",
                "Monthly ",
                "Quaterly ",
                "Half Yearly ",
                "Yearly ",
                "Students monthly ",
                "Students All Route Monthly ",
                "Students Quaterly",
                "Students Half Yearly ",
                "Students Yearly ",
                "Senior Citizen ",

        };

        ArrayAdapter<String> adapterPasstype = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, passTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPassType.setAdapter(adapterPasstype);

        spinnerPassType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                calculateEndDateAndPassAmount(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }


            public void calculateEndDateAndPassAmount(int passTypePosition) {
                int[] passAmounts = {50, 40, 40, 1200, 1000, 3000, 5700, 11400, 600, 750, 3000, 5000, 7500, 300};
                int[] passDurations = {1, 1, 1, 30, 30, 90, 180, 365, 30, 1, 30, 180, 365, 30};

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());

                int duration = passDurations[passTypePosition];
                if (duration == 1) { // Daily pass
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                } else if (duration == 30) { // Monthly pass
                    calendar.add(Calendar.MONTH, 1);
                } else if (duration == 90) { // 3-Month pass
                    calendar.add(Calendar.MONTH, 3);
                } else if (duration == 180) { // 6-Month pass
                    calendar.add(Calendar.MONTH, 6);
                } else if (duration == 365) { // Yearly pass
                    calendar.add(Calendar.YEAR, 1);
                }

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String endDate = dateFormat.format(calendar.getTime());
                tvEndDate.setText(endDate);

                int passAmount = passAmounts[passTypePosition];
                tvPassAmount.setText(String.valueOf(passAmount));
            }

        });

        userRef.orderByChild("mobileNumber").equalTo(mobileNumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        PMPMLTable userData = snapshot.getValue(PMPMLTable.class);
                        if (userData != null) {
                            tvupdatepassname.setText(userData.getName());
                            String aadharNumber = userData.getAadharNumber();
                            String lastFourDigits = aadharNumber.substring(aadharNumber.length() - 4);
                            UpdatePassID.setText(lastFourDigits);

                            btnSubmitUpdatePass.setOnClickListener(v -> {
                                String busRoute1 = spinnerBusRoute.getSelectedItem().toString();
                                String passType1 = spinnerPassType.getSelectedItem().toString();
                                String startDate1 = tvStartDate.getText().toString().trim();
                                String endDate1 = tvEndDate.getText().toString().trim();
                                int passAmount1 = Integer.parseInt(tvPassAmount.getText().toString().trim());

                                // Update userData with new data
                                snapshot.getRef().child("busRoute").setValue(busRoute1);
                                snapshot.getRef().child("passType").setValue(passType1);
                                snapshot.getRef().child("startDate").setValue(startDate1);
                                snapshot.getRef().child("endDate").setValue(endDate1);
                                snapshot.getRef().child("passAmount").setValue(passAmount1);

                                // Show toast to indicate successful update

                                // Start PaymentActivity with updated data
                                Intent i = new Intent(getApplicationContext(), PaymentActivity.class);
                                i.putExtra("Amount", String.valueOf(passAmount1)); // Use the new pass amount
                                startActivity(i);
                            });
                        }
                    }
                } else {
                    Toast.makeText(UpdatePass.this, "No user data found for the provided phone number", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UpdatePass.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}