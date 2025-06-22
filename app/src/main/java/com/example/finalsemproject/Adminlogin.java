package com.example.finalsemproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Adminlogin extends AppCompatActivity {
    TextView Adminforgottext;
    Button AdminloginButton;
    EditText Adminphonenologin, Adminpasswordlogin;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);

        Adminforgottext = findViewById(R.id.Adminforgottext);
        AdminloginButton = findViewById(R.id.AdminloginButton);
        Adminphonenologin = findViewById(R.id.Adminphonenologin);
        Adminpasswordlogin = findViewById(R.id.Adminpasswordlogin);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Admin");

        Adminforgottext.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), AdminForgotPassword.class);
            startActivity(i);
        });

        AdminloginButton.setOnClickListener(v -> {
            String phoneNumber = Adminphonenologin.getText().toString().trim();
            String password = Adminpasswordlogin.getText().toString().trim();

            if (phoneNumber.isEmpty() || !phoneNumber.matches("[3-9][0-9]{9}")) {
                showToast("Please enter a valid 10-digit phone number starting with a digit between 3-9");
                return;
            }

            if (password.isEmpty() || password.length() < 8 || password.length() > 12 ||
                    !password.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,12}$")) {
                showToast("Password must be between 8 and 12 characters and include at least one digit, one uppercase letter, one lowercase letter, and one special character");
                return;
            }

             databaseReference.orderByChild("phoneNumber").equalTo(phoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            User user = snapshot.getValue(User.class);
                            if (user != null && user.getPassword().equals(password)) {
                                Intent intent = new Intent(Adminlogin.this, AdminDash.class);
                                intent.putExtra("userId", user.getUserId());
                                intent.putExtra("name", user.getName());
                                intent.putExtra("email", user.getEmail());
                                intent.putExtra("phoneNumber", user.getPhoneNumber());
                                startActivity(intent);
                                return;
                            }
                        }
                        showToast("Invalid phone number or password");
                    } else {
                        showToast("Invalid phone number or password");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    showToast("Failed to login: " + databaseError.getMessage());
                }
            });
        });
    }
    private void showToast (String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
