package com.example.shieldus.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.shieldus.R;

public class HomeActivity extends BaseActivity {

    private LinearLayout btnEducation, btnMap, btnEmergency, btnChatbot;
    private Button btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Setup navigation drawer
        setupNavigationDrawer();
        setToolbarTitle("ShieldUs");

        initViews();
        setupFunctionButtons();
    }

    private void initViews() {
        btnExit = findViewById(R.id.btnExit);
        btnExit.setOnClickListener(v -> {
            String googleUrl = "https://www.google.com";
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(googleUrl));

            if (browserIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(browserIntent);
            } else {
                Toast.makeText(this, "Nessun browser disponibile", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupFunctionButtons() {
        btnEducation = findViewById(R.id.btnEducation);
        btnMap = findViewById(R.id.btnMap);
        btnEmergency = findViewById(R.id.btnEmergency);
        btnChatbot = findViewById(R.id.btnChatbot);

        btnEducation.setOnClickListener(v -> startActivity(new Intent(this, EducationActivity.class)));
        btnMap.setOnClickListener(v -> startActivity(new Intent(this, MapActivity.class)));
        btnEmergency.setOnClickListener(v -> startActivity(new Intent(this, EmergencyActivity.class)));
        btnChatbot.setOnClickListener(v -> startActivity(new Intent(this, ChatbotActivity.class)));
    }
}