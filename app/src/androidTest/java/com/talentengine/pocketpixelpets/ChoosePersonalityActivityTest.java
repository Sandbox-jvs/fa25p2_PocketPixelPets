package com.talentengine.pocketpixelpets;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import android.util.Log;
import android.view.View;

@RunWith(AndroidJUnit4.class)
public class ChoosePersonalityActivityTest {
    public static final float IS_DIMMED_ALPHA = 0.3f;
    public static final float IS_FULL_ALPHA = 1.0f;
    //Variables used to store the current alpha of the target card for comparison
    float cheerfulAlpha;
    float lazyAlpha;
    float playfulAlpha;
    float cleanFreakAlpha;

    //Views that will reference the material card id for viewing
    View cheerfulCard;
    View lazyCard;
    View playfulCard;
    View cleanFreakCard;

    /**
     * Creates a rule to launch the activity automatically for every test method
     */
    @Rule
    public ActivityScenarioRule<ChoosePersonalityActivity> rule =
                new ActivityScenarioRule<>(ChoosePersonalityActivity.class);

    /**
     * Test that the activity launches successfully
     */
    @Test
    public void activityLaunchSuccessful() {
        rule.getScenario().onActivity(activity -> {
           assertNotNull(activity);
        });
    }

    /**
     * Check to see if the initial state of the material cards are dim by default
     */
    @Test
    public void initialState_allCardsDimmed() {
        rule.getScenario().onActivity(activity -> {
            //Reference the view cards from the xml using the created instance of the activity
            cheerfulCard = activity.findViewById(R.id.cheerfulButton);
            lazyCard = activity.findViewById(R.id.lazyButton);
            playfulCard = activity.findViewById(R.id.playfulButton);
            cleanFreakCard = activity.findViewById(R.id.cleanFreakButton);

            // Get the current alpha and check that the initial state of the cards are low in opacity
            cheerfulAlpha = cheerfulCard.getAlpha();
            lazyAlpha = lazyCard.getAlpha();              //card should return 0.3f
            playfulAlpha = playfulCard.getAlpha();
            cleanFreakAlpha = cleanFreakCard.getAlpha();

            assertEquals(IS_DIMMED_ALPHA, cheerfulAlpha, 0.1f);
            assertEquals(IS_DIMMED_ALPHA, lazyAlpha, 0.1f);
            assertEquals(IS_DIMMED_ALPHA, playfulAlpha, 0.1f);
            assertEquals(IS_DIMMED_ALPHA, cleanFreakAlpha, 0.1f);
        });
    }
    @Test
    public void cheerfulCardHighlightsWhenSelected() {
        onView(withId(R.id.cheerfulButton)).perform(click());
        //Need to reference the alpha for comparative test
        rule.getScenario().onActivity(activity -> {

            //Reference material cards
            cheerfulCard = activity.findViewById(R.id.cheerfulButton);
            lazyCard = activity.findViewById(R.id.lazyButton);
            playfulCard = activity.findViewById(R.id.playfulButton);
            cleanFreakCard = activity.findViewById(R.id.cleanFreakButton);

            //Obtain the current alpha
            cheerfulAlpha = cheerfulCard.getAlpha();
            lazyAlpha = lazyCard.getAlpha();
            playfulAlpha = playfulCard.getAlpha();
            cleanFreakAlpha = cleanFreakCard.getAlpha();

            //Compare the values to ensure only the cheerful card is highlighted
            assertEquals(IS_FULL_ALPHA, cheerfulAlpha, 1.0f);
            assertEquals(IS_DIMMED_ALPHA, lazyAlpha, 0.1f);
            assertEquals(IS_DIMMED_ALPHA, playfulAlpha, 0.1f);
            assertEquals(IS_DIMMED_ALPHA, cleanFreakAlpha, 0.1f);

            Log.d("Test", "Cheerful card selected.\n" +
                    "Cheerful= " + cheerfulAlpha +
                    "Lazy= " + lazyAlpha +
                    "Playful" + playfulAlpha +
                    "Clean Freak= " + cleanFreakAlpha
            );
        });
    }

}
