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
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_personality);

        //Get references to the views within the xml file
        Button btnNext = findViewById(R.id.nextButtonPersonality);
        View btnCheerful = findViewById(R.id.cheerfulButton);
        View btnLazy = findViewById(R.id.lazyButton);
        View btnPlayful = findViewById(R.id.playfulButton);
        View btnCleanFreak = findViewById(R.id.cleanFreakButton);

        username = getIntent().getStringExtra("USERNAME");



        //Wire up with intents
        btnNext.setOnClickListener(v -> {
            if (selectedPersonality == null) {
                Toast.makeText(this, "Oops! Please select a personality for your pet!", Toast.LENGTH_SHORT).show();
            }


            goToNextStep();
        });

        btnLazy.setOnClickListener(v -> {
            selectedPersonality = "Lazy";
            btnNext.setEnabled(true);
        });

        btnPlayful.setOnClickListener(v -> {
            selectedPersonality = "Playful";
            btnNext.setEnabled(true);
        });

        btnCleanFreak.setOnClickListener(v -> {
            selectedPersonality = "Clean Freak";
            btnNext.setEnabled(true);
        });

        btnCheerful.setOnClickListener(v -> {
            selectedPersonality = "Cheerful";
            btnNext.setEnabled(true);
        });
    }

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
