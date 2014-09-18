package com.example.seoulmappedia;

import android.os.Bundle;


import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubePlayer.Provider;

import com.sisfgroupd.seoulmappedia.R;

public class AboutUs extends testYoutTube {



	@Override
	public void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.playerview_demo);

	    YouTubePlayerView youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
	    youTubeView.initialize(DeveloperKey.DEVELOPER_KEY, this);
	}

	@Override
	public void onInitializationSuccess(Provider arg0, YouTubePlayer player,
			boolean arg2) {
		 if (!arg2) {
		      player.cueVideo("PoFX02YPsZM");
		    }
	}

	@Override
	protected Provider getYouTubePlayerProvider() {
		
		return null;
	}

}
