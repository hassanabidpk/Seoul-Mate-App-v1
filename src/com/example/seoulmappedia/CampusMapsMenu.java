package com.example.seoulmappedia;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.FeedDialogBuilder;
import com.facebook.widget.WebDialog.OnCompleteListener;
import com.sisfgroupd.seoulmappedia.R;

public class CampusMapsMenu extends Activity {
	
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
		mCampusListAdapter = new CampusListAdapter(getBaseContext());
		campusListView.setAdapter(mCampusListAdapter);
		campusListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@TargetApi(Build.VERSION_CODES.HONEYCOMB)
			@SuppressLint("NewApi")
			public void onItemClick(AdapterView<?> parent, final View view, final int position,
					long id) {
				
				Bundle bundle = new Bundle();
				bundle.putInt("mapvalue", position);
				intent = new Intent(getBaseContext(), webViewTest.class);
				intent.putExtra("mapvalue", position);
				/*
				if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1) {
				
				popupMenu = new PopupMenu(getApplicationContext(), view);
				MenuInflater inflater = popupMenu.getMenuInflater();
				inflater.inflate(R.menu.campus_menu,popupMenu.getMenu());
//				popupMenu.getMenu().add(Menu.NONE, ONE, Menu.NONE, "go to campus map");
//				popupMenu.getMenu().add(Menu.NONE, TWO, Menu.NONE, "share");
				popupMenu.show();
				popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
					
					public boolean onMenuItemClick(MenuItem item) {
					
						switch(item.getItemId()) {
						case R.id.campusMapSel: 
							if(intent != null)
								startActivity(intent); 
							break;
						case R.id.shareFb: 
							if (Session.getActiveSession().isOpened()) {
								publishFeedDialog2(position);
								break;
							} else {
								Toast.makeText(getApplicationContext(), 
										"You must log in to publish a story", 
										Toast.LENGTH_SHORT)
										.show();
						//		startActivity(intent);
								break;
							}
						case R.id.shareKakao:
							
							if(global != null) {
								
								try {
									global.sendUrlLink(view,position, 1);
								} catch (NameNotFoundException e) {
									
									e.printStackTrace();
								}
							}
							
						}
						return false;
					}
				});
				} else { */
					startActivity(intent);
//				}
				
				
				
			}
		
		
		});
		super.onCreate(savedInstanceState);
	}

private class CampusListAdapter extends BaseAdapter {

	Context mContext;
	private Integer[] mUniIcons = {R.drawable.chungang, R.drawable.duksung, R.drawable.dongkuk, R.drawable.ewha,
			R.drawable.hanyang,R.drawable.hongik,R.drawable.hufs,R.drawable.konkuk,R.drawable.korea,R.drawable.kukmin,R.drawable.kyunghee,
			R.drawable.snu,R.drawable.skku,R.drawable.sogang,R.drawable.sookmyung,R.drawable.uos,
			R.drawable.yonsei};
	
	private String[] Campus_Names = {"Chungang University", "Duksung Women University", "Dongguk University", "Ewha Women University", "Hanyang University", "Hongik University", 
			"HUFS Uni.", "Konkuk University", "Korea University", "Kookmin University", "KyungHee University", "Seoul Nat. University", "SungKyungKwan University", "Sogang University", "SookMyung University",
			"University of Seoul", "Yonsei University"};
	
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

private void publishFeedDialog2(int pos) {
    Bundle params = new Bundle();
    // Add details about the clicked item to the
    // story params Bundle
    params.putString("name","View Campus Map of University");
    params.putString("description", "Welcome to Seoul as an International Student");
    params.putString("link","https://www.facebook.com/SeoulsMaPpediapedia");
    params.putString("picture", "https://raw.github.com/hassanabidpk/SeoulMapPedia/master/SeoulMapPedia/res/drawable-xhdpi/app_share_img.jpg");

    WebDialog feedDialog = (
        new WebDialog.FeedDialogBuilder(this,
            Session.getActiveSession(),
            params))
        .setOnCompleteListener(new OnCompleteListener() {

            public void onComplete(Bundle values,
                FacebookException error) {
                if (error == null) {
                    // When the story is posted, echo the success
                    // and the post Id.
                    final String postId = values.getString("post_id");
                    if (postId != null) {
                        Toast.makeText(getBaseContext(),
                            "Posted story, id: "+postId,
                            Toast.LENGTH_SHORT).show();
                    } else {
                        // User clicked the Cancel button
                        Toast.makeText(getBaseContext(), 
                            "Publish cancelled", 
                            Toast.LENGTH_SHORT).show();
                    }
                } else if (error instanceof 
                FacebookOperationCanceledException) {
                    // User clicked the "x" button
                    Toast.makeText(getBaseContext().getApplicationContext(), 
                        "Publish cancelled", 
                        Toast.LENGTH_SHORT).show();
                } else {
                    // Generic, ex: network error
                    Toast.makeText(getBaseContext().getApplicationContext(), 
                        "Error posting story", 
                        Toast.LENGTH_SHORT).show();
                }
            }

        })
        .build();
    feedDialog.show();
}

@Override
protected void onStart() {
	// TODO Auto-generated method stub
	super.onStart();
	
}

@Override
protected void onStop() {
	// TODO Auto-generated method stub
	super.onStop();
	
}


	
	
}
