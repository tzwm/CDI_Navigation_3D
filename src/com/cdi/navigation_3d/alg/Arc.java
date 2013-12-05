package com.cdi.navigation_3d.alg;

public class Arc {
	public Node target;
	int value = 1;
	public Arc next = null;
	public String name;
	
	Arc(Node from,Node to, String name){
		this(from,to,1,name);
	}
	
	Arc(Node from,Node to,int value,String name){
		next=from.arc;
		from.arc=this;
		target=to;
		this.name=name;
		this.value=value;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Arc)) return false;
		return name.equals(((Arc)o).name);
	}
}
