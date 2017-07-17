package com.tarrotsystem.codepreneur.bakingrite;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by codepreneur on 7/17/17.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class DetailTest {

    @Rule
    public ActivityTestRule<DetailActivity> mActivityRule = new ActivityTestRule<>(
            DetailActivity.class);

    //Elaborate Test

    @Test
    public void checkIngredientDialogDisplay_HasIngredient() {
        onView(ViewMatchers.withId(R.id.recipe_recycler)).perform(RecyclerViewActions.actionOnItemAtPosition(1,click()));
        onView(ViewMatchers.withId(R.id.steps_recycler)).perform(RecyclerViewActions.actionOnItemAtPosition(1,click()));
        onView(withId(R.id.btnIngredients)).perform(click());

        onView(ViewMatchers.withId(R.id.ingredient))
                .inRoot(isDialog())
                .check(matches(isDisplayed()));
    }
}
