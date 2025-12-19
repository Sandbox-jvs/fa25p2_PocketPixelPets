package com.talentengine.pocketpixelpets;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import android.view.View;

/**
 * Test file that checks if the UI launches successfully, checks that the default opacity for the
 * material card views are low, checks if the selected card is the only one highlighted, checks that
 * the next button does not navigate to the next activity without a selection made.
 * @author Jessica Sandoval
 * @since 12/18/2025
 */

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
            assertEquals(IS_FULL_ALPHA, cheerfulAlpha, 0.1f);
            assertEquals(IS_DIMMED_ALPHA, lazyAlpha, 0.1f);
            assertEquals(IS_DIMMED_ALPHA, playfulAlpha, 0.1f);
            assertEquals(IS_DIMMED_ALPHA, cleanFreakAlpha, 0.1f);

        });
    }

    @Test
    public void lazyCardHighlightsWhenSelected() {
        onView(withId(R.id.lazyButton)).perform(click());

        //Need to reference the alpha for comparative test
        rule.getScenario().onActivity(activity -> {

            //Reference material cards
            lazyCard = activity.findViewById(R.id.lazyButton);
            cheerfulCard = activity.findViewById(R.id.cheerfulButton);
            playfulCard = activity.findViewById(R.id.playfulButton);
            cleanFreakCard = activity.findViewById(R.id.cleanFreakButton);

            //Obtain the current alpha
            lazyAlpha = lazyCard.getAlpha();
            cheerfulAlpha = cheerfulCard.getAlpha();
            playfulAlpha = playfulCard.getAlpha();
            cleanFreakAlpha = cleanFreakCard.getAlpha();

            //Compare the values to ensure only the cheerful card is highlighted
            assertEquals(IS_FULL_ALPHA, lazyAlpha, 0.1f);
            assertEquals(IS_DIMMED_ALPHA, cheerfulAlpha, 0.1f);
            assertEquals(IS_DIMMED_ALPHA, playfulAlpha, 0.1f);
            assertEquals(IS_DIMMED_ALPHA, cleanFreakAlpha, 0.1f);

        });
    }

    @Test
    public void playfulCardHighlightsWhenSelected() {
        onView(withId(R.id.playfulButton)).perform(click());

        //Need to reference the alpha for comparative test
        rule.getScenario().onActivity(activity -> {

            //Reference material cards
            playfulCard = activity.findViewById(R.id.playfulButton);
            cheerfulCard = activity.findViewById(R.id.cheerfulButton);
            lazyCard = activity.findViewById(R.id.lazyButton);
            cleanFreakCard = activity.findViewById(R.id.cleanFreakButton);

            //Obtain the current alpha
            playfulAlpha = playfulCard.getAlpha();
            cheerfulAlpha = cheerfulCard.getAlpha();
            lazyAlpha = lazyCard.getAlpha();
            cleanFreakAlpha = cleanFreakCard.getAlpha();

            //Compare the values to ensure only the cheerful card is highlighted
            assertEquals(IS_FULL_ALPHA, playfulAlpha, 0.1f);
            assertEquals(IS_DIMMED_ALPHA, cheerfulAlpha, 0.1f);
            assertEquals(IS_DIMMED_ALPHA, lazyAlpha, 0.1f);
            assertEquals(IS_DIMMED_ALPHA, cleanFreakAlpha, 0.1f);

        });
    }
    @Test
    public void cleanFreakCardHighlightsWhenSelected() {
        onView(withId(R.id.cleanFreakButton)).perform(click());

        //Need to reference the alpha for comparative test
        rule.getScenario().onActivity(activity -> {

            //Reference material cards
            cleanFreakCard = activity.findViewById(R.id.cleanFreakButton);
            cheerfulCard = activity.findViewById(R.id.cheerfulButton);
            lazyCard = activity.findViewById(R.id.lazyButton);
            playfulCard = activity.findViewById(R.id.playfulButton);

            //Obtain the current alpha
            cleanFreakAlpha = cleanFreakCard.getAlpha();
            cheerfulAlpha = cheerfulCard.getAlpha();
            lazyAlpha = lazyCard.getAlpha();
            playfulAlpha = playfulCard.getAlpha();

            //Compare the values to ensure only the cheerful card is highlighted
            assertEquals(IS_FULL_ALPHA, cleanFreakAlpha, 0.1f);
            assertEquals(IS_DIMMED_ALPHA, cheerfulAlpha, 0.1f);
            assertEquals(IS_DIMMED_ALPHA, lazyAlpha, 0.1f);
            assertEquals(IS_DIMMED_ALPHA, playfulAlpha, 0.1f);

        });
    }

    /**
     * This test checks to see if the activity changed when next button is clicked before making
     * a selection. Should pass only if the display is on the same activity
     */
    @Test
    public void nextButtonWillNotNavigateBeforeSelection() {
        // Activity will not change if next button is clicked before selection
        onView(withId(R.id.nextButtonPersonality)).perform(click());

        //Check to see if the view is still on the choose personality Activity
        onView(withId(R.id.choosePersonalityTextView)).check(matches(isDisplayed()));
    }

    @Test
    public void nextButtonNavigatesAfterSelection() {
        //Click on a card
        onView(withId(R.id.playfulButton)).perform(click());

        //click on next button
        onView(withId(R.id.nextButtonPersonality)).check(matches(isDisplayed())).perform(click());

        //Check if activity was changed
        onView(withId(R.id.chooseFoodSubtitleTextView)).check(matches(isDisplayed()));
    }
}
