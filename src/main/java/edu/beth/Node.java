package edu.beth;

import java.util.ArrayList;
import java.util.List;

public class Node {
	private Long nodeId;
	private String nodeName;
	private float lat;
	private float lon;
	private List<Edge> edges;

	public Node(long nodeId, float lon, float lat) {
		edges = new ArrayList<Edge>();
		this.lat = lat;
		this.lon = lon;
		this.nodeId = nodeId;
	}

	public Node(long nodeId) {
		edges = new ArrayList<Edge>();
		this.nodeId = nodeId;

	}

	public Node() {
	}

	public void addEdge(Edge edge) {
		edges.add(edge);
	}

	public List<Edge> getEdges(int nodeId) {
		return edges;
	}

	public long getNodeId() {
		return nodeId;
	}

	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public float getLat() {
		return lat;
	}

	public void setLat(long lat) {
		this.lat = lat;
	}

	public float getLon() {
		return lon;
	}

	public void setLon(float lon) {
		this.lon = lon;
	}

	public List<Edge> getEdges() {
		return edges;
	}

	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}

}
