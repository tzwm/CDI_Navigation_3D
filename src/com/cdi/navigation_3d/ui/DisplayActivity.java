package com.cdi.navigation_3d.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.cdi.navigation_3d.R;

public class DisplayActivity extends FragmentActivity {
	
	private ImageView img_list;
	private ImageView img_map;
	private ImageView img_ar;
	private ImageView img_back;
	private View placeholder;
	private FragmentManager fm;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_display);
		img_ar=(ImageView)findViewById(R.id.img_ar);
		img_map=(ImageView)findViewById(R.id.img_map);
		img_list=(ImageView)findViewById(R.id.img_list);
		img_back=(ImageView)findViewById(R.id.img_back);
		//placeholder=findViewById(R.id.fragment_placeholder);
		fm=getSupportFragmentManager();
		fm.beginTransaction().replace(R.id.fragment_placeholder, new RouteFragment()).commit();
		
		img_back.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				finish();				
			}
		});
		
		img_list.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				fm.beginTransaction().replace(R.id.fragment_placeholder, new RouteFragment()).commit();
			}
		});
	}

}
