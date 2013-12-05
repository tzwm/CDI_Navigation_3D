package com.cdi.navigation_3d.location;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.restlet.data.ChallengeScheme;
import org.restlet.data.Form;
import org.restlet.resource.ClientResource;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Message;
import android.util.Log;

public class WifiScanReceiver extends BroadcastReceiver {

	

	@Override
	public void onReceive(final Context c, Intent i) {
		Message msg=Message.obtain();
		msg.obj=c;
		LocationThread.handler.sendMessage(msg);
	}

	
}
