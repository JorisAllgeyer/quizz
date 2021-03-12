package fr.doranco.myquizz.controller;

import android.content.Context;

import java.util.List;

import fr.doranco.myquizz.entity.User;

public interface IUser {
    List<User> getAllUsers(Context context);
    User getUserByName(Context context, String userName);
    User addUser(Context context, String userName);
    void updateScore(Context context, int score, long userId);
    void cleanDB(Context context);
}
