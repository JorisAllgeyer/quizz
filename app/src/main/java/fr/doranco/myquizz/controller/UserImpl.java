package fr.doranco.myquizz.controller;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import fr.doranco.myquizz.entity.User;

public class UserImpl implements IUser {

    private static final String CONTENT_BASE_URI = "content://fr.doranco.myquizz.model.UserProvider";
    private static final String TABLE_USER = "user";

    @Override
    public List<User> getAllUsers(Context context) {
        // Init List
        List<User> listUsers = new ArrayList<User>();

        try {
            Uri uri = Uri.parse(CONTENT_BASE_URI + "/" + TABLE_USER);
            Cursor cursor = context.getContentResolver()
                    .query(uri, null, null, null, "score DESC");

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    int score = cursor.getInt(cursor.getColumnIndex("score"));

                    User user = new User();
                    user.setName(name);
                    user.setScore(score);

                    // Fill list and move to next
                    listUsers.add(user);
                    cursor.moveToNext();
                }
            }

        } catch (Exception e) {
            System.out.println("[ERROR] getAllUsers -------------------");
            System.out.println(e);
        }

        return listUsers;
    }

    @Override
    public User getUserByName(Context context, String userName) {

        try {
            Uri uri = Uri.parse(CONTENT_BASE_URI + "/" + TABLE_USER);
            Cursor cursor = context.getContentResolver().query(uri, null, "name = ?", new String[]{userName},null);

            if (cursor.moveToFirst()) {

                User user = new User();
                user.setName(cursor.getString(cursor.getColumnIndex("name")));
                user.setScore(cursor.getInt(cursor.getColumnIndex("score")));
                user.setId(cursor.getInt(cursor.getColumnIndex("id")));

                return user;
            }

        } catch (Exception e) {
            System.out.println("[ERROR] getUserByName -------------------");
            System.out.println(e);
        }

        return null;
    }

    @Override
    public User addUser(Context context, String userName) {
        // Values for query
        ContentValues values = new ContentValues();
        values.put("name", userName);
        values.put("score", 0);

        // Init user
        User user = new User();
        user.setName(userName);
        user.setScore(0);

        try {
            Uri uri = Uri.parse(CONTENT_BASE_URI + "/" + TABLE_USER + "/insert");
            Uri uriAdded = context.getContentResolver().insert(uri, values);

            long userId = ContentUris.parseId(uriAdded);
            user.setId(userId);

            return user;

        } catch (SQLiteException e) {
            System.out.println("[ERROR] addUser -------------------");
            System.out.println(e.getMessage());
        }

        return null;
    }

    @Override
    public void updateScore(Context context, int score, long userId) {
        ContentValues values = new ContentValues();
        values.put("score", score);

        try {
            Uri uri = Uri.parse(CONTENT_BASE_URI + "/" + TABLE_USER + "/update");
            int rowsAffected = context.getContentResolver()
                    .update(uri, values, "id = ?", new String[]{String.valueOf(userId)});

            if (rowsAffected < 1)
                System.out.println("No data updated");

        } catch (SQLiteException e) {
            System.out.println("[ERROR] updateScore -------------------");
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void cleanDB(Context context) {
        Uri uri = Uri.parse(CONTENT_BASE_URI + "/" + TABLE_USER + "/delete");
        int rowsAffected = context.getContentResolver()
                .delete(uri, null, null);

        System.out.println("CLEAN DATABASE");
        System.out.println("rowsAffected ->" + rowsAffected);
    }
}
