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

import com.example.seoulmappedia.SpecialDietListFragment.OnDietItemSelectedListener;
import com.sisfgroupd.seoulmappedia.R;

public class SpecialDietFragment extends Fragment {
	
	private static final String LOG_TAG = "SpecialDietFragment";
	
	private ListView mDietList;
	private DietListAdapter mDietListAdapt;
	
	private OnDietMenuItemSelectedListener mDietMenuListener;
	
	

	@Override
	public void onAttach(Activity activity) {
		
		super.onAttach(activity);
		
		try {
			mDietMenuListener = (OnDietMenuItemSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString());
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.specialdietlist_layout, container, false);
		mDietList = (ListView) view.findViewById(R.id.list);
		mDietListAdapt = new DietListAdapter(getActivity().getApplicationContext(), 1);
		mDietList.setAdapter(mDietListAdapt);
		mDietList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {
			
				mDietMenuListener.onDietMenuItemSelected(position);
				
				Log.d(LOG_TAG, "Item selected : " + position);
				
			}
			
			
			
		});
		
		return view;
//		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	public interface OnDietMenuItemSelectedListener {
		public void onDietMenuItemSelected(int pos);
		
	}
	
	
	private class DietListAdapter extends BaseAdapter {

		Context mContext;
		Activity mActivity; 
		int mOption;
	
		private int[] DIET_RESOURCE = {R.string.vegan, R.string.vegetarian, R.string.halal_food};
		private String[] DIETARIES = { getActivity().getResources().getString(DIET_RESOURCE[0]),getActivity().getResources().getString(DIET_RESOURCE[1]),
				getActivity().getResources().getString(DIET_RESOURCE[02])
	    };
		private Integer[] DIETICONS = {R.drawable.vegan,R.drawable.vegeterian, R.drawable.halal};
		
		public DietListAdapter (Context context, int option) {
			
			mContext = context;
			mOption = option;
		}
		
		public int getCount() {
			
			return DIETARIES.length;
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
				
				
				view = inflater.inflate(R.layout.diet_list_view, viewGroup, false);
				
			}
			
			ImageView dietIcons = (ImageView) view.findViewById(R.id.dieticons);
			TextView dietText = (TextView) view.findViewById(R.id.diettext);
			
			dietIcons.setImageResource(DIETICONS[position]);
			dietText.setText(DIETARIES[position]);
//			uniIcons.setImageResource(mUniIcons[position]);
			
			return view;
		}
		
		
		
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}


	
	
	

}
