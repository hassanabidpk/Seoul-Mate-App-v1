package com.example.seoulmappedia;

import android.content.Intent;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.sisfgroupd.seoulmappedia.R;

public abstract class testYoutTube extends YouTubeBaseActivity implements
YouTubePlayer.OnInitializedListener {

	 private static final int RECOVERY_DIALOG_REQUEST = 1;
	 
	 
	public testYoutTube() {

		
	}

	public void onInitializationFailure(Provider arg0,
			YouTubeInitializationResult arg1) {
		if (arg1.isUserRecoverableError()) {
			arg1.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
		    } else {
		      String errorMessage = String.format(getString(R.string.error_player), arg1.toString());
		      Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
		    }
		
	}

	public void onInitializationSuccess(Provider arg0, YouTubePlayer arg1,
			boolean arg2) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		 if (requestCode == RECOVERY_DIALOG_REQUEST) {
		      // Retry initialization if user performed a recovery action
		      getYouTubePlayerProvider().initialize(DeveloperKey.DEVELOPER_KEY, this);
		    }
		  }

		  protected abstract YouTubePlayer.Provider getYouTubePlayerProvider();
}

	
