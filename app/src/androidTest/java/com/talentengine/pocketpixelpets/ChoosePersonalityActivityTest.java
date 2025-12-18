package com.talentengine.pocketpixelpets;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import android.view.View;

@RunWith(AndroidJUnit4.class)
public class ChoosePersonalityActivityTest {
    public static final float IS_DIMMED_ALPHA = 0.3f;
    public static final float IS_FULL_ALPHA = 1.0f;

    float cheerfulAlpha;
    float lazyAlpha;
    float playfulAlpha;
    float cleanFreakAlpha;

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
            View cheerfulCard = activity.findViewById(R.id.cheerfulButton);
            View lazyCard = activity.findViewById(R.id.lazyButton);
            View playfulCard = activity.findViewById(R.id.playfulButton);
            View clearFreakCard = activity.findViewById(R.id.cleanFreakButton);

            // Get the current alpha and check that the initial state of the cards are low in opacity
            cheerfulAlpha = cheerfulCard.getAlpha();
            lazyAlpha = lazyCard.getAlpha();              //card should return 0.3f
            playfulAlpha = playfulCard.getAlpha();
            cleanFreakAlpha = clearFreakCard.getAlpha();

            assertEquals(IS_DIMMED_ALPHA, cheerfulAlpha, 0.1f);
            assertEquals(IS_DIMMED_ALPHA, lazyAlpha, 0.1f);
            assertEquals(IS_DIMMED_ALPHA, playfulAlpha, 0.1f);
            assertEquals(IS_DIMMED_ALPHA, cleanFreakAlpha, 0.1f);
        });
    }
    @Test
    public void cheerfulCardHighlightsWhenSelected() {
        //onView(withId(R.id.cheerfulButton)).perform()
        //assertEquals(IS_FULL_ALPHA, cheerfulAlpha, 1.0f);
        //assertEquals(IS_DIMMED_ALPHA, lazyAlpha, 0.1f);
        //assertNotEquals(IS_DIMMED_ALPHA, playfulAlpha, 1.0f);
        //assertEquals(IS_DIMMED_ALPHA, cleanFreakAlpha, 1.0f);
    }

}
