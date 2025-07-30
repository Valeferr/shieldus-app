package com.example.shieldus.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.shieldus.R;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnAnonymous = findViewById(R.id.btnAnonymous);
        TextView tvRegisterLink = findViewById(R.id.tvRegisterLink);

        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String savedEmail = prefs.getString("email", null);
        String savedPassword = prefs.getString("password", null);
        if (savedEmail != null) etEmail.setText(savedEmail);
        if (savedPassword != null) etPassword.setText(savedPassword);

        tvRegisterLink.setOnClickListener(v ->
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class))
        );

        btnLogin.setOnClickListener(v -> {
            String email = Objects.requireNonNull(etEmail.getText()).toString();
            String password = Objects.requireNonNull(etPassword.getText()).toString();

            String storedEmail = prefs.getString("email", null);
            String storedPassword = prefs.getString("password", null);

            if (email.equals(storedEmail) && password.equals(storedPassword)) {
                prefs.edit().putBoolean("isAnonymous", false).apply();

                Toast.makeText(this, "Accesso riuscito", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, HomeActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Credenziali errate", Toast.LENGTH_SHORT).show();
            }
        });

        btnAnonymous.setOnClickListener(v -> showAnonymousWarning());
    }

    private void showAnonymousWarning() {
        new AlertDialog.Builder(this)
                .setTitle("Accesso anonimo")
                .setMessage("In modalità anonima i progressi non saranno salvati. Continuare?")
                .setPositiveButton("Continua", (dialog, which) -> {
                    SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
                    prefs.edit().putBoolean("isAnonymous", true).apply();

                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Annulla", null)
                .setIcon(R.drawable.ic_warning)
                .show();
    }
}
