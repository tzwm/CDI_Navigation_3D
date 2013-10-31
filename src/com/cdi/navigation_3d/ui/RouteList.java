package com.cdi.navigation_3d.ui;

import java.util.ArrayList;
import java.util.List;

import com.cdi.navigation_3d.alg.Node;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class RouteList extends ListView {
	
	private Context context;

	public RouteList(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	
	public RouteList(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	public RouteList(Context context) {
		super(context);
		init(context);
	}
	
	private void init(Context context){
		this.context=context;
		ArrayAdapter<String> ad=new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,getData());
		setAdapter(ad);
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
}
