package com.cdi.navigation_3d.ui;

import java.util.ArrayList;
import java.util.List;

import com.cdi.navigation_3d.alg.Node;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

public class ListDispFragment extends ListFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
	}
	
	public List<String> getData(){
		ArrayList<String> ret=new ArrayList<String>();
		if (G.result!=null){
			for (Node n:G.result){
				if (!n.disp_name.equals("-"))
					ret.add(n.disp_name);
			}
		}
		return ret;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ArrayAdapter<String> ad=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,getData());
		setListAdapter(ad);
	}
}
