package edu.beth;

import java.util.Hashtable;
import java.util.List;

public interface GraphInterface {
	public int getNumberOfNodes();

	public int getNumberOfEdges();

	public Hashtable<Long, Node> getNodes();

	public void addNode(long nodeId, String nodeName, float f, float g);

	public void addEdge(long src, long destination, double cost, double distance, boolean biDirectional);

	public List<Edge> getEdges(long nodeId);

}
