package com.example.seoulmappedia;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.ProgressBar;


import com.sisfgroupd.seoulmappedia.R;

public class SeoulLifeDetailFragment extends Fragment {
	
	private static final String LOG_TAG = "SeoulLifeDetailFragment"; 
	final static String ARG_POSITION = "position";
	int mCurrentPosition = -1;
	private WebView mSeoulDetail;
	private ProgressBar progress;

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	View view = inflater.inflate(R.layout.seoullife_detail_layout, container, false);
	
	mSeoulDetail = (WebView) view.findViewById(R.id.seoulDetailWeb);
	progress = (ProgressBar) view.findViewById(R.id.progressBarSeoul);
	progress.setMax(100);
	progress.setProgress(0);
	if(mSeoulDetail != null) {
		
//		mSeoulDetail.setWebViewClient(new WebViewClient());
		mSeoulDetail.getSettings().setJavaScriptEnabled(true);
		mSeoulDetail.getSettings().setSupportZoom(true);
		mSeoulDetail.getSettings().setBuiltInZoomControls(true);
		mSeoulDetail.getSettings().setUseWideViewPort(true);
		mSeoulDetail.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		mSeoulDetail.setWebChromeClient(new SpecialSeoulWebClient());
	}
	
	return view;
}

private class SpecialSeoulWebClient extends WebChromeClient
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
	
	if (mSeoulDetail == null) {
		return;
	}
	
	if(pos == 2) {
//	mSeoulDetail.loadUrl("file:///android_asset/seoulweb/seoul_organizations.html");
//	mSeoulDetail.loadUrl("file:///android_asset/seoulweb/seoul_organizations.html");
	} else if (pos == 3) { // expat blogs
		mSeoulDetail.loadUrl("https://googledrive.com/host/0B6Wcefe9HPBJOHRRREJvUXQ5V2c/blogs.html");
	} else if(pos == 5) { // korean tourism supporters
		mSeoulDetail.loadUrl("https://googledrive.com/host/0B6Wcefe9HPBJOHRRREJvUXQ5V2c/korea_tourism.html");
	} else if(pos == 11) { // subway map
		mSeoulDetail.loadUrl(/*"file:///android_asset/seoulweb/media.html"*/"https://googledrive.com/host/0B6Wcefe9HPBJOHRRREJvUXQ5V2c/subway.html");
	} else if(pos == 6) {
	//	mSeoulDetail.loadUrl("https://googledrive.com/host/0B6Wcefe9HPBJOHRRREJvUXQ5V2c/testgithtml.html");
	}else {
//		mSeoulDetail.loadUrl("file:///android_asset/seoulweb/seoul_organizations.html");
	}
	
	
}

@Override
public void onSaveInstanceState(Bundle outState) {
	// TODO Auto-generated method stub
	super.onSaveInstanceState(outState);
}


}
