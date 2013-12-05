package com.cdi.navigation_3d.location;

public class LocationOnWayBean {
	private LocationBean realLocation;
	private LocationBeanEx startNode;
	private LocationBeanEx endNode;
	private String wayName;
	private double rate;
	private double distance;
	public LocationBeanEx getStartNode() {
		return startNode;
	}
	public void setStartNode(LocationBeanEx startNode) {
		this.startNode = startNode;
	}
	public LocationBeanEx getEndNode() {
		return endNode;
	}
	public void setEndNode(LocationBeanEx endNode) {
		this.endNode = endNode;
	}
	/** value between 0.0 and 1.0<br>
	 * the rate from 'stattNode' to 'endNode'<br>
	 * 0.0 means at 'startNode' and 1.0 means at 'endNode' */
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public LocationBean getRealLocation() {
		return realLocation;
	}
	public void setRealLocation(LocationBean realLocation) {
		this.realLocation = realLocation;
	}
	public String getWayName() {
		return wayName;
	}
	public void setWayName(String wayName) {
		this.wayName = wayName;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}

}
