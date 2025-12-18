package com.talentengine.pocketpixelpets;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.talentengine.pocketpixelpets.database.AppDatabase;
import com.talentengine.pocketpixelpets.database.entities.Pet;

public class ChooseFoodActivity extends AppCompatActivity {

    private String username;
    private Button nextButton;

    private String selectedFood = null;
    private static final float FULL_ALPHA = 1.0f;
    private static final float DIMMED_ALPHA = 0.3f;

    View fishCard;
    View fruitCard;
    View cookieCard;
    View seaweedCard;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_food);

        fishCard = findViewById(R.id.fishButton);
        fruitCard = findViewById(R.id.fruitButton);
        cookieCard = findViewById(R.id.cookieButton);
        seaweedCard = findViewById(R.id.seaweedButton);

        nextButton = findViewById(R.id.nextButtonFood);

        username = getIntent().getStringExtra("USERNAME");

        dimAllFoodCards();

        nextButton.setOnClickListener(v -> {
            if (selectedFood == null) {
                return;
            }

            goToNextStep();
        });

        fishCard.setOnClickListener(v -> {
            selectedFood = "Fish";
            onSelectedFood();
            nextButton.setEnabled(true);
        });

        fruitCard.setOnClickListener(v -> {
            selectedFood = "Fruit";
            onSelectedFood();
            nextButton.setEnabled(true);
        });

        cookieCard.setOnClickListener(v -> {
            selectedFood = "Cookie";
            onSelectedFood();
            nextButton.setEnabled(true);
        });

        seaweedCard.setOnClickListener(v -> {
            selectedFood = "Seaweed";
            onSelectedFood();
            nextButton.setEnabled(true);
        });
    }

    private void onSelectedFood() {
        dimAllFoodCards();
        switch (selectedFood) {
            case "Fish":
                fishCard.setAlpha(FULL_ALPHA);
                break;
            case "Fruit":
                fruitCard.setAlpha(FULL_ALPHA);
                break;
            case "Cookie":
                cookieCard.setAlpha(FULL_ALPHA);
                break;
            case "Seaweed":
                seaweedCard.setAlpha(FULL_ALPHA);
                break;
        }
    }

    private void goToNextStep() {
        //Get user id from last activity
        Intent lastIntent = getIntent();
        int user_id = lastIntent.getIntExtra("USER_ID", -1  );

        //Update pet's food choice in the database
        Pet pet = AppDatabase.getDatabase(ChooseFoodActivity.this).PetDao().getPetFromOwnerUserId(user_id);
        pet.setFavorite_food(selectedFood);
        AppDatabase.getDatabase(ChooseFoodActivity.this).PetDao().updatePet(pet);

        // Pass username to the Choose Toy Activity
        Intent intent = new Intent(this, ChooseToyActivity.class);
        intent.putExtra("USERNAME", username);
        intent.putExtra("USER_ID", user_id);
        startActivity(intent);
        finish();
    }

    private void dimAllFoodCards() {
        fishCard.setAlpha(DIMMED_ALPHA);
        fruitCard.setAlpha(DIMMED_ALPHA);
        cookieCard.setAlpha(DIMMED_ALPHA);
        seaweedCard.setAlpha(DIMMED_ALPHA);
    }
}
