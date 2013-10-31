package com.cdi.navigation_3d.ui;

import android.app.Activity;
import android.os.Bundle;

public class RouteActivity extends Activity {
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(new RouteList(this));
}
}
