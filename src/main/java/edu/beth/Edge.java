package edu.beth;

public class Edge {
	private long destinationNodeId;
	private double cost;
	private double distance;
	private String name;
	private String wayType;
	
	public Edge(long destinationNodeId, double cost, double distance, String name, String wayType) {
		super();
		this.destinationNodeId = destinationNodeId;
		this.cost = cost;
		this.distance = distance;
		this.name = name;
		this.wayType= wayType;
	}

	public Edge(long dest, String wayType) {
		this.wayType= wayType;
	}

	public Edge(long destination, double timeInSec, double distance2, String wayType) {
		this.destinationNodeId = destination;
		this.cost = timeInSec;
		this.distance = distance2;
		this.wayType= wayType;
	}

	public String getWayType()
	{
		return wayType;
	}
	
	public void setWayType(String wType)
	{
		wayType= wType;
	}
	
	public long getDestinationNodeId() {
		return destinationNodeId;
	}

	public void setDestinationNodeId(long destinationNodeId) {
		this.destinationNodeId = destinationNodeId;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
