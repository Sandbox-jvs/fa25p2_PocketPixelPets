/**
 * ChoosePetActivity.java | Activity for choosing a pet during user sign-up
 * @author Emmanuel Garcia
 * @since 12/8/25
 */

package com.talentengine.pocketpixelpets;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class ChoosePetActivity extends AppCompatActivity {

    private SpriteView otterSpriteView;
    private SpriteView turtleSpriteView;
    private SpriteView foxSpriteView;
    private MaterialButton continueButton;

    private String selectedPet = null;

    private static final float DIMMED_ALPHA = 0.35f;
    private static final float FULL_ALPHA = 1.0f;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_pet);

        otterSpriteView = findViewById(R.id.otterSpriteView);
        turtleSpriteView = findViewById(R.id.turtleSpriteView);
        foxSpriteView = findViewById(R.id.foxSpriteView);
        continueButton = findViewById(R.id.loginButton);

        // LOGOUT MENU IMPLEMENTATION - Force update the menu
        invalidateOptionsMenu();

        otterSpriteView.setSpriteSheet(
                R.drawable.otter_sprite_sheet_purple,
                2, 3
        );
        turtleSpriteView.setSpriteSheet(
                R.drawable.turtle_sprite_sheet_green,
                2, 3
        );
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

        resetSpritesToDimmed();

        continueButton.setEnabled(false);
        continueButton.setAlpha(DIMMED_ALPHA);

        otterSpriteView.setOnClickListener(v -> onPetSelected("otter"));
        turtleSpriteView.setOnClickListener(v -> onPetSelected("turtle"));
        foxSpriteView.setOnClickListener(v -> onPetSelected("fox"));

        continueButton.setOnClickListener(v -> onContinueClicked());
    }


    private void onPetSelected(String petId) {
        selectedPet = petId;

        resetSpritesToDimmed();

        // Set selected pet to full alpha
        switch (petId) {
            case "otter":
                otterSpriteView.setAlpha(FULL_ALPHA);
                break;
            case "turtle":
                turtleSpriteView.setAlpha(FULL_ALPHA);
                break;
            case "fox":
                foxSpriteView.setAlpha(FULL_ALPHA);
                break;
        }

        continueButton.setEnabled(true);
        continueButton.setAlpha(FULL_ALPHA);
    }

    private void resetSpritesToDimmed() {
        otterSpriteView.setAlpha(DIMMED_ALPHA);
        turtleSpriteView.setAlpha(DIMMED_ALPHA);
        foxSpriteView.setAlpha(DIMMED_ALPHA);
    }
    private void onContinueClicked() {
        if (selectedPet == null) {
            Toast.makeText(this, "Please choose a pet first!", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("selected_pet", selectedPet);
        startActivity(intent);
        finish();
    }


    // vvv vvv vvv LOGOUT MENU IMPLEMENTATION vvv vvv vvv
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

        // TODO: UPDATE THE TITLE TO REFLECT THE USERNAME OF THE CURRENT USER
        item.setTitle("TEST");      // Set the username

        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                // TODO: Logout Alert Dialog
                showLogoutDialog();
                return false;
            }
        });
        return true;
    }

    /**
     * Asks the user if they really want to log out. If so, they will be removed to login screen
     */
    private void showLogoutDialog() {
        // TODO: Update MainActivity to reflect the current activity
        // TODO: Move the logout functionality to the Choose Pet activity
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ChoosePetActivity.this);
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
        // TODO: Start the activity
        Toast.makeText(this, "LOGGED OUT!", Toast.LENGTH_SHORT).show();
    }
    // ^^^ ^^^ ^^^ LOGOUT MENU IMPLEMENTATION ^^^ ^^^ ^^^
}
