package com.codingempires.practice;

import android.content.SharedPreferences;

import com.google.gson.Gson;

public class SharedPref {
    private SharedPreferences mypref;
    private SharedPreferences.Editor editor;

    public SharedPref(SharedPreferences mypref) {
        this.mypref = mypref;
        this.editor = mypref.edit(); // Initialize the editor
    }

    public void setUser(UserModel user) {
        editor.putString("user", new Gson().toJson(user)).apply();
    }

    public UserModel getUser() {
        String userJson = mypref.getString("user", "");
        if (!userJson.isEmpty()) {
            return new Gson().fromJson(userJson, UserModel.class);
        }
        return null;
    }

}
