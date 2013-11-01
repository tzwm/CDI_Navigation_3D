package com.cdi.navigation_3d.ui;

import com.cdi.navigation_3d.R;
import com.cdi.navigation_3d.ar.ArActivity;
import com.cdi.navigation_3d.map.MapRendererActivity;

import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.Layout;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;

public class DisplayActivity3 extends ActivityGroup {
	
	private ImageView img_list;
	private ImageView img_map;
	private ImageView img_ar;
	private ImageView img_back;
	private TabHost th;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_display3);
		img_ar=(ImageView)findViewById(R.id.img_ar);
		img_map=(ImageView)findViewById(R.id.img_map);
		img_list=(ImageView)findViewById(R.id.img_list);
		img_back=(ImageView)findViewById(R.id.img_back);
		th=(TabHost) findViewById(android.R.id.tabhost);
		th.setup(getLocalActivityManager());
		setUpAllTabs();
		th.setCurrentTab(0);		
		
		img_back.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				finish();				
			}
		});
		
		img_list.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setUpAllTabs();
			    th.setCurrentTab(0);
				
			}
		});
		
		img_map.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setUpAllTabs();
				th.setCurrentTab(1);
			}
		});
		
		img_ar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setUpAllTabs();
				th.setCurrentTab(2);
			}
		});
	}
	
	private void setUpAllTabs(){
		th.clearAllTabs();
		th.addTab(th.newTabSpec("list").setIndicator("list").setContent(new Intent(this,RouteActivity.class)));
		th.addTab(th.newTabSpec("map").setIndicator("map").setContent(new Intent(this,MapRendererActivity.class)));
		th.addTab(th.newTabSpec("ar").setIndicator("ar").setContent(new Intent(this,ArActivity.class)));
	}
}
