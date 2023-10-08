package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class GKQuiz extends AppCompatActivity {

    public static final String EXTRA_SCORE = "extraScore";
    androidx.appcompat.widget.Toolbar toolbar;
    private TextView textViewQuestion, textViewScore, textViewQuestionCount;
    private RadioGroup rbGroup;
    private RadioButton rb1, rb2, rb3, rb4;
    private Button buttonConfirmNext;
    private List<Question> questionList;
    private ColorStateList textColorDefaultRb;
    private int questionCounter;
    private int questionCountTotal;
    private Question currentQuestion;
    private int score;
    private boolean answered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gkquiz);
        getWindow().setStatusBarColor(Color.BLACK);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        textViewQuestion = findViewById(R.id.questiontextview);
        textViewScore = findViewById(R.id.scoretextview);
        textViewQuestionCount = findViewById(R.id.questioncounttextview);
        rbGroup = findViewById(R.id.radiogroup);
        rb1 = findViewById(R.id.radiobutton1);
        rb2 = findViewById(R.id.radiobutton2);
        rb3 = findViewById(R.id.radiobutton3);
        rb4 = findViewById(R.id.radiobutton4);
        buttonConfirmNext = findViewById(R.id.confirmnextbutton);

        textColorDefaultRb = rb1.getTextColors();

        QuizDbHelper dbHelper = new QuizDbHelper(this);
        questionList = dbHelper.getAllQuestions();
        questionCountTotal = questionList.size();
        Collections.shuffle(questionList);

        showNextQuestion();
        buttonConfirmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered){
                    if(rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()){
                        checkAnswer();
                    }
                    else {
                        Toast.makeText(GKQuiz.this, "Please select an option.", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    showNextQuestion();
                }
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void showNextQuestion(){
        rb1.setTextColor(textColorDefaultRb);
        rb2.setTextColor(textColorDefaultRb);
        rb3.setTextColor(textColorDefaultRb);
        rb4.setTextColor(textColorDefaultRb);
        rbGroup.clearCheck();

        if (questionCounter < questionCountTotal){
            currentQuestion = questionList.get(questionCounter);
            textViewQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());
            rb4.setText(currentQuestion.getOption4());

            questionCounter++;
            textViewQuestionCount.setText("Question: "+questionCounter + "/" + questionCountTotal);
            answered = false;
            buttonConfirmNext.setText("Confirm");
        }
        else {
            finishQuiz();
        }

    }

    @SuppressLint("SetTextI18n")
    private void checkAnswer(){
        answered = true;
        RadioButton radioButton = findViewById(rbGroup.getCheckedRadioButtonId());
        int answerno = rbGroup.indexOfChild(radioButton) + 1;
        if (answerno == currentQuestion.getAnswernumber()){
            score++;
            textViewScore.setText("Score: " + score);
        }
        showSolution();
    }

    @SuppressLint("SetTextI18n")
    private void showSolution(){
        rb1.setTextColor(Color.RED);
        rb2.setTextColor(Color.RED);
        rb3.setTextColor(Color.RED);
        rb4.setTextColor(Color.RED);

        switch (currentQuestion.getAnswernumber()){
            case 1:
                rb1.setTextColor(Color.GREEN);
                textViewQuestion.setText(" First answer is the correct answer. ");
                break;
            case 2:
                rb2.setTextColor(Color.GREEN);
                textViewQuestion.setText(" Second answer is the correct answer. ");
                break;
            case 3:
                rb3.setTextColor(Color.GREEN);
                textViewQuestion.setText(" Third answer is the correct answer. ");
                break;
            case 4:
                rb4.setTextColor(Color.GREEN);
                textViewQuestion.setText(" Fourth answer is the correct answer. ");
                break;
        }
        if (questionCounter < questionCountTotal){
            buttonConfirmNext.setText("Next");
        }
        else {
            buttonConfirmNext.setText("Finish");
        }

    }

    private void finishQuiz(){
        Intent intent = new Intent();
        intent.putExtra(EXTRA_SCORE, score);
        setResult(RESULT_OK, intent);
        finish();
    }

}