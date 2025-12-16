/**
 * ChoosePetColorActivity.java | Activity for choosing a pet's color after Sign Up
 * @author Cristian Perez
 * @since 12/15/25
 */

package com.talentengine.pocketpixelpets;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class ChoosePetColorActivity extends AppCompatActivity {

    private String username;

    // The sprite that reflects our current pet and color
    private SpriteView variableSprite;

    // The buttons used for selecting the color
    private MaterialButton purpleButton;
    private MaterialButton mintButton;
    private MaterialButton blueButton;
    private MaterialButton pinkButton;
    private MaterialButton selectedButton;      // A reference to our currently selected color

    // The button to go to the next activity
    private MaterialButton nextButton;

    // Used for dimming the buttons
    private static final float DIMMED_ALPHA = 0.35f;
    private static final float FULL_ALPHA = 1.0f;

    private String petType;     // {otter, fox, turtle} and defaults to otter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_pet_color);

        // Initialize all the assets

        // TODO: Pull the current pet from the last activity, currently defaults to otter
        petType = "otter";

        purpleButton = findViewById(R.id.purpleButton);
        mintButton = findViewById(R.id.mintButton);
        blueButton = findViewById(R.id.blueButton);
        pinkButton = findViewById(R.id.pinkButton);
        nextButton = findViewById(R.id.nextButton);
        variableSprite = findViewById(R.id.petSpritePreview);

        username = getIntent().getStringExtra("USERNAME");
        nextButton.setOnClickListener(v -> goToPersonality());

        // LOGOUT MENU IMPLEMENTATION - Force update the menu
        invalidateOptionsMenu();

        // Default select the purple button
        selectButton(purpleButton);

        // Start the animation
        variableSprite.setFrameDuration(1500);
        variableSprite.start();

        // TODO: Get the current TYPE of pet and render that sprite
        purpleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectButton(purpleButton);
            }
        });

        mintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectButton(mintButton);
            }
        });

        blueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectButton(blueButton);
            }
        });

        pinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectButton(pinkButton);
            }
        });

        // TODO: When our 'next' button is pressed, pass that along the chain
    }


    private void goToPersonality() {
        // Pass username to the next activity
        Intent intent = new Intent(this, ChoosePersonalityActivity.class);
        intent.putExtra("USERNAME", username);
        startActivity(intent);
        // Prevent going back to this activity
        finish();
    }

    /**
     * When the user selects a button, this button becomes active and all other buttons are greyed out
     */
    private void selectButton(MaterialButton pressedButton) {
        // If our selectedButton is null, initialize it
        if (selectedButton == null) {
            selectedButton = pressedButton;
        } else if (selectedButton.equals(pressedButton)) {
            // Return if the button is already selected
            return;
        }

        // If not, set all alpha channels to grey
        dimAllButtons();

        // Turn up the alpha channel of the current button
        pressedButton.setAlpha(FULL_ALPHA);

        // Keep track of the new selected button using our variable
        selectedButton = pressedButton;

        // TODO: Update the sprite
        updateSprite(pressedButton);
    }

    /**
     * Dims all the buttons to the preset DIMMED_ALPHA opacity value
     */
    private void dimAllButtons() {
        // Set each of the button's alphas to grey
        purpleButton.setAlpha(DIMMED_ALPHA);
        mintButton.setAlpha(DIMMED_ALPHA);
        blueButton.setAlpha(DIMMED_ALPHA);
        pinkButton.setAlpha(DIMMED_ALPHA);
    }

    /**
     * Given the button that was pressed, set the sprite to match the animal and color
     * @param button the currently selected button
     */
    void updateSprite(MaterialButton button) {
        // Dynamically generate the filename based on the creature type and color
        StringBuilder fileName = new StringBuilder();

        String color = "";
        if (button.equals(purpleButton)) {
            color = "purple";
        } else if (button.equals(mintButton)) {
            color = "green";
        } else if (button.equals(blueButton)) {
            color = "blue";
        } else if (button.equals(pinkButton)) {
            color = "pink";
        }

        // The filename of the sprite sheet is organized as {pet_type}_sprite_sheet_{color}.png
        fileName.append(petType).append("_sprite_sheet_").append(color);

        // Convert the filename to an id as the setSpriteSheet method relies on it
        int resId = getResources().getIdentifier(fileName.toString(), "drawable", getPackageName());

        // Update the sprite
        variableSprite.setSpriteSheet(resId, 2, 3);
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

    /**
     * Asks the user if they really want to log out. If so, they will be removed to login screen
     */
    private void showLogoutDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ChoosePetColorActivity.this);
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
        Intent intent = new Intent(ChoosePetColorActivity.this, MainActivity.class);
        startActivity(intent);
    }
    // ^^^ ^^^ ^^^ LOGOUT MENU IMPLEMENTATION ^^^ ^^^ ^^^
}