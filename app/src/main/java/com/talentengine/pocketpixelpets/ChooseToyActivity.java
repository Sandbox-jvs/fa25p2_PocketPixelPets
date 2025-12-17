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

    private View ballCard;
    private View plushieCard;
    private View stickCard;
    private View bubbleWandCard;
    private static final float FULL_ALPHA = 1.0f;
    private static final float DIMMED_ALPHA = 0.3f;
    private String selectedToy = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_toy);

        username = getIntent().getStringExtra("USERNAME");
        // Reference to the card views inside toy xml
        ballCard = findViewById(R.id.ballButton);
        plushieCard = findViewById(R.id.plushieButton);
        stickCard = findViewById(R.id.stickButton);
        bubbleWandCard = findViewById(R.id.bubbleWandButton);

        // Wire the click listeners for the toy option views
        ballCard.setOnClickListener(v -> {
            selectedToy = "Ball";
            Toast.makeText(this, "Boing! Boing!", Toast.LENGTH_SHORT).show();
                });
        plushieCard.setOnClickListener(v -> {
            selectedToy = "Plushie";
            Toast.makeText(this, "So soft... Or chewy!", Toast.LENGTH_SHORT).show();
        });
        stickCard.setOnClickListener(v -> {
            selectedToy = "Stick";
            Toast.makeText(this, "Organic choice!", Toast.LENGTH_SHORT).show();
        });
        bubbleWandCard.setOnClickListener(v -> {
            selectedToy = "Bubble Wand";
            Toast.makeText(this, "Look at all the bubbles!", Toast.LENGTH_SHORT).show();
        });

        nextButton = findViewById(R.id.nextButtonToy);
        nextButton.setOnClickListener(v -> goToNextStep());

    }

    private void dimToyCards(){
        ballCard.setAlpha(DIMMED_ALPHA);
        plushieCard.setAlpha(DIMMED_ALPHA);
        stickCard.setAlpha(DIMMED_ALPHA);
        bubbleWandCard.setAlpha(DIMMED_ALPHA);
    }
    private void onSelectedToy(){}

    private void goToNextStep() {
        // Pass username to the Choose background Activity
        Intent intent = new Intent(this, ChooseBackgroundActivity.class);
        intent.putExtra("USERNAME", username);
        startActivity(intent);
        finish();
    }
}
