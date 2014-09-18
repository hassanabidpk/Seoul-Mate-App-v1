package com.seoulmate.social;

import java.util.Arrays;
import java.util.List;

import com.example.seoulmappedia.SeoulMapPedia;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;
import com.sisfgroupd.seoulmappedia.R;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SocialLoginActivity extends Activity {
	private Button loginButton;
	private Dialog progressDialog;
	private Button loginButtonTwitter;
	
	private static final String LOG_TAG = "SocialLoginActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		
//		ParseFacebookUtils.initialize(getResources().getString(R.string.app_id));
		loginButton = (Button) findViewById(R.id.loginButton);
		loginButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				onLoginButtonClicked();
				
			}
		});
		
//		loginButtonTwitter = (Button) findViewById(R.id.loginButtonTwitter);
//		loginButtonTwitter.setOnClickListener(new View.OnClickListener() {
//			
//			public void onClick(View v) {
//				onTwitterLoginBtnClicked();
//				
//			}
//		});
		// Check if there is a currently logged in user
		// and they are linked to a Facebook account.
		ParseUser currentUser = ParseUser.getCurrentUser();
		if ((currentUser != null) && ParseFacebookUtils.isLinked(currentUser)) {
			// Go to the user info activity
			showUserDetailsActivity();
		}
//		} else if ((currentUser != null) && ParseTwitterUtils.isLinked(currentUser)) {
//			showUserDetailsActivity();
//		}
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
	}
	
	private void onLoginButtonClicked() {
		SocialLoginActivity.this.progressDialog = ProgressDialog.show(
				SocialLoginActivity.this, "", "Logging in...", true);
		List<String> permissions = Arrays.asList("public_profile", "user_location", "user_hometown");
		ParseFacebookUtils.logIn(permissions, this, new LogInCallback() {
			@Override
			public void done(ParseUser user, ParseException err) {
				SocialLoginActivity.this.progressDialog.dismiss();
				if (user == null) {
					Log.d(IntegratingFacebookTwitterApplicaiton.TAG,
							"Uh oh. The user cancelled the Facebook login.");
				} else if (user.isNew()) {
					Log.d(IntegratingFacebookTwitterApplicaiton.TAG,
							"User signed up and logged in through Facebook!");
					showUserDetailsActivity();
				} else {
					Log.d(IntegratingFacebookTwitterApplicaiton.TAG,
							"User logged in through Facebook!");
					showUserDetailsActivity();
				}
			}
		});
	}
	
	private void onTwitterLoginBtnClicked() {
		
		ParseTwitterUtils.logIn(this, new LogInCallback() {
			  @Override
			  public void done(ParseUser user, ParseException err) {
			    if (user == null) {
			      Log.d(IntegratingFacebookTwitterApplicaiton.TAG, "Uh oh. The user cancelled the Twitter login.");
			    } else if (user.isNew()) {
			      Log.d(IntegratingFacebookTwitterApplicaiton.TAG, "User signed up and logged in through Twitter!");
			      showUserDetailsActivity();
			    } else {
			      Log.d(IntegratingFacebookTwitterApplicaiton.TAG, "User logged in through Twitter!");
			      showUserDetailsActivity();
			    }
			  }
			});
		
	}
	
	private void showUserDetailsActivity() {
		Intent intent = new Intent(this, SeoulMapPedia.class);
		startActivity(intent);
	}


}
