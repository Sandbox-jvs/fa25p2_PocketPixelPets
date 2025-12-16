package com.talentengine.pocketpixelpets;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ChooseToyActivity extends AppCompatActivity {

    private String username;
    private Button nextButton;

    private View ballButton;
    private View plushieButton;
    private View stickButton;
    private View bubbleWandButton;
    private String selectedToy = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_toy);

        username = getIntent().getStringExtra("USERNAME");
        // Reference to the card views inside toy xml
        ballButton = findViewById(R.id .ballButton);
        plushieButton = findViewById(R.id .plushieButton);
        stickButton = findViewById(R.id .stickButton);
        bubbleWandButton = findViewById(R.id .bubbleWandButton);

        // Wire with intents
        ballButton.setOnClickListener(v -> {
            selectedToy = "Ball";
            Toast.makeText(this, "Boing! Boing!", Toast.LENGTH_SHORT).show();
                });

        nextButton = findViewById(R.id.nextButtonToy);
        nextButton.setOnClickListener(v -> goToNextStep());

    }

    private void goToNextStep() {
        // Pass username to the Choose background Activity
        Intent intent = new Intent(this, ChooseBackgroundActivity.class);
        intent.putExtra("USERNAME", username);
        startActivity(intent);
        finish();
    }
}
