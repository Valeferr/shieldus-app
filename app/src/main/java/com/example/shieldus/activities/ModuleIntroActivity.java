package com.example.shieldus.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.shieldus.R;

public class ModuleIntroActivity extends BaseActivity {
    ImageView backToModulesButton;
    TextView titleView;
    ImageView imageView;
    TextView descriptionView;
    Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_intro);

        String moduleId = getIntent().getStringExtra("MODULE_ID");
        String moduleTitle = getIntent().getStringExtra("MODULE_TITLE");
        String moduleDescription = getIntent().getStringExtra("MODULE_DETAIL");
        int moduleImageRes = getIntent().getIntExtra("MODULE_IMAGE", R.drawable.ic_default_module);

        setupNavigationDrawer();
        setToolbarTitle(moduleTitle);

        backToModulesButton = findViewById(R.id.backToModulesButton);
        backToModulesButton.setOnClickListener(v -> {
            Intent intent = new Intent(ModuleIntroActivity.this, EducationActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });

        titleView = findViewById(R.id.moduleTitle);
        imageView = findViewById(R.id.moduleImage);
        descriptionView = findViewById(R.id.moduleDescription);
        startButton = findViewById(R.id.startQuizButton);

        setToolbarTitle(moduleTitle);
        titleView.setText(moduleTitle);
        imageView.setImageResource(moduleImageRes);
        descriptionView.setText(moduleDescription);

        startButton.setOnClickListener(v -> {
            Intent intent = new Intent(ModuleIntroActivity.this, QuizActivity.class);
            intent.putExtra("MODULE_ID", moduleId);
            startActivity(intent);
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}