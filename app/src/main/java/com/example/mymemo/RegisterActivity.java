package com.example.mymemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailField, passwordField;
    private Button registerButton;
    private UserDbManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailField = findViewById(R.id.email);
        passwordField = findViewById(R.id.password);
        registerButton = findViewById(R.id.registerButton);

        dbManager = new UserDbManager(this);
        dbManager.open(); // Membuka koneksi ke database

        registerButton.setOnClickListener(v -> {
            String email = emailField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Insert pengguna ke SQLite
            User user = new User(email, password);
            long result = dbManager.insertUser(user);
            if (result != -1) {
                Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show();
                finish(); // Kembali ke LoginActivity
            } else {
                Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.close(); // Menutup koneksi ke database
    }

    public void login(View view){
        Intent openLoginActivity = new Intent(this,Login.class);
        startActivity(openLoginActivity);
    }
}
