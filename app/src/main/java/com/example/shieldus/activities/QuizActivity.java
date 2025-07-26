package com.example.shieldus.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.*;
import androidx.core.content.ContextCompat;
import com.example.shieldus.R;
import com.example.shieldus.models.QuizQuestion;
import java.util.*;

public class QuizActivity extends BaseActivity {

    private TextView questionText, progressText;
    private RadioGroup optionsGroup;
    private Button submitButton;
    private ProgressBar progressBar;

    private List<QuizQuestion> questions = new ArrayList<>();
    private int currentQuestionIndex = 0;
    private int score = 0;
    private boolean hasAnswered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        setupNavigationDrawer();
        setToolbarTitle("Quiz");

        questionText = findViewById(R.id.questionText);
        optionsGroup = findViewById(R.id.optionsGroup);
        submitButton = findViewById(R.id.submitButton);
        progressBar = findViewById(R.id.quizProgressBar);
        progressText = findViewById(R.id.progressText);

        String moduleId = getIntent().getStringExtra("MODULE_ID");
        loadQuestionsForModule(moduleId);

        showQuestion(currentQuestionIndex);

        submitButton.setOnClickListener(v -> {
            String buttonText = submitButton.getText().toString();

            if (buttonText.equals("Conferma")) {
                int selectedId = optionsGroup.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    Toast.makeText(this, "Seleziona una risposta", Toast.LENGTH_SHORT).show();
                } else {
                    showFeedback(selectedId);
                    hasAnswered = true;
                    submitButton.setText("Prossima domanda");
                }
            } else if (buttonText.equals("Prossima domanda")) {
                goToNext();
            }
        });
    }

    private void loadQuestionsForModule(String moduleId) {
        questions.add(new QuizQuestion(
                "Cos'è il consenso?",
                Arrays.asList(
                        "Quando una persona non si oppone",
                        "Un accordo libero ed entusiasta",
                        "Qualcosa che non può essere revocato",
                        "Solo necessario per il primo rapporto"
                ),
                1
        ));

        questions.add(new QuizQuestion(
                "Puoi ritirare il consenso dopo averlo dato?",
                Arrays.asList(
                        "No, è valido per sempre",
                        "Solo se l'altra persona è d'accordo",
                        "Sì, in qualsiasi momento",
                        "Solo entro 24 ore"
                ),
                2
        ));
    }

    private void showQuestion(int index) {
        if (index >= questions.size()) {
            showResults();
            return;
        }

        hasAnswered = false;
        submitButton.setText("Conferma");

        QuizQuestion question = questions.get(index);
        questionText.setText(question.getQuestionText());

        progressBar.setProgress((index * 100) / questions.size());
        progressText.setText(String.format(Locale.getDefault(), "%d/%d", index + 1, questions.size()));

        optionsGroup.removeAllViews();

        for (int i = 0; i < question.getOptions().size(); i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(question.getOptions().get(i));
            radioButton.setTextSize(16);
            radioButton.setPadding(16, 16, 16, 16);
            radioButton.setId(i);
            radioButton.setEnabled(true);
            radioButton.setBackgroundColor(Color.TRANSPARENT);
            radioButton.setTextColor(ContextCompat.getColor(this, R.color.black));
            optionsGroup.addView(radioButton);
        }
    }

    private void showFeedback(int selectedId) {
        QuizQuestion question = questions.get(currentQuestionIndex);
        int correctIndex = question.getCorrectAnswerIndex();

        for (int i = 0; i < optionsGroup.getChildCount(); i++) {
            RadioButton option = (RadioButton) optionsGroup.getChildAt(i);
            option.setEnabled(false);
        }

        RadioButton correctButton = (RadioButton) optionsGroup.getChildAt(correctIndex);
        correctButton.setBackgroundResource(R.drawable.correct_answer_background);
        correctButton.setTextColor(Color.WHITE);

        if (selectedId != correctIndex) {
            RadioButton selectedButton = (RadioButton) optionsGroup.getChildAt(selectedId);
            selectedButton.setBackgroundResource(R.drawable.wrong_answer_background);
            selectedButton.setTextColor(Color.WHITE);
        } else {
            score++;
        }
    }

    private void goToNext() {
        currentQuestionIndex++;
        showQuestion(currentQuestionIndex);
    }

    private void showResults() {
        Intent intent = new Intent(this, QuizResultsActivity.class);
        intent.putExtra("SCORE", score);
        intent.putExtra("TOTAL_QUESTIONS", questions.size());
        startActivity(intent);
        finish();
    }
}
