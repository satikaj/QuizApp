package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.quizapp.databinding.ActivityQuizBinding;
import com.google.android.material.chip.Chip;

import java.util.Arrays;

public class QuizActivity extends AppCompatActivity {

    ActivityQuizBinding binding;
    SharedPreferences sharedPreferences;
    ColorStateList defaultChipColor;
    int currentQuesNo, currentProgress, score;
    String[] quesTitles, quesDetails, quesAnswers;
    String[][] quesChoices = new String[5][3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Greet user using stored name
        sharedPreferences = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "");
        binding.txtWelcome.setText("Welcome " + name + "!");

        // Store the default color of the choices for future resets
        defaultChipColor = binding.chpChoice1.getChipBackgroundColor();

        // Track progress and score
        currentQuesNo = 1;
        currentProgress = 20;
        score = 0;

        // Get question details from string resources
        quesTitles = getResources().getStringArray(R.array.quesTitles);
        quesDetails = getResources().getStringArray(R.array.quesDetails);
        quesChoices[0] = getResources().getStringArray(R.array.q1Choices);
        quesChoices[1] = getResources().getStringArray(R.array.q2Choices);
        quesChoices[2] = getResources().getStringArray(R.array.q3Choices);
        quesChoices[3] = getResources().getStringArray(R.array.q4Choices);
        quesChoices[4] = getResources().getStringArray(R.array.q5Choices);
        quesAnswers = getResources().getStringArray(R.array.quesAnswers);

        // Display first question
        displayQuestion();

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Warn user if none of the choices were selected
                if (binding.chpGrpChoices.getCheckedChipId() == View.NO_ID) {
                    Toast.makeText(QuizActivity.this, "Please select an answer.", Toast.LENGTH_LONG).show();
                }
                else {
                    checkAnswer();
                }

                // Hide greeting
                binding.txtWelcome.setVisibility(View.GONE);
            }
        });

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show the results page after the last question
                if (currentQuesNo == 5) {
                    Intent getResults = new Intent(QuizActivity.this, ResultActivity.class);
                    getResults.putExtra("score", Integer.toString(score));
                    startActivity(getResults);
                }
                else {
                    // Update progress and show the next question
                    currentQuesNo++;
                    currentProgress = 20 * currentQuesNo;
                    displayQuestion();
                }
            }
        });
    }

    private void displayQuestion() {
        // Show updated question number and progress
        binding.txtQuesNo.setText(currentQuesNo + "/5");
        binding.progressBar.setProgress(currentProgress);

        // Show new question details
        binding.txtQuesTitle.setText(quesTitles[currentQuesNo-1]);
        binding.txtQuesDetails.setText(quesDetails[currentQuesNo-1]);

        // Reset the style of the choices
        binding.chpGrpChoices.clearCheck();
        binding.chpChoice1.setChipBackgroundColor(defaultChipColor);
        binding.chpChoice2.setChipBackgroundColor(defaultChipColor);
        binding.chpChoice3.setChipBackgroundColor(defaultChipColor);

        // Show new choices
        binding.chpChoice1.setText(quesChoices[currentQuesNo-1][0]);
        binding.chpChoice2.setText(quesChoices[currentQuesNo-1][1]);
        binding.chpChoice3.setText(quesChoices[currentQuesNo-1][2]);

        // Make submit button visible instead of the next button
        binding.btnSubmit.setVisibility(View.VISIBLE);
        binding.btnNext.setVisibility(View.GONE);
    }

    private void checkAnswer() {
        int correctChoiceIdx = Arrays.asList(quesChoices[currentQuesNo-1]).indexOf(quesAnswers[currentQuesNo-1]);
        int[] chipIds = {R.id.chpChoice1, R.id.chpChoice2, R.id.chpChoice3};
        int selectedChipId = binding.chpGrpChoices.getCheckedChipId();
        int correctChipId = chipIds[correctChoiceIdx];

        // If the user's selection is the correct answer, increase score
        if (selectedChipId == correctChipId) {
            score++;
        }
        else {
            // Else, highlight the user's selection as wrong using red
            Chip selectedChip = findViewById(selectedChipId);
            selectedChip.setChipBackgroundColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
        }

        // Highlight the correct answer using green
        Chip correctChip = findViewById(correctChipId);
        correctChip.setChipBackgroundColor(ColorStateList.valueOf(getResources().getColor(R.color.green)));

        // Make next button visible instead of the submit button
        binding.btnSubmit.setVisibility(View.GONE);
        binding.btnNext.setVisibility(View.VISIBLE);
    }
}