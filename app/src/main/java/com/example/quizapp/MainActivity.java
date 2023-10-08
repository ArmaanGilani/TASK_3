package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_QUIZ = 1;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_HIGHSCORE = "keyHighscore";

    private TextView textViewHighscore;

    private int highscore;

    androidx.appcompat.widget.Toolbar toolbar;
    Button startquizbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setStatusBarColor(Color.BLACK);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        startquizbutton = findViewById(R.id.startquizbutton);
        textViewHighscore = findViewById(R.id.highscoretextview);
        loadHighscore();

        startquizbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getApplicationContext(), GKQuiz.class),REQUEST_CODE_QUIZ);
            }
        });

    }


    @SuppressLint("SetTextI18n")
    private void loadHighscore(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        highscore = sharedPreferences.getInt(KEY_HIGHSCORE, 0);
        textViewHighscore.setText("HighScore: " + highscore);
    }

    @SuppressLint("SetTextI18n")
    private void updateHighscore(int highscorenew){
        highscore = highscorenew;
        textViewHighscore.setText("Highscore: " + highscore);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_HIGHSCORE, highscore);
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.closeapp) {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Alert!");
            builder.setMessage("Do you want to exit?");
            builder.setCancelable(false);
            builder.setPositiveButton("YES",(dialog, which) -> finish());
            builder.setNegativeButton("NO",(dialog, which) -> dialog.cancel());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}