package code;

public class Node {

//	private int state;
	private int prosperity;
	private int moneySpent;
	private int food;
	private int materials;
	private int energy;
	private Node parentNode;
	private String operateString;
	private int depth;
	private int rootPathCost;
	private int heuristicCost;
	private int delay;
	private String type;

	// For node
	public Node(int prosperity, int moneySpent, int food, int materials, int energy, String operateString, int depth,
			Node parentNode, int rootPathCost, int delay, String type, int heuristicCost) {
		this.prosperity = prosperity;
		this.moneySpent = moneySpent;
		this.food = food;
		this.materials = materials;
		this.energy = energy;
		this.operateString = operateString;
		this.depth = depth;
		this.parentNode = parentNode;
		this.rootPathCost = rootPathCost;
		this.heuristicCost = heuristicCost;
		this.delay = delay;
		this.type = type;
	}

	// For root
	public Node(int prosperity, int moneySpent, int food, int materials, int energy, int depth, int rootPathCost,
			int delay, String type, int heuristicCost) {
		this.prosperity = prosperity;
		this.moneySpent = moneySpent;
		this.food = food;
		this.materials = materials;
		this.energy = energy;
		this.parentNode = null;
		this.operateString = "Root";
		this.depth = depth;
		this.rootPathCost = rootPathCost;
		this.heuristicCost = heuristicCost;
		this.delay = delay;
		this.type = type;
	}

	public int getProsperity() {
		return prosperity;
	}

	public void setProsperity(int prosperity) {
		this.prosperity = prosperity;
	}

	public int getMoneySpent() {
		return moneySpent;
	}

	public void setMoneySpent(int moneySpent) {
		this.moneySpent = moneySpent;
	}

	public int getFood() {
		return food;
	}

	public void setFood(int food) {
		this.food = food;
	}

	public int getMaterials() {
		return materials;
	}

	public void setMaterials(int materials) {
		this.materials = materials;
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public int getHeuristicCost() {
		return heuristicCost;
	}

	public void setHeuristicCost(int heuristicCost) {
		this.heuristicCost = heuristicCost;
	}

	@Override
	public String toString() {
		String stateString = "";

		stateString = "State -> (Prosperity:" + this.prosperity + ", Money Spent:" + this.moneySpent + ", Food:"
				+ this.food + ", Materials:" + this.materials + ", Energy:" + this.energy + ")\nOperator: "
				+ this.operateString + " - Depth: " + this.depth + " - Path Cost: " + this.rootPathCost
				+ " - Heuristic Cost: " + this.heuristicCost + "." + ",Child of: " + this.parentNode.operateString
				+ "\nDelay = " + this.delay + ", Type: " + this.type;
		return stateString;
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
