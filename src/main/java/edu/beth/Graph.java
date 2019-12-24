package edu.beth;

import java.util.Hashtable;
import java.util.List;

public class Graph implements GraphInterface {

	private int numberOfNodes;
	private int numberOfEdges;
	Hashtable<Long, Node> nodes;

	public Graph(int numberOfNodes, int numberOfEdges, Hashtable<Long, Node> nodes) {
		nodes = new Hashtable<Long, Node>();
		this.numberOfNodes = numberOfNodes;
		this.numberOfEdges = numberOfEdges;
		this.nodes = nodes;
	}

	public Graph() {
		nodes = new Hashtable<Long, Node>();

	}

	public Graph(int numberOfNodes) {
		nodes = new Hashtable<Long, Node>();
		this.numberOfNodes = numberOfNodes;
	}

	public int getNumberOfNodes() {
		return numberOfNodes;
	}

	public int getNumberOfEdges() {
		return numberOfEdges;
	}

	public Hashtable<Long, Node> getNodes() {
		return nodes;
	}

	public void addNode(long nodeId, String nodeName, float lon, float lat) {
		Node node = new Node(nodeId, lon, lat);
		nodes.put(nodeId, node);
		numberOfNodes++;
	}
	
	public void addEdge(long source, long destination, double cost, double distance, boolean biDirectional)
	{
		addEdge(source, destination, cost, distance, biDirectional, "other");
	}

	public void addEdge(long source, long destination, double cost, double distance, boolean biDirectional, String wayType) {
		nodes.get(source).addEdge(new Edge(destination, cost, distance, wayType));
		numberOfEdges++;

		if (biDirectional) {
			// if (!nodes.get(destination).equals(null)) {
			nodes.get(destination).addEdge(new Edge(source, cost, distance, wayType));
			numberOfEdges++;

		}
	}

	public void addEdge(long source, long destination, boolean biDirectional, String wayType) {
		nodes.get(source).addEdge(new Edge(destination,wayType));
		numberOfEdges++;

		if (biDirectional) {
			nodes.get(destination).addEdge(new Edge(source,wayType));
			numberOfEdges++;

		}
	}

	public List<Edge> getEdges(long nodeId) {
		if (nodes.get(nodeId) == (null))
			throw new IllegalArgumentException("Node does not exist");

		return nodes.get(nodeId).getEdges();
	}


	public Node get(Long nodeId) {
		return nodes.get(nodeId);
	}

}
