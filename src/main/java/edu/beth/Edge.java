package edu.beth;

public class Edge {
	private long destinationNodeId;
	private double cost;
	private double distance;
	private String name;

	public Edge(long destinationNodeId, double cost, double distance, String name) {
		super();
		this.destinationNodeId = destinationNodeId;
		this.cost = cost;
		this.distance = distance;
		this.name = name;
	}

	public Edge(long dest) {

	}

	public Edge(long destination, double timeInSec, double distance2) {
		this.destinationNodeId = destination;
		this.cost = timeInSec;
		this.distance = distance2;

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
