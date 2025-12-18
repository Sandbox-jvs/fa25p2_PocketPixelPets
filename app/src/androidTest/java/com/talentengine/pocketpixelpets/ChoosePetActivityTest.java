package com.talentengine.pocketpixelpets;

import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isNotEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

@RunWith(AndroidJUnit4.class)
public class ChoosePetActivityTest {

    @Rule
    public ActivityScenarioRule<ChoosePetActivity> activityRule =
            new ActivityScenarioRule<>(
                    new Intent(
                            ApplicationProvider.getApplicationContext(),
                            ChoosePetActivity.class
                    )
            );

    @Test
    public void screenStarts_titleIsVisible() {
        onView(withId(R.id.choosePetTitle))
                .check(matches(isDisplayed()));
    }

    @Test
    public void continueButton_isInitiallyDisabled() {
        onView(withId(R.id.continueButton))
                .check(matches(isNotEnabled()));
    }

    @Test
    public void selectingPet_enablesNextButton() {
        onView(withId(R.id.otterSpriteView))
                .perform(click());

        onView(withId(R.id.continueButton))
                .check(matches(isEnabled()));
    }
}
