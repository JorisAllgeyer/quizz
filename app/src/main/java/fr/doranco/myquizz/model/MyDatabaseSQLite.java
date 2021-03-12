package fr.doranco.myquizz.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseSQLite extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "QuizDB";
    private static final Integer DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "user";
    private static final String CREATE_DB_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL, score INT NOT NULL)";

    protected MyDatabaseSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DB_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL(CREATE_DB_TABLE);
    }
}
