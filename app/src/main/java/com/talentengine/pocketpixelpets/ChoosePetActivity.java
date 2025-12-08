package com.talentengine.pocketpixelpets;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class ChoosePetActivity extends AppCompatActivity {

    private SpriteView otterSpriteView;
    private SpriteView turtleSpriteView;
    private SpriteView foxSpriteView;
    private MaterialButton continueButton;

    // Store which pet is selected
    private String selectedPet = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_pet);

        otterSpriteView = findViewById(R.id.otterSpriteView);
        turtleSpriteView = findViewById(R.id.turtleSpriteView);
        foxSpriteView = findViewById(R.id.foxSpriteView);
        continueButton = findViewById(R.id.loginButton);

        otterSpriteView.setSpriteSheet(
                R.drawable.otter_sprite_sheet_purple,
                2, 3
        );
        turtleSpriteView.setSpriteSheet(
                R.drawable.turtle_sprite_sheet_green,
                2, 3
        );
//        // TODO: change fox sprite file name to match your actual PNG
        foxSpriteView.setSpriteSheet(
                R.drawable.fox_sprite_sheet_pink,
                2, 3
        );

        otterSpriteView.setFrameDuration(1500);
        turtleSpriteView.setFrameDuration(1800);
        foxSpriteView.setFrameDuration(1600);

        otterSpriteView.start();
        turtleSpriteView.start();
        foxSpriteView.start();

        otterSpriteView.setOnClickListener(v -> onPetSelected("otter"));
        turtleSpriteView.setOnClickListener(v -> onPetSelected("turtle"));
        foxSpriteView.setOnClickListener(v -> onPetSelected("fox"));

        continueButton.setOnClickListener(v -> onContinueClicked());
    }

    private void onPetSelected(String petId) {
        selectedPet = petId;

        resetSpriteStates();

        switch (petId) {
            case "otter":
                otterSpriteView.setScaleX(1.1f);
                otterSpriteView.setScaleY(1.1f);
                break;
            case "turtle":
                turtleSpriteView.setScaleX(1.1f);
                turtleSpriteView.setScaleY(1.1f);
                break;
            case "fox":
                foxSpriteView.setScaleX(1.1f);
                foxSpriteView.setScaleY(1.1f);
                break;
        }
    }

    private void resetSpriteStates() {
        SpriteView[] sprites = { otterSpriteView, turtleSpriteView, foxSpriteView };
        for (SpriteView sprite : sprites) {
            sprite.setScaleX(1.0f);
            sprite.setScaleY(1.0f);
        }
    }

    private void onContinueClicked() {
        if (selectedPet == null) {
            Toast.makeText(this, "Please choose a pet first!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Pass the chosen pet to MainActivity (or wherever you want to go)
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("selected_pet", selectedPet);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        otterSpriteView.start();
        turtleSpriteView.start();
        foxSpriteView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        otterSpriteView.stop();
        turtleSpriteView.stop();
        foxSpriteView.stop();
    }
}
