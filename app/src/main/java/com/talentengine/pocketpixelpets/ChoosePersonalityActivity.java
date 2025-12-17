package com.talentengine.pocketpixelpets;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.talentengine.pocketpixelpets.database.AppDatabase;
import com.talentengine.pocketpixelpets.database.entities.Pet;

/**
 * @author Jessica Sandoval
 * <br>
 * Personality Activity requires, at minimum, onCreate(), to extend AppCompatActivity,
 * and a call to setContentView()
 */
public class ChoosePersonalityActivity extends AppCompatActivity {
    //Will be updated when a user selects Cheerful, Lazy, Playful, or Clean Freak
    private String selectedPersonality = null;

    private String username;

    private Button btnNext;
    private View btnCheerful;
    private View btnLazy;
    private View btnPlayful;
    private View btnCleanFreak;

    private static final float ALPHA_SELECTED = 1.0f;
    private static final float ALPHA_UNSELECTED = 0.5f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_personality);

        btnNext = findViewById(R.id.nextButtonPersonality);
        btnCheerful = findViewById(R.id.cheerfulButton);
        btnLazy = findViewById(R.id.lazyButton);
        btnPlayful = findViewById(R.id.playfulButton);
        btnCleanFreak = findViewById(R.id.cleanFreakButton);

        username = getIntent().getStringExtra("USERNAME");

        btnNext.setEnabled(false);
        dimAllPersonalityButtons();

        //Wire up with intents
        btnNext.setOnClickListener(v -> {
            if (selectedPersonality == null) {
                Toast.makeText(this, "Oops! Please select a personality for your pet!", Toast.LENGTH_SHORT).show();
            }
            goToNextStep();
        });

        btnLazy.setOnClickListener(v -> selectPersonality("Lazy", btnLazy));
        btnPlayful.setOnClickListener(v -> selectPersonality("Playful", btnPlayful));
        btnCleanFreak.setOnClickListener(v -> selectPersonality("Clean Freak", btnCleanFreak));
        btnCheerful.setOnClickListener(v -> selectPersonality("Cheerful", btnCheerful));
    }

    private void selectPersonality(String personality, View selectedButton) {
        selectedPersonality = personality;
        btnNext.setEnabled(true);

        dimAllPersonalityButtons();
        selectedButton.setAlpha(ALPHA_SELECTED);
    }

    private void dimAllPersonalityButtons() {
        btnCheerful.setAlpha(ALPHA_UNSELECTED);
        btnLazy.setAlpha(ALPHA_UNSELECTED);
        btnPlayful.setAlpha(ALPHA_UNSELECTED);
        btnCleanFreak.setAlpha(ALPHA_UNSELECTED);
    }

    private void goToNextStep() {
        // Get User ID from last activity
        Intent lastIntent = getIntent();
        int user_id = lastIntent.getIntExtra("USER_ID", -1);

        // Update the Pet in the database
        Pet pet = AppDatabase.getDatabase(ChoosePersonalityActivity.this).PetDao().getPetFromOwnerUserId(user_id);
        pet.setPet_personality(selectedPersonality);

        switch (selectedPersonality) {
            case "Cheerful":
                pet.setHunger(3);
                pet.setHappiness(5);
                pet.setHygiene(2);
                break;
            case "Lazy":
                pet.setHunger(3);
                pet.setHappiness(3);
                pet.setHygiene(3);
                break;
            case "Playful":
                pet.setHunger(3);
                pet.setHappiness(4);
                pet.setHygiene(3);
                break;
            case "Clean Freak":
                pet.setHunger(3);
                pet.setHappiness(3);
                pet.setHygiene(5);
                break;
        }

        AppDatabase.getDatabase(ChoosePersonalityActivity.this).PetDao().updatePet(pet);

        // Pass username to ChooseFoodActivity
        Intent next = new Intent(ChoosePersonalityActivity.this, ChooseFoodActivity.class);
        next.putExtra("USERNAME", username);
        next.putExtra("USER_ID", user_id);
        startActivity(next);
        finish();
    }
}
