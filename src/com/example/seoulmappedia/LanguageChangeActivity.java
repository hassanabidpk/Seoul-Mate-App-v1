package com.example.seoulmappedia;

import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
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

public class LanguageChangeActivity extends Activity {

	
	private static final String LOG_TAG = "LanguageChangeActivity";
	
	private static final String[] LANG_CODES = {"en","ko", "zh", "ja", "es", "pl", "vi", "ar", "ma", "ru"};
	
	private ListView mLangList;
	private LangListAdapter mLangListAdap;
	private SharedPreferences languagePref;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.language_change_activity);
		mLangList = (ListView) findViewById(R.id.langList);
		mLangListAdap = new LangListAdapter(getBaseContext().getApplicationContext(), 1);
		mLangList.setAdapter(mLangListAdap);
		mLangList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {
			  
				
				Log.d(LOG_TAG, "Item selected : " + position);
				
				Intent refresh = new Intent(getApplicationContext(), SeoulMapPedia.class); 
				refresh.putExtra("languageno", position);
		//		finish();
				startActivity(refresh);
				finish();
				
				setLocale(LANG_CODES[position]);
				languagePref = getSharedPreferences("language", MODE_PRIVATE);
				if(languagePref == null)
					return;
				SharedPreferences.Editor editor = languagePref.edit();
				if(editor!=null) {
					editor.putString("language2Load", LANG_CODES[position]);
					editor.commit();
				}
				
			}
			
			
			
		});
		
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Log.d(LOG_TAG, "onResume======");
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.d(LOG_TAG, "onPause======");
		
	}
	
	private class LangListAdapter extends BaseAdapter {

		Context mContext;
		Activity mActivity; 
		int mOption;
	  private String[] LANG_NAME = {"English", "Korean", "Chinese", "Japanese", "Spanish", "Polish", "Vietnamese", "Arabic", "Malay", "Russian"};
	  
	  private int[] LANG_DRAWABLES = {R.drawable.us256, R.drawable.korea256, R.drawable.china256, R.drawable.japan256,R.drawable.spain256,
			  R.drawable.poland,
			  R.drawable.vietnam256,R.drawable.arabic256,R.drawable.malaysia256, R.drawable.russia256};
		
/*		private String[] SEOULMENU = {
	        "Seoul Global Center Locations",
	        "Real-Estate Locations", "Student Organizations", "Expats Blogs", "Korean Tourism Supporters", "Media(TV,Radio)", "News and Events"
	    };
	
	*/	
		public LangListAdapter (Context context, int option) {
			
			mContext = context;
			mOption = option;
		}
		
		public int getCount() {
			
			return LANG_NAME.length;
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
				
				
				view = inflater.inflate(R.layout.language_list_view, viewGroup, false);
				
			}
			
			TextView seoulText = (TextView) view.findViewById(R.id.langtext);
			ImageView countryFlag = (ImageView) view.findViewById(R.id.langicons);
			
			seoulText.setText(LANG_NAME[position]);
			countryFlag.setImageResource(LANG_DRAWABLES[position]);
//			uniIcons.setImageResource(mUniIcons[position]);
			
			return view;
		}
		
		
		
	}
	
	public void setLocale(String lang) { 
		Locale myLocale = new Locale(lang); 
		Resources res = getResources(); 
		DisplayMetrics dm = res.getDisplayMetrics(); 
		Configuration conf = res.getConfiguration(); 
		conf.locale = myLocale; 
		res.updateConfiguration(conf, dm); 
//		finish();
		Intent refresh = new Intent(getApplicationContext(), SeoulMapPedia.class); 
		startActivity(refresh); 
		}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();

		Log.d(LOG_TAG, "onBackPressed======");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d(LOG_TAG, "onDestroy======");
	
	}
	
	
}
