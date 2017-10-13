package com.shevart.google_map.data.db;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;

import static junit.framework.Assert.assertTrue;

@SuppressWarnings("WeakerAccess")
abstract class DatabaseTest {
    protected Context context;
    protected DB databaseManager;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getTargetContext();
        databaseManager = new DatabaseManager(context);
    }

    @After
    public void shutDown() {
        assertTrue(context.deleteDatabase(DbOpenHelper.DB_NAME));
    }
}
