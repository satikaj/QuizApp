package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.quizapp.databinding.ActivityResultBinding;

public class ResultActivity extends AppCompatActivity {

    ActivityResultBinding binding;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Get the stored name and score from the quiz activity and display them
        sharedPreferences = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "");

        Intent getResults = getIntent();
        String score = getResults.getStringExtra("score");

        binding.txtCongrats.setText("Congratulations, " + name + "!");
        binding.txtScore.setText(score + "/5");

        binding.btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go back to the main activity
                Intent newQuiz = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(newQuiz);
            }
        });

        binding.btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close app
                finishAffinity();
            }
        });
    }
}