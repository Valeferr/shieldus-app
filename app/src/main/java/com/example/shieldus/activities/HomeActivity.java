package com.example.shieldus.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.shieldus.models.HomeItem;
import com.example.shieldus.R;
import com.example.shieldus.adapters.HomeAdapter;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private GridView gridView;
    private ArrayList<HomeItem> homeItems;
    private HomeAdapter adapter;

    private boolean isAnonymous;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        isAnonymous = getIntent().getBooleanExtra("isAnonymous", false);
        if (isAnonymous) {
            showAnonymousReminder();
        }

        gridView = findViewById(R.id.gridView);
        createHomeItems();

        adapter = new HomeAdapter(this, homeItems);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HomeItem selectedItem = homeItems.get(position);
                navigateToSection(selectedItem.getTitle());
            }
        });
    }

    private void showAnonymousReminder() {
        new AlertDialog.Builder(this)
                .setTitle("Modalità anonima")
                .setMessage("Ricorda: i tuoi progressi non verranno salvati in questa sessione.")
                .setPositiveButton("Ho capito", null)
                .setIcon(R.drawable.ic_info)
                .show();
    }

    private void createHomeItems() {
        homeItems = new ArrayList<>();
        homeItems.add(new HomeItem("Educazione", R.drawable.ic_education));
        homeItems.add(new HomeItem("Mappa Servizi", R.drawable.ic_map));
        homeItems.add(new HomeItem("Numeri d'emergenza", R.drawable.ic_emergency));
        homeItems.add(new HomeItem("ChatBot", R.drawable.ic_chat));
    }

    private void navigateToSection(String sectionTitle) {
        Intent intent;

        switch (sectionTitle) {
            case "Educazione":
                //intent = new Intent(this, EducationActivity.class);
                break;
            case "Mappa Servizi":
               // intent = new Intent(this, MapActivity.class);
                break;
            case "Numeri d'emergenza":
                //intent = new Intent(this, EmergencyActivity.class);
                break;
            case "Quiz":
                //intent = new Intent(this, QuizActivity.class);
                break;
            default:
                return;
        }

        //startActivity(intent);
    }
}