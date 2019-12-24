package edu.beth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

public class Dijkestra {
	static Hashtable<Long, SearchNode> nodesHash;
	static PriorityQueue<SearchNode> pQueue;
	private Comparator<SearchNode> comparatorCost = new Comparator<SearchNode>() {
		@Override
		public int compare(SearchNode o1, SearchNode o2) {
			return o1.getCost() - o2.getCost();
		}
	};

	public Path shortestPath(Graph g, long source, long destination) {
		pQueue = new PriorityQueue<SearchNode>(comparatorCost);
		nodesHash = new Hashtable<Long, SearchNode>();
		SearchNode sourceSearchNode = new SearchNode(source, 0);
		nodesHash.put(source, sourceSearchNode);
		sourceSearchNode.setParent(-1);
		pQueue.add(sourceSearchNode);

		while (!pQueue.isEmpty()) {
			SearchNode currNode = pQueue.poll();
			if (currNode.getNodeId() == destination) {
				Path printpath = generatePath(currNode);
				return printpath;

			}

			if (currNode.isSettled() == true)
				continue;
			currNode.setSettled(true);
			List<Edge> edges = g.getEdges(currNode.getNodeId());
			for (Edge edge : edges) {
				int newCost = (int) (currNode.getCost() + edge.getCost());
				long edgeDistination = edge.getDestinationNodeId();
				SearchNode searchNode = nodesHash.get(edgeDistination);

				if (searchNode == null) {
					searchNode = new SearchNode(edgeDistination, newCost);
					searchNode.setParent(currNode.getNodeId());
					nodesHash.put(edgeDistination, searchNode);
					pQueue.add(searchNode);

				} else if (newCost < searchNode.getCost()) {
					searchNode.setCost(newCost);
					searchNode.setParent(currNode.getNodeId());
					pQueue.add(searchNode);

				}
			}

		}
		return null;
	}

	private static Path generatePath(SearchNode node) {
		Path path = new Path();
		List<Long> nodeIds = new ArrayList<Long>();
		System.out.println("The path is : ");
		Stack<SearchNode> searchNodesStack = new Stack<SearchNode>();
		SearchNode destination = node;
		while (destination != null) {
			searchNodesStack.push(destination);
			destination = nodesHash.get(destination.getParent());
		}
		while (!searchNodesStack.isEmpty()) {
			long nodeId = searchNodesStack.pop().getNodeId();
			System.out.println(nodeId);
			nodeIds.add((long) nodeId);
		}
		path.setPathNodes(nodeIds);
		path.setCost(node.getCost());

		return path;

	}

}
