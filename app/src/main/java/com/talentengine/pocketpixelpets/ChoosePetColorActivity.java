/**
 * ChoosePetColorActivity.java | Activity for choosing a pet's color after Sign Up
 * @author Cristian Perez
 * @since 12/15/25
 */

package com.talentengine.pocketpixelpets;

import android.os.Bundle;
import android.widget.Button;

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


        // TODO: Default select one of the buttons, greying out the rest
        selectButton(purpleButton);

        // TODO: Get the current TYPE of pet and render that sprite

        // TODO: When our 'next' button is pressed, pass that along the chain
    }



    /**
     * When the user selects a button, this button becomes active and all other buttons are greyed out
     */
    private void selectButton(MaterialButton pressedButton) {
        // Check if our button is already selected
        if (selectedButton.equals(pressedButton)) {
            return;
        }

        // If not, set all alpha channels to grey
        dimAllButtons();

        // Turn up the alpha channel of the current button
        pressedButton.setAlpha(FULL_ALPHA);

        // Keep track of the new selected button using our variable
        selectedButton = pressedButton;

        // TODO: Update the sprite
    }

    private void dimAllButtons() {
        // Set each of the button's alphas to grey
        purpleButton.setAlpha(DIMMED_ALPHA);
        mintButton.setAlpha(DIMMED_ALPHA);
        blueButton.setAlpha(DIMMED_ALPHA);
        pinkButton.setAlpha(DIMMED_ALPHA);
    }
}