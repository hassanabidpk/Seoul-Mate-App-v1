package com.example.seoulmappedia;


import android.annotation.TargetApi;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sisfgroupd.seoulmappedia.R;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class NewsnEventsActivity extends ActionBarActivity implements ActionBar.TabListener {
	
	 AppSectionsPagerAdapter mAppSectionsPagerAdapter;
	 
	 private static int seoulPosition = -1;

	    /**
	     * The {@link ViewPager} that will display the three primary sections of the app, one at a
	     * time.
	     */
	    ViewPager mViewPager;
	
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
//		 requestWindowFeature(Window.FEATURE_ACTION_BAR);
		 setContentView(R.layout.newsnevent_layout);
		 seoulPosition = getIntent().getIntExtra("seoulposition", -1);
	        // Create the adapter that will return a fragment for each of the three primary sections
	        // of the app.
	        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());

	        // Set up the action bar.
	        final ActionBar actionBar = getSupportActionBar();
	        if(actionBar == null) {
	        	Log.d("NewsnEventsActivity", " ActionBar is null");
	        	return;
	        }

	        // Specify that the Home/Up button should not be enabled, since there is no hierarchical
	        // parent.
	        if(actionBar != null) {
	        	Log.d("NewsnEventsActivity", " ActionBar is not null");
	        actionBar.setHomeButtonEnabled(true);

	        // Specify that we will be displaying tabs in the action bar.
	        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	        }

	        // Set up the ViewPager, attaching the adapter and setting up a listener for when the
	        // user swipes between sections.
	        mViewPager = (ViewPager) findViewById(R.id.pager);
	        mViewPager.setAdapter(mAppSectionsPagerAdapter);
	        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
	            @Override
	            public void onPageSelected(int position) {
	                // When swiping between different app sections, select the corresponding tab.
	                // We can also use ActionBar.Tab#select() to do this if we have a reference to the
	                // Tab.
	                actionBar.setSelectedNavigationItem(position);
	            }
	        });

	        // For each of the sections in the app, add a tab to the action bar.
	        for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
	            // Create a tab with text corresponding to the page title defined by the adapter.
	            // Also specify this Activity object, which implements the TabListener interface, as the
	            // listener for when this tab is selected.
	            actionBar.addTab(
	                    actionBar.newTab()
	                            .setText(mAppSectionsPagerAdapter.getPageTitle(i))
	                            .setTabListener(this));
	        }
	}
	
	


	   /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
     * sections of the app.
     */
    public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {

        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                default:
                    // The first section of the app is the most interesting -- it offers
                    // a launchpad into the other demonstrations in this example application.
                	
                	Fragment launchFragment = new LaunchpadSectionFragment();
                	Bundle args1 = new Bundle();
                	args1.putInt("eventno", i);
                	args1.putInt("position", seoulPosition);
                	launchFragment.setArguments(args1);
;                   return launchFragment;
		/*
                default:
                    // The other sections of the app are dummy placeholders.
                    Fragment fragment = new DummySectionFragment();
                    Bundle args = new Bundle();
                    args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, i + 1);
                    fragment.setArguments(args);
                    return fragment;
                    */
            }
        }

        @Override
        public int getCount() {
        	if(seoulPosition == -1)
        		return 0; 
        	if(seoulPosition == 0) return 2;
        	if(seoulPosition == 1) return 2;
        	if(seoulPosition == 2) return 2;
        	if(seoulPosition == 3) return 8;
        	if(seoulPosition == 4) return 2;
        	if(seoulPosition == 5) return 4;
        	if(seoulPosition == 6) return 1;
        	if(seoulPosition == 7) return 1;
        	if(seoulPosition == 8) return 2;
        	if(seoulPosition == 9) return 2;
        	if(seoulPosition == 10) return 3;
        	if(seoulPosition == 11) return 2;
        	if(seoulPosition == 12) return 3;

        	return 0;
        		
        }

        @Override
        public CharSequence getPageTitle(int position) {
        	if(seoulPosition == -1)
        		return "Not Available";
        	if(seoulPosition == 0) {
            	if (position == 0)  return "Guest House-1";
            	if(position == 1) return "Guest House-2";
        		
        	} else if(seoulPosition == 1) {
        		if (position == 0)  return "Sports";
            	if(position == 1) return "Culture";
        	} else if(seoulPosition == 2) {
        		if (position == 0)  return "Blogs";
            	if(position == 1) return "Youtube Channels";
        	} else if(seoulPosition == 3) {
        		if (position == 0)  return "Resouce 1";
            	if(position == 1) return "Resource 2";
            	if (position == 2)  return "Resource 3";
            	if(position == 3) return "Resource 4";
            	if (position == 4)  return "Resource 5";
            	if(position == 5) return "Resource 6";
            	if (position == 6)  return "Resource 7";
            	if(position == 7) return "Resource 8";
            	if (position == 8)  return "Resource 9";
            	
        	} else if(seoulPosition == 4) {
        		if (position == 0)  return "Global Seoul-Mates";
            	if(position == 1) return "KTO";
		
        	} else if(seoulPosition == 5) {
        		if (position == 0)  return "News";
            	if(position == 1) return "TV";
            	if(position == 2) return "Radio";
            	if(position == 3) return "Magazine";
            	
        	} else if(seoulPosition == 6) {
        		if (position == 0)  return "Scholarships";
//            	if(position == 1) return "Scholarships";
//            	if(position == 2) return "Study in Korea";
        		
        	}else if(seoulPosition == 7) {
        		if (position == 0)  return "Jobs and Internships";
//            	if(position == 1) return "PART-TIME";
        	} else if(seoulPosition == 8) {
        		if (position == 0)  return "Online-courses Intro";
            	if(position == 1) return "Penguinstep MOOC Campus";
        	}else if(seoulPosition == 9) {
        		if (position == 0)  return "UNESCO CCAP";
            	if(position == 1) return "Others";
        	}else if (seoulPosition == 10) {
        		if (position == 0)  return "Drama Guide";
            	if(position == 1) return "KPop Tour";
            	if(position == 2) return "Mountain Tour";
        	}else if(seoulPosition == 11) {
        		if (position == 0)  return "Gangnam Tour";
            	if(position == 1) return "Articles";
        	} else if(seoulPosition == 12) {
        		if (position == 0)  return "K-Drama";
            	if(position == 1) return "K-Music";
            	if(position == 2) return "K-Food";
        		
        	}else return "Not Available";
    //        return "Section " + (position + 1);
			return "not Available";
        }
    }
	
    
    /**
     * A fragment that launches other parts of the demo application.
     */
    public static class LaunchpadSectionFragment extends Fragment {
    	private ProgressBar progress;
    	private static final String DUMMY_WEB = "https://googledrive.com/host/0B6Wcefe9HPBJOHRRREJvUXQ5V2c/dummy.html";
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.travel_section_layout, container, false);
            Bundle args = getArguments();
            WebView webView = (WebView) rootView.findViewById(R.id.seoulNews);
            progress = (ProgressBar) rootView.findViewById(R.id.progressBarNews);
            progress.setMax(100);
        	progress.setProgress(0);
        	webView.getSettings().setJavaScriptEnabled(true);
        	webView.getSettings().setSupportZoom(true);
        	//webView.getSettings().setPluginsEnabled(true);
        	webView.getSettings().setPluginState(PluginState.ON);
    		webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
    		webView.setWebChromeClient(new WebChromeClient() {
    			
    			@Override
    			public void onProgressChanged(WebView view, int newProgress) {
    				if(progress != null)
    					progress.setProgress(newProgress)
;    				super.onProgressChanged(view, newProgress);
    			}
    			
    		});
    		webView.setWebViewClient(new WebViewClient() {
    			@Override
    			public void onPageFinished(WebView view, String url) {
    				super.onPageFinished(view, url);
    				view.clearCache(true);
    			}
    		});
    		int event = args.getInt("eventno", -1);
    		int position = args.getInt("position", -1);
    		setNewsandEventsArticle(event, webView, position);
 //   		webView.loadUrl("https://googledrive.com/host/0B6Wcefe9HPBJOHRRREJvUXQ5V2c/testgithtml.html");
            
/*
            // Demonstration of a collection-browsing activity.
            rootView.findViewById(R.id.demo_collection_button)
                    .setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                  //          Intent intent = new Intent(getActivity(), CollectionDemoActivity.class);
                  //          startActivity(intent);
                        }
                    });
*/

            return rootView;
        }
        
        private void  setNewsandEventsArticle(int eventNo, WebView web, int pos) {
        	 if (web == null)
        		 return;
        	 	if(seoulPosition == 0) {
                  	if(eventNo == 0)
                   		web.loadUrl("http://seoulmateapp.wordpress.com/accomodation/guest-house-1/");
                	else if (eventNo == 1)
                		web.loadUrl("http://seoulmateapp.wordpress.com/accomodation/guest-house-2/");
                	else 
                		web.loadUrl(DUMMY_WEB);
        	 		
        	 	} else if(seoulPosition == 1) {
        	 		
        	 		if(eventNo == 0)
                   		web.loadUrl("https://googledrive.com/host/0B6Wcefe9HPBJOHRRREJvUXQ5V2c/sportevents.html");
                	else if (eventNo == 1)
                		web.loadUrl("https://googledrive.com/host/0B6Wcefe9HPBJOHRRREJvUXQ5V2c/cultureevents.html");
                	else 
                		web.loadUrl(DUMMY_WEB);
            		
            	} else if(seoulPosition == 2) {
            		if(eventNo == 0)
                   		web.loadUrl("http://allthekoreablogs.blogspot.kr");
                	else if (eventNo == 1)
                		web.loadUrl("http://seoulistic.com/just-for-fun/8-must-watch-korea-youtubers-with-over-1-million-views/");
                	else 
                		web.loadUrl(DUMMY_WEB);
            		
            	} else if(seoulPosition == 3) {
            	 	if(eventNo == 0)
                   		web.loadUrl("http://seoulmate.me/index_section/korean-language/national_institute_korean.html");
                	else if (eventNo == 1)
                		web.loadUrl("http://seoulmate.me/index_section/korean-language/sejong_hakdong.html");
                	else if (eventNo == 2)
                		web.loadUrl("http://seoulmate.me/index_section/korean-language/national_institute_intl_education.html");
                	else if (eventNo == 3)
                		web.loadUrl("http://seoulmate.me/index_section/korean-language/bbc_korean_guide.html");
                	else if (eventNo == 4)
                		web.loadUrl("http://seoulmate.me/index_section/korean-language/sogang_korean_program.html");
                	else if (eventNo == 5)
                		web.loadUrl("http://seoulmate.me/index_section/korean-language/naver_dictionary.html");
                	else if (eventNo == 6)
                		web.loadUrl("http://seoulmate.me/index_section/korean-language/genie_talk_app.html");
                	else if (eventNo == 7)
                		web.loadUrl("http://seoulmate.me/index_section/korean-language/topik.html");
                	else 
                		web.loadUrl(DUMMY_WEB);
            		
            	} else if(seoulPosition == 4) {
                	if(eventNo == 0)
                   		web.loadUrl("http://english.seoul.go.kr/community/global-seoul-mate/global-seoul-mate-introduction/");
                	else if (eventNo == 1)
                		web.loadUrl("https://plus.google.com/u/0/communities/111755480870435070106");
                	else 
                		web.loadUrl(DUMMY_WEB);
            		
            	} else if(seoulPosition == 5) {
             		if(eventNo == 0)
                   		web.loadUrl("https://googledrive.com/host/0B6Wcefe9HPBJOHRRREJvUXQ5V2c/newspaper.html");
                	else if (eventNo == 1)
                		web.loadUrl("https://googledrive.com/host/0B6Wcefe9HPBJOHRRREJvUXQ5V2c/tv.html");
                 	else if (eventNo == 2)
                		web.loadUrl("https://googledrive.com/host/0B6Wcefe9HPBJOHRRREJvUXQ5V2c/radio.html");
                 	else if (eventNo == 3)
                		web.loadUrl("https://googledrive.com/host/0B6Wcefe9HPBJOHRRREJvUXQ5V2c/magazine.html");
                	else 
                		web.loadUrl(DUMMY_WEB);
            		
            	} else if(seoulPosition == 6) {
            		
               		if(eventNo == 0)
               			web.loadUrl("http://www.seoulmate.me/scholarships/");
//                   		web.loadUrl("http://seoulmate.me/blog/how-to-get-scholarships-in-south-korea/");
//                	else if (eventNo == 1)
//                		web.loadUrl("http://seoulmate.me/korean-government-scholarships-2014.html");
//                 	else if (eventNo == 2)
//                		web.loadUrl("http://m.studyinkorea.go.kr/mobile/eng/");
                	else 
                		web.loadUrl(DUMMY_WEB);
//            		if(eventNo == 0)
//                   		web.loadUrl("https://googledrive.com/host/0B6Wcefe9HPBJOHRRREJvUXQ5V2c/kdrama.html");
//                	else if (eventNo == 1)
//                		web.loadUrl("https://googledrive.com/host/0B6Wcefe9HPBJOHRRREJvUXQ5V2c/cultureevents.html");
//                	else 
//                		web.loadUrl(DUMMY_WEB);
            	}  else if(seoulPosition == 7) {
              	if(eventNo == 0)
              		web.loadUrl("http://www.seoulmate.me/jobs-and-internships/");
//           		web.loadUrl("https://googledrive.com/host/0B6Wcefe9HPBJOHRRREJvUXQ5V2c/fulltimejobs.html");
//        	else if (eventNo == 1)
//        		web.loadUrl("https://googledrive.com/host/0B6Wcefe9HPBJOHRRREJvUXQ5V2c/parttimejobs.html");
        	else 
        		web.loadUrl(DUMMY_WEB);
            		
            	}else if(seoulPosition == 8) {
              	if(eventNo == 0)
           		web.loadUrl("https://googledrive.com/host/0B6Wcefe9HPBJOHRRREJvUXQ5V2c/onlinecourse1.html");
        	else if (eventNo == 1)
        		web.loadUrl("https://www.coursera.org");
        	else 
        		web.loadUrl(DUMMY_WEB);

            	}else if(seoulPosition == 9) {
             		if(eventNo == 0)
           		web.loadUrl("http://seoulmate.me/blog/recruiting-volunteers-for-ccap-cross-cultural-awareness-program/");
        	else if (eventNo == 1)
        		web.loadUrl("https://googledrive.com/host/0B6Wcefe9HPBJOHRRREJvUXQ5V2c/volunteer.html");
        	else 
        		web.loadUrl(DUMMY_WEB);
            		
            	} else if(seoulPosition == 10) {
             		if(eventNo == 0)
           		web.loadUrl("http://seoulmate.me/blog/recommended-korean-dramas-to-watch/");
        	else if (eventNo == 1)
        		web.loadUrl("https://googledrive.com/host/0B6Wcefe9HPBJOHRRREJvUXQ5V2c/kpoptour.html");
        	else if (eventNo == 2)
        		web.loadUrl("https://googledrive.com/host/0B6Wcefe9HPBJOHRRREJvUXQ5V2c/mountaintour.html");
        	else 
        		web.loadUrl(DUMMY_WEB);
            	} else if(seoulPosition == 11) {
            		if(eventNo == 0)
                   		web.loadUrl("https://googledrive.com/host/0B6Wcefe9HPBJOHRRREJvUXQ5V2c/gangnamtour.html");
                	else if (eventNo == 1)
                		web.loadUrl("https://googledrive.com/host/0B6Wcefe9HPBJOHRRREJvUXQ5V2c/gangnamarticle.html");
                	else 
                		web.loadUrl(DUMMY_WEB);
            	} else if(seoulPosition == 12) {
            		if(eventNo == 0)
                   		web.loadUrl("http://seoulmate.me/blog/recommended-korean-dramas-to-watch/");
                	else if (eventNo == 1)
                		web.loadUrl("http://seoulmate.me/index_section/media_entertainment.html");
                	else if (eventNo == 2)
                		web.loadUrl("https://googledrive.com/host/0B6Wcefe9HPBJOHRRREJvUXQ5V2c/kfood.html");
                	else 
                		web.loadUrl(DUMMY_WEB);
            		
            	}else {
            		web.loadUrl(DUMMY_WEB);
            	}
        		
      	}
      }
    
    /**
     * A dummy fragment representing a section of the app, but that simply displays dummy text.
     */
    public static class DummySectionFragment extends Fragment {

        public static final String ARG_SECTION_NUMBER = "section_number";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_section_dummy, container, false);
            Bundle args = getArguments();
            ((TextView) rootView.findViewById(android.R.id.text1)).setText(
                    getString(R.string.dummy_section_text, args.getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

	public void onTabReselected(Tab arg0,
			android.support.v4.app.FragmentTransaction arg1) {
	
		
	}




	public void onTabSelected(Tab tab,
			android.support.v4.app.FragmentTransaction arg1) {
		 // When the given tab is selected, switch to the corresponding page in the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
		
		
	}




	public void onTabUnselected(Tab arg0,
			android.support.v4.app.FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}
	
	  @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        switch (item.getItemId()) {
	            case android.R.id.home:
	                // This is called when the Home (Up) button is pressed in the action bar.
	                // Create a simple intent that starts the hierarchical parent activity and
	                // use NavUtils in the Support Package to ensure proper handling of Up.
	                Intent upIntent = new Intent(this, SeoulLifeActivity.class);
	                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
	                    // This activity is not part of the application's task, so create a new task
	                    // with a synthesized back stack.
	                    TaskStackBuilder.from(this)
	                            // If there are ancestor activities, they should be added here.
	                            .addNextIntent(upIntent)
	                            .startActivities();
	                    finish();
	                } else {
	                    // This activity is part of the application's task, so simply
	                    // navigate up to the hierarchical parent activity.
	                    NavUtils.navigateUpTo(this, upIntent);
	                }
	                return true;
	        }
	        return super.onOptionsItemSelected(item);
	    }
	  
	  
	  @Override
	  protected void onStart() {
	  	// TODO Auto-generated method stub
	  	super.onStart();
//	  	FlurryAgent.onStartSession(getApplicationContext(), "NGSDDGMFBBJ5Z3NSZW84");
	  }

	  @Override
	  protected void onStop() {
	  	// TODO Auto-generated method stub
	  	super.onStop();
//	  	FlurryAgent.onEndSession(getApplicationContext());
	  }
	  
}
