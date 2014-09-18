package com.example.seoulmappedia;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import com.sisfgroupd.seoulmappedia.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ExpiredSeoulActivity extends Activity {
private static final Date EXPIRES = SeoulMapPedia.getInfo();
@Override
protected void onCreate(Bundle savedInstanceState) {
	
	setContentView(R.layout.expired_activity);
	TextView text = (TextView) findViewById(R.id.expired);
	text.setText("App is Expired!!!");
	super.onCreate(savedInstanceState);
}

public static boolean checkExpiredDate(Activity activity) {
	
	Date now = new Date();
	
	Log.d("ExpiredActivity", " Now Date : " + now  + " EXPIRED: " + EXPIRES);

	if(EXPIRES!=null && now.after(EXPIRES)) {
		Intent intent = new Intent(activity,ExpiredSeoulActivity.class);
		activity.startActivityForResult(intent, 0);
		activity.finish();
		return true;
		
	}
	
	return false;
	
	
}


}
