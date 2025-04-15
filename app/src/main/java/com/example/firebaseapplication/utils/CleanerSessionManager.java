package com.example.firebaseapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class CleanerSessionManager {

    private static final String PREF_NAME = "cleaner_session";
    private static final String KEY_UID = "uid";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";
    private final SharedPreferences prefs;
    private final SharedPreferences.Editor editor;

    public CleanerSessionManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void saveCleanerSession(String uid, String name, String email, String phone) {
        editor.putString(KEY_UID, uid);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PHONE, phone);
        editor.apply();
    }

    public String getCleanerId() {

        return prefs.getString(KEY_UID, null);
    }

    public String getName() {
        return prefs.getString(KEY_NAME, null);
    }

    public String getEmail() {
        return prefs.getString(KEY_EMAIL, null);
    }

    public String getPhone() {
        return prefs.getString(KEY_PHONE, null);
    }

    public void clearSession() {
        editor.clear();
        editor.apply();
    }

}


