package com.example.shieldus.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.shieldus.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public abstract class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected DrawerLayout drawerLayout;
    protected NavigationView navigationView;
    protected MaterialToolbar toolbar;
    protected boolean isAnonymous;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected void setupNavigationDrawer() {
        // Inizializza le view
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        isAnonymous = getIntent().getBooleanExtra("isAnonymous", false);
        if (isAnonymous) {
            showAnonymousReminder();
        }

        updateNavHeader();
    }

    protected void updateNavHeader() {
        View headerView = navigationView.getHeaderView(0);
        TextView textViewEmail = headerView.findViewById(R.id.textViewEmail);

        // Qui puoi impostare l'email dell'utente loggato
        // Esempio: textViewEmail.setText(user.getEmail());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home && !(this instanceof HomeActivity)) {
            startActivity(new Intent(this, HomeActivity.class));
        }
        else if (id == R.id.nav_education && !(this instanceof EducationActivity)) {
            startActivity(new Intent(this, EducationActivity.class));
        }
//        else if (id == R.id.nav_map && !(this instanceof MapActivity)) {
//            startActivity(new Intent(this, MapActivity.class));
//        }
//        else if (id == R.id.nav_emergency && !(this instanceof EmergencyActivity)) {
//            startActivity(new Intent(this, EmergencyActivity.class));
//        }
//        else if (id == R.id.nav_settings && !(this instanceof SettingsActivity)) {
//            startActivity(new Intent(this, SettingsActivity.class));
//        }
        else if (id == R.id.nav_logout) {
            performLogout();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void performLogout() {
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

    protected void showAnonymousReminder() {
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

    protected void setToolbarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }
}