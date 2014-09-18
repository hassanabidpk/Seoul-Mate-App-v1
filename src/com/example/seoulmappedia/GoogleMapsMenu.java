package com.example.seoulmappedia;

import java.util.Arrays;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sisfgroupd.seoulmappedia.R;


public class GoogleMapsMenu extends Activity {
	private String TAG_E = "GoogleMapsMenu";

	private ListView campusListView;
	private MapsListAdapter mGoogleListAdapter;
	private ZoomNDragMaps campusMaps;
	private int[] ignore = {0,1,2,3,4};
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		setContentView(R.layout.google_maps_list_view);
		campusMaps = new ZoomNDragMaps(getBaseContext());
		campusListView = (ListView) findViewById(R.id.GoogleMapsList);
		mGoogleListAdapter = new MapsListAdapter(getBaseContext());
		campusListView.setAdapter(mGoogleListAdapter);
		campusListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
			
					Log.v(TAG_E, "Icon Position =" + position);
		//			int i = Arrays.
					/*
					if (position == 11)
						startGoogleMaps(position);
					else { */
						DemoDetails demo = new DemoDetails(R.string.uisettings_demo, R.string.uisettings_description,
												GoogleMapsFragmentClass.class);
						Bundle bundle = new Bundle();
						if(demo != null & bundle != null)
							bundle.putInt("univalue", position);
							Intent intent = new Intent(getBaseContext(),demo.activityClass);
							intent.putExtra("univalue", position);
							intent.putExtra("mapString", UniversityList.MAP_NAME[position]);
						  startActivity(intent);


		//			}
			}
		
		
		});
		super.onCreate(savedInstanceState);
	}
	
	public boolean contains(final int[] array, final int key) {
		for (int i = 0; i < array.length; i++){
			   if(array[i] == key){
			      return true;
			  }
			}
		return false;
//	    return Arrays.asList(array).contains(key);
	}
	

private class MapsListAdapter extends BaseAdapter {

	Context mContext;
	private Integer[] mUniIcons = {R.drawable.chungang, R.drawable.duksung, R.drawable.dongkuk, R.drawable.ewha,
			R.drawable.hanyang,R.drawable.hongik,R.drawable.hufs,R.drawable.konkuk,R.drawable.korea,R.drawable.kukmin,R.drawable.kyunghee,
			R.drawable.snu,R.drawable.skku,R.drawable.sogang,R.drawable.sookmyung,R.drawable.uos,
			R.drawable.yonsei};
	
	private String[] Campus_Names = {"Chungang University", "Duksung Women University", "Dongguk University", "Ewha Women University", "Hanyang University", "Hongik University", 
			"HUFS Uni.", "Konkuk University", "Korea University", "Kookmin University", "KyungHee University", "Seoul Nat. University", "SungKyungKwan University", "Sogang University", "SookMyung University",
			"University of Seoul", "Yonsei University"};
	
	public MapsListAdapter (Context context) {
		
		mContext = context;
		
	}
	
	public int getCount() {
		
		return mUniIcons.length;
	}

	public Object getItem(int position) {
		
		return position;
	}

	public long getItemId(int position) {
		
		return position;
	}

	public View getView(int position, View convertView, ViewGroup viewGroup) {
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = (View) convertView;
		
		if (convertView == null) {
			
			
			view = inflater.inflate(R.layout.campus_maps_icon_view, viewGroup, false);
			
		}
		
		ImageView uniIcons = (ImageView) view.findViewById(R.id.CampusIcons);
		uniIcons.setImageResource(mUniIcons[position]);
		TextView uniNames = (TextView) view.findViewById(R.id.CampusNames);
		uniNames.setText(Campus_Names[position]);
		uniIcons.setImageResource(mUniIcons[position]);
		
		return view;
	}
	
	
	
}	

private void startGoogleMaps(int pos) {
	
	Intent intent = new Intent(getBaseContext(), GoogleMapFragmentClass.class);
	Bundle b = new Bundle();
	b.putInt("position", pos);
	intent.putExtras(b);
	startActivity(intent);
	
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

@Override
public void onBackPressed() {
	// TODO Auto-generated method stub
	super.onBackPressed();
}


@Override
protected void onStart() {
	// TODO Auto-generated method stub
	super.onStart();
//	FlurryAgent.onStartSession(getApplicationContext(), "NGSDDGMFBBJ5Z3NSZW84");
}

@Override
protected void onStop() {
	// TODO Auto-generated method stub
	super.onStop();
//	FlurryAgent.onEndSession(getApplicationContext());
}

}
