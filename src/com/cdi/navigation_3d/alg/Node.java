package com.cdi.navigation_3d.alg;

import java.util.Scanner;

public class Node {
	public static final int MAX_DISTANCE = 0x3fffffff;
	
	private int index;
	boolean inQueue;
	public String name;
	public String disp_name;
	int dis;
	public Arc arc;
	Node prev;
	
	public int getIndex(){return index;}
	public int getDistance(){return dis;}
	
	public Node(int i, Scanner sc) {
		index=i;
		arc=null;
		name=sc.next();
		disp_name=sc.next();
		reset();
	}
	
	public void reset(){
		dis=MAX_DISTANCE;
		inQueue=false;
		//arc=null;
		prev=null;
	}
	
	@Override
	public String toString() {
		if (disp_name.equals("-")) return null;
		return disp_name.replace('_', ' ');
	}
}
