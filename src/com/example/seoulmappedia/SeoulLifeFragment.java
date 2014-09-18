package com.example.seoulmappedia;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.seoulmappedia.SpecialDietFragment.OnDietMenuItemSelectedListener;
import com.sisfgroupd.seoulmappedia.R;

public class SeoulLifeFragment extends Fragment {
	
	private static final String LOG_TAG = "SeoulLifeFragment";
	
	private ListView mSeoulList;
	private SeoulListAdapter mSeoulListAdapt;
	
	private OnSeoulMenuItemSelectedListener mSeoulMenuListener;
	
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		try {
			mSeoulMenuListener = (OnSeoulMenuItemSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString());
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.seoullife_list_layout, container, false);
		mSeoulList = (ListView) view.findViewById(R.id.seoullist);
		mSeoulListAdapt = new SeoulListAdapter(getActivity().getApplicationContext(), 1);
		mSeoulList.setAdapter(mSeoulListAdapt);
		mSeoulList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {
				
				if(mSeoulMenuListener == null)
				  return;
			  
				mSeoulMenuListener.onSeoulMenuItemSelected(position);
				
				Log.d(LOG_TAG, "Item selected : " + position);
				
			}
			
			
			
		});
		
		return view;
	}
	
	public interface OnSeoulMenuItemSelectedListener {
		public void onSeoulMenuItemSelected(int pos);
		
	}
	
	private class SeoulListAdapter extends BaseAdapter {

		Context mContext;
		Activity mActivity; 
		int mOption;
	  private int[] SEOUL_MENU = {R.string.accommodation, R.string.events_students, R.string.expats_blogs, R.string.korean_lessons, 
			  R.string.korean_supporters, R.string.korean_media,R.string.sgc_locations, R.string.subway_map};
		
/*		private String[] SEOULMENU = {
	        "Seoul Global Center Locations",
	        "Real-Estate Locations", "Student Organizations", "Expats Blogs", "Korean Tourism Supporters", "Media(TV,Radio)", "News and Events"
	    };
	
	*/	
		public SeoulListAdapter (Context context, int option) {
			
			mContext = context;
			mOption = option;
		}
		
		public int getCount() {
			
			return SEOUL_MENU.length;
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
				
				
				view = inflater.inflate(R.layout.seoul_list_view, viewGroup, false);
				
			}
			
			TextView seoulText = (TextView) view.findViewById(R.id.seoultext);
			
			seoulText.setText(getActivity().getResources().getString(SEOUL_MENU[position]));
			
//			uniIcons.setImageResource(mUniIcons[position]);
			
			return view;
		}
		
		
		
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}
	


}
