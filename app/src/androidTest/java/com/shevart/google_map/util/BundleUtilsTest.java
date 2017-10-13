package com.shevart.google_map.util;

import android.content.Intent;

import com.shevart.google_map.utils_for_test.MockUtil;
import com.shevart.google_map.utils_for_test.TestUtil;
import com.shevart.google_map.models.TripPoint;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Example for Android Instrumented tests for {@link BundleUtils}
 */
public class BundleUtilsTest {
    @Test
    public void testPassingTripPoint() throws Exception {
        final Intent intent = new Intent();
        final TripPoint tripPoint = MockUtil.getMockTripPoint();
        // set tripPoint to intent
        BundleUtils.setTripPoint(intent, tripPoint);
        // compare first tripPoint with tripPoint from intent
        final TripPoint fromIntent = BundleUtils.getTripPoint(intent);
        assertThat(TestUtil.isEquals(tripPoint, fromIntent), is(true));
    }
}