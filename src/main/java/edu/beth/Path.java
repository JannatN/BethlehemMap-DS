package edu.beth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class Path {
	private List<Long> pathNodes;
	private int cost;
	private String type = "Feature";
	private Geometry geometry = new Geometry();
	private HashMap<String, String> properties = new HashMap<String, String>();

	public void addProperties(String type, String value) {
		properties.put(type, value);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

	public HashMap<String, String> getProperties() {
		return properties;
	}

	public void setProperties(HashMap<String, String> properties) {
		this.properties = properties;
	}

	public Path(int cost) {
		pathNodes = new ArrayList<Long>();
		this.cost = cost;
	}

	public Path(List<Long> pathNodes, int cost) {
		pathNodes = new ArrayList<Long>();
		this.cost = cost;
		this.pathNodes = pathNodes;

	}

	public Path() {
		pathNodes = new ArrayList<Long>();

	}

	public List<Long> getPathNodes() {
		return pathNodes;
	}

	public void setPathNodes(List<Long> pathNodes) {
		this.pathNodes = pathNodes;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	@Override
	public String toString() {
		return "Path [pathNodes=" + pathNodes + ", cost=" + cost + "]";
	}

}
