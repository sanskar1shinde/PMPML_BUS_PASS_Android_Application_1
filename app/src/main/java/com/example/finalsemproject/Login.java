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

public class Login extends AppCompatActivity {
    TextView forgottext;
    Button createaccount, loginButton,admintextbutton;
    EditText phoneNumberEditText, passwordEditText;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        forgottext = findViewById(R.id.forgottext);
        createaccount = findViewById(R.id.createaccount);
        loginButton = findViewById(R.id.loginButton);
        phoneNumberEditText = findViewById(R.id.phonenologin);
        passwordEditText = findViewById(R.id.passwordlogin);
        admintextbutton=findViewById(R.id.admintextbutton);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");

        admintextbutton.setOnClickListener(v -> {
            Intent i=new Intent(getApplicationContext(),Adminlogin.class);
            startActivity(i);
        });

        forgottext.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), ForgotPassword.class);
            startActivity(i);
        });

        createaccount.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), Sighnup.class);
            startActivity(i);
        });

        loginButton.setOnClickListener(v -> {
            String phoneNumber = phoneNumberEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (phoneNumber.isEmpty() || !phoneNumber.matches("[3-9][0-9]{9}")) {
                showToast("Please enter a valid 10-digit phone number starting with a digit between 3-9");
                return;
            }

            if (password.isEmpty() || password.length() < 8 || password.length() > 12 ||
                    !password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,12}$")) {
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
                                Intent intent = new Intent(Login.this, UserDash.class);
                                intent.putExtra("userId", user.getUserId());
                                intent.putExtra("name", user.getName());
                                intent.putExtra("email", user.getEmail());
                                intent.putExtra("phoneNumber", user.getPhoneNumber());
                                startActivity(intent);
                                finish();
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
                    showToast("Failed to login");
                }
            });
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
