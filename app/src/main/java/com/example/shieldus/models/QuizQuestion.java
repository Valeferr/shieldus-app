package com.example.shieldus.models;

import java.util.List;

public class QuizQuestion {
    private String questionText;
    private List<String> options;
    private int correctAnswerIndex;

    public QuizQuestion(String questionText, List<String> options, int correctAnswerIndex) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getQuestionText() { return questionText; }
    public List<String> getOptions() { return options; }
    public int getCorrectAnswerIndex() { return correctAnswerIndex; }
}