package com.shevart.google_map.data.db.contract;

/***
 *  <h3>Developer description</h3>
 *  <br/><br/>
 *  Class which define base SQLite constants for work with SQLite
 *  <br/><br/>
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class Contract {
    public static final String CREATE_TABLE = "CREATE TABLE ";
    public static final String ID = " id ";
    public static final String OPEN = " ( ";
    public static final String CLOSE = " )";
    public static final String END_STATEMENT = ";";
    public static final String INTEGER = " INTEGER ";
    public static final String INTEGER_PRIMARY_KEY_AUTOINCREMENT = " INTEGER PRIMARY KEY AUTOINCREMENT ";
    public static final String INTEGER_PRIMARY_KEY = " INTEGER PRIMARY KEY ";
    public static final String TEXT = " TEXT ";
    public static final String TEXT_PRIMARY_KEY = " TEXT PRIMARY KEY ";
    public static final String REAL = " REAL ";
    public static final String PRIMARY_KEY = " PRIMARY KEY ";
    public static final String AUTOINCREMENT = " AUTOINCREMENT ";
    public static final String NON_NULL = " NON NUL ";
    public static final String DEFAULT = " DEFAULT ";
    public static final String ZERO = " 0 ";
    public static final String FALSE = " 0 ";
    public static final String TRUE = " 1 ";
    public static final String DEFAULT_ID = "DEFAULT - 1";

    public static final String INSERT_OR_REPLACE_INTO = "INSERT OR REPLACE INTO ";
    public static final String INSERT_OR_IGNORE_INTO = "INSERT OR IGNORE INTO ";
    public static final String INSERT_INTO = "INSERT INTO ";
    public static final String UPDATE = "UPDATE ";
    public static final String SET = " SET ";
    public static final String DROP_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS ";
    public static final String DELETE_FROM = " DELETE FROM ";
    public static final String VALUES = " VALUES ";

    public static final String CREATE_TRIGGER = "CREATE TRIGGER ";
    public static final String AFTER_DELETE_ON = " AFTER DELETE ON ";
    public static final String AFTER_UPDATE_ON = " AFTER UPDATE ON ";
    public static final String FOR_EACH_ROW = " FOR EACH ROW ";
    public static final String EQUALS = " = ";
    public static final String GREATER_THEN = " > ";
    public static final String LESS_THEN = " < ";
    public static final String OLD_VARIABLE = " OLD.";
    public static final String BEGIN = " BEGIN ";
    public static final String END = " END ";
    public static final String OR = " OR ";
    public static final String IN = " IN ";
    public static final String AND = " AND ";
    public static final String WHERE = " WHERE ";
    public static final String SELECT = "SELECT ";
    public static final String ALL = " * ";
    public static final String FROM = " FROM ";
    public static final String AS = " AS ";
    public static final String ON = " ON ";
    public static final String ORDER_BY = " ORDER BY ";
    public static final String ASC = " ASC ";
    public static final String DESC = " DESC ";
    public static final String LIMIT = " LIMIT ";
    public static final String LEFT_OUTER_JOIN = " LEFT OUTER JOIN ";
    public static final String INNER_JOIN = " INNER JOIN ";
    public static final String VARIABLE = "?";
    public static final String VARIABLE_STRING = "%s";

    public static final String COMMA = ",";
    public static final String DOT = ".";

    public static final String DEFAULT_JOIN_SUFFIX = "_1";
}