package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.quizapp.databinding.ActivityQuizBinding;

public class QuizActivity extends AppCompatActivity {

    ActivityQuizBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Intent startQuiz = getIntent();
        String name = startQuiz.getStringExtra("name");
        Toast.makeText(QuizActivity.this, "Welcome " + name + "!", Toast.LENGTH_LONG).show();
    }
}