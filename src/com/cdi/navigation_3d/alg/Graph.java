package com.cdi.navigation_3d.alg;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Scanner;

import android.util.Log;

public class Graph {
	
	private Node[] nodes;
	private List<Node> result=new ArrayList<Node>();
	private Queue<Node> queue=new LinkedList<Node>();
	
	public static Graph build(InputStream f) throws Exception{
		Graph g=new Graph();
		Scanner sc=new Scanner(f);
		int n=sc.nextInt();
		int m=sc.nextInt();
		g.nodes=new Node[n];
		for (int i=0;i<n;++i) g.nodes[i]=new Node(i,sc);
		for (int i=0;i<m;++i){			
			String x=sc.next();
			String y=sc.next();
			String name=sc.next();
			new Arc(g.getNode(x),g.getNode(y),name);
			new Arc(g.getNode(y),g.getNode(x),name);
		}
		return g;
	}
	
	
	
	private boolean push(Node node){
		if (node.inQueue) return false;
		queue.add(node);
		node.inQueue=true;
		return true;
	}
	
	private Node pop(){
		Node node= queue.poll();
		node.inQueue=false;
		return node;
	}
	
	void genResult(Node node){
		if (node!=null){
			genResult(node.prev);
			result.add(node);
		}
	}
	
	public List<Node> spfa(Node from,Node to){
		reset();
		from.dis=0;
		push(from);
		while (queue.size()>0){
			Node node=pop();
			for (Arc r=node.arc;r!=null;r=r.next){
				if (node.dis+r.value<r.target.dis){
					r.target.dis=node.dis+r.value;
					r.target.prev=node;
					push(r.target);
				}
			}
		}
		result.clear();
		genResult(to);
		return result;
	}
	
	public void dfs(Node node){
		node.inQueue=true;
		if (!node.disp_name.equals("-"))
			Log.i("dfs",node.getIndex()+" "+node.name+" "+node.disp_name.replace('_', ' '));
		for (Arc a=node.arc;a!=null;a=a.next){
			if (a.target.inQueue==false){
				Log.d("dfs", node.name+" "+a.target.name+" "+a.name);
				dfs(a.target);
			}
		}
	}	
	
	public void reset(){for (Node n:nodes) n.reset();}	
	public List<Node> spfa(int from,int to){return spfa(getNode(from),getNode(to));}
	public List<Node> spfa(String from,String to){return spfa(getNode(from),getNode(to));}
	
	public static List<Arc> nodes2arcs(Iterable<Node> nodes){
		List<Arc> ret=new ArrayList<Arc>();
		Iterator<Node> i=nodes.iterator();
		Node cur=null;
		Node next=i.next();
		while (i.hasNext()){
			cur=next;
			next=i.next();
			Arc tmp=null;
			for (Arc a=cur.arc;a!=null;a=a.next){
				if (a.target==next) {
					tmp=a;
					break;
				}
			}
			if (tmp==null) return null;
			ret.add(tmp);
		}
		return ret;
	}
	
	public int nodeCount(){return nodes==null?0:nodes.length;}
	public Node getNode(int i){return nodes[i];}
	public Node getNode(String name){ for (Node n:nodes) if (n.name.equals(name)) return n;return null;}
	
}
