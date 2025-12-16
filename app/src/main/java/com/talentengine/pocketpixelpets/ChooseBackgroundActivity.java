package com.talentengine.pocketpixelpets;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class ChooseBackgroundActivity extends AppCompatActivity {

    private ImageView backgroundPreview;

    private MaterialCardView btnNaturalPond;
    private MaterialCardView btnMagicForest;
    private MaterialCardView btnNightSky;
    private MaterialCardView btnFantasyClouds;
    private MaterialButton nextButton;

    private int selectedBackgroundResId = R.drawable.bg_natural_pond; // default

    private String username;
    private PetDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_background);

        // Get username from previous step
        Intent intent = getIntent();
        username = intent.getStringExtra("USERNAME");

        backgroundPreview   = findViewById(R.id.backgroundPreview);
        btnNaturalPond      = findViewById(R.id.btnNaturalPond);
        btnMagicForest      = findViewById(R.id.btnMagicForest);
        btnNightSky         = findViewById(R.id.btnNightSky);
        btnFantasyClouds    = findViewById(R.id.btnFantasyClouds);
        nextButton          = findViewById(R.id.nextButton);

        applySelection(btnNaturalPond, R.drawable.bg_natural_pond);

        btnNaturalPond.setOnClickListener(v ->
                applySelection(btnNaturalPond, R.drawable.bg_natural_pond));

        btnMagicForest.setOnClickListener(v ->
                applySelection(btnMagicForest, R.drawable.bg_magical_forest));

        btnNightSky.setOnClickListener(v ->
                applySelection(btnNightSky, R.drawable.bg_night_sky));

        btnFantasyClouds.setOnClickListener(v ->
                applySelection(btnFantasyClouds, R.drawable.bg_fantasy_clouds));

        nextButton.setOnClickListener(v -> {
            // Pass username to NamePetActivity
            Intent next = new Intent(ChooseBackgroundActivity.this, NamePetActivity.class);
            next.putExtra("USERNAME", username);
            startActivity(next);
            finish();
        });
    }

    private void applySelection(MaterialCardView selectedCard, int backgroundResId) {
        selectedBackgroundResId = backgroundResId;
        backgroundPreview.setImageResource(backgroundResId);

        float dim = 0.35f;
        btnNaturalPond.setAlpha(dim);
        btnMagicForest.setAlpha(dim);
        btnNightSky.setAlpha(dim);
        btnFantasyClouds.setAlpha(dim);

        // Highlight selected
        selectedCard.setAlpha(1f);
    }
}
