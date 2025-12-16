/**
 * ChoosePetColorActivity.java | Activity for choosing a pet's color after Sign Up
 * @author Cristian Perez
 * @since 12/15/25
 */

package com.talentengine.pocketpixelpets;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class ChoosePetColorActivity extends AppCompatActivity {

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
}