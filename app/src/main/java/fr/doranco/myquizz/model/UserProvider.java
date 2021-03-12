package fr.doranco.myquizz.model;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;

public class UserProvider extends ContentProvider {

    private SQLiteDatabase db;
    private static final String TABLE_NAME_USERS = "user";
    private static final String PROVIDER_NAME_USERS = "fr.doranco.myquizz.model.UserProvider";
    protected static final Uri CONTENT_USERS_URI = Uri.parse("content://" + PROVIDER_NAME_USERS + "/" + TABLE_NAME_USERS);

    private static final UriMatcher uriMatcher;
    private static final int URI_CODE_DEFAULT = 0;
    private static final int URI_CODE_DELETE = 1;
    private static final int URI_CODE_UPDATE = 2;
    private static final int URI_CODE_INSERT = 3;

    private static HashMap<String, String> values;

    static {
        // Default: no match.
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME_USERS, TABLE_NAME_USERS, URI_CODE_DEFAULT);
        uriMatcher.addURI(PROVIDER_NAME_USERS, TABLE_NAME_USERS + "/delete", URI_CODE_DELETE);
        uriMatcher.addURI(PROVIDER_NAME_USERS, TABLE_NAME_USERS + "/update", URI_CODE_UPDATE);
        uriMatcher.addURI(PROVIDER_NAME_USERS, TABLE_NAME_USERS + "/insert", URI_CODE_INSERT);
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        MyDatabaseSQLite dbHelper = new MyDatabaseSQLite(context);
        db = dbHelper.getWritableDatabase();

        if (db != null)
            return true;

        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] columns, String selection, String[] selectionArgs,
                        String sortOrder) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TABLE_NAME_USERS);

        // Check if uri is permitted
        switch (uriMatcher.match(uri)) {
            case URI_CODE_DEFAULT:
                queryBuilder.setProjectionMap(values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        if (sortOrder == null || sortOrder.isEmpty()) {
            // /!\ -> id, or idUser etc...
            sortOrder = "id";
        }

        Cursor cursor = queryBuilder.query(db, columns, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) throws SQLiteException {

        // Check if uri is permitted
        if (uriMatcher.match(uri) != URI_CODE_INSERT)
            throw new IllegalArgumentException("Unknown URI: " + uri);

        Long rowId = db.insert(TABLE_NAME_USERS, "", values);

        if (rowId > 0) {
            Uri _uri = ContentUris.withAppendedId(uri, rowId);
            // Notify registered observers that a row was updated
            // and attempt to sync changes to the network.
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }

        throw new SQLiteException("insert error on uri: " + CONTENT_USERS_URI);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        // Check if uri is permitted
        if (uriMatcher.match(uri) != URI_CODE_DELETE)
            throw new IllegalArgumentException("Unknown URI: " + uri);

        int rowsAffected = db.delete(TABLE_NAME_USERS, selection, selectionArgs);

        if (rowsAffected > 0)
            getContext().getContentResolver().notifyChange(uri, null);

        return rowsAffected;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        // Check if uri is permitted
        if (uriMatcher.match(uri) != URI_CODE_UPDATE)
            throw new IllegalArgumentException("Unknown URI: " + uri);

        int rowsAffected = db.update(TABLE_NAME_USERS, values, selection, selectionArgs);

        if (rowsAffected > 0)
            getContext().getContentResolver().notifyChange(uri, null);

        return rowsAffected;
    }
}
