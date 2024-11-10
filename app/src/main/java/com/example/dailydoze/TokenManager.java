package com.example.dailydoze;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenManager {

    private static TokenManager instance;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String PREF_NAME = "user_prefs"; // Renamed for clarity
    private static final String KEY_USERNAME = "username"; // Key for storing the username

    private TokenManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static TokenManager getInstance(Context context) {
        if (instance == null) {
            instance = new TokenManager(context);
        }
        return instance;
    }

    // Save the username
    public void saveUsername(String username) {
        editor.putString(KEY_USERNAME, username);
        editor.apply();
    }

    // Get the saved username
    public String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, null); // Return null if no username found
    }

    // Remove the username
    public void clearUsername() {
        editor.remove(KEY_USERNAME);
        editor.apply();
    }
}
