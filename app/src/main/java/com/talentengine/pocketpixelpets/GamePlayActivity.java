package com.talentengine.pocketpixelpets;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.talentengine.pocketpixelpets.database.ActionDao;
import com.talentengine.pocketpixelpets.database.AppDatabase;
import com.talentengine.pocketpixelpets.database.PetDao;
import com.talentengine.pocketpixelpets.database.entities.Action;
import com.talentengine.pocketpixelpets.database.entities.Pet;

public class GamePlayActivity extends AppCompatActivity {

    private AppDatabase database;
    private PetDao petDao;
    private ActionDao actionDao;

    private int userId = -1;

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

    private TextView tvStatusMessage;

    private static final int MAX_STAT = 5;
    private static final int MIN_STAT = 0;

    // Cool downs
    private static final long FEED_COOLDOWN_MS = 2 * 60 * 1000; // 2 minutes
    private static final long BATHE_COOLDOWN_MS = 5 * 60 * 1000; // 5 minutes

    private static final int MAX_STAMINA = 3;
    private int stamina = MAX_STAMINA;

    private long lastFeedTime = 0L;
    private long lastBatheTime = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        // LOGOUT MENU IMPLEMENTATION - Force update the menu
        invalidateOptionsMenu();

        database = AppDatabase.getDatabase(this);
        petDao = database.PetDao();
        actionDao = database.actionDao();

        Intent intent = getIntent();
        userId = intent.getIntExtra("USER_ID", -1);

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

        tvStatusMessage = findViewById(R.id.tvStatusMessage);
    }

    private void setupButtonListeners() {
        btnFood.setOnClickListener(v -> onFeedClicked());
        btnPlay.setOnClickListener(v -> onPlayClicked());
        btnBathe.setOnClickListener(v -> onBatheClicked());
    }

    private void showStatus(String Message) {
        tvStatusMessage.setText(Message);
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

                updateMetersUI();
                updateActionButtons();

                showStatus(pet.getName() + " is ready to play!");
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
        petSpriteView.setFrameDuration(3000);
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
            bgRes = R.drawable.bg_night_sky;
        } else {
            bgRes = R.drawable.bg_fantasy_clouds;
        }

        PetBackground.setImageResource(bgRes);
    }

    private void updateActionButtons() {
        if (pet == null) return;

        String foodEmoji = getFoodEmoji(pet.getFavorite_food());
        String toyEmoji = getToyEmoji(pet.getPet_toy());

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

        long now = System.currentTimeMillis();
        if (now - lastFeedTime < FEED_COOLDOWN_MS) {
            long remainingSec = (FEED_COOLDOWN_MS - (now - lastFeedTime)) / 1000;
            showStatus("You can feed again in " + remainingSec + "s.");
            return;
        }

        int hunger = pet.getHunger();
        int happiness = pet.getHappiness();

        if (hunger >= MAX_STAT) {
            // Overfeeding penalty: happiness -1
            pet.setHappiness(Math.max(MIN_STAT, happiness - 1));
            showStatus(pet.getName() + " is too full!");
        } else {
            // Hunger +1 (max 5), small happiness +1
            hunger = Math.min(MAX_STAT, hunger + 1);
            happiness = Math.min(MAX_STAT, happiness + 1);
            pet.setHunger(hunger);
            pet.setHappiness(happiness);
            showStatus("Nom nom!");
        }

        lastFeedTime = now;

        updateMetersUI();
        updateActionButtons();
        savePetAsync();
        logAction("feed");
    }

    private void onPlayClicked() {
        if (pet == null) return;

        // If hunger <= 1, Play is disabled
        if (pet.getHunger() <= 1) {
            showStatus(pet.getName() + " is too hungry to play!");
            return;
        }

        if (stamina <= 0) {
            showStatus(pet.getName() + " is too tired to play more!");
            return;
        }

        int happiness = pet.getHappiness();
        int hunger = pet.getHunger();
        int hygiene = pet.getHygiene();

        // Action effect:
        // Happiness +1
        // Hunger −1
        // Hygiene −1
        happiness = Math.min(MAX_STAT, happiness + 1);
        hunger = Math.max(MIN_STAT, hunger - 1);
        hygiene = Math.max(MIN_STAT, hygiene - 1);

        pet.setHappiness(happiness);
        pet.setHunger(hunger);
        pet.setHygiene(hygiene);

        stamina--;

        showStatus("Playtime!");

        updateMetersUI();
        updateActionButtons();
        savePetAsync();
        logAction("play");
    }

    private void onBatheClicked() {
        if (pet == null) return;

        long now = System.currentTimeMillis();
        if (now - lastBatheTime < BATHE_COOLDOWN_MS) {
            long remainingSec = (BATHE_COOLDOWN_MS - (now - lastBatheTime)) / 1000;
            showStatus("You can bathe again in " + remainingSec + "s.");
            return;
        }

        int hygiene = pet.getHygiene();
        int happiness = pet.getHappiness();

        // If hygiene is already at 4 or 5, bathing reduces Happiness -1
        if (hygiene >= 4) {
            happiness = Math.max(MIN_STAT, happiness - 1);
            pet.setHappiness(happiness);
            showStatus("I'm already clean!");
        } else {
            hygiene = Math.min(MAX_STAT, hygiene + 3);
            pet.setHygiene(hygiene);
            showStatus("Squeaky clean!");
        }

        lastBatheTime = now;

        updateMetersUI();
        updateActionButtons();
        savePetAsync();
        logAction("bathe");
    }

    private void updateMetersUI() {
        if (pet == null) return;
        updateSquaresForStat(happinessSquares, pet.getHappiness());
        updateSquaresForStat(hungerSquares, pet.getHunger());
        updateSquaresForStat(hygieneSquares, pet.getHygiene());
    }

    private void updateSquaresForStat(View[] squares, int value) {
        for (int i = 0; i < squares.length; i++) {
            if (squares[i] == null) continue;
            squares[i].setAlpha(i < value ? 1.0f : 0.2f);
        }
    }

    private void savePetAsync() {
        new Thread(() -> petDao.updatePet(pet)).start();
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

    // vvv vvv vvv LOGOUT MENU IMPLEMENTATION vvv vvv vvv
    // When copying the menu implementation, you must also include the following line in OnCreate:
    // invalidateOptionsMenu(); // Force update the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.logoutMenuItem);
        item.setVisible(true);

        // Retrieve the username from the previous activity (login or signup)
        Intent loginIntent = getIntent();
        String username = loginIntent.getStringExtra("USERNAME");

        // Use the collected username and set the Menu bar title to it
        item.setTitle(username);

        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                showLogoutDialog();
                return false;
            }
        });
        return true;
    }

    private void logAction(String actionType) {
        if (pet == null) return;

        // Assuming Pet has getPet_id()
        int petId = pet.getPet_id();

        Action action = new Action(actionType, petId);

        new Thread(() -> {
            actionDao.insertAction(action);
        }).start();
    }

    /**
     * Asks the user if they really want to log out. If so, they will be removed to login screen
     */
    private void showLogoutDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(GamePlayActivity.this);
        final AlertDialog alertDialog = alertBuilder.create();

        /*
         * Display a menu as:
         * | - - - - - - - - - - - - - - - |
         * | Do you really want to logout? |
         * |        Logout | Cancel        |
         * | - - - - - - - - - - - - - - - |
         */
        alertBuilder.setTitle("Do you really want to logout?");
        alertBuilder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logout();
            }
        });

        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        alertBuilder.create().show();
    }

    private void logout() {
        // TODO: Finish logout method
        Intent intent = new Intent(GamePlayActivity.this, MainActivity.class);
        startActivity(intent);
    }
    // ^^^ ^^^ ^^^ LOGOUT MENU IMPLEMENTATION ^^^ ^^^ ^^^
}