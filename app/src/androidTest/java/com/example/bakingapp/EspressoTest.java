package com.example.bakingapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;



@RunWith(AndroidJUnit4.class)
public class EspressoTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);
    @Before
    public void servic(){
        WidgetProvider.StartService(activityTestRule.getActivity());
    }





    @Test
    public void fm(){
        onView(withId(R.id.recycleview)).perform(click());
        onView(withId(R.id.prevRecipe)).check(matches(withText("Prev")));
        onView(withId(R.id.stepsRecycle)).perform(click());
        onView(withId(R.id.stepText)).check(matches(isDisplayed()));


    }
}

