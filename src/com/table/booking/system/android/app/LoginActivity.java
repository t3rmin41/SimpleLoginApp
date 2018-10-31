package com.table.booking.system.android.app;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class LoginActivity extends Activity {

  EditText emailEditText, passwordEditText;
  
  private RequestHandler requestHandler = new RequestHandler();
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    
    emailEditText = (EditText) findViewById(R.id.emailEditText);
    passwordEditText = (EditText) findViewById(R.id.passwordEditText);
    
    //if user presses on 'loginButton' call userLogin()
    findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (userLogin()) {
              getLoggedUserData();
            }
        }
    });

  }
  
  private boolean userLogin() {
    //first getting the values
    final String email = emailEditText.getText().toString();
    final String password = passwordEditText.getText().toString();

    //validating inputs
    if (TextUtils.isEmpty(email)) {
      emailEditText.setError("Please enter your email");
      emailEditText.requestFocus();
      return false;
    }

    if (TextUtils.isEmpty(password)) {
      passwordEditText.setError("Please enter your password");
      passwordEditText.requestFocus();
      return false;
    }

    //if everything is fine
    class UserLogin extends AsyncTask<String, String, String> {

        ProgressBar progressBar;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE);
        }

        @Override
        protected String doInBackground(String... args) {
            UserCredentials credentials = new UserCredentials();
            credentials.setEmail(email);
            credentials.setPassword(password);

            //returing the response
            return requestHandler.sendLoginRequest(credentials);
        }
    }

    UserLogin ul = new UserLogin();
    try {
      ul.execute();
    } catch (RuntimeException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }
  
  private void getLoggedUserData() {
  
    class GetUserData extends AsyncTask<String, String, String> {

      @Override
      protected String doInBackground(String... args) {
        String token = SharedPreferencesManager.getTokenFromSharedPreferences();
        return requestHandler.sendHttpRequest(token, "POST", URLs.URL_LOGIN_SUCCESS, new HashMap<String, String>());
      }
    
      @Override
      protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
          //converting response to json object
          JSONObject obj = new JSONObject(s);

          //if no error in response
          if (!obj.getBoolean("error")) {
              Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

              //getting the user from the response
              JSONObject userJson = obj.getJSONObject("user");

              //creating a new user object
              User user = new User(
                      userJson.getLong("id"),
                      userJson.getString("email"),
                      userJson.getString("firstName"),
                      userJson.getString("lastName")
              );

              //storing the user in shared preferences
              SharedPreferencesManager.getInstance(getApplicationContext()).userLogin(user);

              //starting the profile activity
              finish();
              startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
          } else {
              Toast.makeText(getApplicationContext(), "Invalid email or password", Toast.LENGTH_SHORT).show();
          }
        } catch (JSONException e) {
          e.printStackTrace();
        }
      }
    }

    GetUserData getUserData = new GetUserData();
    try {
      getUserData.execute();
    } catch (RuntimeException e) {
      e.printStackTrace();
    }
  }
}
