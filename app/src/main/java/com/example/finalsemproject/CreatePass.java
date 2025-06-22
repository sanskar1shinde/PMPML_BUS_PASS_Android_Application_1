package com.example.finalsemproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
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
import java.util.Objects;

public class CreatePass extends AppCompatActivity {

    private TextView tvEndDate;
    EditText etAge, etWorkplace, etWorkplaceAddress, etAadharNumber;

    Button btnSubmit, btnReset;
    TextView tvPassAmount, etName;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pass);

        Spinner spinnerPassType = findViewById(R.id.spinnerPassType);
        TextView tvStartDate = findViewById(R.id.tvStartDate);
        tvPassAmount = findViewById(R.id.tvPassAmount);
        tvEndDate = findViewById(R.id.tvEndDate);

        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        etWorkplace = findViewById(R.id.etWorkplace);
        etWorkplaceAddress = findViewById(R.id.etWorkplaceAddress);
        etAadharNumber = findViewById(R.id.etAadharNumber);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnReset = findViewById(R.id.btnReset);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(CreatePass.this,CreatePass.class);
                startActivity(i);
            }
        });

        String yourname = Objects.requireNonNull(getIntent().getExtras()).getString("name");
        etName.setText(yourname);

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
        Spinner spinnerBusRoute = findViewById(R.id.spinnerBusRoute);
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
        btnSubmit.setOnClickListener(view -> {
            if (validateInputs()) {
                String name = etName.getText().toString().trim();
                int age = Integer.parseInt(etAge.getText().toString().trim());
                String gender = getSelectedGender();
                String workplace = etWorkplace.getText().toString().trim();
                String workplaceAddress = etWorkplaceAddress.getText().toString().trim();
                String aadharNumber = etAadharNumber.getText().toString().trim();
                String mobileNumber = getIntent().getExtras().getString("phoneNumber");
                String email = getIntent().getExtras().getString("email");
                String busRoute = spinnerBusRoute.getSelectedItem().toString();
                String passType = spinnerPassType.getSelectedItem().toString();
                String startDate = tvStartDate.getText().toString().trim();
                String endDate = tvEndDate.getText().toString().trim();
                int passAmount = Integer.parseInt(tvPassAmount.getText().toString().trim());

                // Check if pass already exists for the given mobile number
                DatabaseReference passReference = FirebaseDatabase.getInstance().getReference("PMPMLPASSDATA");
                passReference.orderByChild("mobileNumber").equalTo(mobileNumber).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // Pass already exists, show toast message
                            Toast.makeText(CreatePass.this, "You already have a pass. You can update your pass.", Toast.LENGTH_SHORT).show();
                        } else {
                            // Pass does not exist, create new pass
                            PMPMLTable pmpmlTable = new PMPMLTable(name, age, gender, workplace, workplaceAddress,
                                    aadharNumber, mobileNumber, email, busRoute, passType, startDate, endDate, passAmount);
                            String passAmountFinal= String.valueOf(passAmount);
                            databaseReference = FirebaseDatabase.getInstance().getReference("PMPMLPASSDATA");
                            String key = databaseReference.push().getKey();
                            if (key != null) {
                                databaseReference.child(key).setValue(pmpmlTable);
                                Intent intent = new Intent(CreatePass.this, PaymentActivity.class);
                                intent.putExtra("Amount", passAmountFinal);
                                startActivity(intent);
                             } else {
                                Toast.makeText(CreatePass.this, "Failed to create pass", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(CreatePass.this, "Error checking pass status", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
        private String getSelectedGender() {
        RadioGroup genderRadioGroup = findViewById(R.id.radioGroupGender);
        int selectedId = genderRadioGroup.getCheckedRadioButtonId();
        if (selectedId == R.id.radioButtonMale) {
            return "Male";
        } else{
            return "Female";

        }
    }
    private boolean validateInputs() {
        String name = etName.getText().toString().trim();
        int age;
        try {
            age = Integer.parseInt(etAge.getText().toString().trim());
            if (age < 5 || age > 99) {
                Toast.makeText(this, "Age should be between 5 and 99.", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid age format.", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate gender (Assuming a gender selection elsewhere in the UI)

        String workplace = etWorkplace.getText().toString().trim();
        if (!workplace.matches("[a-zA-Z ]+")) {
            Toast.makeText(this, "Workplace should contain only letters and spaces.", Toast.LENGTH_SHORT).show();
            return false;
        }

        String workplaceAddress = etWorkplaceAddress.getText().toString().trim();
        if (workplaceAddress.isEmpty()) {
            Toast.makeText(this, "Workplace address is mandatory.", Toast.LENGTH_SHORT).show();
            return false;
        }

        String aadharNumber = etAadharNumber.getText().toString().trim();
        if (!aadharNumber.matches("\\d{12}")) {
            Toast.makeText(this, "Aadhar number should be 12 digits.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

}
