
package com.example.seoulmappedia;


import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_HYBRID;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_NORMAL;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_SATELLITE;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_TERRAIN;

import java.util.Locale;
import java.util.Random;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.FacebookDialog.PendingCall;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.sisfgroupd.seoulmappedia.R;

/**
 * This shows how to place markers on a map.
 */
public class GoogleMapsFragmentClass extends FragmentActivity
        implements OnMarkerClickListener, OnInfoWindowClickListener, OnMarkerDragListener, OnItemSelectedListener {
	
	private static final String LOG_TAG = "GoogleMapsFragmentClass";
	
	
    private static final LatLng BRISBANE = new LatLng(-27.47093, 153.0235);
    private static final LatLng MELBOURNE = new LatLng(-37.81319, 144.96298);
    private static final LatLng SYDNEY = new LatLng(-33.87365, 151.20689);
    private static final LatLng ADELAIDE = new LatLng(-34.92873, 138.59995);
    private static final LatLng PERTH = new LatLng(-31.952854, 115.857342);
    private UiLifecycleHelper uiHelper;
    private boolean canPresentShareDialog;
    private  UniversityList list;
    
    private Session.StatusCallback callback = new Session.StatusCallback() {
    	   public void call(Session session, SessionState state, Exception exception) {
    	           onSessionStateChange(session, state, exception);
    	       }
    	   };
    
    private int uniPos = -1;

    /** Demonstrates customizing the info window and/or its contents. */
    class CustomInfoWindowAdapter implements InfoWindowAdapter {
//        private final RadioGroup mOptions;

        // These a both viewgroups containing an ImageView with id "badge" and two TextViews with id
        // "title" and "snippet".
        private final View mWindow;
        private final View mContents;
        private UniversityList universityList;

        CustomInfoWindowAdapter() {
            mWindow = getLayoutInflater().inflate(R.layout.custom_info_window, null);
            mContents = getLayoutInflater().inflate(R.layout.custom_info_contents, null);
            universityList = new UniversityList();
  //          mOptions = (RadioGroup) findViewById(R.id.custom_info_window_options);
        }

        public View getInfoWindow(Marker marker) {
        	/*
            if (mOptions.getCheckedRadioButtonId() != R.id.custom_info_window) {
                // This means that getInfoContents will be called.
                return null;
            }
            */
            render(marker, mWindow);
            return mWindow;
        }

        public View getInfoContents(Marker marker) {
        	/*
            if (mOptions.getCheckedRadioButtonId() != R.id.custom_info_contents) {
                // This means that the default info contents will be used.
                return null;
            }
            */
            render(marker, mContents);
            return mContents;
        }

        private void render(Marker marker, View view) {
            int badge;
            String drawable = universityList.getDrawableName(marker.getTitle());
        	if(!drawable.toLowerCase(Locale.US).equals("nostringfound"))
        		badge = getApplicationContext().getResources().getIdentifier(drawable, "drawable", getApplicationContext().getPackageName());
        	else badge = 0;
        	
        	/*
            // Use the equals() method on a Marker to check for equals.  Do not use ==.
            if (marker.equals(mBrisbane)) {
                badge = R.drawable.badge_qld;
            } else if (marker.equals(mAdelaide)) {
                badge = R.drawable.badge_sa;
            } else if (marker.equals(mSydney)) {
                badge = R.drawable.badge_nsw;
            } else if (marker.equals(mMelbourne)) {
                badge = R.drawable.badge_victoria;
            } else if (marker.equals(mPerth)) {
                badge = R.drawable.badge_wa;
            } else {
                // Passing 0 to setImageResource will clear the image view.
                badge = 0;
            }
            */
            ((ImageView) view.findViewById(R.id.badge)).setImageResource(badge);

            String title = marker.getTitle();
            TextView titleUi = ((TextView) view.findViewById(R.id.title));
            if (title != null) {
                // Spannable string allows us to edit the formatting of the text.
                SpannableString titleText = new SpannableString(title);
                titleText.setSpan(new ForegroundColorSpan(Color.RED), 0, titleText.length(), 0);
                titleUi.setText(titleText);
            } else {
                titleUi.setText("");
            }

            String snippet = marker.getSnippet();
      //      String snippet = "Near Daksung Women University.Address: #102, 263-33, Chang 5-dong, Dobong-gu,Seoul,Korea. Contact Information: 82-2-990-0708(82-2-990-0711)";
            TextView snippetUi = ((TextView) view.findViewById(R.id.snippet));
            if (snippet != null ) {
                SpannableString snippetText = new SpannableString(snippet);
      //          snippetText.setSpan(new ForegroundColorSpan(Color.MAGENTA), 0, 10, 0);
                snippetText.setSpan(new ForegroundColorSpan(Color.BLUE), 0, snippet.length(), 0);
                snippetUi.setText(snippetText);
            } else {
                snippetUi.setText("");
            }
        }
    }

    private GoogleMap mMap;

    private Marker mPerth;
    private Marker mSydney;
    private Marker mBrisbane;
    private Marker mAdelaide;
    private Marker mMelbourne;
    private TextView mTopText;
    
    private TextView mMapText;
    private String mapName;
    
    private GlobalClass global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 //       setContentView(R.layout.marker_demo);
        setContentView(R.layout.ui_settings_demo);
        
        uiHelper = new UiLifecycleHelper(this, callback);
	    uiHelper.onCreate(savedInstanceState);
	    
        global = new GlobalClass(getApplicationContext(), this);
		uniPos = getIntent().getIntExtra("univalue", -1);
		mapName = getIntent().getStringExtra("mapString");
		mMapText = (TextView) findViewById(R.id.mapTitle);
		if(mapName != null)
			mMapText.setText(mapName);
        Spinner spinner = (Spinner) findViewById(R.id.layers_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.layers_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        
		list = new UniversityList();
		
        canPresentShareDialog = FacebookDialog.canPresentShareDialog(this,
                FacebookDialog.ShareDialogFeature.SHARE_DIALOG);
        
        
        Button fbShareMap = (Button) findViewById(R.id.fbShareMap);
        Button kakaoShareMap = (Button) findViewById(R.id.kakaoButtonMap);
        fbShareMap.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
			/*	if (Session.getActiveSession().isOpened()) {
					if(global != null)
						global.publishFeedDialog(uniPos, 2, "Check Customized Google Maps of the University surrounding");
				} else {
					Toast.makeText(getApplicationContext(), 
							"You must log in to publish a story", 
							Toast.LENGTH_SHORT)
							.show();
				}*/	
				if(canPresentShareDialog) 
				{
//		    		   name = "Check Campus Map on this link";
//		    		  list.CAMPUS_URLS[mapPos];
					FacebookDialog shareDialog = createShareDialogBuilder("Check Customized Google Maps of the University", 
							"This link will take you to customized Google maps of university", 
							list.MAP_URLS[uniPos]).build();
		            uiHelper.trackPendingDialogCall(shareDialog.present());	
				}
				else {
				Intent facebookpage = new Intent("android.intent.action.VIEW", Uri.parse("https://www.facebook.com/pages/Seoul-Mate/617014701692564?ref=hl"));
				startActivity(facebookpage);
				}
				
			}
		});
        
        kakaoShareMap.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if(global != null) {
					
					try {
						global.sendUrlLink(v,uniPos, 2);
					} catch (NameNotFoundException e) {
						
						e.printStackTrace();
					}
				}
				
			}
		});

//        mTopText = (TextView) findViewById(R.id.top_text);

        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
    
        // Hide the zoom controls as the button panel will cover it.
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.setMyLocationEnabled(true);

        // Add lots of markers to the map.
//        addMarkersToMap();
        addMarkersToMapNew();
        // Setting an info window adapter allows us to change the both the contents and look of the
        // info window.
        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());

        // Set listeners for marker events.  See the bottom of this class for their behavior.
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
        mMap.setOnMarkerDragListener(this);

        // Pan to see all markers in view.
        // Cannot zoom to bounds until the map has a size.
        final View mapView = getSupportFragmentManager().findFragmentById(R.id.map).getView();
        if (mapView.getViewTreeObserver().isAlive()) {
            mapView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                @SuppressWarnings("deprecation") // We use the new method when supported
                @SuppressLint("NewApi") // We check which build version we are using.
                public void onGlobalLayout() {
                    LatLngBounds bounds = new LatLngBounds.Builder()
                    /*
                            .include(PERTH)
                            .include(SYDNEY)
                            .include(ADELAIDE)
                            .include(BRISBANE)
                            .include(MELBOURNE)
                            */
                    .include(new LatLng(MarkersforMaps.LAT_START[uniPos], MarkersforMaps.LONG_START[uniPos]))
                    .include(new LatLng(MarkersforMaps.LAT_END[uniPos], MarkersforMaps.LONG_END[uniPos]))
                            .build();
                    /*
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                      mapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                      */
             //       } else {
             //         mapView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            //        }
                    mapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));
                }
            });
        }
    }
/*
    private void addMarkersToMap() {
        // Uses a colored icon.
        mBrisbane = mMap.addMarker(new MarkerOptions()
                .position(BRISBANE)
                .title("Brisbane")
                .snippet("Population: 2,074,200")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        // Uses a custom icon.
        mSydney = mMap.addMarker(new MarkerOptions()
                .position(SYDNEY)
                .title("Sydney")
                .snippet("Population: 4,627,300")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.arrow)));

        // Creates a draggable marker. Long press to drag.
        mMelbourne = mMap.addMarker(new MarkerOptions()
                .position(MELBOURNE)
                .title("Melbourne")
                .snippet("Population: 4,137,400")
                .draggable(true));

        // A few more markers for good measure.
        mPerth = mMap.addMarker(new MarkerOptions()
                .position(PERTH)
                .title("Perth")
                .snippet("Population: 1,738,800"));
        mAdelaide = mMap.addMarker(new MarkerOptions()
                .position(ADELAIDE)
                .title("Adelaide")
                .snippet("Population: 1,213,000"));

        // Creates a marker rainbow demonstrating how to create default marker icons of different
        // hues (colors).
        int numMarkersInRainbow = 12;
        for (int i = 0; i < numMarkersInRainbow; i++) {
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(
                            -30 + 10 * Math.sin(i * Math.PI / (numMarkersInRainbow - 1)),
                            135 - 10 * Math.cos(i * Math.PI / (numMarkersInRainbow - 1))))
                    .title("Marker " + i)
                    .icon(BitmapDescriptorFactory.defaultMarker(i * 360 / numMarkersInRainbow)));
        }
    }
    */
    private void addMarkersToMapNew() {
        
    	final double[]  LAT;
    	final double[]  LONG;
    	final String[]  TITLE;
    	final String[]  SNIPPET;
    	MarkersforMaps markerClass = new MarkersforMaps(uniPos);
    	UniversityList universityClass = new UniversityList();
    	LAT = markerClass.getLat(uniPos);
    	LONG = markerClass.getLong(uniPos);
    	TITLE = getApplicationContext().getResources().getStringArray(universityClass.getPlcID(uniPos));
    	SNIPPET = getApplicationContext().getResources().getStringArray(universityClass.getDescID(uniPos));
    	if(LAT == null || LONG == null || TITLE == null || SNIPPET == null) {
    		
    		Log.d(LOG_TAG, "LAT, LONG , TITLE or SNIPPER is Null");
    		return;
    	}
    	String drawable;

        // Creates a marker rainbow demonstrating how to create default marker icons of different
        // hues (colors).
        int numMarkers = LAT.length;
        for (int i = 0; i < numMarkers; i++) {
        	drawable = universityClass.getDrawableName(TITLE[i]);
        	if(!drawable.toLowerCase(Locale.US).equals("nostringfound")) {
        		int resrouceId = getApplicationContext().getResources().getIdentifier(drawable, "drawable", getApplicationContext().getPackageName());
        		if(resrouceId == 0)
        			resrouceId = R.drawable.cafetaria;
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(LAT[i], LONG[i]))
                    .title(TITLE[i]).snippet(SNIPPET[i])
                    .icon(BitmapDescriptorFactory.fromResource(resrouceId)));
        	} else {
        		
        		 mMap.addMarker(new MarkerOptions()
                 .position(new LatLng(LAT[i], LONG[i]))
                 .title(TITLE[i]).snippet(SNIPPET[i])
                 .icon(BitmapDescriptorFactory.defaultMarker(i * 360 / numMarkers)));
        	}
        }
    }

    private boolean checkReady() {
        if (mMap == null) {
            Toast.makeText(this, R.string.map_not_ready, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /** Called when the Clear button is clicked. */
    public void onClearMap(View view) {
        if (!checkReady()) {
            return;
        }
        mMap.clear();
    }

    /** Called when the Reset button is clicked. */
    public void onResetMap(View view) {
        if (!checkReady()) {
            return;
        }
        // Clear the map because we don't want duplicates of the markers.
        mMap.clear();
        addMarkersToMapNew();
    }

    //
    // Marker related listeners.
    //

    public boolean onMarkerClick(final Marker marker) {
    	
    	/*
        if (marker.equals(mPerth)) {
            // This causes the marker at Perth to bounce into position when it is clicked.
            final Handler handler = new Handler();
            final long start = SystemClock.uptimeMillis();
            final long duration = 1500;

            final Interpolator interpolator = new BounceInterpolator();

            handler.post(new Runnable() {
                public void run() {
                    long elapsed = SystemClock.uptimeMillis() - start;
                    float t = Math.max(1 - interpolator
                            .getInterpolation((float) elapsed / duration), 0);
                    marker.setAnchor(0.5f, 1.0f + 2 * t);

                    if (t > 0.0) {
                        // Post again 16ms later.
                        handler.postDelayed(this, 16);
                    }
                }
            });
        } else if (marker.equals(mAdelaide)) {
            // This causes the marker at Adelaide to change color.
            marker.setIcon(BitmapDescriptorFactory.defaultMarker(new Random().nextFloat() * 360));
        }
        // We return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
        
        */ 
    	return false;
    }

    public void onInfoWindowClick(Marker marker) {
 //       Toast.makeText(getBaseContext(), "Click Info Window", Toast.LENGTH_SHORT).show();
    }

    public void onMarkerDragStart(Marker marker) {
  //      mTopText.setText("onMarkerDragStart");
    }

    public void onMarkerDragEnd(Marker marker) {
   //     mTopText.setText("onMarkerDragEnd");
    }

    public void onMarkerDrag(Marker marker) {
  //      mTopText.setText("onMarkerDrag.  Current Position: " + marker.getPosition());
    }

	public void onItemSelected(AdapterView<?> parent, View arg1, int position,
			long arg3) {


        setLayer((String) parent.getItemAtPosition(position));
    }

    private void setLayer(String layerName) {
        if (!checkReady()) {
            return;
        }
        if (layerName.equals(getString(R.string.normal))) {
            mMap.setMapType(MAP_TYPE_NORMAL);
        } else if (layerName.equals(getString(R.string.hybrid))) {
            mMap.setMapType(MAP_TYPE_HYBRID);
        } else if (layerName.equals(getString(R.string.satellite))) {
            mMap.setMapType(MAP_TYPE_SATELLITE);
        } else if (layerName.equals(getString(R.string.terrain))) {
            mMap.setMapType(MAP_TYPE_TERRAIN);
        } else {
            Log.i("LDA", "Error setting layer with name " + layerName);
        }
    }

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
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
    
}
