package com.example.shieldus.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shieldus.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText etName, etEmail, etPassword, etConfirmPassword;
    private TextInputLayout nameLayout, emailLayout, passwordLayout, confirmPasswordLayout;
    private Button btnRegister;
    private TextView tvLoginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        nameLayout = findViewById(R.id.nameLayout);
        emailLayout = findViewById(R.id.emailLayout);
        passwordLayout = findViewById(R.id.passwordLayout);
        confirmPasswordLayout = findViewById(R.id.confirmPasswordLayout);

        btnRegister = findViewById(R.id.btnRegister);
        tvLoginLink = findViewById(R.id.tvLoginLink);

        // Setup listeners
        setupTextWatchers();
        setupRegisterButton();
        setupLoginLink();
    }

    private void setupTextWatchers() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateFields();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        etName.addTextChangedListener(textWatcher);
        etEmail.addTextChangedListener(textWatcher);
        etPassword.addTextChangedListener(textWatcher);
        etConfirmPassword.addTextChangedListener(textWatcher);
    }

    private void setupRegisterButton() {
        btnRegister.setOnClickListener(v -> attemptRegistration());
    }

    private void setupLoginLink() {
        tvLoginLink.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void validateFields() {
        // Nome
        if (etName.getText().toString().trim().isEmpty()) {
            nameLayout.setError("Inserisci il tuo nome");
        } else {
            nameLayout.setError(null);
        }

        // Email
        String email = etEmail.getText().toString().trim();
        if (email.isEmpty()) {
            emailLayout.setError("Inserisci un'email");
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailLayout.setError("Email non valida");
        } else {
            emailLayout.setError(null);
        }

        // Password
        String password = etPassword.getText().toString();
        if (password.isEmpty()) {
            passwordLayout.setError("Inserisci una password");
        } else if (password.length() < 6) {
            passwordLayout.setError("Almeno 6 caratteri");
        } else {
            passwordLayout.setError(null);
        }

        // Conferma password
        String confirmPassword = etConfirmPassword.getText().toString();
        if (!confirmPassword.equals(etPassword.getText().toString())) {
            confirmPasswordLayout.setError("Le password non coincidono");
        } else {
            confirmPasswordLayout.setError(null);
        }
    }

    private boolean areFieldsValid() {
        return nameLayout.getError() == null &&
                emailLayout.getError() == null &&
                passwordLayout.getError() == null &&
                confirmPasswordLayout.getError() == null &&
                !etName.getText().toString().trim().isEmpty() &&
                !etEmail.getText().toString().trim().isEmpty() &&
                !etPassword.getText().toString().isEmpty() &&
                !etConfirmPassword.getText().toString().isEmpty();
    }

    private void attemptRegistration() {
        validateFields();

        if (areFieldsValid()) {
            showRegistrationSuccessDialog();
        } else {
            Toast.makeText(this, "Correggi gli errori nel form", Toast.LENGTH_SHORT).show();
        }
    }

    private void showRegistrationSuccessDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Registrazione completata")
                .setMessage("Il tuo account è stato creato con successo! Ora puoi accedere con le tue credenziali.")
                .setPositiveButton("Accedi", (dialog, which) -> {
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    intent.putExtra("email", etEmail.getText().toString().trim());
                    startActivity(intent);
                    finish();
                })
                .setCancelable(false)
                .show();
    }
}