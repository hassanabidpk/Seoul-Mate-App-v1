package com.example.seoulmappedia;

import com.sisfgroupd.seoulmappedia.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class AiportMapsMenu extends Activity {
	
	private String TAG_E = "AirportMapsMenu";

	private ListView airporListView;
	private CampusListAdapter mAirportListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.airport_maps_list_view);
		airporListView = (ListView) findViewById(R.id.AirportMapsList);
		mAirportListAdapter = new CampusListAdapter(getBaseContext());
		airporListView.setAdapter(mAirportListAdapter);
		airporListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				
				Bundle bundle = new Bundle();
				bundle.putInt("airportvalue", position);
				Intent intent = new Intent(getBaseContext(), AirportTest.class);
				intent.putExtra("airportvalue", position);
				startActivity(intent);
				
				
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
		uniIcons.setImageResource(mUniIcons[position]);
		TextView uniNames = (TextView) view.findViewById(R.id.CampusNames);
		uniNames.setText(Campus_Names[position]);
		uniIcons.setImageResource(mUniIcons[position]);
		
		return view;
	}
	
	
	
}	
	
	
}
