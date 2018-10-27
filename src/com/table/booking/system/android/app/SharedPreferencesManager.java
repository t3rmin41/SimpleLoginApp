package com.table.booking.system.android.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferencesManager {

  private static final String SHARED_PREF_NAME = "simpleloginsharedpref";
  private static final String KEY_FIRSTNAME = "keyfirstname";
  private static final String KEY_LASTNAME = "keylastname";
  private static final String KEY_EMAIL = "keyemail";
  private static final String KEY_ID = "keyid";

  private static SharedPreferencesManager managerInstance;
  private static SharedPreferences sharedPreferences;
  private static Context mCtx;
  
  private SharedPreferencesManager(Context context) {
    mCtx = context;
    sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
}

  public static synchronized SharedPreferencesManager getInstance(Context context) {
    if (managerInstance == null) {
        managerInstance = new SharedPreferencesManager(context);
    }
    return managerInstance;
  }

  //method to let the user login
  //this method will store the user data in shared preferences
  public void userLogin(User user) {
	SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putLong(KEY_ID, user.getId());
    editor.putString(KEY_FIRSTNAME, user.getFirstName());
    editor.putString(KEY_LASTNAME, user.getLastName());
    editor.putString(KEY_EMAIL, user.getEmail());
    editor.apply();
  }

  //this method will checker whether user is already logged in or not
  public boolean isLoggedIn() {
    return sharedPreferences.getString(KEY_EMAIL, null) != null;
  }

  //this method will give the logged in user
  public User getUser() {
    return new User(
            sharedPreferences.getLong(KEY_ID, -1),
            sharedPreferences.getString(KEY_EMAIL, null),
            sharedPreferences.getString(KEY_FIRSTNAME, null),
            sharedPreferences.getString(KEY_LASTNAME, null)
    );
  }

  //this method will logout the user 
  public void logout() {
	SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.clear();
    editor.apply();
    mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
  }

}
