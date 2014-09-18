package com.example.seoulmappedia;


import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.sisfgroupd.seoulmappedia.R;

public class SpecialDietListFragment extends ListFragment {

	private ListView dietList;
	private OnDietItemSelectedListener onClickListener;
	 static String[] DIETARIES = {
	        "Vegan",
	        "Vegeterian", "Muslim (Halal Food)"
	    };
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		try {
			onClickListener = (OnDietItemSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString());
		}
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		
		super.onActivityCreated(savedInstanceState);
	}
	/*
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		ViewGroup view = (ViewGroup) inflater.inflate(R.layout.specialdietlist_layout, container, false);
		dietList = (ListView) view.findViewById(R.id.list);
	
		
		return view;
	//	return super.onCreateView(inflater, container, savedInstanceState);
		
		
	}
	*/
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	      // We need to use a different list item layout for devices older than Honeycomb
        int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;

        // Create an array adapter for the list view, using the Ipsum headlines array
        setListAdapter(new ArrayAdapter<String>(getActivity(), layout, DIETARIES));
		super.onCreate(savedInstanceState);
	}
	
	
	public interface OnDietItemSelectedListener {
		public void onDietItemSelected(int pos);
		
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		
		
		onClickListener.onDietItemSelected(position);
;	}
	
	
	

}
