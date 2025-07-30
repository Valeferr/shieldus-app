package com.example.shieldus.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import com.example.shieldus.R;
import com.example.shieldus.fragments.ArticlesFragment;

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
        setupArticles();
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

    private void setupArticles() {
        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            ArticlesFragment fragment = new ArticlesFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.articlesContainer, fragment)
                    .commitNow();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Errore nel caricamento degli articoli", Toast.LENGTH_SHORT).show();
        }
    }

}