/**
 * ChoosePetColorActivity.java | Activity for choosing a pet's color after Sign Up
 * @author Cristian Perez
 * @since 12/15/25
 */

package com.talentengine.pocketpixelpets;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ChoosePetColorActivity extends AppCompatActivity {

    // The sprite that reflects our current pet and color
    private SpriteView variableSprite;

    // The buttons used for selecting the color
    private Button purpleButton;
    private Button mintButton;
    private Button blueButton;
    private Button pinkButton;
    private Button selectedButton;      // A reference to our currently selected color

    // The button to go to the next activity
    private Button nextButton;

    // Used for dimming the buttons
    private static final float DIMMED_ALPHA = 0.35f;
    private static final float FULL_ALPHA = 1.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_pet_color);

        // Initialize all the assets
        // TODO: Pull the current pet from the last activity, currently defaults to otter

        // TODO: Default select one of the buttons, greying out the rest

        // TODO: Get the current TYPE of pet and render that sprite

        // TODO: When our 'next' button is pressed, pass that along the chain
    }



    /**
     * When the user selects a button, this button becomes active and all other buttons are greyed out
     */
    private void selectButton(Button pressedButton) {
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
    }

    private void dimAllButtons() {
        // Set each of the button's alphas to grey
        purpleButton.setAlpha(DIMMED_ALPHA);
        mintButton.setAlpha(DIMMED_ALPHA);
        blueButton.setAlpha(DIMMED_ALPHA);
        pinkButton.setAlpha(DIMMED_ALPHA);
    }
}