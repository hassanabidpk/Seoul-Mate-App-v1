package com.sisfgroupd.seoulmappedia;

import java.util.Set;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.PushService;
import com.parse.SaveCallback;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sisfgroupd.seoulmappedia.R;

public class TeamSubscribeActivity extends Activity {

	private static final String TAG = "TeamSubscribeActivity";
	private Button teamSub;
	private Spinner spinner;
	private TextView teamText;
	
	private TextView teamName;
	private String[]  TEAM_ARRAY;
	SharedPreferences settings;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.team_layout);
		settings = getApplicationContext().getSharedPreferences("TEAM", 0);
		TEAM_ARRAY = getBaseContext().getResources().getStringArray(R.array.wc_teams);
		teamSub = (Button) findViewById(R.id.teamButton);
		spinner = (Spinner) findViewById(R.id.teamSpinner);
		teamName = (TextView) findViewById(R.id.teamSubcribed);
		teamText = (TextView) findViewById(R.id.teamText);
		boolean selection = settings.getBoolean("selection", false);
		String myTeamName = settings.getString("myteam", "NONE");
		if(selection && myTeamName != "NONE") {
			if(isNetworkAvailable() && isWifiNetworkAvailable()) {
			String[] teamsArray = subscribedTeams().toArray(new String[subscribedTeams().size()]);
			teamName.setText(teamsArray[0]);
			teamName.setVisibility(View.VISIBLE);
			teamSub.setBackgroundResource(R.drawable.subscribed);
			teamSub.setEnabled(false);
			spinner.setVisibility(View.INVISIBLE);
			teamText.setText("My Team : ");
			Log.d(TAG, "Getting team from PARSE!!");
			} else {
			teamName.setText(myTeamName);
			teamName.setVisibility(View.VISIBLE);
			teamSub.setBackgroundResource(R.drawable.subscribed);
			teamSub.setEnabled(false);
			spinner.setVisibility(View.INVISIBLE);
			teamText.setText("My Team : ");
			Log.d(TAG, "Getting team from Local storage!!!");
			}
			
		}
		
		teamSub.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
//				int pos = spinner.getSelectedItemPosition();
//				teamName.setText(TEAM_ARRAY[pos]);
//				teamName.setVisibility(View.VISIBLE);
//				teamSub.setBackgroundResource(R.drawable.subscribed);
//				teamSub.setEnabled(false);
//				SharedPreferences.Editor editor = settings.edit();
//			    editor.putString("myteam", TEAM_ARRAY[pos]);
//			    editor.putBoolean("selection", true); 
//
//			      // Commit the edits!
//			    editor.commit();
//				subscribeToTeam(TEAM_ARRAY[pos]);
//				saveCurrentInstallation();
//				
				
			}
		});
		
		
	}
	
	public void saveCurrentInstallation(){
		
		// Save the current Installation to Parse.
		 String  android_id = Secure.getString(getApplicationContext().getContentResolver(),Secure.ANDROID_ID);         
		    Log.e("LOG","android id >>" + android_id);

//		    PushService.setDefaultPushCallback(this, MainActivity.class);

		    ParseInstallation installation = ParseInstallation.getCurrentInstallation();
		    installation.put("UniqueId",android_id);
		    installation.saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException e) {
				if (e == null) {
					Log.d(TAG, "Saved Info to Parse!");
				} else {
					e.printStackTrace();
					Log.d(TAG, "Saved Info to Parse failed!!!!");

				}
			}
		});
		
	}
	
	public void subscribeToTeam(String team) {
		
		// team = 'Giants';
		// When users indicate they are Giants fans, we subscribe them to that channel.
		PushService.subscribe(getApplicationContext(), team, TeamSubscribeActivity.class);
		Log.d(TAG, "Subscribed to TEAM : " + team);
		
	}
	
	public void unSubscribeTeam(String team) {
		
		// When users indicate they are no longer Giants fans, we unsubscribe them.
		PushService.unsubscribe(getApplicationContext(), team);
		Log.d(TAG, "UnSubscribed to TEAM : " + team);
	}
	
	public Set<String> subscribedTeams() {
		
		Set<String> setOfAllSubscriptions = PushService.getSubscriptions(getApplicationContext());
		Log.d(TAG, "Subscribed to TEAMS : " + setOfAllSubscriptions.toString());
		return setOfAllSubscriptions;

		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}
	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	
	 private boolean isNetworkAvailable() {
	    	
	    	boolean haveConnectedWifi = false;
	        boolean haveConnectedMobile = false;
	        ConnectivityManager connectivityManager 
	              = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
	        NetworkInfo[] activeNetworkInfo = connectivityManager.getAllNetworkInfo();
	        for (NetworkInfo ni : activeNetworkInfo) {
	        	if(ni.getTypeName().equalsIgnoreCase("WIFI"))
	        		if(ni.isConnected())
	        			haveConnectedWifi = true;
	        	if(ni.getTypeName().equalsIgnoreCase("MOBILE"))
	        		if(ni.isConnected())
	        			haveConnectedMobile = true;
	        }
	        return haveConnectedMobile; // || haveConnectedWifi;
	    }
	    
	    private boolean isWifiNetworkAvailable() {
	        ConnectivityManager connectivityManager 
	              = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
	        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	    }
}
