package com.cdi.navigation_3d.alg;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

@Deprecated
public class Main {
	
	public static void main(String args[]){
		File f=new File("map.txt");
		try {
			List<Node> r=Graph.build(new FileInputStream(f)).spfa(0, 10);
			System.out.println("dis: "+r.get(r.size()-1).dis);
			for (Node n:r) System.out.println(n.getIndex()+" "+n.dis);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
