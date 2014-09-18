package com.example.seoulmappedia;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.seoulmate.social.SocialLoginActivity;
import com.sisfgroupd.seoulmappedia.R;

public class SplashActivity extends Activity {

	private static final int DELAY_TIME = 500;

	private int splashCount;
	private View mSeoulMapPedia;
	
protected void onCreate(android.os.Bundle savedInstanceState) {
	
	
	super.onCreate(savedInstanceState);
	
	setContentView(R.layout.splash_activity);

	mSeoulMapPedia = (View) findViewById(R.id.splash);
	
	splashCount = 0;

	new Handler().postDelayed(new Runnable() {
		public void run() {

			if (splashCount == 0) {
				splashCount++;
				mSeoulMapPedia.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out));
		//		mSeoulMapPedia.setVisibility(View.INVISIBLE);
				new Handler().postDelayed(this, DELAY_TIME);
				return;
			}
			
//			startActivity(new Intent(SplashActivity.this, SeoulMapPedia.class));
			startActivity(new Intent(SplashActivity.this, SocialLoginActivity.class));
			
			overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
			finish();

		}
	}, DELAY_TIME);
	
	
};

@Override
protected void onStart() {
	// TODO Auto-generated method stub
	super.onStart();
//	FlurryAgent.onStartSession(this, "NGSDDGMFBBJ5Z3NSZW84");
}

@Override
protected void onStop() {
	// TODO Auto-generated method stub
	super.onStop();
//	FlurryAgent.onEndSession(this);
}


}
