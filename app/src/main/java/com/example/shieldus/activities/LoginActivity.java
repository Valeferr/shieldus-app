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
import com.example.shieldus.utils.Utils;

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
        if (savedEmail != null) {
            etEmail.setText(savedEmail);
        }

        tvRegisterLink.setOnClickListener(v ->
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class))
        );

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Inserisci email e password", Toast.LENGTH_SHORT).show();
                return;
            }

            if (isValidUser(email, password)) {
                prefs.edit()
                        .putString("email", email)
                        .putBoolean("isAnonymous", false)
                        .apply();

                Toast.makeText(this, "Accesso riuscito", Toast.LENGTH_SHORT).show();
                goToHome(email);
            } else {
                Toast.makeText(this, "Credenziali errate", Toast.LENGTH_SHORT).show();
            }
        });

        btnAnonymous.setOnClickListener(v -> showAnonymousWarning());
    }

    private boolean isValidUser(String email, String password) {
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);

        String storedHash = prefs.getString("password_" + email, null);
        if (storedHash == null) return false;

        String hashedInput = Utils.hash(password);
        return hashedInput.equals(storedHash);
    }

    private void goToHome(String userId) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("USER_ID", userId);
        intent.putExtra("IS_ANONYMOUS", false);
        startActivity(intent);
        finish();
    }

    private void showAnonymousWarning() {
        new AlertDialog.Builder(this)
                .setTitle("Accesso anonimo")
                .setMessage("In modalità anonima i progressi non saranno salvati. Continuare?")
                .setPositiveButton("Continua", (dialog, which) -> {
                    SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
                    String anonId = "anonymous_" + System.currentTimeMillis();
                    prefs.edit()
                            .putString("current_user_id", anonId)
                            .putBoolean("isAnonymous", true)
                            .apply();

                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.putExtra("USER_ID", anonId);
                    intent.putExtra("IS_ANONYMOUS", true);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Annulla", null)
                .setIcon(R.drawable.ic_warning)
                .show();
    }
}
