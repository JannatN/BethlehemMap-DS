package edu.beth;

public class SearchNode {
	private long nodeId;
	private int cost;
	private long parent;
	private boolean settled;

	public SearchNode(long edgeDistination, int cost) {
		this.nodeId = edgeDistination;
		this.cost = cost;

	}

	public long getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public long getParent() {
		return parent;
	}

	public void setParent(long parent) {
		this.parent = parent;
	}

	public boolean isSettled() {
		return settled;
	}

	public void setSettled(boolean settled) {
		this.settled = settled;
	}

}
