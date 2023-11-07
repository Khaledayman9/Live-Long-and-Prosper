package projectOneAI;

public class Node {

	private String stateString;
	private Node parentNode;
	private String operateString;
	private int depth;
	private int rootPathCost;
	private int heuristicCost;

	public Node(String stateString, Node parentNode, String operateString, int depth, int rootPathCost,
			int heuristicCost) {
		this.stateString = stateString;
		this.parentNode = parentNode;
		this.operateString = operateString;
		this.depth = depth;
		this.rootPathCost = rootPathCost;
		this.heuristicCost = heuristicCost;
	}

	public static void main(String[] args) {
		System.out.println("Hi");
	}

	public int getHeuristicCost() {
		return heuristicCost;
	}

	public void setHeuristicCost(int heuristicCost) {
		this.heuristicCost = heuristicCost;
	}

	@Override
	public String toString() {
		return "State: " + this.stateString + ", Operator: " + this.operateString + ", Depth: " + this.depth
				+ ", Path Cost: " + this.rootPathCost;
	}

	public String getStateString() {
		return stateString;
	}

	public void setStateString(String stateString) {
		this.stateString = stateString;
	}

	public Node getParentNode() {
		return parentNode;
	}

	public void setParentNode(Node parentNode) {
		this.parentNode = parentNode;
	}

	public String getOperateString() {
		return operateString;
	}

	public void setOperateString(String operateString) {
		this.operateString = operateString;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getRootPathCost() {
		return rootPathCost;
	}

	public void setRootPathCost(int rootPathCost) {
		this.rootPathCost = rootPathCost;
	}

}
