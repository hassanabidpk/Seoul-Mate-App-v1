/**
 * 
 */
package com.example.seoulmappedia;


import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

import com.sisfgroupd.seoulmappedia.R;

/**
 * @author minhld
 *
 */
public class HalalFoodWeb extends Activity {

	private Button mGeneral;
	private WebView mWebView;
	private Button mBackPress;
	private Button mHaramSnacks;
	private Button mHalalSnacks;
	private Button mAck;
	private Button mKoreanFood;
	private Button mHalalRest;
	private ProgressBar progress;
	private String getLang;
	
	public static class HalalSections {
		public int id;
		public int url;
		public View v;
		
	public void HalalSections (int id, int url, View v) {
		this.id = id;
		this.url = url;
		this.v = v;
		
	}
	private static final String[] LANG_CODES = {"en","ko", "zh", "ja", "es", "fr", "vi", "fil", "ma", "ru"};
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.halal_food_web);
		
		// getting language preferences 
		SharedPreferences settings = getSharedPreferences("language", MODE_PRIVATE);
    	getLang = settings.getString("language2Load", "notfound");
    	/*
    	if(!getLang.equals("notfound")) {
    		Log.d("HalalFoodWeb", "Setting Locale, Language2 : " + getLang);
    		setLocale(getLang, 0);
    	} else {
    		String currLanguage = getApplicationContext().getResources().getConfiguration().locale.getLanguage();
    		setLocale(currLanguage, -1);
    		Log.d("HalalFoodWeb", "Setting Locale, Language3 : " + currLanguage);
    		
    		
    	}
    	*/
    	
		mWebView = (WebView) findViewById(R.id.halalweb);
		progress = (ProgressBar) findViewById(R.id.progressBar);
		progress.setMax(100);
//		mWebView.setWebViewClient(new myWebClient());
		mWebView.setWebChromeClient(new MyChromeClient());
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setSupportZoom(true);
		HalalFoodWeb.this.progress.setProgress(0);
//		webView.getSettings().setBuiltInZoomControls(true);
//		webView.getSettings().setUseWideViewPort(false);
		mWebView.getSettings().setLoadWithOverviewMode(true);
//		webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
//		webView.setScrollbarFadingEnabled(false);
		mWebView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
//		webView.loadUrl("file:///android_asset/airport/test.html");
//		mWebView.loadUrl("file:///android_asset/halalfood/general_tips.html");
		mBackPress = (Button) findViewById(R.id.backToHome);
		mBackPress.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				finish();
				
			}
		});
		mGeneral = (Button) findViewById(R.id.generaltips);
		mGeneral.performClick();
		mWebView.loadUrl("file:///android_asset/halalweb/general_tips.html");
	//	mGeneral.setPressed(true);
		mGeneral.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				mHalalSnacks.setPressed(false);
				mHalalSnacks.setSelected(false);
				mHaramSnacks.setPressed(false);
				mGeneral.setPressed(true);
//				webViewSetting(true,true,true, 100);
				if(mWebView != null) {
					mWebView.loadUrl("file:///android_asset/halalweb/general_tips.html");
				}
			}
		});
		
		mHaramSnacks = (Button) findViewById(R.id.haram);
		mHaramSnacks.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				mHalalSnacks.setPressed(false);
				mHalalSnacks.setSelected(false);
				mHaramSnacks.setPressed(true);
				mGeneral.setPressed(false);
//				webViewSetting(true,true,true, 100);
				if(mWebView != null) {
					mWebView.loadUrl("file:///android_asset/halalweb/haramsnacks.html");
				}
			}
		});
		
		mHalalSnacks = (Button) findViewById(R.id.halal);
		mHalalSnacks.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				mHalalSnacks.setPressed(true);
				mHalalSnacks.setSelected(true);
				mHaramSnacks.setPressed(false);
				mGeneral.setPressed(false);
//				webViewSetting(true,true,true, 100);
				if(mWebView != null) {
					mWebView.loadUrl("file:///android_asset/halalweb/halalsnacks.html");
				}
			}
				
		});
		mAck = (Button) findViewById(R.id.acknow);
		mAck.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
//				webViewSetting(true,true,true, 100);
				if(mWebView != null) {
			//		mWebView.loadUrl("https://raw.github.com/hassanabidpk/SeoulMapPedia/master/SeoulMapPedia/testgit/testgithtml.html");
					mWebView.loadUrl("file:///android_asset/halalweb/acknow.html");
				}
			}

		});
		mKoreanFood = (Button) findViewById(R.id.koreanfood);
		mKoreanFood.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
	//			webViewSetting(true,true,true, 80);
				if(mWebView != null) {
					mWebView.loadUrl("http://www.visitkorea.or.kr/ena/SI/SI_EN_3_6.jsp?cid=309666");
				}
				
			}
		});
		
		mHalalRest = (Button) findViewById(R.id.restaurants);
		mHalalRest.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				DemoDetails demo = new DemoDetails(R.string.uisettings_demo, R.string.uisettings_description,
						GoogleMapsFragmentClass.class);
					Bundle bundle = new Bundle();
					if(demo != null & bundle != null)
						bundle.putInt("univalue", 17);
					Intent intent = new Intent(getBaseContext(),demo.activityClass);
					intent.putExtra("univalue", 17);
					intent.putExtra("mapString", UniversityList.MAP_NAME[17]);
;							startActivity(intent);
				
			}
		});
	}
	
	private class MyChromeClient extends WebChromeClient
	{
		@Override
        public void onProgressChanged(WebView view, int newProgress) {          
            HalalFoodWeb.this.setValue(newProgress);

            super.onProgressChanged(view, newProgress);
        }
		
		
    }
	
	private class myWebClient extends WebViewClient {
		
		
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
		
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			progress.setVisibility(View.VISIBLE);
			HalalFoodWeb.this.progress.setProgress(0);
			super.onPageStarted(view, url, favicon);
		}
		
		@Override
		public void onPageFinished(WebView view, String url) {
			progress.setVisibility(View.GONE);
			HalalFoodWeb.this.progress.setProgress(100);
			super.onPageFinished(view, url);
		}
		
	}
 
	public void setValue(int progress) {
        this.progress.setProgress(progress);        
    }
	
	@Override
	protected void onResume() {
		/*
		if(!getLang.equals("notfound")) {
    		Log.d("HalalFoodWeb", "onResume Setting Locale, Language1 : " + getLang);
    		setLocale(getLang, 0);
    	} else {
    		String currLanguage = getApplicationContext().getResources().getConfiguration().locale.getLanguage();
    		setLocale(currLanguage, -1);
    		Log.d("HalalFoodWeb", "onResume Setting Locale, Language2 : " + currLanguage);
    		
    		
    	}
    	*/
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}
	
	
	public void onBackPressed() {
		finish();
		
	};
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	private void webViewSetting(boolean layout, boolean zoomControls, boolean wideView, int scale) {
		
		if(mWebView == null)
			return;
		if(layout)
			mWebView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.NORMAL);
		else 
			mWebView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		mWebView.getSettings().setBuiltInZoomControls(zoomControls);
		mWebView.getSettings().setUseWideViewPort(wideView);
//		if(scale !=100 )
			mWebView.setInitialScale(scale);
		
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
	
	public void setLocale(String lang, int langPos) { 
		Locale myLocale = new Locale(lang); 
		Resources res = getResources(); 
		DisplayMetrics dm = res.getDisplayMetrics(); 
		Configuration conf = res.getConfiguration(); 
		conf.locale = myLocale; 
		res.updateConfiguration(conf, dm); 
		Log.d("HalalFoodWeb", "Setting Locale to : " + lang);


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
