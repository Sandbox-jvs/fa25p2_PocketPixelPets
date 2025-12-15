package com.talentengine.pocketpixelpets;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.talentengine.pocketpixelpets.database.AppDatabase;
import com.talentengine.pocketpixelpets.database.PetDao;
import com.talentengine.pocketpixelpets.database.entities.Pet;

public class GamePlayActivity extends AppCompatActivity {

    private AppDatabase database;
    private PetDao petDao;

    private int userId = -1;
    private String username;

    private Pet pet;

    private SpriteView petSpriteView;
    private ImageView PetBackground;

    // Stat squares (5 each)
    private View[] happinessSquares = new View[5];
    private View[] hungerSquares = new View[5];
    private View[] hygieneSquares = new View[5];

    // Action buttons
    private TextView btnFood;
    private TextView btnPlay;
    private TextView btnBathe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        database = AppDatabase.getDatabase(this);
        petDao = database.PetDao();

        Intent intent = getIntent();
        userId = intent.getIntExtra("USER_ID", -1);
        username = intent.getStringExtra("USERNAME");

        if (userId == -1) {
            Toast.makeText(this, "Error: no user ID passed to GamePlayActivity", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        bindViews();
        setupButtonListeners();
        loadPet();
    }

    private void bindViews() {
        petSpriteView = findViewById(R.id.petSpriteView);
        PetBackground = findViewById(R.id.ivPetBackground);

        // Happiness squares
        happinessSquares[0] = findViewById(R.id.happySquare1);
        happinessSquares[1] = findViewById(R.id.happySquare2);
        happinessSquares[2] = findViewById(R.id.happySquare3);
        happinessSquares[3] = findViewById(R.id.happySquare4);
        happinessSquares[4] = findViewById(R.id.happySquare5);

        // Hunger squares
        hungerSquares[0] = findViewById(R.id.hungerSquare1);
        hungerSquares[1] = findViewById(R.id.hungerSquare2);
        hungerSquares[2] = findViewById(R.id.hungerSquare3);
        hungerSquares[3] = findViewById(R.id.hungerSquare4);
        hungerSquares[4] = findViewById(R.id.hungerSquare5);

        // Hygiene squares
        hygieneSquares[0] = findViewById(R.id.hygieneSquare1);
        hygieneSquares[1] = findViewById(R.id.hygieneSquare2);
        hygieneSquares[2] = findViewById(R.id.hygieneSquare3);
        hygieneSquares[3] = findViewById(R.id.hygieneSquare4);
        hygieneSquares[4] = findViewById(R.id.hygieneSquare5);

        btnFood = findViewById(R.id.btnFood);
        btnPlay = findViewById(R.id.btnPlay);
        btnBathe = findViewById(R.id.btnBathe);
    }

    private void setupButtonListeners() {
        btnFood.setOnClickListener(v -> onFeedClicked());
        btnPlay.setOnClickListener(v -> onPlayClicked());
        btnBathe.setOnClickListener(v -> onBatheClicked());
    }

    /**
     * Load the Pet for this user from the database
     */
    private void loadPet() {
        new Thread(() -> {
            pet = petDao.getPetFromOwnerUserId(userId);

            runOnUiThread(() -> {
                if (pet == null) {
                    Toast.makeText(this, "No pet found for this user.", Toast.LENGTH_SHORT).show();
                    return;
                }
                setupPetSprite();
                setupBackground();
                updateActionButtons();
            });
        }).start();
    }

    /**
     * Configure sprite based on pet_type and color
     */
    private void setupPetSprite() {
        if (petSpriteView == null || pet == null) return;

        String type = pet.getPet_type();
        String color = pet.getPet_color();

        if (type == null) type = "otter";
        if (color == null) color = "purple";

        String typeLower = type.toLowerCase();
        String colorLower = color.toLowerCase();

        int spriteRes;

        switch (typeLower) {
            case "otter":
                spriteRes = getOtterSpriteForColor(colorLower);
                break;
            case "turtle":
                spriteRes = getTurtleSpriteForColor(colorLower);
                break;
            case "fox":
                spriteRes = getFoxSpriteForColor(colorLower);
                break;
            default:
                spriteRes = R.drawable.otter_sprite_sheet_purple;
                break;
        }

        petSpriteView.setSpriteSheet(spriteRes, 2, 3);
        petSpriteView.setFrameDuration(1500);
        petSpriteView.start();

        applyPetPositioning();
    }

    /**
     * Otter needs to be centered while Fox/Turtle need to be placed at bottom
     */
    private void applyPetPositioning() {
        if (pet == null || petSpriteView == null) return;

        String type = pet.getPet_type();
        if (type == null) type = "";

        type = type.toLowerCase();

        float offsetDp;

        switch (type) {
            case "fox":
            case "turtle":
                offsetDp = 55f;
                break;
            default:
                offsetDp = 20f;
                break;
        }

        float offsetPx = dpToPx(offsetDp);
        petSpriteView.setTranslationY(offsetPx);
    }

    private float dpToPx(float dp) {
        return dp * getResources().getDisplayMetrics().density;
    }

    private int dp(int dpValue) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale);
    }

    private int getOtterSpriteForColor(String color) {
        switch (color) {
            case "pink":
                return R.drawable.otter_sprite_sheet_pink;
            case "blue":
                return R.drawable.otter_sprite_sheet_blue;
            case "green":
                return R.drawable.otter_sprite_sheet_green;
            case "purple":
            default:
                return R.drawable.otter_sprite_sheet_purple;
        }
    }

    private int getTurtleSpriteForColor(String color) {
        switch (color) {
            case "pink":
                return R.drawable.turtle_sprite_sheet_pink;
            case "blue":
                return R.drawable.turtle_sprite_sheet_blue;
            case "green":
                return R.drawable.turtle_sprite_sheet_green;
            case "purple":
            default:
                return R.drawable.turtle_sprite_sheet_purple;
        }
    }

    private int getFoxSpriteForColor(String color) {
        switch (color) {
            case "pink":
                return R.drawable.fox_sprite_sheet_pink;
            case "blue":
                return R.drawable.fox_sprite_sheet_blue;
            case "green":
                return R.drawable.fox_sprite_sheet_green;
            case "purple":
            default:
                return R.drawable.fox_sprite_sheet_purple;
        }
    }

    /**
     * Set background image based what the user selected
     */
    private void setupBackground() {
        if (PetBackground == null || pet == null) return;

        String bg = pet.getBackground();
        int bgRes;

        if ("natural".equalsIgnoreCase(bg)) {
            bgRes = R.drawable.bg_natural_pond;
        } else if ("magic".equalsIgnoreCase(bg)) {
            bgRes = R.drawable.bg_magical_forest;
        } else if ("night".equalsIgnoreCase(bg)) {
            bgRes = R.drawable.bg_icon_night_sky;
        } else {
            bgRes = R.drawable.bg_fantasy_clouds;
        }

        PetBackground.setImageResource(bgRes);
    }

    private void updateActionButtons() {
        if (pet == null) return;

        String foodEmoji = getFoodEmoji(pet.getFavorite_food());
        String toyEmoji  = getToyEmoji(pet.getPet_toy());

        btnFood.setText(foodEmoji);
        btnPlay.setText(toyEmoji);

    }

    private String getFoodEmoji(String food) {
        if (food == null) return "❓";

        switch (food.toLowerCase()) {
            case "fish":
                return "🐟";
            case "fruit":
                return "🍓";
            case "cookie":
                return "🍪";
            case "seaweed":
                return "🌿";
            default:
                return "❓";
        }
    }

    private String getToyEmoji(String toy) {
        if (toy == null) return "❓";

        switch (toy.toLowerCase()) {
            case "ball":
                return "🎾";
            case "plushie":
                return "🧸";
            case "stick":
                return "🪵";
            case "bubble wand":
                return "🪄";
            default:
                return "❓";
        }
    }

    private void onFeedClicked() {
        if (pet == null) return;
        //TODO: Implement feed action
        Toast.makeText(GamePlayActivity.this, "Feed to be implemented soon!", Toast.LENGTH_SHORT).show();
    }

    private void onPlayClicked() {
        if (pet == null) return;
        //TODO: Implement play action
        Toast.makeText(GamePlayActivity.this, "Play to be implemented soon!", Toast.LENGTH_SHORT).show();
    }

    private void onBatheClicked() {
        if (pet == null) return;
        //TODO: Implement bathe action
        Toast.makeText(GamePlayActivity.this, "Bathe to be implemented soon!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (petSpriteView != null) {
            petSpriteView.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (petSpriteView != null) {
            petSpriteView.stop();
        }
    }
}
