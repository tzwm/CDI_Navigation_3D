package com.cdi.navigation_3d.location;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.restlet.data.ChallengeScheme;
import org.restlet.data.Form;
import org.restlet.resource.ClientResource;

import com.cdi.navigation_3d.ui.G;
import com.google.gson.Gson;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class LocationThread extends Thread {
	
	static public Handler handler;
	@Override
	public void run() {
		Looper.prepare();
		handler=new LocationHandler();
		Looper.loop();
	}
	
	static class LocationHandler extends Handler{
		private final boolean DEBUG = true;

		private final String BASE_URL = "https://its.navizon.com/api/v1/";
		private final String SITE_ID = "1088"; // Your site ID here
		private final static String USERNAME = "evanfun@me.com"; // Your username
		private final static String PASSWORD = ""; // Your password
		private Context context;
		
		public Object buildarg(Map<String, Integer> map) {
			Form ret = new Form();
			String s = "";
			boolean start = true;
			Set<String> keys = map.keySet();
			for (String key : keys) {
				if (start)
					start = false;
				else
					s += ";";
				s += String.format("0,%s,%d,0", key.replace(":", ""), map.get(key));
			}
			// ret.add("scan", s);
			Log.e("PostArg", s);
			ret.add("scans", s);
			return ret;
		}
		
		public void debugdata(Map<String, Integer> map) {
			if (!DEBUG) return;
			map.clear();
			map.put("E88D285F593D", -56);
			map.put("A8154D0D9774", -65);
			map.put("14CF92929C02", -46);
			map.put("14CF92929BF4", -64);
			map.put("14CF92929B9A", -56);
			map.put("14CF92929C26", -56);
			map.put("14CF92929C28", -62);
			map.put("E88D285F593E", -50);
			map.put("E88D286205C3", -56);
			map.put("E88D285F593D", -82);
			map.put("E88D286205C4", -84);
			map.put("14CF92929CB4", -80);
			map.put("3822D6882AA0", -46);
			map.put("3822D6882AA2", -46);
			map.put("3822D6882AA1", -45);
			map.put("14CF9292A1C8", -75);
			map.put("14CF9292A530", -78);
			map.put("3822D6882AB1", -33);
			map.put("3822D6882AB2", -32);
			map.put("3822D6882AB0", -33);
			map.put("14CF92929958", -95);
			map.put("A8154D0D9775", -64);
		}

		public String getLocalMacAddress() {
			if (DEBUG)
				return "38AA3C6D4CF7";
			else {
				WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
				WifiInfo info = wifi.getConnectionInfo();
				return info.getMacAddress().replace(":", "");
			}
		}
		
		@Override
		public void handleMessage(Message msg) {
			context=(Context)msg.obj;
			
			WifiManager mgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			try {
				List<ScanResult> results = mgr.getScanResults();
				HashMap<String, Integer> map = new HashMap<String, Integer>();
				for (ScanResult sr : results) map.put(sr.BSSID, sr.level);
				debugdata(map);
				String url = BASE_URL + "sites/" + SITE_ID + "/stations/" + getLocalMacAddress() + "/locate/";
				Log.e("url", url);
				// show(url);
				ClientResource itsClient = new ClientResource(url);
				itsClient.setChallengeResponse(ChallengeScheme.HTTP_BASIC, USERNAME, PASSWORD);
				try {
					String s = itsClient.post(buildarg(map)).getText();
					Log.e("response", s);
					LocationBean lb=new Gson().fromJson(s, LocationBean.class);
					G.location.invoke(lb);
					// set(s);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} finally {
				mgr.startScan();
			}
		}
	}
}
