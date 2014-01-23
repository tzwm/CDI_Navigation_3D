package com.cdi.navigation_3d.location;

import com.cdi.navigation_3d.ui.G;

public class LocationAlgHolder {
	private static LocationBeanEx curlocation = null;
	private static int differRate = 0;
	
	public static LocationBeanEx locate(LocationBeanEx lb){
		if (curlocation==null){
			curlocation=lb;
			differRate=0;
		}else{
			if (G.g.distance(curlocation.getName(), lb.getName())<=differRate){
				curlocation=lb;
				differRate=0;
			}else{
				++differRate;
			}
		}	
		return curlocation;
	}
}
