package com.example.seoulmappedia;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;
import com.sisfgroupd.seoulmappedia.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


public class GlobalClass {
	
	private Context mContext;
	private Activity mActivity;
	private String encoding = "UTF-8";
	private  UniversityList list;

	public GlobalClass(Context context, Activity activity) {
		mContext = context;
		mActivity = activity;
		
	}
	
	/**
	 * Send URL
	 * @throws NameNotFoundException 
	 */
	public void sendUrlLink(View v, int pos, int campus) throws NameNotFoundException {
		// Recommended: Use application context for parameter.
		String mapUrl = "";
		String name = "";
		KakaoLink kakaoLink = KakaoLink.getLink(mContext);
		list = new UniversityList();
       if(list != null) {
    	   mapUrl = list.MAP_URLS[pos];
    	   
    	   if(campus == 1) {
    		   name = "Check Campus Map on this link";
    		   mapUrl = list.CAMPUS_URLS[pos];
    		   
    	   } else if (campus == 2){
    	    mapUrl = list.MAP_URLS[pos];
    	    name = "Check Custom Google Map on this link";
    	   } else if (campus == 3) {
    		   mapUrl = list.AIRPORT_URLS[pos];
    		   name = "Check Airport Bus route on this link";
    		   
    	   } else {
    		   mapUrl = list.MAP_URLS[pos];
    		   name = "First Seoul'sMapPedia Message for send url.";
    	   }
    	   
       }
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
		kakaoLink.openKakaoLink(mActivity, 
				mapUrl, 
				name, 
				mContext.getPackageName(), 
				mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionName, 
				"Seoul'sMapPedia beta App", 
				encoding);
	}

	
	private void alert(String message) {
		AlertDialog alertDialog = new AlertDialog.Builder(mActivity)
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setTitle(R.string.app_name)
		.setMessage(message)
		.setPositiveButton(android.R.string.ok, null).create();
		Log.d("GlobalClass", " alert dialog 1 : " + alertDialog);
		/*
		if(alertDialog == null) {
			alertDialog = new AlertDialog.Builder(mContext)
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setTitle(R.string.app_name)
			.setMessage(message)
			.setPositiveButton(android.R.string.ok, null).create();
			Log.d("GlobalClass", " alert dialog 2 : " + alertDialog);
		}
		*/
		if (alertDialog != null) {
			alertDialog.show();
			Log.d("GlobalClass", " alert dialog 3 : " + alertDialog);
		}
		else return;
	}
	
	   public void publishFeedDialog(int pos, int campus, String name) {
		   String description = null;
		   String link = null;
		   String picture = null;
		   list = new UniversityList();
		   if(list != null) {
	    	   
	    	   if(campus == 1) {
	    		   link = list.CAMPUS_URLS[pos];
	    	   } else if(campus == 2) {
	    	    link = list.MAP_URLS[pos];
	    	   } else if(campus == 3) {
	    		   link = list.AIRPORT_URLS[pos];
	    	   } else {
	    		   
	    	   }
	    	   
	       }
	        Bundle params = new Bundle();
	        
	        if(name == null)
	        	name = "Go to Seoul'sMapPedia Facebook Page";
	        
	        if(link == null)
	        	link = "https://www.facebook.com/SeoulsMaPpediapedia";
	        
	        // Add details about the clicked item to the
	        // story params Bundle
	        params.putString("name",name);
	        params.putString("description", "Welcome to Seoul as an International Student");
	        params.putString("link",link);
	        params.putString("picture", "https://raw.github.com/hassanabidpk/SeoulMapPedia/master/SeoulMapPedia/res/drawable-xhdpi/app_share_img.jpg");

	        WebDialog feedDialog = (
	            new WebDialog.FeedDialogBuilder(mActivity,
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
	                            Toast.makeText(mContext,
	                                "Posted story, id: "+postId,
	                                Toast.LENGTH_SHORT).show();
	                        } else {
	                            // User clicked the Cancel button
	                            Toast.makeText(mContext, 
	                                "Publish cancelled", 
	                                Toast.LENGTH_SHORT).show();
	                        }
	                    } else if (error instanceof 
	                    FacebookOperationCanceledException) {
	                        // User clicked the "x" button
	                        Toast.makeText(mContext.getApplicationContext(), 
	                            "Publish cancelled", 
	                            Toast.LENGTH_SHORT).show();
	                    } else {
	                        // Generic, ex: network error
	                        Toast.makeText(mContext.getApplicationContext(), 
	                            "Error posting story", 
	                            Toast.LENGTH_SHORT).show();
	                    }
	                }

	            })
	            .build();
	        feedDialog.show();
	    }

	

}
