package com.example.seoulmappedia;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.FacebookDialog.PendingCall;
import com.sisfgroupd.seoulmappedia.R;

public class AirportTest extends Activity {
	
private String airportMapName = "nolink";
private int airportPos = -1;
private GlobalClass global; 

private UiLifecycleHelper uiHelper;
private boolean canPresentShareDialog;
private  UniversityList list;

private Session.StatusCallback callback = new Session.StatusCallback() {
public void call(Session session, SessionState state, Exception exception) {
        onSessionStateChange(session, state, exception);
    }
};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.webviewtest);
		airportPos = getIntent().getIntExtra("airportvalue", -1);
		if(airportPos != -1)
			airportMapName= "file:///android_asset/airport/" + UniversityList.AIRPORT_LIST[airportPos] + ".html";
		else 
			airportMapName= "file:///android_asset/airport/cauair.html";
		uiHelper = new UiLifecycleHelper(this, callback);
	    uiHelper.onCreate(savedInstanceState);
		Log.d("AirportTest", "HTML Link :" + airportMapName);
		
		WebView webView = (WebView) findViewById(R.id.webviewTest);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setSupportZoom(true);
//		webView.getSettings().setBuiltInZoomControls(true);
//		webView.getSettings().setUseWideViewPort(false);
//		webView.getSettings().setLoadWithOverviewMode(true);
//		webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
//		webView.setScrollbarFadingEnabled(false);
		webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
//		webView.loadUrl("file:///android_asset/airport/test.html");
		webView.loadUrl(airportMapName);

		 global = new GlobalClass(getApplicationContext(), this);
	        Button fbShare = (Button) findViewById(R.id.fbShareAirport);
	        Button kakaoButton = (Button) findViewById(R.id.kakaoButtonAirport);
	        
	        kakaoButton.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					
					try {
						global.sendUrlLink(v, airportPos, 3);
					} catch (NameNotFoundException e) {
						
						e.printStackTrace();
					}
					
				}
			});
	        
	        list = new UniversityList();
			
	        canPresentShareDialog = FacebookDialog.canPresentShareDialog(this,
	                FacebookDialog.ShareDialogFeature.SHARE_DIALOG);
	        
	        fbShare.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
//					if (Session.getActiveSession().isOpened()) {
//						global.publishFeedDialog(airportPos, 3, "Check Airport Route to University");
//					} else {
//						Toast.makeText(getApplicationContext(), 
//								"You must log in to publish a story", 
//								Toast.LENGTH_SHORT)
//								.show();
//					}	
					if(canPresentShareDialog) 
					{
//			    		   name = "Check Campus Map on this link";
//			    		  list.CAMPUS_URLS[mapPos];
						FacebookDialog shareDialog = createShareDialogBuilder("Check Campus Map on this link", "This link will take you to website of university", 
								list.AIRPORT_URLS[airportPos]).build();
			            uiHelper.trackPendingDialogCall(shareDialog.present());	
					}
					else {
					Intent facebookpage = new Intent("android.intent.action.VIEW", Uri.parse("https://www.facebook.com/pages/Seoul-Mate/617014701692564?ref=hl"));
					startActivity(facebookpage);
					}
					
				}
			});
		
		
		super.onCreate(savedInstanceState);
	}
	
	  private void onSessionStateChange(Session session, SessionState state, Exception exception) {
	        updateUI();
	    }
		
	    private void updateUI() {
	    	 Session session = Session.getActiveSession();
	    }
	    @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    	super.onActivityResult(requestCode, resultCode, data);
	    	
	    	uiHelper.onActivityResult(requestCode, resultCode, data, new FacebookDialog.Callback() {
				
				public void onError(PendingCall pendingCall, Exception error, Bundle data) {
					Log.e("Facebook", String.format("Error: %s", error.toString()));
					
				}
				
				public void onComplete(PendingCall pendingCall, Bundle data) {
					Log.i("Facebook", "Success!");
					
				}
			});
	    }
	    private FacebookDialog.ShareDialogBuilder createShareDialogBuilder(String name, String description, String link) {
	        return new FacebookDialog.ShareDialogBuilder(this)
	                .setName(name)
	                .setDescription(description)
	                .setLink(link);
	    }
	
	
	

}
