package org.helmo.memories;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import org.helmo.memories.utils.RecyclerViewItemCount;
import org.helmo.memories.view.activities.MainActivity;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DeleteMemoryTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<MainActivity>(MainActivity.class);

    @BeforeClass
    public static void beforeClass() {
        ApplicationProvider.getApplicationContext().deleteDatabase("memories_database");
    }

    @Test
    public void testClickOnDeleteMemoryShowNoMemory() {
        onView(ViewMatchers.withId(R.id.add_btn)).perform(click());

        onView(withId(R.id.addName)).perform(typeText("test"), closeSoftKeyboard());
        onView(withId(R.id.addDescription)).perform(typeText("test"), closeSoftKeyboard());

        onView(withId(R.id.addMemoryBtn)).perform(click());

        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.deleteBtn)).perform(click());

        onView(withText("Oui")).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.fragment_container)).check(new RecyclerViewItemCount(0));
    }


}
