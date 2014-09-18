package com.example.seoulmappedia;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import com.sisfgroupd.seoulmappedia.R;

public class GoogleMapsTestClass extends Activity {
	private WebView                 _webView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.google_maps_test);
		
		/*
		setContentView(R.layout.googlemaps_webview);
		
		

	    _webView = (WebView)findViewById(R.id.googleMapsWeb);

	    _webView.getSettings().setJavaScriptEnabled(true);
	    _webView.getSettings().setBuiltInZoomControls(true);
	    _webView.getSettings().setSupportZoom(true);

	    _webView.setWebChromeClient(new WebChromeClient() {
	        public void onProgressChanged(WebView view, int progress) {
	            // Activities and WebViews measure progress with different scales.
	            // The progress meter will automatically disappear when we reach 100%
	      //      ((Activity) activity).setProgress(progress * 1000);
	        }

	    });

	    // to handle its own URL requests :
//	    _webView.setWebViewClient(new MyWebViewClient());
	    _webView.loadUrl("http://maps.google.com/maps?q=Pizza,texas&ui=maps");
		
		*/
		
	}

	
	
	
}
