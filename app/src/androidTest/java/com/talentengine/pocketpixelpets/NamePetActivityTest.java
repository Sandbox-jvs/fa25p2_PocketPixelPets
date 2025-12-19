package com.talentengine.pocketpixelpets;

import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class NamePetActivityTest {

    @Rule
    public ActivityScenarioRule<NamePetActivity> activityRule =
            new ActivityScenarioRule<>(
                    new Intent(
                            ApplicationProvider.getApplicationContext(),
                            NamePetActivity.class
                    )
            );

    @Test
    public void screenStarts_inputFieldIsVisible() {
        onView(withId(R.id.petNameInput))
                .check(matches(isDisplayed()));
    }

    @Test
    public void screenStarts_nextButtonIsVisible() {
        onView(withId(R.id.nextButtonName))
                .check(matches(isDisplayed()));
    }

    @Test
    public void enteringName_displaysInField() {
        onView(withId(R.id.petNameInput))
                .perform(typeText("Sparky"), closeSoftKeyboard());

        onView(withId(R.id.petNameInput))
                .check(matches(withText("Sparky")));
    }
}
