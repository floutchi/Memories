package org.helmo.memories;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import org.helmo.memories.utils.RecyclerViewItemCount;
import org.helmo.memories.view.activities.MainActivity;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.widget.Button;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AddMemoryTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<MainActivity>(MainActivity.class);

    @BeforeClass
    public static void beforeClass() {
        ApplicationProvider.getApplicationContext().deleteDatabase("memories_database");
    }

    @Test
    public void testClickAddResultNewFragmentContainsFieldAndButton() {
        onView(ViewMatchers.withId(R.id.add_btn)).perform(click());

        onView(withId(R.id.addName)).check(matches(withText("")));
        onView(withId(R.id.addDescription)).check(matches(withText("")));
        onView(withId(R.id.addDateBtn)).check(matches(withText("Ajouter une date")));
        onView(withId(R.id.addPlaceBtn)).check(matches(withText("Ajouter un lieu")));
        onView(withId(R.id.addPicBtn)).check(matches(withText("Ajouter une photo")));
        onView(withId(R.id.takePicBtn)).check(matches(withText("Prendre une photo")));

        onView(withId(R.id.addMemoryBtn)).check(matches(withText("Ajouter le souvenir")));
    }

    @Test
    public void testClickAddResultSetMemoryAndPreviousThenMemoryCreated() {
        onView(ViewMatchers.withId(R.id.add_btn)).perform(click());

        onView(withId(R.id.addName)).perform(typeText("test"), closeSoftKeyboard());
        onView(withId(R.id.addDescription)).perform(typeText("test"), closeSoftKeyboard());

        onView(withId(R.id.addMemoryBtn)).perform(click());

        onView(withId(R.id.fragment_container)).check(new RecyclerViewItemCount(1));
    }



}
