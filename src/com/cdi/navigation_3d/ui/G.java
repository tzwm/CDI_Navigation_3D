package com.cdi.navigation_3d.ui;

import java.util.List;

import android.view.View.OnLayoutChangeListener;

import com.cdi.navigation_3d.alg.Graph;
import com.cdi.navigation_3d.alg.Node;
import com.cdi.navigation_3d.location.LocationManager;
import com.cdi.navigation_3d.location.OnLocationChangedListener;

public class G {
	
	public static List<Node> result = null;
	public static Graph g;
	public static Node from;
	public static Node to;	
	public static LocationManager location = new LocationManager();
}
