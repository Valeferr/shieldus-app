package com.example.shieldus.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.*;
import androidx.core.content.ContextCompat;
import com.example.shieldus.R;
import com.example.shieldus.models.QuizQuestion;
import com.example.shieldus.persistence.ProgressManager;
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
    private String moduleId;

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

        moduleId = getIntent().getStringExtra("MODULE_ID");
        loadQuestionsForModule(moduleId);

        currentQuestionIndex = ProgressManager.getQuizState(this, moduleId);

        if (currentQuestionIndex >= questions.size()) {
            currentQuestionIndex = 0;
            score = 0;
        }

        showQuestion(currentQuestionIndex);

        submitButton.setOnClickListener(v -> {
            if (submitButton.getText().toString().equals("Conferma")) {
                checkAnswer();
            } else {
                goToNext();
            }
        });
    }

    private void loadQuestionsForModule(String moduleId) {
        String[] questionTexts = null;
        int[] correctAnswers = null;
        String optionsPrefix = "";

        switch (moduleId) {
            case "1":
                questionTexts = getResources().getStringArray(R.array.questions_consent);
                correctAnswers = getResources().getIntArray(R.array.correct_answers_consent);
                optionsPrefix = "options_consent_";
                break;

            case "2":
                questionTexts = getResources().getStringArray(R.array.questions_sexual_health);
                correctAnswers = getResources().getIntArray(R.array.correct_answers_sexual_health);
                optionsPrefix = "options_sexual_health_";
                break;

            case "3":
                questionTexts = getResources().getStringArray(R.array.questions_work_rights);
                correctAnswers = getResources().getIntArray(R.array.correct_answers_work_rights);
                optionsPrefix = "options_work_rights_";
                break;

            case "4":
                questionTexts = getResources().getStringArray(R.array.questions_toxic_relationships);
                correctAnswers = getResources().getIntArray(R.array.correct_answers_toxic_relationships);
                optionsPrefix = "options_toxic_relationships_";
                break;

            case "5":
                questionTexts = getResources().getStringArray(R.array.questions_safety);
                correctAnswers = getResources().getIntArray(R.array.correct_answers_safety);
                optionsPrefix = "options_safety_";
                break;
        }

        if (questionTexts != null) {
            for (int i = 0; i < questionTexts.length; i++) {
                String arrayName = optionsPrefix + (i + 1);
                int resourceId = getResources().getIdentifier(arrayName, "array", getPackageName());
                String[] options = getResources().getStringArray(resourceId);

                questions.add(new QuizQuestion(
                        questionTexts[i],
                        Arrays.asList(options),
                        correctAnswers[i]
                ));
            }
        }
    }

    private void showQuestion(int index) {
        if (index >= questions.size()) {
            finishQuiz();
            return;
        }

        hasAnswered = false;
        submitButton.setText("Conferma");
        optionsGroup.clearCheck();

        QuizQuestion question = questions.get(index);
        questionText.setText(question.getQuestionText());

        int progress = (int) (((float) (index) / questions.size()) * 100);
        progressBar.setProgress(progress);
        progressText.setText(String.format(Locale.getDefault(), "%d/%d", index + 1, questions.size()));

        optionsGroup.removeAllViews();

        for (int i = 0; i < question.getOptions().size(); i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(question.getOptions().get(i));
            radioButton.setTextSize(16);
            radioButton.setPadding(16, 16, 16, 16);
            radioButton.setId(i);
            radioButton.setEnabled(true);
            radioButton.setTextColor(ContextCompat.getColor(this, R.color.black));
            optionsGroup.addView(radioButton);
        }
    }

    private void checkAnswer() {
        int selectedId = optionsGroup.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(this, "Seleziona una risposta", Toast.LENGTH_SHORT).show();
            return;
        }

        QuizQuestion question = questions.get(currentQuestionIndex);
        int correctIndex = question.getCorrectAnswerIndex();

        for (int i = 0; i < optionsGroup.getChildCount(); i++) {
            RadioButton option = (RadioButton) optionsGroup.getChildAt(i);
            option.setEnabled(false);

            if (i == correctIndex) {
                option.setBackgroundResource(R.drawable.correct_answer_background);
                option.setTextColor(Color.WHITE);
            }
        }

        if (selectedId != correctIndex) {
            RadioButton selectedButton = (RadioButton) optionsGroup.getChildAt(selectedId);
            selectedButton.setBackgroundResource(R.drawable.wrong_answer_background);
            selectedButton.setTextColor(Color.WHITE);
        } else {
            score++;
        }

        hasAnswered = true;
        submitButton.setText("Prossima domanda");
    }

    private void goToNext() {
        currentQuestionIndex++;
        showQuestion(currentQuestionIndex);
    }

    private void finishQuiz() {
        int progress = (int) (((float) score / questions.size()) * 100);

        ProgressManager.saveProgress(this, moduleId, progress);

        Intent resultIntent = new Intent();
        resultIntent.putExtra("MODULE_ID", moduleId);
        resultIntent.putExtra("NEW_PROGRESS", progress);
        resultIntent.putExtra("SCORE", score);
        resultIntent.putExtra("TOTAL_QUESTIONS", questions.size());

        setResult(RESULT_OK, resultIntent);

        Intent resultsIntent = new Intent(this, QuizResultsActivity.class);
        resultsIntent.putExtra("SCORE", score);
        resultsIntent.putExtra("TOTAL_QUESTIONS", questions.size());
        resultsIntent.putExtra("MODULE_ID", moduleId);
        startActivity(resultsIntent);

        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        savePartialProgress();
    }

    @Override
    protected void onStop() {
        super.onStop();
        savePartialProgress();
    }

    @Override
    protected void onDestroy() {
        savePartialProgress();
        super.onDestroy();
    }

    private void savePartialProgress() {
        if (currentQuestionIndex > 0) {
            int partialProgress = (int) (((float) currentQuestionIndex / questions.size()) * 100);
            ProgressManager.saveQuizState(this, moduleId, partialProgress, currentQuestionIndex, score);
        }
    }
}
