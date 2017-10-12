package com.shevart.google_map.data.db.contract;

@SuppressWarnings("WeakerAccess")
public class TripPointContract extends Contract {
    public static final String TABLE_NAME = "history_trip_points";

    public static final String NAME = "name";
    public static final String ADDRESS = "address";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";

    public static final class Script {
        public static final String CREATE = CREATE_TABLE + TABLE_NAME + OPEN +
                ID + INTEGER_PRIMARY_KEY_AUTOINCREMENT + COMMA +
                NAME + TEXT + COMMA +
                ADDRESS + TEXT + COMMA +
                LATITUDE + REAL + COMMA +
                LONGITUDE + REAL + CLOSE;

        public static final String INSERT = INSERT_INTO + TABLE_NAME + OPEN +
                NAME + COMMA +
                ADDRESS + COMMA +
                LATITUDE + COMMA +
                LONGITUDE +
                CLOSE + VALUES + OPEN + "(?, ?, ?, ?)" + CLOSE;

    }
}