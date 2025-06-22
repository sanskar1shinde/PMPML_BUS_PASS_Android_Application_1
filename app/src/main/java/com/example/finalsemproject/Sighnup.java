package com.example.finalsemproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Sighnup extends AppCompatActivity {
    private TextInputEditText nameEditText, phoneNumberEditText, emailEditText, passwordEditText, rePasswordEditText;
    private Button signupButton;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sighnup);

        nameEditText = findViewById(R.id.name);
        phoneNumberEditText = findViewById(R.id.phonenumber);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        rePasswordEditText = findViewById(R.id.repassword);
        signupButton = findViewById(R.id.signupbtn);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");

        signupButton.setOnClickListener(v -> {
            String name = Objects.requireNonNull(nameEditText.getText()).toString().trim();
            String phoneNumber = Objects.requireNonNull(phoneNumberEditText.getText()).toString().trim();
            String email = Objects.requireNonNull(emailEditText.getText()).toString().trim();
            String password = Objects.requireNonNull(passwordEditText.getText()).toString().trim();
            String rePassword = Objects.requireNonNull(rePasswordEditText.getText()).toString().trim();

            if (name.isEmpty()) {
                showToast("Please enter your name");
                return;
            }

            if (phoneNumber.isEmpty() || !phoneNumber.matches("[3-9][0-9]{9}")) {
                showToast("Please enter a valid 10-digit phone number starting with a digit between 3-9");
                return;
            }

            if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                showToast("Please enter a valid email address");
                return;
            }

            if (password.isEmpty() || password.length() < 8 || password.length() > 12 ||
                    !password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,12}$")) {
                showToast("Password must be between 8 and 12 characters and include at least one digit, one uppercase letter, one lowercase letter, and one special character");
                return;
            }

            if (!password.equals(rePassword)) {
                showToast("Passwords do not match");
                return;
            }

            databaseReference.orderByChild("phoneNumber").equalTo(phoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        showToast("Your account is already created");
                    } else {
                        String userId = generateUserId();
                        User user = new User(userId, name, phoneNumber, email, password);
                        databaseReference.child(userId).setValue(user);
                        showToast("Account created successfully");
                        startActivity(new Intent(Sighnup.this, Login.class));
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    showToast("Failed to create account");
                }
            });
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private String generateUserId() {
        String alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "0123456789";
        StringBuilder userIdBuilder = new StringBuilder();

        for (int i = 0; i < 3; i++) {
            userIdBuilder.append(alphabets.charAt((int) (Math.random() * alphabets.length())));
        }

        for (int i = 0; i < 4; i++) {
            userIdBuilder.append(numbers.charAt((int) (Math.random() * numbers.length())));
        }

        return userIdBuilder.toString();
    }
}
