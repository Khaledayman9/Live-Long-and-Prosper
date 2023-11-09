package projectOneAI;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class GenericSearch {
	// LLAPProblem problem;
	private boolean success;

	private Node goalNode;
	private Queue<Node> pathNodes;
	private int l;
	private int p;

	public GenericSearch() {
//		this.problem = problem;
		this.success = false;
		this.l = 0;
		this.pathNodes = new LinkedList<Node>();
		this.p = 0;
	}

	public void GeneralSearch(LLAPProblem problem, String QueueFun) {
		if (QueueFun.toLowerCase().equals("bf") || QueueFun.toLowerCase().equals("df")
				|| QueueFun.toLowerCase().equals("uc") || QueueFun.toLowerCase().equals("as1")
				|| QueueFun.toLowerCase().equals("as2") || QueueFun.toLowerCase().equals("id")
				|| QueueFun.toLowerCase().equals("gr1") || QueueFun.toLowerCase().equals("gr2")) {
			Deque<Node> nodes = new LinkedList<Node>();
			String stateString = setStringState(problem.getInitialProsperityLevel(), problem.getMoneySpent(),
					problem.getInitialFood(), problem.getInitialMaterials(), problem.getInitialEnergy());
			int[] stateArray = setArrayState(problem.getInitialProsperityLevel(), problem.getMoneySpent(),
					problem.getInitialFood(), problem.getInitialMaterials(), problem.getInitialEnergy());
			Node initialNode = new Node(stateString, stateArray, 0, problem.getMoneySpent(),
					problem.remainingProsperityHeuristic("Root"));
			nodes.add(initialNode);
			while (true) {
				if (nodes.isEmpty()) {
					goalNode = null;
					success = false;
					break;
				}
				Node n = nodes.poll();
				if (!(n.getParentNode() == null)) {
					if (n.getOperateString().toLowerCase().equals("wait")) {
						if (problem.getDelay() != 0) {
							problem.WAIT();
						}
					} else if (n.getOperateString().toLowerCase().equals("requestfood")) {
						if (problem.getDelay() == 0) {
							problem.RequestFood();
						}
					} else if (n.getOperateString().toLowerCase().equals("requestaterials")) {
						if (problem.getDelay() == 0) {
							problem.RequestMaterials();
						}
					} else if (n.getOperateString().toLowerCase().equals("requestenergy")) {
						if (problem.getDelay() == 0) {
							problem.RequestEnergy();
						}
					} else if (n.getOperateString().toLowerCase().equals("build1")) {
						problem.Build1();
					} else if (n.getOperateString().toLowerCase().equals("build2")) {
						problem.Build2();
					}

				}
				if (problem.getInitialEnergy() <= 0 || problem.getInitialFood() <= 0
						|| problem.getInitialMaterials() <= 0) {

					if (problem.getInitialEnergy() <= 0 && problem.getInitialFood() > 0
							&& problem.getInitialMaterials() > 0) {

						n.setStateString(setStringState(problem.getInitialProsperityLevel(), problem.getMoneySpent(),
								problem.getInitialFood(), problem.getInitialMaterials(), 0));
						n.setState(setArrayState(problem.getInitialProsperityLevel(), problem.getMoneySpent(),
								problem.getInitialFood(), problem.getInitialMaterials(), 0));

					} else if (problem.getInitialEnergy() > 0 && problem.getInitialFood() <= 0
							&& problem.getInitialMaterials() > 0) {

						n.setStateString(setStringState(problem.getInitialProsperityLevel(), problem.getMoneySpent(), 0,
								problem.getInitialMaterials(), problem.getInitialEnergy()));
						n.setState(setArrayState(problem.getInitialProsperityLevel(), problem.getMoneySpent(), 0,
								problem.getInitialMaterials(), problem.getInitialEnergy()));

					} else if (problem.getInitialEnergy() > 0 && problem.getInitialFood() > 0
							&& problem.getInitialMaterials() <= 0) {

						n.setStateString(setStringState(problem.getInitialProsperityLevel(), problem.getMoneySpent(),
								problem.getInitialFood(), 0, problem.getInitialEnergy()));
						n.setState(setArrayState(problem.getInitialProsperityLevel(), problem.getMoneySpent(),
								problem.getInitialFood(), 0, problem.getInitialEnergy()));

					} else if (problem.getInitialEnergy() <= 0 && problem.getInitialFood() <= 0
							&& problem.getInitialMaterials() <= 0)

						n.setStateString(
								setStringState(problem.getInitialProsperityLevel(), problem.getMoneySpent(), 0, 0, 0));
					n.setState(setArrayState(problem.getInitialProsperityLevel(), problem.getMoneySpent(), 0, 0, 0));

				} else {
					n.setStateString(setStringState(problem.getInitialProsperityLevel(), problem.getMoneySpent(),
							problem.getInitialFood(), problem.getInitialMaterials(), problem.getInitialEnergy()));
					n.setState(setArrayState(problem.getInitialProsperityLevel(), problem.getMoneySpent(),
							problem.getInitialFood(), problem.getInitialMaterials(), problem.getInitialEnergy()));
				}
//				print(nodes);
				// pathNodes.add(n);
				if (n.getState()[1] > 100000) {
					this.setSuccess(false);
					break;
				} else if (n.getState()[2] == 0 || n.getState()[3] == 0 || n.getState()[4] == 0) {
					this.setSuccess(false);
					break;
				} else if (goalTest(problem, n) && n.getState()[1] <= 100000) {
					this.setSuccess(true);
					pathNodes.add(n);
					this.goalNode = n;
					break;
				} else {
					pathNodes.add(n);
					if (QueueFun == "ID") {
						nodes = expandID(problem, n, nodes);
					} else {
						nodes = expandPath(problem, n, nodes, QueueFun);
					}
				}
			}
		} else {
			System.out.println("You have not enetered a Valid Search Algorithm.");
			System.out.println("The options are: [BF, DF, UC, ID, AS1, AS2, GR1, GR2]");
			return;
		}
	}

	public String setStringState(int prosperityLevel, int moneySpent, int initialFood, int initialMaterials,
			int initialEnergy) {
		String result = "Prosperity level = " + prosperityLevel + " ,Money Spent: " + moneySpent + " ,Food: "
				+ initialFood + " ,Materials: " + initialMaterials + " ,Energy: " + initialEnergy + ".";
		return result;
	}

	public int getL() {
		return l;
	}

	public void setL(int l) {
		this.l = l;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int[] setArrayState(int prosperityLevel, int moneySpent, int initialFood, int initialMaterials,
			int initialEnergy) {
		int[] result = new int[5];
		result[0] = prosperityLevel;
		result[1] = moneySpent;
		result[2] = initialFood;
		result[3] = initialMaterials;
		result[4] = initialEnergy;
		return result;
	}

	public Deque<Node> expandPath(LLAPProblem problem, Node n, Deque<Node> nodes, String QueueFun) {
		if (problem.getDelay() != 0) {
			if (QueueFun.toLowerCase().equals("bf")) {
				Node waitNode = new Node("WAIT", n.getDepth() + 1, n, problem.getWaitCost());
				Node build1Node = new Node("BUILD1", n.getDepth() + 1, n, problem.getBuild1Cost());
				Node build2Node = new Node("BUILD2", n.getDepth() + 1, n, problem.getBuild2Cost());
				nodes.add(waitNode);
				nodes.add(build1Node);
				nodes.add(build2Node);
			} else if (QueueFun.toLowerCase().equals("df")) {
				Node waitNode = new Node("WAIT", n.getDepth() + 1, n, problem.getWaitCost());
				Node build1Node = new Node("BUILD1", n.getDepth() + 1, n, problem.getBuild1Cost());
				Node build2Node = new Node("BUILD2", n.getDepth() + 1, n, problem.getBuild2Cost());
				nodes.addFirst(waitNode);
				nodes.addFirst(build1Node);
				nodes.addFirst(build2Node);
			} else if (QueueFun.toLowerCase().equals("uc")) {
				Node waitNode = new Node("WAIT", n.getDepth() + 1, n, problem.getWaitCost());
				Node build1Node = new Node("BUILD1", n.getDepth() + 1, n, problem.getBuild1Cost());
				Node build2Node = new Node("BUILD2", n.getDepth() + 1, n, problem.getBuild2Cost());
				nodes.add(waitNode);
				nodes.add(build1Node);
				nodes.add(build2Node);
				orderQueue(nodes, "UC");
			} else if (QueueFun.toLowerCase().equals("gr1")) {
				Node waitNode = new Node("WAIT", n.getDepth() + 1, n, problem.getWaitCost(),
						problem.remainingProsperityHeuristic("wait"));
				Node build1Node = new Node("BUILD1", n.getDepth() + 1, n, problem.getBuild1Cost(),
						problem.remainingProsperityHeuristic("Build1"));
				Node build2Node = new Node("BUILD2", n.getDepth() + 1, n, problem.getBuild2Cost(),
						problem.remainingProsperityHeuristic("Build2"));
				nodes.add(waitNode);
				nodes.add(build1Node);
				nodes.add(build2Node);
				orderQueue(nodes, "GR");
			} else if (QueueFun.toLowerCase().equals("gr2")) {
				Node waitNode = new Node("WAIT", n.getDepth() + 1, n, problem.getWaitCost(),
						problem.resourceCostHeuristic());
				Node build1Node = new Node("BUILD1", n.getDepth() + 1, n, problem.getBuild1Cost(),
						problem.resourceCostHeuristic());
				Node build2Node = new Node("BUILD2", n.getDepth() + 1, n, problem.getBuild2Cost(),
						problem.resourceCostHeuristic());
				nodes.add(waitNode);
				nodes.add(build1Node);
				nodes.add(build2Node);
				orderQueue(nodes, "GR");
			}

			return nodes;
		} else {
			if (QueueFun.toLowerCase().equals("bf")) {
				Node requestFoodNode = new Node("RequestFood", n.getDepth() + 1, n, problem.getRequestResouce());
				Node requestMaterialsNode = new Node("RequestMaterials", n.getDepth() + 1, n,
						problem.getRequestResouce());
				Node requestEnergyNode = new Node("RequestEnergy", n.getDepth() + 1, n, problem.getRequestResouce());
				Node build1Node = new Node("BUILD1", n.getDepth() + 1, n, problem.getBuild1Cost());
				Node build2Node = new Node("BUILD2", n.getDepth() + 1, n, problem.getBuild2Cost());
				nodes.add(requestFoodNode);
				nodes.add(requestMaterialsNode);
				nodes.add(requestEnergyNode);
				nodes.add(build1Node);
				nodes.add(build2Node);
			} else if (QueueFun.toLowerCase().equals("df")) {
				Node requestFoodNode = new Node("RequestFood", n.getDepth() + 1, n, problem.getRequestResouce());
				Node requestMaterialsNode = new Node("RequestMaterials", n.getDepth() + 1, n,
						problem.getRequestResouce());
				Node requestEnergyNode = new Node("RequestEnergy", n.getDepth() + 1, n, problem.getRequestResouce());
				Node build1Node = new Node("BUILD1", n.getDepth() + 1, n, problem.getBuild1Cost());
				Node build2Node = new Node("BUILD2", n.getDepth() + 1, n, problem.getBuild2Cost());
				nodes.addFirst(requestFoodNode);
				nodes.addFirst(requestMaterialsNode);
				nodes.addFirst(requestEnergyNode);
				nodes.addFirst(build1Node);
				nodes.addFirst(build2Node);
			} else if (QueueFun.toLowerCase().equals("uc")) {
				Node requestFoodNode = new Node("RequestFood", n.getDepth() + 1, n, problem.getRequestResouce());
				Node requestMaterialsNode = new Node("RequestMaterials", n.getDepth() + 1, n,
						problem.getRequestResouce());
				Node requestEnergyNode = new Node("RequestEnergy", n.getDepth() + 1, n, problem.getRequestResouce());
				Node build1Node = new Node("BUILD1", n.getDepth() + 1, n, problem.getBuild1Cost());
				Node build2Node = new Node("BUILD2", n.getDepth() + 1, n, problem.getBuild2Cost());
				nodes.add(requestFoodNode);
				nodes.add(requestMaterialsNode);
				nodes.add(requestEnergyNode);
				nodes.add(build1Node);
				nodes.add(build2Node);
				orderQueue(nodes, "UC");
			} else if (QueueFun.toLowerCase().equals("gr1")) {
				Node requestFoodNode = new Node("RequestFood", n.getDepth() + 1, n, problem.getRequestResouce(),
						problem.remainingProsperityHeuristic("Request"));
				Node requestMaterialsNode = new Node("RequestMaterials", n.getDepth() + 1, n,
						problem.getRequestResouce(), problem.remainingProsperityHeuristic("Request"));
				Node requestEnergyNode = new Node("RequestEnergy", n.getDepth() + 1, n, problem.getRequestResouce(),
						problem.remainingProsperityHeuristic("Request"));
				Node build1Node = new Node("BUILD1", n.getDepth() + 1, n, problem.getBuild1Cost(),
						problem.remainingProsperityHeuristic("Build1"));
				Node build2Node = new Node("BUILD2", n.getDepth() + 1, n, problem.getBuild2Cost(),
						problem.remainingProsperityHeuristic("Build2"));
				nodes.add(requestFoodNode);
				nodes.add(requestMaterialsNode);
				nodes.add(requestEnergyNode);
				nodes.add(build1Node);
				nodes.add(build2Node);
				orderQueue(nodes, "GR");
			} else if (QueueFun.toLowerCase().equals("gr2")) {
				Node requestFoodNode = new Node("RequestFood", n.getDepth() + 1, n, problem.getRequestResouce(),
						problem.resourceCostHeuristic());
				Node requestMaterialsNode = new Node("RequestMaterials", n.getDepth() + 1, n,
						problem.getRequestResouce(), problem.resourceCostHeuristic());
				Node requestEnergyNode = new Node("RequestEnergy", n.getDepth() + 1, n, problem.getRequestResouce(),
						problem.resourceCostHeuristic());
				Node build1Node = new Node("BUILD1", n.getDepth() + 1, n, problem.getBuild1Cost(),
						problem.resourceCostHeuristic());
				Node build2Node = new Node("BUILD2", n.getDepth() + 1, n, problem.getBuild2Cost(),
						problem.resourceCostHeuristic());
				nodes.add(requestFoodNode);
				nodes.add(requestMaterialsNode);
				nodes.add(requestEnergyNode);
				nodes.add(build1Node);
				nodes.add(build2Node);
				orderQueue(nodes, "GR");
			}

			return nodes;
		}
	}

	public Deque<Node> expandID(LLAPProblem problem, Node n, Deque<Node> nodes) {
		if (n.getDepth() < l) {
			if (problem.getDelay() != 0) {
				Node waitNode = new Node("wait", n.getDepth() + 1, n, problem.getWaitCost());
				Node build1Node = new Node("build1", n.getDepth() + 1, n, problem.getBuild1Cost());
				Node build2Node = new Node("build2", n.getDepth() + 1, n, problem.getBuild2Cost());
				nodes.addFirst(waitNode);
				nodes.addFirst(build1Node);
				nodes.addFirst(build2Node);
				return nodes;
			} else {
				Node requestFoodNode = new Node("requestFood", n.getDepth() + 1, n, problem.getRequestResouce());
				Node requestMaterialsNode = new Node("requestMaterials", n.getDepth() + 1, n,
						problem.getRequestResouce());
				Node requestEnergyNode = new Node("requestEnergy", n.getDepth() + 1, n, problem.getRequestResouce());
				Node build1Node = new Node("build1", n.getDepth() + 1, n, problem.getBuild1Cost());
				Node build2Node = new Node("build2", n.getDepth() + 1, n, problem.getBuild2Cost());
				nodes.addFirst(requestFoodNode);
				nodes.addFirst(requestMaterialsNode);
				nodes.addFirst(requestEnergyNode);
				nodes.addFirst(build1Node);
				nodes.addFirst(build2Node);
				return nodes;
			}
		} else {
			this.l++;
			problem.reset(problem.getInitialState());
			nodes.removeAll(nodes);
			String stateString = setStringState(problem.getInitialProsperityLevel(), problem.getMoneySpent(),
					problem.getInitialFood(), problem.getInitialMaterials(), problem.getInitialEnergy());
			int[] stateArray = setArrayState(problem.getInitialProsperityLevel(), problem.getMoneySpent(),
					problem.getInitialFood(), problem.getInitialMaterials(), problem.getInitialEnergy());
			Node initialNode = new Node(stateString, stateArray, 0, problem.getMoneySpent(), 0);
			nodes.add(initialNode);
			return nodes;
		}
	}

	public void print(Deque<Node> nodes) {
		while (!nodes.isEmpty()) {
			if (nodes.peek().getParentNode() != null) {
				System.out.println(nodes.poll().getOperateString());
			}
		}
	}

	public void orderQueue(Deque<Node> nodes, String SearchMode) {
		ArrayList<Node> tempNodes = new ArrayList<Node>();
		while (!nodes.isEmpty()) {
			Node elementNode = nodes.peek();
			nodes.poll();
			tempNodes.add(elementNode);
		}

		if (SearchMode == "UC") {
			// System.out.println("At " + p);
			for (int j = 0; j < tempNodes.size(); j++) {
				Node minNode = tempNodes.get(j);
				int minIndex = j;
				for (int k = j + 1; k < tempNodes.size(); k++) {
					if (minNode.getRootPathCost() >= tempNodes.get(k).getRootPathCost()) {
						minNode = tempNodes.get(k);
						minIndex = k;
					}
				}
				// System.out.print(minNode.getOperateString() + " ,");
				nodes.add(minNode);
				tempNodes.remove(minIndex);
				if (tempNodes.size() > 0) {
					j--;
				}
			}

			// System.out.println("");
			// this.p++;
		} else if (SearchMode == "GR") {
			for (int j = 0; j < tempNodes.size(); j++) {
				Node minNode = tempNodes.get(j);
				int minIndex = j;
				for (int k = j + 1; k < tempNodes.size(); k++) {
					if (minNode.getRootPathCost() >= tempNodes.get(k).getHeuristicCost()) {
						minNode = tempNodes.get(k);
						minIndex = k;
					}
				}
				nodes.add(minNode);
				tempNodes.remove(minIndex);
				if (tempNodes.size() > 0) {
					j--;
				}
			}
		}
	}

	public boolean goalTest(LLAPProblem problem, Node n) {
		int goalState = n.getState()[0];
//		if (goalState >= 100 && problem.getInitialEnergy() != 0 && problem.getInitialFood() != 0
//				&& problem.getInitialMaterials() != 0 && problem.getMoneySpent() <= 100000) {
//			return true;
//		}
		if (goalState >= 100) {
			return true;
		}
		return false;
	}

	public Queue<Node> getPathNodes() {
		return pathNodes;
	}

	public void setPathNodes(Queue<Node> pathNodes) {
		this.pathNodes = pathNodes;
	}

	public Node getGoalNode() {
		return goalNode;
	}

	public void setGoalNode(Node goalNode) {
		this.goalNode = goalNode;
	}

	public void visualize(Queue<Node> path) {
		int size = path.size();
		while (!path.isEmpty()) {
			// Node currentNode = path.peek();
			if (path.peek().getParentNode() == null) {
				System.out.println("Root Node: " + path.peek().getStateString() + " ,Root Path Cost: "
						+ path.peek().getRootPathCost() + " ,Heuristic Cost: " + path.peek().getHeuristicCost()
						+ " ,At Depth: " + path.peek().getDepth());
			} else {
				System.out.println("Node: " + path.peek().getStateString() + " ,Root Path Cost: "
						+ path.peek().getRootPathCost() + " ,Heuristic Cost: " + path.peek().getHeuristicCost()
						+ " ,At Depth: " + path.peek().getDepth());
			}
			path.poll();
			// Node nextNode = path.peek();

			if (!path.isEmpty()) {
				if (path.peek().getParentNode() == null) {
					System.out.println(
							"__________________________________________________________________________________________________________________________________________________");
				} else {
					System.out.println("");
					System.out.println("|");
					System.out.println("| " + path.peek().getOperateString());
					System.out.println("|");
					System.out.println("v");
					System.out.println("");
				}
			}
		}
	}

	public String returnString(LLAPProblem problem, Queue<Node> path) {
		int size = path.size();

		String output = "";
		while (!path.isEmpty()) {
			// Node currentNode = path.peek();
			if (path.peek().getParentNode() == null) {
				path.poll();
				output += path.peek().getOperateString() + ",";
			} else {
				if (path.size() == 1) {
					output += path.peek().getOperateString();
				} else {
					output += path.peek().getOperateString() + ",";
				}
			}
		}
		output += ";" + problem.getMoneySpent() + ";" + size;
		if (success) {
			return output;
		} else {
			return "NOSOLUTION";
		}
		// return output;

	}

	public static void main(String[] args) {
		String initialState = "50;" + "22,22,22;" + "50,60,70;" + "30,2;19,1;15,1;" + "300,5,7,3,20;" + "500,8,6,3,40;";
		LLAPProblem problem = new LLAPProblem(initialState);
		GenericSearch xGenericSearch = new GenericSearch();
		xGenericSearch.GeneralSearch(problem, "bf");
//		if (xGenericSearch.success == true) {
//			System.out.println("Yeeehaw: " + xGenericSearch.goalNode.getDepth());
//			System.out.println("Yeeehaw: " + xGenericSearch.goalNode.getStateString());
//		} else {
//			System.out.println("Neehaw: " + xGenericSearch.goalNode.getDepth());
//		}
		xGenericSearch.visualize(xGenericSearch.pathNodes);
		// System.out.println(xGenericSearch.returnString(problem,
		// xGenericSearch.pathNodes));
	}

}
