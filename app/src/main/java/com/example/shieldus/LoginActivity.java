package com.example.shieldus;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView tvRegisterLink = findViewById(R.id.tvRegisterLink);
        tvRegisterLink.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });

        String emailFromRegistration = getIntent().getStringExtra("email");
        if (emailFromRegistration != null) {
            TextInputEditText etEmail = findViewById(R.id.etEmail);
            etEmail.setText(emailFromRegistration);
        }

        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnAnonymous = findViewById(R.id.btnAnonymous);

        // TODO: Inserire la logica di autenticazione
//        btnLogin.setOnClickListener(v -> {
//            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//            intent.putExtra("isAnonymous", false);
//            startActivity(intent);
//            finish();
//        });

        btnAnonymous.setOnClickListener(v -> showAnonymousWarning());
    }

    private void showAnonymousWarning() {
//        new AlertDialog.Builder(this)
//                .setTitle("Accesso anonimo")
//                .setMessage("Attenzione: in modalità anonima i tuoi progressi nei moduli non verranno salvati. Vuoi continuare?")
//                .setPositiveButton("Continua", (dialog, which) -> {
//                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//                    intent.putExtra("isAnonymous", true);
//                    startActivity(intent);
//                    finish();
//                })
//                .setNegativeButton("Annulla", null)
//                .setIcon(R.drawable.ic_warning)
//                .show();
    }
}