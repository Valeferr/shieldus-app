package com.example.shieldus.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import com.example.shieldus.R;

public class HomeActivity extends BaseActivity {
    private LinearLayout btnEducation, btnMap, btnEmergency, btnChatbot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupNavigationDrawer();
        setToolbarTitle("ShieldUs");

        initViews();
        setupFunctionButtons();
    }

    private void initViews() {
        btnEducation = findViewById(R.id.btnEducation);
        btnMap = findViewById(R.id.btnMap);
        btnEmergency = findViewById(R.id.btnEmergency);
        btnChatbot = findViewById(R.id.btnChatbot);
    }

    private void setupFunctionButtons() {
        btnEducation.setOnClickListener(v -> startActivity(new Intent(this, EducationActivity.class)));
        btnMap.setOnClickListener(v -> startActivity(new Intent(this, MapActivity.class)));
        btnEmergency.setOnClickListener(v -> startActivity(new Intent(this, EmergencyActivity.class)));
        btnChatbot.setOnClickListener(v -> startActivity(new Intent(this, ChatbotActivity.class)));
    }
}