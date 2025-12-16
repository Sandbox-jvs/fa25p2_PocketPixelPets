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
    //Full opacity
    private static final float FULL_ALPHA = 1.0f;
    //Partial transparency
    private static final float DIMMED_ALPHA = 0.3f;
    View cheerfulCard;
    View lazyCard;
    View playfulCard;
    View cleanFreakCard;
    private String username;
    private Button nextButtonPersonality;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_personality);

        //Get references to the views within the xml file
        nextButtonPersonality = findViewById(R.id.nextButtonPersonality);
        cheerfulCard = findViewById(R.id.cheerfulButton);
        lazyCard = findViewById(R.id.lazyButton);
        playfulCard = findViewById(R.id.playfulButton);
        cleanFreakCard = findViewById(R.id.cleanFreakButton);

        username = getIntent().getStringExtra("USERNAME");

        dimmedPersonalityCard();


        //Wire up with intents
        nextButtonPersonality.setOnClickListener(v -> {
            if (selectedPersonality == null) {
                Toast.makeText(this, "Oops! Please select a personality for your pet!", Toast.LENGTH_SHORT).show();
            }

            goToNextStep();
        });

        lazyCard.setOnClickListener(v -> {
            selectedPersonality = "Lazy";
            nextButtonPersonality.setEnabled(true);
        });

        playfulCard.setOnClickListener(v -> {
            selectedPersonality = "Playful";
            nextButtonPersonality.setEnabled(true);
        });

        cleanFreakCard.setOnClickListener(v -> {
            selectedPersonality = "Clean Freak";
            nextButtonPersonality.setEnabled(true);
        });

        cheerfulCard.setOnClickListener(v -> {
            selectedPersonality = "Cheerful";
            nextButtonPersonality.setEnabled(true);
        });
    }

    private void dimmedPersonalityCard() {
        cheerfulCard.setAlpha(DIMMED_ALPHA);
        lazyCard.setAlpha(DIMMED_ALPHA);
        playfulCard.setAlpha(DIMMED_ALPHA);
        cleanFreakCard.setAlpha(DIMMED_ALPHA);

    }
    private void onSelectedPersonality() {}

private void goToNextStep() {
    // Get User ID from last activity
    Intent lastIntent = getIntent();
    int user_id = lastIntent.getIntExtra("USER_ID", -1);

    // Update the Pet in the database
    Pet pet = AppDatabase.getDatabase(ChoosePersonalityActivity.this).PetDao().getPetFromOwnerUserId(user_id);
    pet.setPet_personality(selectedPersonality);
    AppDatabase.getDatabase(ChoosePersonalityActivity.this).PetDao().updatePet(pet);

    // Pass username to NamePetActivity
    Intent next = new Intent(ChoosePersonalityActivity.this, NamePetActivity.class);
    next.putExtra("USERNAME", username);
    next.putExtra("USER_ID", user_id);
    startActivity(next);
    finish();
}
}
