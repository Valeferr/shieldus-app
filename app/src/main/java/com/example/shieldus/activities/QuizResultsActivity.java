package com.example.shieldus.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import com.example.shieldus.R;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import java.util.Locale;

public class QuizResultsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_results);

        setupNavigationDrawer();
        setToolbarTitle("Risultato Quiz");

        int score = getIntent().getIntExtra("SCORE", 0);
        int totalQuestions = getIntent().getIntExtra("TOTAL_QUESTIONS", 1);
        String moduleId = getIntent().getStringExtra("MODULE_ID");

        int percentage = (score * 100) / totalQuestions;

        if (moduleId != null) {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("MODULE_ID", moduleId);
            resultIntent.putExtra("NEW_PROGRESS", percentage);
            setResult(RESULT_OK, resultIntent);
        }

        CircularProgressIndicator scoreProgress = findViewById(R.id.scoreProgress);
        scoreProgress.setProgress(percentage);

        String scoreString = String.format(Locale.getDefault(),
                "Hai risposto correttamente a %d/%d domande (%d%%)",
                score, totalQuestions, percentage);

        TextView scoreText = findViewById(R.id.scoreText);
        scoreText.setText(scoreString);

        TextView resultTitle = findViewById(R.id.resultTitle);
        TextView resultMessage = findViewById(R.id.resultMessage);

        if (percentage >= 80) {
            resultTitle.setText("Ottimo lavoro!");
            resultMessage.setText("Hai dimostrato un'ottima comprensione dell'argomento. Continua così!");
        } else if (percentage >= 50) {
            resultTitle.setText("Buon risultato!");
            resultMessage.setText("Hai una buona conoscenza di base, ma puoi ancora migliorare.");
        } else {
            resultTitle.setText("Puoi fare meglio");
            resultMessage.setText("Rivedi i contenuti del modulo e riprova. Imparerai sicuramente!");
        }
        findViewById(R.id.finishButton).setOnClickListener(v -> {
            Intent intent = new Intent(this, EducationActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
    }
}