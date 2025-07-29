package com.example.shieldus.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AlertDialog;
import com.example.shieldus.R;
import androidx.core.view.GravityCompat;

public class EmergencyActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        setupNavigationDrawer();
        setToolbarTitle("Emergenza");

        setupEmergencyButtons();
    }

    private void setupEmergencyButtons() {
        int[] buttonIds = {
                R.id.btn_violenza_domestica,
                R.id.btn_supporto_psicologico,
                R.id.btn_emergenza_medica,
                R.id.btn_centri_antiviolenza,
                R.id.btn_chiamata_generica
        };

        for (int id : buttonIds) {
            Button btn = findViewById(id);
            btn.setOnClickListener(this::handleEmergencyClick);
        }
    }

    private void handleEmergencyClick(View v) {
        String number = "";
        String title = "";

        if (v.getId() == R.id.btn_centri_antiviolenza) {
            showCentersDialog();
            return;
        } else if (v.getId() == R.id.btn_chiamata_generica) {
            number = "112";
            title = getString(R.string.emergency_general_title);
        } else if (v.getId() == R.id.btn_emergenza_medica) {
            number = "118";
            title = getString(R.string.emergency_medical_title);
        } else if (v.getId() == R.id.btn_supporto_psicologico) {
            number = "800833833";
            title = getString(R.string.emergency_psychological_title);
        } else if (v.getId() == R.id.btn_violenza_domestica) {
            number = "1522";
            title = getString(R.string.emergency_violence_title);
        }

        showConfirmationDialog(title, number);
    }

    private void showCentersDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.emergency_centers_title)
                .setMessage(R.string.emergency_centers_message)
                .setPositiveButton(R.string.open_map, (dialog, which) -> {
                    Intent intent = new Intent(this, MapActivity.class);
                    intent.putExtra("show_centers", true);
                    startActivity(intent);
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    private void showConfirmationDialog(String title, String number) {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.confirm_call, title))
                .setMessage(getString(R.string.call_number, number))
                .setPositiveButton(R.string.call, (dialog, which) -> callNumber(number))
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    private void callNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_emergency) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
        return super.onNavigationItemSelected(item);
    }
}