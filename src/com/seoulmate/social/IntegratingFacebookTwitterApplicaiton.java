package com.seoulmate.social;

import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseTwitterUtils;
import com.sisfgroupd.seoulmappedia.R;

import android.app.Application;

public class IntegratingFacebookTwitterApplicaiton extends Application {

	static final String TAG = "SeoulMateApp";

	@Override
	public void onCreate() {
		super.onCreate();

		 Parse.initialize(this, "rZ4xc4nb7PgqASjHeV8oTgH52TRVlDJBb5TBvO1O", "TyzLDXJYiNUOFLkAmHRWniZXAehsNqfgjP46n1Wr");

		// Set your Facebook App Id in strings.xml
		ParseFacebookUtils.initialize(getString(R.string.app_id));
		
//		ParseTwitterUtils.initialize("7QmBSTeKLUWFVV9u9L17XK5ON", "K9TZJGse7eNwa1bQL24eOUNA1IhCIspx98rsm2RskqXnyEfqKl");

	}
	
}
