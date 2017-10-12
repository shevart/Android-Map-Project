package com.shevart.google_map.app;

import com.shevart.google_map.data.db.DB;
import com.shevart.google_map.data.net.Net;

public interface App {
    Net getNet();

    DB getDB();
}
