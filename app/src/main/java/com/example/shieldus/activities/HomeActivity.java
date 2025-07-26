package com.example.shieldus.activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.shieldus.models.HomeItem;
import com.example.shieldus.R;
import com.example.shieldus.adapters.HomeAdapter;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private ArrayList<HomeItem> homeItems;
    private HomeAdapter adapter;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private MaterialToolbar toolbar;
    private boolean isAnonymous;
    private LinearLayout btnEducation, btnMap, btnEmergency, btnChatbot;
    private Button btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initViews();
        setupFunctionButtons();

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

        setupNavigationDrawer();
    }

    private void initViews() {
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        isAnonymous = getIntent().getBooleanExtra("isAnonymous", false);
        if (isAnonymous) {
            showAnonymousReminder();
        }
    }

    private void setupFunctionButtons() {
        btnEducation = findViewById(R.id.btnEducation);
        btnMap = findViewById(R.id.btnMap);
        btnEmergency = findViewById(R.id.btnEmergency);
        btnChatbot = findViewById(R.id.btnChatbot);

        addRippleEffect(btnEducation);
        addRippleEffect(btnMap);
        addRippleEffect(btnEmergency);
        addRippleEffect(btnChatbot);

        btnEducation.setOnClickListener(v -> startActivity(new Intent(this, EducationActivity.class)));
        btnMap.setOnClickListener(v -> startActivity(new Intent(this, MapActivity.class)));
        btnEmergency.setOnClickListener(v -> startActivity(new Intent(this, EmergencyActivity.class)));
        btnChatbot.setOnClickListener(v -> startActivity(new Intent(this, ChatbotActivity.class)));
    }

    private void addRippleEffect(View view) {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true);
        view.setBackgroundResource(typedValue.resourceId);
    }

    private void setupNavigationDrawer() {
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this::handleNavigationItemSelected);
    }

    private boolean handleNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
        }
        else if (id == R.id.nav_education) {
            startActivity(new Intent(this, EducationActivity.class));
        }
        else if (id == R.id.nav_map) {
          //  startActivity(new Intent(this, MapActivity.class));
        }
        else if (id == R.id.nav_emergency) {
           // startActivity(new Intent(this, EmergencyActivity.class));
        }
        else if (id == R.id.nav_settings) {
           // startActivity(new Intent(this, SettingsActivity.class));
        }
        else if (id == R.id.nav_logout) {
            performLogout();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void performLogout() {
        new AlertDialog.Builder(this)
                .setTitle("Esci")
                .setMessage("Sei sicuro di voler uscire dall'applicazione?")
                .setPositiveButton("Esci", (dialog, which) -> {
                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Annulla", null)
                .show();
    }

    private void showAnonymousReminder() {
        new AlertDialog.Builder(this)
                .setTitle("Modalità anonima")
                .setMessage("Ricorda: i tuoi progressi non verranno salvati in questa sessione.")
                .setPositiveButton("Ho capito", null)
                .show();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}