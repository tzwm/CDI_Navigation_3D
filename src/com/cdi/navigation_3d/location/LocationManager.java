package com.cdi.navigation_3d.location;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.cdi.navigation_3d.alg.Arc;
import com.cdi.navigation_3d.alg.Node;
import com.cdi.navigation_3d.ui.G;

import android.content.Context;
import android.os.Handler;

public class LocationManager {
	private OnLocationChangedListener onLocationChangedListener=null;
	private Context context;
	private List<LocationBeanEx> list=new ArrayList<LocationBeanEx>();
	private Handler handler=new Handler();
	
	public LocationManager() {
		
	}
	
	public void init(Context context){
		this.context=context;
		readFile("l3_4.txt",3);
	}
	
	private void readFile(String filename,int level){
		try {
			InputStream is=context.getAssets().open(filename);
			Scanner sc=new Scanner(is);
			while(true){
				if (!sc.hasNext()) break;
				LocationBeanEx lbx=new LocationBeanEx();
				lbx.setLevelId(level);
				lbx.setName(sc.next());
				lbx.setLng(Double.valueOf(sc.next()));
				lbx.setLat(Double.valueOf(sc.next()));
				list.add(lbx);
			}
			sc.close();
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	OnLocationChangedListener getOnLocationChangedListener() {
		return onLocationChangedListener;
	}

	public void setOnLocationChangedListener(OnLocationChangedListener onLocationChangedListener) {
		this.onLocationChangedListener = onLocationChangedListener;
	}
	
	public void computeLocationOnway(LocationOnWayBean low){
		Double minDis=(double)0x7fffffff;
		LocationBean lb=low.getRealLocation();
		Map<Node,LocationBeanEx> nodes=getNodesOnLevel(lb.getLevelId());
		for (Node n:nodes.keySet()){
			for (Arc a=n.arc;a!=null;a=a.next){
				double d=pointToLine(nodes.get(n),nodes.get(a.target), lb);
				if (d<minDis) {
					minDis=d;
					low.setDistance(minDis);
					low.setStartNode(nodes.get(n));
					low.setEndNode(nodes.get(a.target));
					low.setWayName(a.name);
				}
			}
		}
		computeRate(low);
		
		minDis=(double)0x7fffffff;
		for (LocationBeanEx lbe:list){
			double d=distance(lbe, lb);
			if (d<minDis) {
				minDis=d;
				low.setNearestNode(lbe);
			}
		}
	}
	
	public void computeRate(LocationOnWayBean low){
		if (distance(low.getRealLocation(), low.getStartNode())<0.00000001) {
			low.setRate(0);
			return;
		}else if (distance(low.getRealLocation(), low.getEndNode())<0.00000001) {
			low.setRate(1);
			return;
		}else{
			double a2=squreDistance(low.getRealLocation(), low.getStartNode());
			double len2=a2-(low.getDistance()*low.getDistance());
			double len=Math.sqrt(len2);
			low.setRate(len/distance(low.getStartNode(), low.getEndNode()));
		}
	}
	
	public Map<Node,LocationBeanEx> getNodesOnLevel(int level){
		HashMap<Node,LocationBeanEx> nodes=new HashMap<Node,LocationBeanEx>();
		for(LocationBeanEx e : list){
			if (e.getLevelId()==level){
				String name=e.getName();
				if (name.charAt(0)=='*') name=name.substring(1);
				nodes.put(G.g.getNode(name), e);				
			}
		}
		return nodes;
	}
	
	public double distance(LocationBean p1,LocationBean p2){
		return Math.sqrt(squreDistance(p1,p2));
	}
	
	public double squreDistance(LocationBean p1,LocationBean p2){
		double cx=p1.getLat()-p2.getLat();
		double cy=p1.getLng()-p2.getLng();
		return cx*cx+cy*cy;
	}
	
	
	private double pointToLine(LocationBean p,LocationBean q,LocationBean r){
		if (p==null) return 0x7fffffff;
		if (q==null) return 0x7fffffff;
		if (r==null) return 0x7fffffff;
		return pointToLine(p.getLat(),p.getLng(), q.getLat(),q.getLng(), r.getLat(),r.getLng());
	}
	
	// 点到直线的最短距离的判断 点（x0,y0） 到由两点组成的线段（x1,y1） ,( x2,y2 )
    private double pointToLine(double x1, double y1, double x2, double y2, double x0, double y0) {
        double space = 0;
        double a, b, c;
        a = lineSpace(x1, y1, x2, y2);// 线段的长度
        b = lineSpace(x1, y1, x0, y0);// (x1,y1)到点的距离
        c = lineSpace(x2, y2, x0, y0);// (x2,y2)到点的距离
        if (c+b == a) {//点在线段上
           space = 0;
           return space;
        }
        if (a <= 0.000001) {
           space = b;
           return space;
        }
        if (c * c >= a * a + b * b) { //组成直角三角形或钝角三角形，(x1,y1)为直角或钝角
           space = b;
           return space;
        }
        if (b * b >= a * a + c * c) {//组成直角三角形或钝角三角形，(x2,y2)为直角或钝角
           space = c;
           return space;
        }        
        //组成锐角三角形，则求三角形的高
        double p = (a + b + c) / 2;// 半周长
        double s = Math.sqrt(p * (p - a) * (p - b) * (p - c));// 海伦公式求面积
        space = 2 * s / a;// 返回点到线的距离（利用三角形面积公式求高）
        return space;
    }

    // 计算两点之间的距离
    private double lineSpace(double x1, double y1, double x2, double y2) {
        double lineLength = 0;
        lineLength = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2)  * (y1 - y2));
        return lineLength;
    }
    
    public void invoke(LocationBean lb){
    	LocationOnWayBean b=new LocationOnWayBean();
		b.setRealLocation(lb);
		computeLocationOnway(b);
		final LocationOnWayBean B=b;
		if (onLocationChangedListener!=null)
			handler.post(new Runnable() {			
				@Override
				public void run() {
					onLocationChangedListener.LocationChanged(B);
				}
			});			
    }
}
