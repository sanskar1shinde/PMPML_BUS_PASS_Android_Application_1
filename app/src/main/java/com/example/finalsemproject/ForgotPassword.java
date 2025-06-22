package com.example.finalsemproject;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ForgotPassword extends AppCompatActivity {

    TextInputEditText phoneno, password, repassword;
    Button updateButton;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        phoneno = findViewById(R.id.forgotphoneno);
        password = findViewById(R.id.passwordforgot);
        repassword = findViewById(R.id.repasswordforgot);
        updateButton = findViewById(R.id.updateButton);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");

        updateButton.setOnClickListener(v -> {
            String phoneNumber = Objects.requireNonNull(phoneno.getText()).toString().trim();
            String newPassword = Objects.requireNonNull(password.getText()).toString().trim();
            String reEnteredPassword = Objects.requireNonNull(repassword.getText()).toString().trim();

            if (phoneNumber.isEmpty() || !phoneNumber.matches("[3-9][0-9]{9}")) {
                showToast("Please enter a valid 10-digit phone number starting with a digit between 3-9");
                return;
            }

            if (newPassword.isEmpty() || newPassword.length() < 8 || newPassword.length() > 12 ||
                    !newPassword.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,12}$")) {
                showToast("Password must be between 8 and 12 characters and include at least one digit, one uppercase letter, one lowercase letter, and one special character");
                return;
            }

            if (!newPassword.equals(reEnteredPassword)) {
                showToast("Passwords do not match");
                return;
            }

            databaseReference.orderByChild("phoneNumber").equalTo(phoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            User user = snapshot.getValue(User.class);
                            if (user != null) {
                                if (newPassword.equals(user.getPassword())) {
                                    showToast("Try with a different password");
                                    return;
                                }

                                user.setPassword(newPassword);
                                databaseReference.child(user.getUserId()).setValue(user);
                                showToast("Password updated successfully");
                                finish();
                                return;
                            }
                        }
                        showToast("User not found");
                    } else {
                        showToast("User not found");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    showToast("Failed to update password");
                }
            });
        });

    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
