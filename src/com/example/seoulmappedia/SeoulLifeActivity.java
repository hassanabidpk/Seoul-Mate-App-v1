package com.example.seoulmappedia;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.sisfgroupd.seoulmappedia.R;

public class SeoulLifeActivity extends FragmentActivity implements SeoulLifeFragment.OnSeoulMenuItemSelectedListener {
	
	private static final String LOG_TAG = "SeoulLifeActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.seoullife_layout);
		
		if(findViewById(R.id.fragment_container_seoul) != null) {
			
			  if (savedInstanceState != null) {
	                return;
	            } 
		}
		
		SeoulLifeFragment mSeoulLifeFragment = new SeoulLifeFragment();
		mSeoulLifeFragment.setArguments(getIntent().getExtras());
		getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_seoul, mSeoulLifeFragment).commit();
		  
		
		
		
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}
	
	
	protected void onResume() {
		super.onResume();
		
	};
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}



	public void onSeoulMenuItemSelected(int pos) {
		
		Log.d(LOG_TAG, " Position clicked: " + pos);
		
			
		
		switch(pos) {
		
		case 0:  
			 Intent intent = new Intent(getBaseContext(), NewsnEventsActivity.class);
			 intent.putExtra("seoulposition", pos);
			 startActivity(intent);
				break;

		case 1: // startDetailFragment(2);
			 Intent intent2 = new Intent(getBaseContext(), NewsnEventsActivity.class);
			 intent2.putExtra("seoulposition", pos);
            startActivity(intent2);
				break;
		case 2: // startDetailFragment(3);
			Intent intent33 = new Intent(getBaseContext(), NewsnEventsActivity.class);
			 intent33.putExtra("seoulposition", pos);
           startActivity(intent33);
				break;
		case 3: // startDetailFragment(3);
			Intent intent3 = new Intent(getBaseContext(), NewsnEventsActivity.class);
			 intent3.putExtra("seoulposition", pos);
           startActivity(intent3);
				break;
		case 4:	// startDetailFragment(4);
			 Intent intent4 = new Intent(getBaseContext(), NewsnEventsActivity.class);
			 intent4.putExtra("seoulposition", pos);
            startActivity(intent4);
				break;
		case 5:// startDetailFragment(5);
		Intent intent5 = new Intent(getBaseContext(), NewsnEventsActivity.class);
		 intent5.putExtra("seoulposition", pos);
       startActivity(intent5);
				break;
		case 6:		DemoDetails demo = new DemoDetails(R.string.uisettings_demo, R.string.uisettings_description,
				GoogleMapsFragmentClass.class);
			Bundle bundle = new Bundle();
			Intent globalIntent = new Intent(getBaseContext(),demo.activityClass);
			if(demo != null & bundle != null)
				bundle.putInt("univalue", 18);
				globalIntent.putExtra("univalue", 18);
				globalIntent.putExtra("mapString", UniversityList.MAP_NAME[18]);
				startActivity(globalIntent);
				break;
		case 7:	{
					startDetailFragment(11);
					break;
		}
			
		default: break;
		
		}
		
	}
	
	private void startDetailFragment(int position) {
		  // If the frag is not available, we're in the one-pane layout and must swap frags...

        // Create fragment and give it an argument for the selected article
        SeoulLifeDetailFragment newFragment = new SeoulLifeDetailFragment();
        Bundle args = new Bundle();
        args.putInt(SeoulLifeDetailFragment.ARG_POSITION, position);
        newFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container_seoul, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
	}
	
	/**
	* A simple POJO that holds the details about the demo that are used by the List Adapter.
	*/
	private static class DemoDetails {
	 /**
	  * The resource id of the title of the demo.
	  */
	 private final int titleId;

	 /**
	  * The resources id of the description of the demo.
	  */
	 private final int descriptionId;

	 /**
	  * The demo activity's class.
	  */
	 private final Class<? extends FragmentActivity> activityClass;

	 public DemoDetails(
	         int titleId, int descriptionId, Class<? extends FragmentActivity> activityClass) {
	     super();
	     this.titleId = titleId;
	     this.descriptionId = descriptionId;
	     this.activityClass = activityClass;
	 }
	}
	
	private static final DemoDetails[] demos = {new DemoDetails(R.string.uisettings_demo, R.string.uisettings_description,
			GoogleMapsFragmentClass.class)};
	
	
	  @Override
	  protected void onStart() {
	  	// TODO Auto-generated method stub
	  	super.onStart();
//	  	FlurryAgent.onStartSession(getApplicationContext(), "NGSDDGMFBBJ5Z3NSZW84");
	  }

	  @Override
	  protected void onStop() {
	  	// TODO Auto-generated method stub
	  	super.onStop();
//	  	FlurryAgent.onEndSession(getApplicationContext());
	  }

}
