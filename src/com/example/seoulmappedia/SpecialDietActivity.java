package com.example.seoulmappedia;

import java.util.Locale;

import com.example.seoulmappedia.SpecialDietFragment.OnDietMenuItemSelectedListener;
import com.example.seoulmappedia.SpecialDietListFragment.OnDietItemSelectedListener;
import com.sisfgroupd.seoulmappedia.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;

public class SpecialDietActivity extends FragmentActivity implements OnDietItemSelectedListener, OnDietMenuItemSelectedListener{

	private static final String LOG_TAG = "SpecialDietActivity";
	private String getLang;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.specialdiet_layout);
		SharedPreferences settings = getSharedPreferences("language", MODE_PRIVATE);
    	getLang = settings.getString("language2Load", "notfound");
		
		if(findViewById(R.id.fragment_container_diet) != null) {
			
			  if (savedInstanceState != null) {
	                return;
	            } 
			  /*
		SpecialDietListFragment   mSpecialDietListFragment = new SpecialDietListFragment();
		
		mSpecialDietListFragment.setArguments(getIntent().getExtras());
		   // Add the fragment to the 'fragment_container' FrameLayout
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mSpecialDietListFragment).commit();
                */
			  
			  SpecialDietFragment mSpecial = new SpecialDietFragment();
			  mSpecial.setArguments(getIntent().getExtras());
			  getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_diet, mSpecial).commit();
			  
		}
		
		
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
  protected void onPause() {
	  
	  super.onPause();
	  /*
		String curLang = getApplicationContext().getResources().getConfiguration().locale.getLanguage();
		setLocale(curLang, -1);
		Log.d(LOG_TAG, "onPause ---- Setting Locale, Language : " + curLang);
	  super.onPause();
	  */
  };
  
  @Override
  public void onBackPressed() {

		String curLang = getApplicationContext().getResources().getConfiguration().locale.getLanguage();
		if(!getLang.equals("notfound")) {
			setLocale(getLang, -1);
			Log.d(LOG_TAG, "onStop ---- Setting Locale, Language1 : " + getLang);
		} else {
			setLocale(curLang, -1);
			Log.d(LOG_TAG, "onStop ---- Setting Locale, Language1 : " + curLang);
		}

	  super.onBackPressed();
  }

	public void onDietItemSelected(int pos) {
	
	
	
		if(pos == 2) {
		
			Intent intent = new Intent(getApplicationContext(),
					HalalFoodWeb.class);
			//	      intent.putExtra(DetailActivity.EXTRA_URL, link);
		     startActivityForResult(intent, 0);
		
	}
	
}

	public void onDietMenuItemSelected(int pos) {
	
		Log.d(LOG_TAG, "Position clicked :" + pos);
		
		if(pos == 0) {
			startDetailFragment(0);
		}
		if(pos == 1) {
			startDetailFragment(1);
		}
		if(pos == 2) {
		
			Intent intent = new Intent(getApplicationContext(),
		          HalalFoodWeb.class);
			//	      intent.putExtra(DetailActivity.EXTRA_URL, link);
		     startActivityForResult(intent, 0);
	
		} 
	
	}
	
	private void startDetailFragment(int position) {
		  // If the frag is not available, we're in the one-pane layout and must swap frags...

      // Create fragment and give it an argument for the selected article
	SpecialDietDetailFragment newDietFragment = new SpecialDietDetailFragment();
      Bundle args = new Bundle();
      args.putInt(SpecialDietDetailFragment.ARG_POSITION, position);
      Log.d(LOG_TAG, " Args : " + position);
      newDietFragment.setArguments(args);
      FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

      // Replace whatever is in the fragment_container view with this fragment,
      // and add the transaction to the back stack so the user can navigate back
      transaction.replace(R.id.fragment_container_diet, newDietFragment);
      transaction.addToBackStack(null);

      // Commit the transaction
      transaction.commit();
	}
	
	@Override
	protected void onStop() {
		/*
		String curLang = getApplicationContext().getResources().getConfiguration().locale.getLanguage();
		setLocale(curLang, -1);
		Log.d(LOG_TAG, "onStop ---- Setting Locale, Language : " + curLang);
		*/
		super.onStop();
		
	
	}
	public void setLocale(String lang, int langPos) { 
		Locale myLocale = new Locale(lang); 
		Resources res = getResources(); 
		DisplayMetrics dm = res.getDisplayMetrics(); 
		Configuration conf = res.getConfiguration(); 
		conf.locale = myLocale; 
		res.updateConfiguration(conf, dm); 
		Log.d(LOG_TAG, "Setting Locale to : " + lang);


		}
	
	  @Override
	  protected void onStart() {
	  	// TODO Auto-generated method stub
	  	super.onStart();
	  
	  }

	
}
