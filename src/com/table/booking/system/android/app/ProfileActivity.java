package com.table.booking.system.android.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ProfileActivity extends Activity {

  TextView textViewId, textViewEmail, textViewFirstName, textViewLastName;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_profile);
    
    //if the user is not logged in
    //starting the login activity
    if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
        finish();
        startActivity(new Intent(this, LoginActivity.class));
    }
    
    textViewId = (TextView) findViewById(R.id.textViewId);
    textViewEmail = (TextView) findViewById(R.id.textViewEmail);
    textViewFirstName = (TextView) findViewById(R.id.textViewFirstName);
    textViewLastName = (TextView) findViewById(R.id.textViewLastName);
    
    User user = SharedPrefManager.getInstance(this).getUser();
    
    textViewId.setText(String.valueOf(user.getId()));
    textViewEmail.setText(user.getEmail());
    textViewFirstName.setText(user.getFirstName());
    textViewLastName.setText(user.getLastName());
    
    //when the user presses logout button
    //calling the logout method
    findViewById(R.id.logoutButton).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
            SharedPrefManager.getInstance(getApplicationContext()).logout();
        }
    });
    
  }
}
