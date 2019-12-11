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

	public void addEdge(long source, long destination, double cost, double distance, boolean biDirectional) {
		nodes.get(source).addEdge(new Edge(destination, cost, distance));
		numberOfEdges++;

		if (biDirectional) {
			// if (!nodes.get(destination).equals(null)) {
			nodes.get(destination).addEdge(new Edge(source, cost, distance));
			numberOfEdges++;

		}
	}

	public void addEdge(long source, long destination, boolean biDirectional) {
		nodes.get(source).addEdge(new Edge(destination));
		numberOfEdges++;

		if (biDirectional) {
			nodes.get(destination).addEdge(new Edge(source));
			numberOfEdges++;

		}
	}

	public List<Edge> getEdges(long nodeId) {
		if (nodes.get(nodeId) == (null))
			throw new IllegalArgumentException("Node does not exist");

		return nodes.get(nodeId).getEdges();
	}

	public void edgesPrint(int j) {
		for (int i = 0; i < getEdges(j).size(); i++) {
			System.out.println(getEdges(j).get(i).getDestinationNodeId());

		}
	}

	public Node get(Long nodeId) {
		return nodes.get(nodeId);
	}

}
