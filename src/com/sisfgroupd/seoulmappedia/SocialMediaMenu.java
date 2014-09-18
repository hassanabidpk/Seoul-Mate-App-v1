package com.sisfgroupd.seoulmappedia;

import com.example.seoulmappedia.GlobalClass;
import com.example.seoulmappedia.ZoomNDragMaps;
import com.example.seoulmappedia.webViewTest;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class SocialMediaMenu extends Activity {
	
	private String TAG_E = "CampusMapsMenu";

	private ListView campusListView;
	private CampusListAdapter mCampusListAdapter;
	private ZoomNDragMaps campusMaps;
	private Intent intent;
	private PopupMenu popupMenu;
	private final static int ONE = 1;
    private final static int TWO = 2;
    private final static int THREE = 3;
    private GlobalClass global;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		setContentView(R.layout.campus_maps_list_view);
		global = new GlobalClass(getApplicationContext(), this);
		campusMaps = new ZoomNDragMaps(getBaseContext());
		campusListView = (ListView) findViewById(R.id.CampusMapsList);
		Button button = (Button) findViewById(R.id.campusButton);
		button.setText("Social Media /SNS");
		mCampusListAdapter = new CampusListAdapter(getBaseContext());
		campusListView.setAdapter(mCampusListAdapter);
		campusListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@TargetApi(Build.VERSION_CODES.HONEYCOMB)
			@SuppressLint("NewApi")
			public void onItemClick(AdapterView<?> parent, final View view, final int position,
					long id) {
				
		switch(position) {
		case 0 : Intent website = new Intent("android.intent.action.VIEW", Uri.parse("http://seoulmate.me"));
		startActivity(website); break;
		case 1 : Intent blog = new Intent("android.intent.action.VIEW", Uri.parse("http://seoulmate.me/blog/"));
		startActivity(blog); break;
		case 2 : Intent facebookgroup = new Intent("android.intent.action.VIEW", Uri.parse("https://www.facebook.com/groups/setoulmatecommunity/"));
		startActivity(facebookgroup); break;
		case 3 : Intent facebookpage = new Intent("android.intent.action.VIEW", Uri.parse("https://www.facebook.com/pages/Seoul-Mate/617014701692564?ref=hl"));
		startActivity(facebookpage); break;
		case 4 : Intent googleplus = new Intent("android.intent.action.VIEW", Uri.parse("https://plus.google.com/u/0/communities/108322455835912963390"));
		startActivity(googleplus); break;
		default: break;
		}
				
				
				
			}
		
		
		});

super.onCreate(savedInstanceState);
	}

private class CampusListAdapter extends BaseAdapter {

	Context mContext;
	private Integer[] mUniIcons = {R.drawable.weblogo,R.drawable.weblogo, R.drawable.facebook_share_96, R.drawable.facebook_share_96, R.drawable.googleplus};
	
	private String[] Campus_Names = {"Seoul Mate Website ", "Seoul Mate Blog" , "Facebook Group", "Facebook Page", "Google Plus Community"};
	
	public CampusListAdapter (Context context) {
		
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
		TextView uniNames = (TextView) view.findViewById(R.id.CampusNames);
		uniNames.setText(Campus_Names[position]);
		uniIcons.setImageResource(mUniIcons[position]);
		
		return view;
	}
	
	
	
}




}
