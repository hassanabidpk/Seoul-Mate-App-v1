package com.example.seoulmappedia;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.json.JSONException;
import org.json.JSONObject;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings.Secure;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.FacebookRequestError;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.ProfilePictureView;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;
import com.facebook.Session.StatusCallback;
import com.seoulmate.social.SocialLoginActivity;
import com.sisfgroupd.seoulmappedia.R;
import com.sisfgroupd.seoulmappedia.SocialMediaMenu;
import com.sisfgroupd.seoulmappedia.TeamSubscribeActivity;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.PushService;


public class SeoulMapPedia extends Activity implements OnClickListener, OnItemSelectedListener {

	private static final String LOG = "SeoulMapPedia";
	private static final boolean v2 = false;
	private static final boolean v3 = true;
	
	private ProfilePictureView userProfilePictureView;
	private TextView userNameView;
	private TextView userLocationView;
	private TextView userGenderView;
	private TextView userDateOfBirthView;
	private TextView userRelationshipView;
	private Button logoutButton;
	
	private Bundle bundle;
	
	private static final String[] LANG_CODES = {"en","ko", "zh", "ja", "es", "pl", "vi", "ar", "ma", "ru"};
	
	private ZoomNDragMaps testView;
	private View shareButton;
	private ProfilePictureView profilePictureView;
	private LoginButton loginButton;
	private GraphUser user;
	private String curLang;
	// Recommened Charset UTF-8
	private String encoding = "UTF-8";
	public static enum MapEdition {
		DeviceLock, TimeLock
	};
	private static final MapEdition EDITION = MapEdition.TimeLock; 
	private static final Date TIMELOCK_EXPIRATION = UTCDate1(2013, 9, 28, 9, 15, 10);
	private SharedPreferences languagePref;
	private int languageNo = 0;
	private String langCode ;
    
	// add the UiLifecycleHelper to manage Session
	
   private UiLifecycleHelper uiHelper;
   private Session.StatusCallback callback = new Session.StatusCallback() {
       public void call(Session session, SessionState state, Exception exception) {
           onSessionStateChange(session, state, exception);
       }
   };
   private Session.StatusCallback statusCallback = 
		    new SessionStatusCallback();

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	 super.onCreate(savedInstanceState);
    	 
    	 final SharedPreferences setting = getApplicationContext().getSharedPreferences("TEAM", 0);
    	 if(setting.getBoolean("selection", false) == false) {
    		 if(isNetworkAvailable() && isWifiNetworkAvailable()) {
    			 String[] teamsArray = PushService.getSubscriptions(getApplicationContext()).toArray(new String[PushService.getSubscriptions(getApplicationContext()).size()]);
    			 SharedPreferences.Editor editor = setting.edit();
    			 if (teamsArray.length != 0) {
    				 editor.putString("myteam", teamsArray[0]);
    				 editor.putBoolean("selection", true); 
    			 } else {
    				 editor.putBoolean("selection", false); 
    			 }

				 // Commit the edits!
				 editor.commit();
    		 
    		 
    		 } else {

    			 AlertDialog.Builder builder = new AlertDialog.Builder(SeoulMapPedia.this);
    			 builder.setTitle("Select Your Worldcup Team!")
    			 .setItems(R.array.teamselect_array, new DialogInterface.OnClickListener() {

    				 public void onClick(DialogInterface dialog, int which) {

    					 String[] TEAM_ARRAY = getBaseContext().getResources().getStringArray(R.array.wc_teams);						if (which == 0)  {
    						 Intent j = new Intent(getBaseContext(), TeamSubscribeActivity.class);
    						 startActivity(j);

    					 }

    					 PushService.setDefaultPushCallback(getApplicationContext(), SeoulMapPedia.class);
    					 // Save the current Installation to Parse.
    					 String  android_id = Secure.getString(getApplicationContext().getContentResolver(),Secure.ANDROID_ID);         
    					 Log.e("LOG","android id >>" + android_id);

    					 //					 	    PushService.setDefaultPushCallback(this, MainActivity.class);

    					 ParseInstallation installation = ParseInstallation.getCurrentInstallation();
    					 installation.put("UniqueId",android_id);
    					 PushService.subscribe(getApplicationContext(), TEAM_ARRAY[which], SeoulMapPedia.class);
    					 installation.saveInBackground();
    					 ParseAnalytics.trackAppOpened(getIntent());
    					 SharedPreferences.Editor editor = setting.edit();
    					 editor.putString("myteam", TEAM_ARRAY[which]);
    					 editor.putBoolean("selection", true); 

    					 // Commit the edits!
    					 editor.commit();
    				 }


    			 });
    			 builder.create().show();
    		 }

    	 }
    	 
    	// Parse.initialize(this, "rZ4xc4nb7PgqASjHeV8oTgH52TRVlDJBb5TBvO1O", "TyzLDXJYiNUOFLkAmHRWniZXAehsNqfgjP46n1Wr");
//    	 ParseObject testObject = new ParseObject("TestObject");
//    	 testObject.put("foo", "bar");
//    	 testObject.saveInBackground();
    	 
    	 
    	 /*
    	if(ExpiredSeoulActivity.checkExpiredDate(this)) {      
    	       Log.d("SeoulMapPedia", "Activity Expired!!!!");
    	       return;
    	}
    	
    	*/
    	
   // 	getHashKeyforFb();
    	 
    	
    	if(v3) {
    		setContentView(R.layout.main_2);
    	} else {
    		if(Build.MODEL.toLowerCase().equals("lg-f100l") || Build.MODEL.toLowerCase().equals("lg-f100s") 
    				|| Build.MODEL.toLowerCase().equals("lg-f200") || Build.MODEL.toLowerCase().contains("lg-f200")) {
    			/*	
    		DisplayMetrics metrics = new DisplayMetrics();
    		getWindowManager().getDefaultDisplay().getMetrics(metrics);
    		Log.d(LOG, "Width of Device : " + metrics.widthPixels + "Height : " + metrics.heightPixels );
    			 */
    			Log.d(LOG, "LG optmus Vu Device");
    			setContentView(R.layout.main_vu);
    		} else {

    			setContentView(R.layout.main);
    		}
    	}
    	if(v3) {
    		logoutButton = (Button) findViewById(R.id.logoutButton);
    		logoutButton.setOnClickListener(new View.OnClickListener() {
    			public void onClick(View v) {
    				onLogoutButtonClicked();
    			}
    		});

    		userProfilePictureView = (ProfilePictureView) findViewById(R.id.userProfilePicture);
    		userNameView = (TextView) findViewById(R.id.userName);
    		userLocationView = (TextView) findViewById(R.id.userLocation);
    		
    		// Fetch Facebook user info if the session is active
    		Session session = ParseFacebookUtils.getSession();
    		if (session != null && session.isOpened()) {
    			makeMeRequest();
    		}

    		GridView gridview = (GridView) findViewById(R.id.mainGridView);
    		gridview.setAdapter(new SeoulMenuAdapterWC(SeoulMapPedia.this));
    		
    		 gridview.setOnItemClickListener(new OnItemClickListener() {
    			 

				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					switch(position) {
					
					case 6: Intent j = new Intent(getBaseContext(), TeamSubscribeActivity.class);
							startActivity(j);
							break;
					case 1 : Intent m = new Intent(getBaseContext(), SpecialDietActivity.class);
							startActivity(m);
							break;
							
					case 0 : buildDialog();
								break;
					case 2 : Intent webIntent4 = new Intent("android.intent.action.VIEW", Uri.parse("http://www.kc4dh.com/"));
        			startActivity(webIntent4);
        			break;
					case 3 : Intent t = new Intent(getBaseContext(), SeoulLifeActivity.class);
					startActivity(t);
					break;
					case 4 : Intent socialmedia = new Intent(getBaseContext(), SocialMediaMenu.class);
	 				startActivity(socialmedia);break;
					case 5 : Intent webIntent = new Intent("android.intent.action.VIEW", Uri.parse("http://worldcup.seoulmate.me/instagram.php"));
        			startActivity(webIntent);
        			break;
					case 7 : Intent webIntent2 = new Intent("android.intent.action.VIEW", Uri.parse("http://resources.fifa.com/mm/document/tournament/competition/01/52/99/91/2014fwc_matchschedule_wgroups_22042014_en_neutral.pdf"));
        			startActivity(webIntent2);
        			break;
					case 8 : Intent webIntent3 = new Intent("android.intent.action.VIEW", Uri.parse("http://seoulmate.me"));
        			startActivity(webIntent3);break;
					
					}
					
				}
			});
    		 
    		 
    	}
    	else if(v2) {
    		
//    		TextView about = (TextView) findViewById(R.id.aboutus);
//    		about.setOnClickListener(new View.OnClickListener() {
//				
//				public void onClick(View v) {
//			 	Intent about = new Intent("android.intent.action.VIEW", Uri.parse("http://seoulmate.me/blog/meet-up-with-seoul-mate/"));
//    			startActivity(about);
//					
//				}
//			});
    		
    		 GridView gridview = (GridView) findViewById(R.id.mainGridView);
    		    gridview.setAdapter(new SeoulMenuAdapter(SeoulMapPedia.this));

    		    gridview.setOnItemClickListener(new OnItemClickListener() {
    		        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//    		            Toast.makeText(SeoulMapPedia.this, "" + position, Toast.LENGTH_SHORT).show();
    		        	switch(position) {
    		        	case 0 : 	Intent j = new Intent(getBaseContext(), CampusMapsMenu.class);
        							startActivity(j);
        							break;
    		        	case 1: Intent k = new Intent(getBaseContext(), GoogleMapsMenu.class);
        				//		Intent k = new Intent(getBaseContext(), GoogleMapsFragmentClass.class);
        						startActivity(k);
        						break;

    		        	case 2: Intent l = new Intent(getBaseContext(), AiportMapsMenu.class);
        						//		Intent k = new Intent(getBaseContext(), GoogleMapsFragmentClass.class);
        						startActivity(l);
        						break;
    		        	case 3: Intent m = new Intent(getBaseContext(), SpecialDietActivity.class);
        						startActivity(m);
        						break;
    		        	case 4: 		 Intent intent = new Intent(getBaseContext(), NewsnEventsActivity.class);
    					 				intent.putExtra("seoulposition", 6);
    					 				startActivity(intent); break;
    		        	case 5: 		 Intent jobs = new Intent(getBaseContext(), NewsnEventsActivity.class);
    		        					jobs.putExtra("seoulposition", 7);
    					 				startActivity(jobs);
    					 				break;
    		        	case 6:  Intent online = new Intent(getBaseContext(), NewsnEventsActivity.class);
    		        	online.putExtra("seoulposition", 8);
		 				startActivity(online);break;
    		        	case 7:  Intent volunteer = new Intent(getBaseContext(), NewsnEventsActivity.class);
    		        	volunteer.putExtra("seoulposition", 9);
		 				startActivity(volunteer);
		 				break;
    		        	case 8: Intent t = new Intent(getBaseContext(), SeoulLifeActivity.class);
        						startActivity(t);
        						break;
    		        	case 9: Intent korea = new Intent(getBaseContext(), NewsnEventsActivity.class);
    		        	korea.putExtra("seoulposition", 10);
		 				startActivity(korea);
		 				break;
    		        	case 10:  Intent gangnam = new Intent(getBaseContext(), NewsnEventsActivity.class);
    		        	gangnam.putExtra("seoulposition", 11);
		 				startActivity(gangnam); break;
    		        	case 11: Intent webIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.facebook.com/GrooveKorea"));
    		        			startActivity(webIntent);
    		        			break;
    		        	case 12: Intent socialmedia = new Intent(getBaseContext(), SocialMediaMenu.class);
		 				startActivity(socialmedia);break;
    		        	case 13:    Intent r = new Intent(getBaseContext(), ContactnHelpFragment.class);
        							Bundle bundle = new Bundle();
        							bundle.putInt("mainposition", 1);
        							r.putExtra("mainposition", 1);
        							startActivity(r);
        							break;
    		          	case 14: Intent korenwave = new Intent(getBaseContext(), NewsnEventsActivity.class);
    		          	korenwave.putExtra("seoulposition", 12);
		 				startActivity(korenwave);
	        			break;
    		          	case 15: Intent naverdic = new Intent("android.intent.action.VIEW", Uri.parse("http://m.dic.naver.com"));
	        			startActivity(naverdic);
	        			break;

    		        	default: break;
    		        		
    		        	
    		        	}
    		        }
    		    });
    		
    	} else {
    		languageNo = getIntent().getIntExtra("languageno", -1);

    		if(languageNo != -1) {

    			setLocale(LANG_CODES[languageNo], languageNo);
    			Log.d(LOG, "Setting Locale, Language1 : " + LANG_CODES[languageNo]);


    		} else {

    			SharedPreferences settings = getSharedPreferences("language", MODE_PRIVATE);
    			String getLang = settings.getString("language2Load", "notfound");
    			if(!getLang.equals("notfound")) {
    				Log.d(LOG, "Setting Locale, Language2 : " + getLang);
    				setLocale(getLang, -1);
    			}

    		}

    		// setting shared Preferences 

    		uiHelper = new UiLifecycleHelper(this, callback);
    		uiHelper.onCreate(savedInstanceState);
    		Button langButton = (Button) findViewById(R.id.main_spinner);
    		langButton.setOnClickListener(new View.OnClickListener() {

    			public void onClick(View v) {

    				Intent j = new Intent(getBaseContext(), LanguageChangeActivity.class);
    				startActivity(j);
    				finish();
    				//  		startActivityForResult(j, 0);

    			}
    		});
    		Locale current = getApplicationContext().getResources().getConfiguration().locale;
    		curLang = getApplicationContext().getResources().getConfiguration().locale.getLanguage();
    		Log.d(LOG, " Current Language: " + curLang + " 1: "+ current.getCountry() + " 2 : " + current.getDisplayLanguage(Locale.US));



    		/*
        Spinner spinner = (Spinner) findViewById(R.id.main_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.lang_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
//        spinner.setOnItemSelectedListener(this);

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {

				Log.d(LOG, " Spinner item selected at " + arg2);
			setLocale(LANG_CODES[arg2]);

			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
    		 */

    		//      testView = new ZoomNDragMaps(getBaseContext()); 
    		View campusButton = this.findViewById(R.id.mainbutton1);
    		//      campusButton.setOnClickListener((OnClickListener) this);

    		View googleButton = this.findViewById(R.id.mainbutton2);
    		//       googleButton.setOnClickListener((OnClickListener) this);/

    		View airportButton = this.findViewById(R.id.mainbutton3);

    		View seoulButton = this.findViewById(R.id.mainbutton5);
    		//       sgcButton.setOnClickListener((OnClickListener) this);

    		View halalButton = this.findViewById(R.id.mainbutton4);

    		View aboutButton = this.findViewById(R.id.mainbutton7);

    		View contactButton = this.findViewById(R.id.mainbutton10);

    		View helpButton = this.findViewById(R.id.mainbutton8);

    		shareButton = this.findViewById(R.id.mainbutton9);
    		shareButton.setOnClickListener(new View.OnClickListener() {

    			public void onClick(View v) {
    				if (Session.getActiveSession().isOpened()) {
    					publishFeedDialog();
    				} else {
    					Toast.makeText(getBaseContext(), 
    							"You must log in to publish a story", 
    							Toast.LENGTH_SHORT)
    							.show();
    				}
    			}
    		});
    		profilePictureView = (ProfilePictureView) findViewById(R.id.profilePicture);
    		loginButton = (LoginButton) findViewById(R.id.authButton);

    		if(!isNetworkAvailable() && !isWifiNetworkAvailable()) {
    			Toast.makeText(getBaseContext(), 
    					"Internet is not available", 
    					Toast.LENGTH_SHORT)
    					.show();
    		}

    		Log.d(LOG, " Mobile Network is : " + isNetworkAvailable() + " Wifi is : " + isWifiNetworkAvailable());


    		loginButton.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
    			public void onUserInfoFetched(GraphUser user) {
    				SeoulMapPedia.this.user = user;
    				updateUI();
    				// It's possible that we were waiting for this.user to be populated in order to post a
    				// status update.
    				//       handlePendingAction();
    			}
    		});

    		campusButton.setOnClickListener(new View.OnClickListener() {

    			public void onClick(View v) {

    				Intent j = new Intent(getBaseContext(), CampusMapsMenu.class);
    				startActivity(j);
    				/*	
				testView.setBitmap(R.drawable.caumap);
				testView.setAspectRatio( 16f / 9f );
				testView.setInsetSize(20);
				setContentView(testView);
    				 */
    			}
    		});     

    		googleButton.setOnClickListener(new View.OnClickListener() {

    			public void onClick(View view) {

    				Intent k = new Intent(getBaseContext(), GoogleMapsMenu.class);
    				//		Intent k = new Intent(getBaseContext(), GoogleMapsFragmentClass.class);
    				startActivity(k);


    			}
    		});
    		airportButton.setOnClickListener(new View.OnClickListener() {

    			public void onClick(View v) {
    				Intent k = new Intent(getBaseContext(), AiportMapsMenu.class);
    				//		Intent k = new Intent(getBaseContext(), GoogleMapsFragmentClass.class);
    				startActivity(k);

    			}
    		});

    		halalButton.setOnClickListener(new View.OnClickListener() {

    			public void onClick(View v) {
    				//		Intent k = new Intent(getBaseContext(), HalalFoodWeb.class);
    				//		startActivityForResult(k, 0);
    				Intent k = new Intent(getBaseContext(), SpecialDietActivity.class);
    				startActivity(k);


    			}
    		});
    		seoulButton.setOnClickListener(new View.OnClickListener() {

    			public void onClick(View v) {
    				Intent k = new Intent(getBaseContext(), SeoulLifeActivity.class);
    				startActivity(k);

    			}
    		});
    		aboutButton.setOnClickListener(new View.OnClickListener() {

    			public void onClick(View v) {
    				Intent i = new Intent(getBaseContext(), AboutUs.class);
    				startActivity(i);
    				//		Intent intent = new Intent();
    				//	    intent.setComponent(new ComponentName(getPackageName(), "About"));
    				//	    startActivity(intent);

    			}
    		});

    		contactButton.setOnClickListener(new View.OnClickListener() {

    			public void onClick(View v) {

    				Intent k = new Intent(getBaseContext(), ContactnHelpFragment.class);
    				Bundle bundle = new Bundle();
    				bundle.putInt("mainposition", 1);
    				k.putExtra("mainposition", 1);
    				startActivity(k);



    			}
    		});

    		helpButton.setOnClickListener(new View.OnClickListener() {

    			public void onClick(View v) {

    				Intent k = new Intent(getBaseContext(), ContactnHelpFragment.class);
    				Bundle bundle = new Bundle();
    				bundle.putInt("mainposition", 2);
    				k.putExtra("mainposition", 2);
    				startActivity(k);



    			}
    		});

    	}
 
        
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
    

 
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		/*
		
		switch (item.getItemId())
		{
		
		case R.id.menu_first: 
			Toast.makeText(SeoulMapPedia.this, "Campus Mode is Selected", Toast.LENGTH_SHORT).show();
			return true;
			
		case R.id.menu_second:
		Toast.makeText(SeoulMapPedia.this,"Google Map is Selected", Toast.LENGTH_SHORT).show();
		return true;
		
		case R.id.menu_third:
		Toast.makeText(SeoulMapPedia.this,"Search", Toast.LENGTH_SHORT).show();
		return true;
			
		case R.id.menu_fourth:
			Toast.makeText(SeoulMapPedia.this, "fouth", Toast.LENGTH_SHORT).show();
			return true;
			
		case R.id.menu_fifth:
			Toast.makeText(SeoulMapPedia.this, "fifth",Toast.LENGTH_SHORT).show();
			return true;
			
		case R.id.menu_sixth:
			Toast.makeText(SeoulMapPedia.this, "sixth", Toast.LENGTH_SHORT).show();
			return true;
			
		default:
			return super.onOptionsItemSelected(item);

		
	}
	*/
		return super.onOptionsItemSelected(item);

	}
	
	private static Date UTCDate2( int year, int month, int day, int hour, int minute, int second ) {
		Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT+09:00")); 
		cal.set(year, month-1, day, hour, minute, second); 
		return cal.getTime();
	}

	public static Date UTCDate1(int year, int month , int day, int hour, int minute, int second) {
	return UTCDate2 (year, month, day, hour,minute,second);
	}
	
	public static Date getInfo() {
		
		if(EDITION == MapEdition.TimeLock) {
			return TIMELOCK_EXPIRATION;
		}
		else 
			return null;
		
	}
	
    // Monitor changes in Session state
    private void onSessionStateChange(Session session, 
    		SessionState state, Exception exception) {
            if (state.equals(SessionState.OPENED)) {
            	
            	// Now we have a logged-in user. Let's get some information
            	// about them!
            	
                Request firstRequest = Request.newMeRequest(Session.getActiveSession(), 
                		new Request.GraphUserCallback() {
					public void onCompleted(GraphUser user, Response response) {
						if(user != null) {
						Toast.makeText(getApplicationContext(), 
								"Hello, " + user.getName(), 
								Toast.LENGTH_SHORT)
								.show();
						} else {
							Toast.makeText(getApplicationContext(), 
									"Network not available", 
									Toast.LENGTH_SHORT)
									.show();
						}
					}
				});
                firstRequest.executeAsync();
                shareButton.setVisibility(View.VISIBLE);
     //           profilePictureView.setVisibility(View.VISIBLE);
 
            } else if (state.isClosed()) {
            	 shareButton.setVisibility(View.GONE);
    //        	 profilePictureView.setVisibility(View.INVISIBLE);
            }
        
    }
    
    private void updateUI() {
        Session session = Session.getActiveSession();
        boolean enableButtons = (session != null && session.isOpened());

        if (enableButtons && user != null) {
            profilePictureView.setProfileId(user.getId());
        } else {
            profilePictureView.setProfileId(null);
        }
    }
    
 // Take the currently selected item and populate a Feed
    // Dialog with its content to be posted to Facebook
    
    private void publishFeedDialog() {
        Bundle params = new Bundle();
        
        // Add details about the clicked item to the
        // story params Bundle
        params.putString("name","Go to Seoul'sMapPedia Facebook Page");
        params.putString("description", "Welcome to Seoul as an International Student");
        params.putString("link","https://www.facebook.com/seoulforeveryone");
        params.putString("picture", "https://raw.github.com/hassanabidpk/SeoulMapPedia/master/SeoulMapPedia/res/drawable-xhdpi/app_share_img.jpg");

        WebDialog feedDialog = (
            new WebDialog.FeedDialogBuilder(this,
                Session.getActiveSession(),
                params))
            .setOnCompleteListener(new OnCompleteListener() {

                public void onComplete(Bundle values,
                    FacebookException error) {
                    if (error == null) {
                        // When the story is posted, echo the success
                        // and the post Id.
                        final String postId = values.getString("post_id");
                        if (postId != null) {
                            Toast.makeText(getBaseContext(),
                                "Posted story, id: "+postId,
                                Toast.LENGTH_SHORT).show();
                        } else {
                            // User clicked the Cancel button
                            Toast.makeText(getBaseContext(), 
                                "Publish cancelled", 
                                Toast.LENGTH_SHORT).show();
                        }
                    } else if (error instanceof 
                    FacebookOperationCanceledException) {
                        // User clicked the "x" button
                        Toast.makeText(getBaseContext().getApplicationContext(), 
                            "Publish cancelled", 
                            Toast.LENGTH_SHORT).show();
                    } else {
                        // Generic, ex: network error
                        Toast.makeText(getBaseContext().getApplicationContext(), 
                            "Error posting story", 
                            Toast.LENGTH_SHORT).show();
                    }
                }

            })
            .build();
        feedDialog.show();
    }
    
    private class SessionStatusCallback implements Session.StatusCallback {

		public void call(Session session, SessionState state,
				Exception exception) {
			// TODO Auto-generated method stub
			
		}

	
    }
    
    @Override
    public void onResume() {
        super.onResume();
        curLang = getApplicationContext().getResources().getConfiguration().locale.getLanguage();
        Log.d(LOG, "onResume" + " Curr Language is : " + curLang);
//        setLocale(curLang, -1);
        if (uiHelper != null)
        	uiHelper.onResume();
     /*   
        if(bundle != null) {
    		langCode = bundle.getString("currentlang");
    		if(langCode != null) {
    			setLocale(langCode, -1);
    			Log.d(LOG, "(onResume1) Setting Locale, langCode: " + langCode);
    		}
    	 } else {
    		 bundle = new Bundle();
    		 langCode = bundle.getString("currentlang");
     		if(langCode != null) {
     			setLocale(langCode, -1);
     			Log.d(LOG, "(onResume2) Setting Locale, langCode: " + langCode);
     		}
    	 }
    	 */
        
		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser != null) {
			// Check if the user is currently logged
			// and show any cached content
			updateViewsWithProfileInfo();
		} else {
			// If the user is not logged in, go to the
			// activity showing the login view.
			startLoginActivity();
		}
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(LOG, "onPause");
        if (uiHelper != null)
        uiHelper.onPause();
        
        bundle = new Bundle();
        if(bundle != null) {
        	if(languageNo != -1) {
        		bundle.putString("currentlang", LANG_CODES[languageNo]);
        	Log.d(LOG, "(onPause) Saving Locale, langCode: " + LANG_CODES[languageNo]);
        	}
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (uiHelper != null)
        uiHelper.onActivityResult(requestCode, resultCode, data);
        
        
        if (resultCode != RESULT_OK) {
			return;
		}
		/*
		Cursor c = getApplicationContext().getContentResolver().query(data.getData(), null, null, null, null);
		if(c!=null) {
		c.moveToNext();
		String path = c.getString(c.getColumnIndex(MediaStore.MediaColumns.DATA));
		c.close();
		
		StoryLink storyLink = StoryLink.getLink(getApplicationContext());

		// check, intent is available.
		if (!storyLink.isAvailableIntent()) {
			alert("Not installed KakaoStory.");
			return;
		}
		
		storyLink.openStoryLinkImageApp(this, path);
		}
		*/

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG, "onDestroy");
        if (uiHelper != null)
        uiHelper.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (uiHelper != null)
        uiHelper.onSaveInstanceState(outState);
    }
    
    public void sendGroove(View v) {
    	
    	Intent webIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.facebook.com/GrooveKorea"));
    	startActivity(webIntent);    
    	
    }
    

	/**
	 * Send URL
	 * @throws NameNotFoundException 
	 */
	public void sendUrlLink(View v) throws NameNotFoundException {
		// Recommended: Use application context for parameter.
		KakaoLink kakaoLink = KakaoLink.getLink(getApplicationContext());

		// check, intent is available.
		if (!kakaoLink.isAvailableIntent()) {
			alert("Not installed KakaoTalk.");			
			return;
		}

		/**
		 * @param activity
		 * @param url
		 * @param message
		 * @param appId
		 * @param appVer
		 * @param appName
		 * @param encoding
		 */
		kakaoLink.openKakaoLink(this, 
				"https://www.facebook.com/SeoulsMaPpediapedia", 
				"First Seoul'sMAPpedia Message for send url.", 
				getPackageName(), 
				getPackageManager().getPackageInfo(getPackageName(), 0).versionName, 
				"Seoul'sMAPpedia beta App", 
				encoding);
	}

	/**
	 * Send App data
	 */
	public void sendAppData(View v) throws NameNotFoundException {
		ArrayList<Map<String, String>> metaInfoArray = new ArrayList<Map<String, String>>();

		// If application is support Android platform.
		Map<String, String> metaInfoAndroid = new Hashtable<String, String>(1);
		metaInfoAndroid.put("os", "android");
		metaInfoAndroid.put("devicetype", "phone");
		metaInfoAndroid.put("installurl", "market://details?id=com.kakao.talk");
		metaInfoAndroid.put("executeurl", "kakaoLinkTest://starActivity");
		
		// If application is support ios platform.
		Map<String, String> metaInfoIOS = new Hashtable<String, String>(1);
		metaInfoIOS.put("os", "ios");
		metaInfoIOS.put("devicetype", "phone");
		metaInfoIOS.put("installurl", "your iOS app install url");
		metaInfoIOS.put("executeurl", "kakaoLinkTest://starActivity");
		
		// add to array
		metaInfoArray.add(metaInfoAndroid);
		metaInfoArray.add(metaInfoIOS);
		
		// Recommended: Use application context for parameter. 
		KakaoLink kakaoLink = KakaoLink.getLink(getApplicationContext());
		
		// check, intent is available.
		if(!kakaoLink.isAvailableIntent()) {
			alert("Not installed KakaoTalk.");			
			return;
		}
		
		/**
		 * @param activity
		 * @param url
		 * @param message
		 * @param appId
		 * @param appVer
		 * @param appName
		 * @param encoding
		 * @param metaInfoArray
		 */
		kakaoLink.openKakaoAppLink(
				this, 
				"http://link.kakao.com/?test-android-app", 
				"First KakaoLink Message for send app data.",  
				getPackageName(), 
				getPackageManager().getPackageInfo(getPackageName(), 0).versionName,
				"KakaoLink Test App",
				encoding, 
				metaInfoArray);
	}

	
	/**
	 * Story Link
	 * Send posting (text or url)
	 * @throws NameNotFoundException 
	 */
	public void sendPostingLink(View v) throws NameNotFoundException {
		Map<String, Object> urlInfoAndroid = new Hashtable<String, Object>(1);
		urlInfoAndroid.put("title", "(Korean) blah blah");
		urlInfoAndroid.put("desc", "(bah blah.");
		urlInfoAndroid.put("imageurl", new String[] {"http://m1.daumcdn.net/photo-media/201209/27/ohmynews/R_430x0_20120927141307222.jpg"});
		urlInfoAndroid.put("type", "article");
		
		// Recommended: Use application context for parameter.
		StoryLink storyLink = StoryLink.getLink(getApplicationContext());

		// check, intent is available.
		if (!storyLink.isAvailableIntent()) {
			alert("Not installed KakaoStory.");			
			return;
		}

		/**
		 * @param activity
		 * @param post (message or url)
		 * @param appId
		 * @param appVer
		 * @param appName
		 * @param encoding
		 * @param urlInfoArray
		 */
		storyLink.openKakaoLink(this, 
				"http://m.media.daum.net/entertain/enews/view?newsid=20120927110708426",
				getPackageName(), 
				getPackageManager().getPackageInfo(getPackageName(), 0).versionName, 
				"Hangeul",
				encoding, 
				urlInfoAndroid);
	}
	
	/**
	 * Story Link
	 * Send posting (image)
	 * @throws NameNotFoundException 
	 */
	public void sendPostingImage(View v) throws NameNotFoundException {
		StoryLink storyLink = StoryLink.getLink(getApplicationContext());
		
		// check, intent is available.
		if (!storyLink.isAvailableIntent()) {
			alert("Not installed KakaoStory.");
			return;
		}
		
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");
		intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(intent, 0); 
	}
	
	
	private void alert(String message) {
		new AlertDialog.Builder(this)
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setTitle(R.string.app_name)
			.setMessage(message)
			.setPositiveButton(android.R.string.ok, null)
			.create().show();
	}





	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	private void getHashKeyforFb() {
		try {
		    PackageInfo info = getPackageManager().getPackageInfo(
		          "com.sisfgroupd.seoulmappedia", PackageManager.GET_SIGNATURES);
		    for (Signature signature : info.signatures) 
		        {
		           MessageDigest md = MessageDigest.getInstance("SHA");
		           md.update(signature.toByteArray());
		           Log.d(LOG , "KeyHash : " + Base64.encodeToString(md.digest(), Base64.DEFAULT));
		        }
		    } catch (NameNotFoundException e) {
		    	
		    } catch (NoSuchAlgorithmException e) {
			
		}

	}
	
	public void setLocale(String lang, int langPos) { 
		Locale myLocale = new Locale(lang); 
		Resources res = getResources(); 
		DisplayMetrics dm = res.getDisplayMetrics(); 
		Configuration conf = res.getConfiguration(); 
		conf.locale = myLocale; 
		res.updateConfiguration(conf, dm); 
		Log.d(LOG, "Setting Locale to : " + lang);
		
		languagePref = getSharedPreferences("language", MODE_PRIVATE);
		if(languagePref == null)
			return;
		SharedPreferences.Editor editor = languagePref.edit();
		if(editor!=null) {
			if(langPos != -1) {
				editor.putString("language2Load", LANG_CODES[langPos]);
				Log.d(LOG, "Setting Locale to (2) : saving Lang: " + LANG_CODES[langPos]);
			}
			else {
				if(langPos == -1) {
					editor.putString("language2Load", lang);
					Log.d(LOG, "Setting Locale to (3) : saving Lang: " + lang);
				}
			}
				
		}
		editor.commit();
		
		
//		resetScreen(this);

		}


	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long arg3) {
		
//		setLocale(LANG_CODES[position]);
		
	}


	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	} 
	
	@Override
	public void onBackPressed() {
		Log.d(LOG, "onBackPressed and currLanguage is : " + curLang);
		setLocale(curLang, -1);
		finish();
		super.onBackPressed();
	}

	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static void resetScreen(Activity activity) {
	    if (activity != null) {
	        if (Build.VERSION.SDK_INT >= 11) {
	            activity.recreate();
	        } else {
	            Intent intent = activity.getIntent();
	            activity.overridePendingTransition(0, 0);
	            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
	            activity.finish();

	            activity.overridePendingTransition(0, 0);
	            activity.startActivity(intent);
	        }
	    }
	}
    
	@Override
	protected void onStop() {
		super.onStop();
		curLang = getApplicationContext().getResources().getConfiguration().locale.getLanguage();
		Log.d(LOG, "onStop");
		languagePref = getSharedPreferences("language", MODE_PRIVATE);
		if(languagePref == null)
			return;
		SharedPreferences.Editor editor = languagePref.edit();
		if(editor!=null) {
			if(languageNo != -1) {
				editor.putString("language2Load", LANG_CODES[languageNo]);
				Log.d(LOG, "onStop  saving language1 : " + LANG_CODES[languageNo]);
			}
			else {
				if(languageNo == -1)  {
					
					editor.putString("language2Load", curLang.toLowerCase(Locale.US));
					Log.d(LOG, "onStop  saving language2 : " + curLang.toLowerCase(Locale.US));
				}
			}
				
		}
		editor.commit();
		Log.d(LOG, "onStop  saving and setting language3 : " + curLang.toLowerCase(Locale.US));
		setLocale(curLang, -1);
		
//		FlurryAgent.onEndSession(this);
	}
	
	public class SeoulMenuAdapter extends BaseAdapter {
		
		private Context mContext;
		LayoutInflater inflater;
		
		public SeoulMenuAdapter(Context c) {
			mContext = c;
			inflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public int getCount() {
			return mThumbIds.length;
		}

		public Object getItem(int arg0) {
			return arg0;
		}

		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			
			  if (convertView == null) {
		            convertView = inflater.inflate(R.layout.cell, null);
					GridView.LayoutParams layoutParams = new GridView.LayoutParams(230,295);
					convertView.setLayoutParams(layoutParams);
					convertView.setPadding(0, 0, 0, 0);
		        }
	//	        Button button = (Button) convertView.findViewById(R.id.grid_item);
	//	        button.setText(mTextIds[position]);
		        ImageView button = (ImageView) convertView.findViewById(R.id.grid_item);
		        TextView textView = (TextView) convertView.findViewById(R.id.menuText);
		        button.setImageResource(mThumbIds[position]);
		        textView.setText(mTextIds[position]);
		        return convertView;
			
			/*ViewGroup view = (ViewGroup) convertView;
			ImageView imageView = null;
			TextView textView = null;
			if(convertView == null) {
				
				view = (ViewGroup) inflater.inflate(R.layout.menu_grid_view, null);
				ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(200,200);
		//		view.setLayoutParams(layoutParams);
				view.setPadding(5, 5, 5, 5);
				
			}
			imageView = (ImageView) view.findViewById(R.id.menuImage);
			textView = (TextView) view.findViewById(R.id.menuText);
			imageView.setImageResource(mThumbIds[position]);
			textView.setText(mTextIds[position]);
			return imageView;*/
		}
		
	    // references to our images
	    private Integer[] mThumbIds = {
	            R.drawable.campus, R.drawable.maps, R.drawable.airportbus, R.drawable.dining, 
	            R.drawable.grants, R.drawable.jobs, R.drawable.onlinecourses, R.drawable.volunteering,
	            R.drawable.aboutseoul, R.drawable.koreatour, R.drawable.gangnamtour, R.drawable.groove_96_new, 
	            R.drawable.socialmedia,R.drawable.contactus, R.drawable.koreanwave, R.drawable.naver_dictionary
	    };
	    
	    private Integer[] mTextIds = {
	            R.string.campus, R.string.google_maps,R.string.airport_bus, R.string.dining,
	            R.string.scholarships,R.string.jobs,R.string.online_courses,R.string.volunteering_seoul,
	            R.string.seoul_life,R.string.korea_tour, R.string.gangnam_tour,R.string.groove_korea,
	            R.string.social,R.string.contact_us, R.string.korean_wave, R.string.naver_dic
	    };
		
	}
	
	public class SeoulMenuAdapterWC extends BaseAdapter {
		
		private Context mContext;
		LayoutInflater inflater;
		
		public SeoulMenuAdapterWC(Context c) {
			mContext = c;
			inflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public int getCount() {
			return mThumbIds.length;
		}

		public Object getItem(int arg0) {
			return arg0;
		}

		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			
			  if (convertView == null) {
		            convertView = inflater.inflate(R.layout.cell, null);
					GridView.LayoutParams layoutParams = new GridView.LayoutParams(270,345);
					convertView.setLayoutParams(layoutParams);
					convertView.setPadding(0, 0, 0, 0);
		        }
	//	        Button button = (Button) convertView.findViewById(R.id.grid_item);
	//	        button.setText(mTextIds[position]);
		        ImageView button = (ImageView) convertView.findViewById(R.id.grid_item);
		        TextView textView = (TextView) convertView.findViewById(R.id.menuText);
		        button.setImageResource(mThumbIds[position]);
		        textView.setText(mTextIds[position]);
		        return convertView;
			
			/*ViewGroup view = (ViewGroup) convertView;
			ImageView imageView = null;
			TextView textView = null;
			if(convertView == null) {
				
				view = (ViewGroup) inflater.inflate(R.layout.menu_grid_view, null);
				ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(200,200);
		//		view.setLayoutParams(layoutParams);
				view.setPadding(5, 5, 5, 5);
				
			}
			imageView = (ImageView) view.findViewById(R.id.menuImage);
			textView = (TextView) view.findViewById(R.id.menuText);
			imageView.setImageResource(mThumbIds[position]);
			textView.setText(mTextIds[position]);
			return imageView;*/
		}
		
	    // references to our images
	    private Integer[] mThumbIds = {
	            R.drawable.student_life, R.drawable.dinningnew, R.drawable.onlinecourses,
	            R.drawable.seoul_life,
	            R.drawable.social_media, R.drawable.instagram150, R.drawable.worldcup_menu,R.drawable.worldcup_schedule,R.drawable.ic_launcher
	    };
	    
	    private Integer[] mTextIds = {
	            R.string.student_life, R.string.dining,R.string.online_courses,
	            R.string.seoul_life,
	            R.string.social, R.string.instagram_wc, R.string.worldcup_text,R.string.worldcup_schedule, R.string.sm_website
	    };
		
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
//		FlurryAgent.onStartSession(this, "NGSDDGMFBBJ5Z3NSZW84");
	}

	private void onLogoutButtonClicked() {
		// Log the user out
		ParseUser.logOut();

		// Go to the login view
		startLoginActivity();
	}

	private void startLoginActivity() {
		Intent intent = new Intent(this, SocialLoginActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
	
	private void makeMeRequest() {
		Request request = Request.newMeRequest(ParseFacebookUtils.getSession(),
				new Request.GraphUserCallback() {
					public void onCompleted(GraphUser user, Response response) {
						if (user != null) {
							// Create a JSON object to hold the profile info
							JSONObject userProfile = new JSONObject();
							try {
								// Populate the JSON object
								userProfile.put("facebookId", user.getId());
								userProfile.put("name", user.getName());
								if (user.getLocation().getProperty("name") != null) {
									userProfile.put("location", (String) user
											.getLocation().getProperty("name"));
								}
								if (user.getBirthday() != null) {
									userProfile.put("birthday",
											user.getBirthday());
								}
								if (user.getProperty("user_hometown") != null) {
									userProfile
											.put("user_hometown",
													(String) user
															.getProperty("user_hometown"));
								}

								// Save the user profile info in a user property
								ParseUser currentUser = ParseUser
										.getCurrentUser();
								currentUser.put("profile", userProfile);
								currentUser.saveInBackground();

								// Show the user info
								updateViewsWithProfileInfo();
							} catch (JSONException e) {
								Log.d(LOG,
										"Error parsing returned user data.");
							}

						} else if (response.getError() != null) {
							if ((response.getError().getCategory() == FacebookRequestError.Category.AUTHENTICATION_RETRY)
									|| (response.getError().getCategory() == FacebookRequestError.Category.AUTHENTICATION_REOPEN_SESSION)) {
								Log.d(LOG,
										"The facebook session was invalidated.");
								onLogoutButtonClicked();
							} else {
								Log.d(LOG,
										"Some other error: "
												+ response.getError()
														.getErrorMessage());
							}
						}
					}
				});
		request.executeAsync();

	}
	
	private void updateViewsWithProfileInfo() {
		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser.get("profile") != null) {
			JSONObject userProfile = currentUser.getJSONObject("profile");
			try {
				if (userProfile.getString("facebookId") != null) {
					String facebookId = userProfile.get("facebookId")
							.toString();
					userProfilePictureView.setProfileId(facebookId);
				} else {
					// Show the default, blank user profile picture
					userProfilePictureView.setProfileId(null);
				}
				if (userProfile.getString("name") != null) {
					userNameView.setText(userProfile.getString("name"));
				} else {
					userNameView.setText("");
				}
				if (userProfile.getString("location") != null) {
					userLocationView.setText(userProfile.getString("location"));
				} else {
					userLocationView.setText("");
				}
			} catch (JSONException e) {
				Log.d(LOG,
						"Error parsing saved user data.");
			}

		}
	}
	
	private void buildDialog() {
		
		 AlertDialog.Builder builder = new AlertDialog.Builder(SeoulMapPedia.this);
		    builder.setTitle("Select")
		           .setItems(R.array.student_array, new DialogInterface.OnClickListener() {
		               public void onClick(DialogInterface dialog, int which) {
		               // The 'which' argument contains the index position
		               // of the selected item
		            	   
		            	   switch (which) {
		            	   case 0 : 	Intent j = new Intent(getBaseContext(), CampusMapsMenu.class);
		            	   startActivity(j);
		            	   break;
		            	   case 1: Intent k = new Intent(getBaseContext(), GoogleMapsMenu.class);
		            	   //		Intent k = new Intent(getBaseContext(), GoogleMapsFragmentClass.class);
		            	   startActivity(k);
		            	   break;

		            	   case 2: Intent l = new Intent(getBaseContext(), AiportMapsMenu.class);
		            	   //		Intent k = new Intent(getBaseContext(), GoogleMapsFragmentClass.class);
		            	   startActivity(l);
		            	   break;
		            	   case 3: 		 Intent scholar = new Intent(getBaseContext(), NewsnEventsActivity.class);
		            	   scholar.putExtra("seoulposition", 6);
		            	   startActivity(scholar); break;
		            	   case 4: 		 Intent jobs = new Intent(getBaseContext(), NewsnEventsActivity.class);
		            	   jobs.putExtra("seoulposition", 7);
		            	   startActivity(jobs);
		            	   break;

		            	   }
		           }
		    });
		    builder.create().show();
		
	}

}
