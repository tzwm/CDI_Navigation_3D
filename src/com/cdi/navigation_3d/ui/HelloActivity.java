package com.cdi.navigation_3d.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.cdi.navigation_3d.R;
import com.cdi.navigation_3d.alg.Graph;
import com.cdi.navigation_3d.alg.Node;
import com.cdi.navigation_3d.location.LocationOnWayBean;
import com.cdi.navigation_3d.location.LocationThread;
import com.cdi.navigation_3d.location.OnLocationChangedListener;

public class HelloActivity extends Activity implements OnClickListener,OnItemSelectedListener{
	
	private List<Node> nodes=new ArrayList<Node>();
	private Spinner sp_from;
	private Spinner sp_to;
	private ImageButton button;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sp_from=(Spinner)findViewById(R.id.spinner1);
		sp_to=(Spinner)findViewById(R.id.spinner2);
		button=(ImageButton)findViewById(R.id.imageButton1);
		button.setOnClickListener(this);
		//lv=(ListView)findViewById(R.id.listView1);
		//lv_adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		//lv.setAdapter(lv_adapter);
		AssetManager asset=getAssets();
		try {
			G.g=Graph.build(asset.open("map.txt"));
			G.g.reset();
			for (int i=0;i<G.g.nodeCount();++i) if (G.g.getNode(i).toString()!=null) nodes.add(G.g.getNode(i));
			sp_from.setAdapter(new ArrayAdapter<Node>(this, android.R.layout.simple_spinner_item,nodes));
			sp_to.setAdapter(new ArrayAdapter<Node>(this, android.R.layout.simple_spinner_item,nodes));
			sp_from.setOnItemSelectedListener(this);
			sp_to.setOnItemSelectedListener(this);
			onItemSelected(null, null,0, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		new LocationThread().start();
		G.location.init(this);
		((WifiManager) getSystemService(WIFI_SERVICE)).startScan();
		G.location.setOnLocationChangedListener(new OnLocationChangedListener() {
			
			@Override
			public void LocationChanged(LocationOnWayBean lb) {
				LocationOnWayBean b=lb.from("n11");
				Log.e("",b.toString());
				Toast.makeText(HelloActivity.this, b.getNearestNode().getName()+"\n"
						+b.getRealLocation().getLng()+"\n"+b.getRealLocation().getLat()+"\n"
				+b.getWayName()+"\n"+b.getRate(), Toast.LENGTH_SHORT).show();
			}
		});
	}
		
	@Override
	public void onItemSelected(AdapterView<?> par, View v, int arg2, long arg3) {
		G.from=(Node)sp_from.getSelectedItem();
		G.to=(Node)sp_to.getSelectedItem();
		G.result=G.g.spfa(G.from, G.to);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
	}

	@Override
	public void onClick(View v) {
		Intent intent=new Intent(this,DisplayActivity3.class);
		startActivity(intent);
	}	
}
