package com.table.booking.system.android.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class SharedPrefManager {

  private static final String SHARED_PREF_NAME = "simpleloginsharedpref";
  private static final String KEY_FIRSTNAME = "keyfirstname";
  private static final String KEY_LASTNAME = "keylastname";
  private static final String KEY_EMAIL = "keyemail";
  private static final String KEY_ID = "keyid";

  private static SharedPrefManager mInstance;
  private static Context mCtx;
  
  private SharedPrefManager(Context context) {
    mCtx = context;
}

  public static synchronized SharedPrefManager getInstance(Context context) {
    if (mInstance == null) {
        mInstance = new SharedPrefManager(context);
    }
    return mInstance;
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
    SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    return sharedPreferences.getString(KEY_EMAIL, null) != null;
  }

  //this method will give the logged in user
  public User getUser() {
    SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
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
