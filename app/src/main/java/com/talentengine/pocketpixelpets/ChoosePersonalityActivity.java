package com.talentengine.pocketpixelpets;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author Jessica Sandoval
 * <br>
 * Personality Activity requires, at minimum, onCreate(), to extend AppCompatActivity,
 * and a call to setContentView()
 */
public class ChoosePersonalityActivity extends AppCompatActivity {
    //Will be updated when a user selects Cheerful, Lazy, Playful, or Clean Freak
    private String selectedPersonality = null;

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



        //Wire up with intents
        btnNext.setOnClickListener(v -> {
            if (selectedPersonality == null) {
                Toast.makeText(this, "Oops! Please select a personality for your pet!", Toast.LENGTH_SHORT).show();
            }

            Intent intent = new Intent(this, ChooseFoodActivity.class);
            startActivity(intent);
        });

        btnLazy.setOnClickListener(v -> {
            selectedPersonality = "Cheerful";
            btnNext.setEnabled(true);
        });

        btnPlayful.setOnClickListener(v -> {
            selectedPersonality = "Lazy";
            btnNext.setEnabled(true);
        });

        btnCleanFreak.setOnClickListener(v -> {
            selectedPersonality = "Playful";
            btnNext.setEnabled(true);
        });

        btnCheerful.setOnClickListener(v -> {
            selectedPersonality = "Clean Freak";
            btnNext.setEnabled(true);
        });
    }

}

