package com.cdi.navigation_3d.ui;

import java.util.ArrayList;
import java.util.List;

import com.cdi.navigation_3d.alg.Arc;
import com.cdi.navigation_3d.alg.Graph;
import com.cdi.navigation_3d.alg.Node;
import com.cdi.navigation_3d.location.LocationOnWayBean;
import com.cdi.navigation_3d.location.OnLocationChangedListener;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class RouteList extends ListView {
	
	private Context context;
	private ArrayAdapter<String> ad;
	private List<Node> result;

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
		result=G.result;
		ad=new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,getData());
		setAdapter(ad);
		G.location.setOnLocationChangedListener(new OnLocationChangedListener() {			
			@Override
			public void LocationChanged(LocationOnWayBean lb) {
				// TODO Auto-generated method stub
				if (G.to!=null){
					result=G.g.spfa(lb.getNearestNode().getName(), G.to.name);
					ad.clear();
					List<String> ss=getData();
					for(String s:ss) ad.add(s);
					ad.notifyDataSetChanged();
					Toast.makeText(RouteList.this.context, "Route Updated", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	public List<String> getData(){
		if (result==null) return null;
		ArrayList<String> ret=new ArrayList<String>();
		if (G.result!=null){
			for (Node n:result){
				if (!n.disp_name.equals("-"))
					ret.add(n.disp_name);
			}
		}
		return ret;
	}	
}
