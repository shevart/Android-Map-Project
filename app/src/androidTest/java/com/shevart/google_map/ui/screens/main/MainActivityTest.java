package com.shevart.google_map.ui.screens.main;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import com.shevart.google_map.R;
import com.shevart.google_map.util.SystemUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


public class MainActivityTest {
    private Context targetContext;

    @Rule
    public ActivityTestRule<MainActivity> mainActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void startUp() {
        targetContext = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void testStartUpLogic() throws InterruptedException {
        testAskATurnOnGPSWhenAppStarted();
        testIsFirstFieldHasFocusWhenActivityStarted();
        testFieldsFocusChangingDependsOnClicks();
    }

    // check - if GPS dialog showed - and click No for turn On GPS
    private void testAskATurnOnGPSWhenAppStarted() {
        if (!SystemUtils.GPS.isGPSEnabled(targetContext)) {
            // check is dialog showed
            onView(withText(targetContext.getString(R.string.ask_gps_turn_on_msg)))
                    .check(matches(isDisplayed()));
            // close dialog
            onView(withText(targetContext.getString(R.string.no)))
                    .perform(click());
        }
    }

    private void testIsFirstFieldHasFocusWhenActivityStarted() throws InterruptedException {
        // wait until dialog will close
        Thread.sleep(1000);
        // check is first field has focus
        onView(ViewMatchers.withId(R.id.etRouteStart))
                .check(matches(ViewMatchers.hasFocus()));
    }

    private void testFieldsFocusChangingDependsOnClicks() {
        // click on the first field and check is focused
        onView(ViewMatchers.withId(R.id.etRouteStart))
                .perform(click());
        onView(ViewMatchers.withId(R.id.etRouteStart))
                .check(matches(ViewMatchers.hasFocus()));

        // click on the second field and check is focused
        onView(ViewMatchers.withId(R.id.etRouteEnd))
                .perform(click());
        onView(ViewMatchers.withId(R.id.etRouteEnd))
                .check(matches(ViewMatchers.hasFocus()));
    }

}