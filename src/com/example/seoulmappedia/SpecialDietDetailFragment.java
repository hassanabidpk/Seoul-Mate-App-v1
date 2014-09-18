package com.example.seoulmappedia;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.ProgressBar;

import com.sisfgroupd.seoulmappedia.R;

public class SpecialDietDetailFragment extends Fragment {
	
	private static final String LOG_TAG = "SpecialDietDetailFragment"; 
	final static String ARG_POSITION = "dietposition";
	int mCurrentPosition = -1;
	
	private WebView mSpecialDiet;
	private ProgressBar progress;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.specialdiet_detail_layout, container, false);
		
		mSpecialDiet = (WebView) view.findViewById(R.id.dietDetailWeb);
		progress = (ProgressBar) view.findViewById(R.id.progressBarDiet);
		progress.setMax(100);
		progress.setProgress(0);
		if(mSpecialDiet != null) {
			
//			mSeoulDetail.setWebViewClient(new WebViewClient());
			mSpecialDiet.getSettings().setJavaScriptEnabled(true);
			mSpecialDiet.getSettings().setSupportZoom(true);
			mSpecialDiet.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
			mSpecialDiet.setWebChromeClient(new SpecialDietWebClient());
		}
		
		return view;
	}
	
	private class SpecialDietWebClient extends WebChromeClient
	{
		@Override
        public void onProgressChanged(WebView view, int newProgress) {          
            setValue(newProgress);

            super.onProgressChanged(view, newProgress);
        }
		
		
    }
	private void setValue(int progress) {
        this.progress.setProgress(progress);        
    }
	
	@Override
	public void onStart() {
		
		 Bundle args = getArguments();
	     if (args != null) {
	         // Set article based on argument passed in
	         updateWebView(args.getInt(ARG_POSITION));
	         Log.d(LOG_TAG, " Position: " + args.getInt(ARG_POSITION));
	     } else if (mCurrentPosition != -1) {
	         // Set article based on saved instance state defined during onCreateView
	         updateWebView(mCurrentPosition);
	     }
		
		super.onStart();
	}
	
	
	private void updateWebView (int pos) {
		
		if (mSpecialDiet == null) {
			Log.d(LOG_TAG, " mSpecial Diet is null: " + mSpecialDiet);
			return;
		}
		
		if(pos == 0) {
			mSpecialDiet.loadUrl("file:///android_asset/halalweb/vegan.html");
			Log.d(LOG_TAG, " Web Postion : " + pos);
		} else if (pos == 1) {
			mSpecialDiet.loadUrl("file:///android_asset/halalweb/vegetarian.html");
		} else {
			mSpecialDiet.loadUrl("file:///android_asset/halalweb/vegan.html");
		}
		
		
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}
	
	
	
}
