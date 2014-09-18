package com.example.seoulmappedia;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.sisfgroupd.seoulmappedia.R;

public class ContactnHelpFragment extends Activity {
	
	private static final String LOG_TAG = "ContactnHelpFragment"; 
	final static String ARG_POSITION = "contactposition";
	int mCurrentPosition = -1;
	
	private WebView mContact;
	
	 private static final int PROGRESS = 0x1;

	 private ProgressBar progress;
     private int mProgressStatus = 0;
     private Handler mHandler = new Handler();


	
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
//	this.getWindow().requestFeature(Window.FEATURE_PROGRESS);
	setContentView(R.layout.contactnhelp_layout);
	Log.d(LOG_TAG, "onCreate");
//	getWindow().setFeatureInt(Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);
	mContact = (WebView) findViewById(R.id.contactWeb);
	progress = (ProgressBar) findViewById(R.id.progressBar);
	progress.setMax(100);
	ContactnHelpFragment.this.progress.setProgress(0);
	if(mContact != null) {
		
//		mSeoumContactlDetail.setWebViewClient(new WebViewClient());
		mContact.getSettings().setJavaScriptEnabled(true);
		mContact.getSettings().setSupportZoom(true);
		mContact.getSettings().setLayoutAlgorithm(LayoutAlgorithm.NORMAL);
		mContact.setWebChromeClient(new MyContactChromeClient());
		final int position = getIntent().getIntExtra("mainposition", -1);
		updateWebView(position);
		
	}
	/*
		mContact.setWebViewClient(new WebViewClient() {
			
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl("https://docs.google.com/forms/d/1XYJgFdcX9GSIZlccrMw4x7QNXDSxThfZ7wY_SdAdbQU/viewform");
				
				Log.d(LOG_TAG, "shouldOverrideUrlLoading");
				return true;
			}
			
			@Override
			public void onPageFinished(WebView view, String url) {
				progress.setVisibility(View.GONE);
				ContactnHelpFragment.this.progress.setProgress(100);
				
				Log.d(LOG_TAG, "onPageFinished");
				super.onPageFinished(view, url);
			}
			
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				progress.setVisibility(View.VISIBLE);
				ContactnHelpFragment.this.progress.setProgress(0);
				
				super.onPageStarted(view, url, favicon);
			}
			
			
		});
		*/
		

		

}

	private class MyContactChromeClient extends WebChromeClient
	{
		@Override
		public void onProgressChanged(WebView view, int newProgress) {          
			ContactnHelpFragment.this.setValue(newProgress);

			super.onProgressChanged(view, newProgress);
		}
	
	
	}

	private void setValue(int progress) {
		this.progress.setProgress(progress);        
	}
	

	private void updateWebView (int pos) {
		
		if (mContact == null) {
			Log.d(LOG_TAG, " mSpecial Diet is null: " + mContact);
			return;
		}
		
		if(pos == 1) {
			mContact.loadUrl("https://docs.google.com/forms/d/1XYJgFdcX9GSIZlccrMw4x7QNXDSxThfZ7wY_SdAdbQU/viewform");
			Log.d(LOG_TAG, " Web Postion : " + pos);
		} else if (pos == 2) {
			mContact.loadUrl("file:///android_asset/seoulweb/help.html");
		} else {
			mContact.loadUrl("https://docs.google.com/forms/d/1XYJgFdcX9GSIZlccrMw4x7QNXDSxThfZ7wY_SdAdbQU/viewform");
		}
		
		
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}
	
	
	
	

}
