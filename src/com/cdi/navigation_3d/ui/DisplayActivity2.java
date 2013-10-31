package com.cdi.navigation_3d.ui;

import com.cdi.navigation_3d.R;

import min3d.core.RendererActivity;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.Layout;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class DisplayActivity2 extends RendererActivity {
	
	private ImageView img_list;
	private ImageView img_map;
	private ImageView img_ar;
	private ImageView img_back;
	private FrameLayout placeholder;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_display);
		img_ar=(ImageView)findViewById(R.id.img_ar);
		img_map=(ImageView)findViewById(R.id.img_map);
		img_list=(ImageView)findViewById(R.id.img_list);
		img_back=(ImageView)findViewById(R.id.img_back);
		placeholder=(FrameLayout) findViewById(R.id.fragment_placeholder);
		placeholder.addView(new RouteList(DisplayActivity2.this));
		img_back.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				finish();				
			}
		});
		
		img_list.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				placeholder.addView(new RouteList(DisplayActivity2.this));
			}
		});
	}
}
