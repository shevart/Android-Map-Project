package com.shevart.google_map.data.db;

import android.support.test.runner.AndroidJUnit4;

import com.shevart.google_map.utils_for_test.MockUtil;
import com.shevart.google_map.utils_for_test.TestUtil;
import com.shevart.google_map.models.TripPoint;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class DatabaseManagerTest extends DatabaseTest {
    @Test
    public void testSave() {
        final TripPoint tripPoint = MockUtil.getMockTripPoint();
        databaseManager.save(tripPoint);
        final TripPoint fromDB = databaseManager.getTripPointsFromHistory(1).get(0);
        assertThat(TestUtil.isEquals(tripPoint, fromDB), is(true));
    }

    @Test
    public void testClearHistory() {
        // save some points
        databaseManager.save(MockUtil.getMockTripPoint());
        databaseManager.save(MockUtil.getMockTripPoint());
        databaseManager.save(MockUtil.getMockTripPoint());
        // clear history in database
        databaseManager.clearTripPointsHistory();
        // get history size in DB, must be 0
        final int size = databaseManager.getTripPointsFromHistory(Integer.MAX_VALUE).size();
        assertThat(size, is(0));
    }

    @Test
    public void testSaveAndReadListOfTripPoints() {
        final int LIST_SIZE = 50;
        // clear history
        databaseManager.clearTripPointsHistory();
        // save 50 items to DB
        for (int i = 0; i < LIST_SIZE; i++) {
            databaseManager.save(MockUtil.getMockTripPoint());
        }
        final int size = databaseManager.getTripPointsFromHistory(Integer.MAX_VALUE).size();
        assertThat(size, is(LIST_SIZE));
    }

    @Test
    public void testReadListLimit() {
        final int LIMIT = 1;
        final int LIMIT2 = 10;
        // clear history
        databaseManager.clearTripPointsHistory();
        // save 50 items to DB
        for (int i = 0; i < 50; i++) {
            databaseManager.save(MockUtil.getMockTripPoint());
        }
        final int sizeForLimit = databaseManager.getTripPointsFromHistory(LIMIT).size();
        final int sizeForLimit2 = databaseManager.getTripPointsFromHistory(LIMIT2).size();
        assertThat(sizeForLimit, is(LIMIT));
        assertThat(sizeForLimit2, is(LIMIT2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullObjectSave() {
        //noinspection ConstantConditions
        databaseManager.save(null);
    }
}