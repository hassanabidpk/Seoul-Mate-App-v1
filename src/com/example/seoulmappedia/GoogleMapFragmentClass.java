package com.example.seoulmappedia;

import com.google.android.gms.maps.UiSettings;
import com.sisfgroupd.seoulmappedia.R;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.webkit.GeolocationPermissions.Callback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class GoogleMapFragmentClass extends Activity implements LocationListener {
	
//	private static final String MAP_URL = "http://gmaps-samples.googlecode.com/svn/trunk/articles-android-webmap/simple-android-map.html";
	private static final String MAP_URL = "https://maps.google.com/maps/ms?msid=217819860417471254058.0004a941be30aa3f7bde0&msa=0&ll=37.589615,127.038624&spn=0.013738,0.032079";
	private static final String[] MAP_URLS = {"https://www.google.com/maps/ms?msid=217819860417471254058.0004aab47e0c672bf9626&msa=0",
		"https://www.google.com/maps/ms?msid=217819860417471254058.0004a941c4b45b812af5a&msa=0"
		,"https://www.google.com/maps/ms?msid=217819860417471254058.0004aa0d3fc5b291b5959&msa=0",
			"https://www.google.com/maps/ms?msid=217819860417471254058.0004aab4f335093a9b48a&msa=0",
				"https://www.google.com/maps/ms?msid=217819860417471254058.0004aa9f3b9515193eaa7&msa=0",
				"https://www.google.com/maps/ms?msid=217819860417471254058.0004aa0e7c48a5de67eee&msa=0",
				"https://www.google.com/maps/ms?msid=217819860417471254058.0004a941c10d5413ec7cd&msa=0&iwloc=0004a9f939efda8b84a3c",
				"https://www.google.com/maps/ms?msid=217819860417471254058.0004aa9edddc6a2324275&msa=0",
				"https://www.google.com/maps/ms?msid=217819860417471254058.0004a941be30aa3f7bde0&msa=0",
				"https://www.google.com/maps/ms?msid=217819860417471254058.0004a941ca05e4ecdc089&msa=0",
				"https://www.google.com/maps/ms?msid=217819860417471254058.0004aab552741f13da039&msa=0&ll=37.591757,127.052507&spn=0.006869,0.01604",
				"https://www.google.com/maps/ms?msid=217819860417471254058.0004ab5f5ce0415944589&msa=0",
				"https://www.google.com/maps/ms?msid=217819860417471254058.0004a941c233da8e313ae&msa=0",
				"https://www.google.com/maps/ms?msid=217819860417471254058.0004aab44848dedb07645&msa=0",
				"https://www.google.com/maps/ms?msid=217819860417471254058.0004a941c6e39a2ddfa03&msa=0",
				"https://www.google.com/maps/ms?msid=217819860417471254058.0004a941c7f4df146f3fe&msa=0",
				"https://www.google.com/maps/ms?msid=217819860417471254058.0004a941c7f4df146f3fe&msa=0"};
	
	private Location mostRecentLocation;  
	private WebView webView;
	private UiSettings mUiSettings;
	private Bundle bundle; 
	private int pos = 0;
	  @Override
	  /** Called when the activity is first created. */
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.googlemaps_webview);
	      bundle = getIntent().getExtras();
	      if(bundle != null)
	    	  pos = bundle.getInt("position");
	      
//	    mostRecentLocation = (Location)getSystemService(Context.LOCATION_SERVICE);
	    getLocation();
	    setupWebView(pos);
	    this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	  }
	  /** Sets up the WebView object and loads the URL of the page **/
	  private void setupWebView(int position){

	    final String centerURL = "javascript:centerAt(" +
	    mostRecentLocation.getLatitude() + "," +
	    mostRecentLocation.getLongitude()+ ")";
	    webView = (WebView) findViewById(R.id.webview);
	    webView.getSettings().setJavaScriptEnabled(true);
	    webView.getSettings().setSupportZoom(true);
	    webView.getSettings().setBuiltInZoomControls(true);
	    /*
	    webView.setWebChromeClient(new WebChromeClient() {

			@Override
			public void onGeolocationPermissionsShowPrompt(String origin,
					Callback callback) {
				callback.invoke(origin, true, false);
;				super.onGeolocationPermissionsShowPrompt(origin, callback);
			}
	    	
	    });
	    */
	    //Wait for the page to load then send the location information
//	    webView.setWebViewClient(new WebViewClient());
	    webView.setWebViewClient(new WebViewClient(){  
	        @Override  
	        public void onPageFinished(WebView view, String url)  
	        {
	          webView.loadUrl(centerURL);
	        }
	       

	      });
	    webView.loadUrl(MAP_URLS[position]);
	  }
	public void onLocationChanged(Location location) {
		mostRecentLocation = location;
	//	latitude = location.getLatitude();
	//    longitude = location.getLongitude();
		// TODO Auto-generated method stub
		
	}
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	
	private void getLocation() {
	    LocationManager locationManager =
	      (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	    
	    Criteria criteria = new Criteria();
	    criteria.setAccuracy(Criteria.ACCURACY_FINE);
	    String provider = locationManager.getBestProvider(criteria,true);
	    //In order to make sure the device is getting the location, request updates.
	    locationManager.requestLocationUpdates(provider, 1, 0, this);
	    mostRecentLocation = locationManager.getLastKnownLocation(provider);
	    mostRecentLocation = new Location(provider);
	    if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
	    	mostRecentLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	    	else if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
	    		mostRecentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	    		else {
	    if(mostRecentLocation == null) 
	    	mostRecentLocation = new Location(LocationManager.GPS_PROVIDER);
	    		}
	    	
	//    mostRecentLocation = locationManager.getLastKnownLocation(provider);
	  } 

	  

}
