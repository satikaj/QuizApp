package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.quizapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Set name edit text to the already given name
        sharedPreferences = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "");
        binding.edtName.setText(name);

        binding.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Remember name input
                SharedPreferences.Editor nameEditor = sharedPreferences.edit();
                nameEditor.putString("name", binding.edtName.getText().toString());
                nameEditor.apply();

                // Start quiz
                Intent startQuiz = new Intent(MainActivity.this, QuizActivity.class);
                startActivity(startQuiz);
            }
        });
    }
}