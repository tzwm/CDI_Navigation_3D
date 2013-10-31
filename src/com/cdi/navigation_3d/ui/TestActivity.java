package com.cdi.navigation_3d.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.cdi.navigation_3d.R;
import com.cdi.navigation_3d.alg.Arc;
import com.cdi.navigation_3d.alg.Graph;
import com.cdi.navigation_3d.alg.Node;

public class TestActivity extends Activity{
	
	private List<Node> nodes=new ArrayList<Node>();
	private Spinner sp_from;
	private Spinner sp_to;
	private ListView lv;
	private Graph g;
	private ArrayAdapter lv_adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		sp_from=(Spinner)findViewById(R.id.spinner1);
		sp_to=(Spinner)findViewById(R.id.spinner2);
		lv=(ListView)findViewById(R.id.listView1);
		lv_adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		lv.setAdapter(lv_adapter);
		AssetManager asset=getAssets();
		try {
			g=Graph.build(asset.open("map.txt"));
			g.reset();
			//g.dfs(g.getNode(0));
			//List<Node> result=g.spfa(0, 10);
			//for (Node n:result) Log.i("path",n.getIndex()+","+n.getDistance());
			for (int i=0;i<g.nodeCount();++i) if (g.getNode(i).toString()!=null) nodes.add(g.getNode(i));
			sp_from.setAdapter(new ArrayAdapter<Node>(this, android.R.layout.simple_spinner_item,nodes));
			sp_to.setAdapter(new ArrayAdapter<Node>(this, android.R.layout.simple_spinner_item,nodes));
			sp_from.setOnItemSelectedListener(l);
			sp_to.setOnItemSelectedListener(l);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	OnItemSelectedListener l=new OnItemSelectedListener(){
		@Override
		public void onItemSelected(AdapterView<?> par, View v, int arg2,
				long arg3) {
			Node from=(Node)sp_from.getSelectedItem();
			Node to=(Node)sp_to.getSelectedItem();
			List<Node> path=g.spfa(from, to);
			List<Arc> arcpath=Graph.nodes2arcs(path);
			lv_adapter.clear();
			for (Arc n:arcpath){
				lv_adapter.add(n.name);
			}
			lv_adapter.notifyDataSetChanged();
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}		
	};
}
