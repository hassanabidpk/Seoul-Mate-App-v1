package com.example.seoulmappedia;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.util.EventLogTags.Description;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.FacebookDialog.PendingCall;
import com.sisfgroupd.seoulmappedia.R;

public class webViewTest extends Activity {
   private int mapPos = -1;
   private String mapName = "";
   private Button fbAirport;
   private Button kakaoAirport;
   private GlobalClass global;
   private ProgressBar mCampus;
   private WebView mWeb;
   
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
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webviewtest);
		
		uiHelper = new UiLifecycleHelper(this, callback);
	    uiHelper.onCreate(savedInstanceState);
	    
		global = new GlobalClass(getApplicationContext(), this);
		mapPos = getIntent().getIntExtra("mapvalue", -1);
		if(mapPos != -1 ) {
			/*
		}
			if(mapPos == 4 || mapPos == 5 || mapPos == 9 || mapPos == 14 || mapPos == 15) {
				mapName = "\"" + "file:///android_asset/campusImages/" + UniversityList.MAP_LIST[0] + "\"" + "></body></html>";
			}
			else { */
		//		mapName = "\"" + "file:///android_asset/campusImages/" + UniversityList.MAP_LIST[mapPos] + "\"" + "></body></html>";
			mapName = "\"" + "https://raw.github.com/hassanabidpk/testing1/master/campusImages/" + UniversityList.MAP_LIST[mapPos] + "\"" + "></body></html>";
	//		}
		}
		String sHtmlTemplate = "<html><head></head><body><img src= " + mapName;
		// Html:<html><head></head><body><img src= file:///android_asset/campusImages/dgumap.jpg"></body></html>
//         Html:<html><head></head><body><img src="file:///android_asset/campusImages/caumap.jpg"></body></html>

//		String sHtmlTemplate = "<html><head></head><body><img src=\"file:///android_asset/campusImages/caumap.jpg\"></body></html>";
		Log.d("WebViewTEST", "mapPos: " + mapPos +  "  mapName: " + mapName + " Html:" + sHtmlTemplate);
		mWeb = (WebView) findViewById(R.id.webviewTest);
		mCampus = (ProgressBar) findViewById(R.id.progressBarCampus);
		mCampus.setMax(100);
		mCampus.setProgress(0);
		mWeb.getSettings().setJavaScriptEnabled(true);
		mWeb.getSettings().setSupportZoom(true);
		mWeb.getSettings().setBuiltInZoomControls(true);
		mWeb.getSettings().setUseWideViewPort(true);
		mWeb.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
//		mWeb.loadUrl("http://www.cau.ac.kr/campusmap_eng/campusmap_seoul.htm");
//		mWeb.loadUrl("http://www.google.com");
//		if(savedInstanceState == null)
		mWeb.loadDataWithBaseURL(null, sHtmlTemplate, "text/html", "utf-8", null);
		mWeb.setWebChromeClient(new WebChromeClient() {
			
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				if(mCampus != null)
					mCampus.setProgress(newProgress)
;    				super.onProgressChanged(view, newProgress);
			}
			
		});
		list = new UniversityList();
		
        canPresentShareDialog = FacebookDialog.canPresentShareDialog(this,
                FacebookDialog.ShareDialogFeature.SHARE_DIALOG);
		
		fbAirport = (Button) findViewById(R.id.fbShareAirport);
		fbAirport.setOnClickListener(new View.OnClickListener() {
		
			public void onClick(View v) {/*
				if (Session.getActiveSession().isOpened()) {
					if(global != null)
						global.publishFeedDialog(mapPos, 1, "Check Campus Map on University Website");
				} else {
					Toast.makeText(getApplicationContext(), 
							"You must log in to publish a story", 
							Toast.LENGTH_SHORT)
							.show();
				}	*/
				if(canPresentShareDialog) 
				{
//		    		   name = "Check Campus Map on this link";
//		    		  list.CAMPUS_URLS[mapPos];
					FacebookDialog shareDialog = createShareDialogBuilder("Check Campus Map on this link", "This link will take you to website of university", 
							list.CAMPUS_URLS[mapPos]).build();
		            uiHelper.trackPendingDialogCall(shareDialog.present());	
				}
				else {
				Intent facebookpage = new Intent("android.intent.action.VIEW", Uri.parse("https://www.facebook.com/pages/Seoul-Mate/617014701692564?ref=hl"));
				startActivity(facebookpage);
				}
			
			}
		});
		
		kakaoAirport = (Button) findViewById(R.id.kakaoButtonAirport);
		kakaoAirport.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				
				if(global != null) {
					
					try {
						global.sendUrlLink(v,mapPos, 1);
					} catch (NameNotFoundException e) {
						
						e.printStackTrace();
					}
				}
			}
		});
		
		
	//	super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();
		uiHelper.onResume();
	}
	
	@Override
		protected void onPause() {
			super.onPause();
			uiHelper.onPause();
		}
	
	@Override
		protected void onDestroy() {
			super.onDestroy();
			uiHelper.onDestroy();
		}

	@Override
		protected void onSaveInstanceState(Bundle outState) {
			
			super.onSaveInstanceState(outState);
			/*
			if(mWeb != null) {
				mWeb.saveState(outState);
			}
			*/
			uiHelper.onSaveInstanceState(outState);
		}
	
	
	@Override
		protected void onRestoreInstanceState(Bundle savedInstanceState) {
			super.onRestoreInstanceState(savedInstanceState);
			/*
			if(mWeb != null) {
				mWeb.restoreState(savedInstanceState);
			}
			*/
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

    private void postStatusUpdate() {
        if (canPresentShareDialog) {
         //   FacebookDialog shareDialog = createShareDialogBuilder().build();
         //   uiHelper.trackPendingDialogCall(shareDialog.present());
        }
    }
    
    
	  @Override
	  protected void onStart() {
	  	// TODO Auto-generated method stub
	  	super.onStart();
	  
	  }

	  @Override
	  protected void onStop() {
	  	// TODO Auto-generated method stub
	  	super.onStop();
	  	
	  }
}
