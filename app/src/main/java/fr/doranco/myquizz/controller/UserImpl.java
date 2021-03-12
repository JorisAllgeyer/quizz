package fr.doranco.myquizz.controller;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fr.doranco.myquizz.entity.User;

public class UserImpl implements IUser {

    @Override
    public List<User> getAllUsers(Context context) {
        List<User> listUsers = new ArrayList<User>();

        try {
            Uri uri = Uri.parse("content://fr.doranco.myquizz.model.UserProvider/user");
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, "score DESC");

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    int score = cursor.getInt(cursor.getColumnIndex("score"));

                    User user = new User();
                    user.setName(name);
                    user.setScore(score);

                    System.out.println("user");
                    System.out.println(user);

                    listUsers.add(user);

                    // Move to next
                    cursor.moveToNext();
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return listUsers;
    }

    @Override
    public User getUserByName(Context context, String userName) {

        try {
            Uri uri = Uri.parse("content://fr.doranco.myquizz.model.UserProvider/user");
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
            Uri uri = Uri.parse("content://fr.doranco.myquizz.model.UserProvider/user/insert");
            Uri uriAdded = context.getContentResolver().insert(uri, values);

            long userId = ContentUris.parseId(uriAdded);
            user.setId(userId);

            return user;

        } catch (SQLiteException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @Override
    public void updateScore(Context context, int score, long userId) {

        ContentValues values = new ContentValues();
        values.put("score", score);
        try {
            Uri uri = Uri.parse("content://fr.doranco.myquizz.model.UserProvider/user/update");
            int rowsAffected = context.getContentResolver().update(uri, values, "id = ?", new String[]{String.valueOf(userId)});

        } catch (SQLiteException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void cleanDB(Context context) {
        Uri uri = Uri.parse("content://fr.doranco.myquizz.model.UserProvider/user/delete");
        int rowsAffected = context.getContentResolver().delete(uri, null, null);

        System.out.println("CLEAN DATABASE");
        System.out.println("rowsAffected ->" + rowsAffected);
    }
}
