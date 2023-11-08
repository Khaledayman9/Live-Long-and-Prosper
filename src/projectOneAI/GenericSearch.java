package projectOneAI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class GenericSearch {
	LLAPProblem problem;
	private boolean success;
	private Node goalNode;
	Queue<Node> pathNodes;
	private int l;

	public GenericSearch(LLAPProblem problem) {
		this.problem = problem;
		this.success = false;
		this.l = 0;
		this.pathNodes = new LinkedList<Node>();
	}

	public void GeneralSearch(LLAPProblem problem, String QueueFun) throws IOException {
		if (QueueFun == "BF" || QueueFun == "DF" || QueueFun == "UC" || QueueFun == "AS1" || QueueFun == "AS2"
				|| QueueFun == "ID" || QueueFun == "GR1" || QueueFun == "GR2") {
			Deque<Node> nodes = new LinkedList<Node>();
			String stateString = setStringState(problem.getInitialProsperityLevel(), problem.getMoneySpent(),
					problem.getInitialFood(), problem.getInitialMaterials(), problem.getInitialEnergy());
			int[] stateArray = setArrayState(problem.getInitialProsperityLevel(), problem.getMoneySpent(),
					problem.getInitialFood(), problem.getInitialMaterials(), problem.getInitialEnergy());
			Node initialNode = new Node(stateString, stateArray, 0, problem.getMoneySpent(), 0);
			nodes.add(initialNode);
			while (true) {
				if (nodes.isEmpty()) {
					goalNode = null;
					break;
				}
				Node n = nodes.poll();
				if (!(n.getParentNode() == null)) {
					if (n.getOperateString() == "wait") {
						problem.WAIT();
					} else if (n.getOperateString() == "requestFood") {
						problem.RequestFood();
					}
					if (n.getOperateString() == "requestMaterials") {
						problem.RequestMaterials();
					}
					if (n.getOperateString() == "requestEnergy") {
						problem.RequestEnergy();
					}
					if (n.getOperateString() == "build1") {
						problem.Build1();
					}
					if (n.getOperateString() == "build2") {
						problem.Build2();
					}
					// n.setRootPathCost(problem.getMoneySpent());
					if (problem.getInitialEnergy() <= 0 && problem.getInitialFood() <= 0
							&& problem.getInitialMaterials() <= 0) {
						n.setStateString(
								setStringState(problem.getInitialProsperityLevel(), problem.getMoneySpent(), 0, 0, 0));
						n.setState(
								setArrayState(problem.getInitialProsperityLevel(), problem.getMoneySpent(), 0, 0, 0));
					} else {
						n.setStateString(setStringState(problem.getInitialProsperityLevel(), problem.getMoneySpent(),
								problem.getInitialFood(), problem.getInitialMaterials(), problem.getInitialEnergy()));
						n.setState(setArrayState(problem.getInitialProsperityLevel(), problem.getMoneySpent(),
								problem.getInitialFood(), problem.getInitialMaterials(), problem.getInitialEnergy()));
					}
				}
				pathNodes.add(n);
				if (n.getState()[2] <= 0) {
					this.setSuccess(false);
					break;
				} else if (goalTest(n)) {
					this.setSuccess(true);
					this.goalNode = n;
					break;
				} else {
					if (QueueFun == "ID") {
						nodes = expandID(n, nodes);
					} else {
						nodes = expandPath(n, nodes, QueueFun);
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

	public Deque<Node> expandPath(Node n, Deque<Node> nodes, String QueueFun) {
		if (problem.getDelay() != 0) {
			if (QueueFun == "BF") {
				Node waitNode = new Node("wait", n.getDepth() + 1, n, problem.getWaitCost());
				Node build1Node = new Node("build1", n.getDepth() + 1, n, problem.getBuild1Cost());
				Node build2Node = new Node("build2", n.getDepth() + 1, n, problem.getBuild2Cost());
				nodes.add(waitNode);
				nodes.add(build1Node);
				nodes.add(build2Node);
			} else if (QueueFun == "DF") {
				Node waitNode = new Node("wait", n.getDepth() + 1, n, problem.getWaitCost());
				Node build1Node = new Node("build1", n.getDepth() + 1, n, problem.getBuild1Cost());
				Node build2Node = new Node("build2", n.getDepth() + 1, n, problem.getBuild2Cost());
				nodes.addFirst(waitNode);
				nodes.addFirst(build1Node);
				nodes.addFirst(build2Node);
			} else if (QueueFun == "UC") {
				Node waitNode = new Node("wait", n.getDepth() + 1, n, problem.getWaitCost());
				Node build1Node = new Node("build1", n.getDepth() + 1, n, problem.getBuild1Cost());
				Node build2Node = new Node("build2", n.getDepth() + 1, n, problem.getBuild2Cost());
				nodes.add(waitNode);
				nodes.add(build1Node);
				nodes.add(build2Node);
				orderQueue(nodes, "UC");
			} else if (QueueFun == "GR1") {
				Node waitNode = new Node("wait", n.getDepth() + 1, n, problem.getWaitCost(),
						problem.remainingProsperityHeuristic("wait"));
				Node build1Node = new Node("build1", n.getDepth() + 1, n, problem.getBuild1Cost(),
						problem.remainingProsperityHeuristic("Build1"));
				Node build2Node = new Node("build2", n.getDepth() + 1, n, problem.getBuild2Cost(),
						problem.remainingProsperityHeuristic("Build2"));
				nodes.add(waitNode);
				nodes.add(build1Node);
				nodes.add(build2Node);
				orderQueue(nodes, "GR");
			} else if (QueueFun == "GR2") {
				Node waitNode = new Node("wait", n.getDepth() + 1, n, problem.getWaitCost(),
						problem.resourceCostHeuristic());
				Node build1Node = new Node("build1", n.getDepth() + 1, n, problem.getBuild1Cost(),
						problem.resourceCostHeuristic());
				Node build2Node = new Node("build2", n.getDepth() + 1, n, problem.getBuild2Cost(),
						problem.resourceCostHeuristic());
				nodes.add(waitNode);
				nodes.add(build1Node);
				nodes.add(build2Node);
				orderQueue(nodes, "GR");
			}

			return nodes;
		} else {
			if (QueueFun == "BF") {
				Node requestFoodNode = new Node("requestFood", n.getDepth() + 1, n, problem.getRequestResouce());
				Node requestMaterialsNode = new Node("requestMaterials", n.getDepth() + 1, n,
						problem.getRequestResouce());
				Node requestEnergyNode = new Node("requestEnergy", n.getDepth() + 1, n, problem.getRequestResouce());
				Node build1Node = new Node("build1", n.getDepth() + 1, n, problem.getBuild1Cost());
				Node build2Node = new Node("build2", n.getDepth() + 1, n, problem.getBuild2Cost());
				nodes.add(requestFoodNode);
				nodes.add(requestMaterialsNode);
				nodes.add(requestEnergyNode);
				nodes.add(build1Node);
				nodes.add(build2Node);
			} else if (QueueFun == "DF") {
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
			} else if (QueueFun == "UC") {
				Node requestFoodNode = new Node("requestFood", n.getDepth() + 1, n, problem.getRequestResouce());
				Node requestMaterialsNode = new Node("requestMaterials", n.getDepth() + 1, n,
						problem.getRequestResouce());
				Node requestEnergyNode = new Node("requestEnergy", n.getDepth() + 1, n, problem.getRequestResouce());
				Node build1Node = new Node("build1", n.getDepth() + 1, n, problem.getBuild1Cost());
				Node build2Node = new Node("build2", n.getDepth() + 1, n, problem.getBuild2Cost());
				nodes.add(requestFoodNode);
				nodes.add(requestMaterialsNode);
				nodes.add(requestEnergyNode);
				nodes.add(build1Node);
				nodes.add(build2Node);
				orderQueue(nodes, "UC");
			} else if (QueueFun == "GR1") {
				Node requestFoodNode = new Node("requestFood", n.getDepth() + 1, n, problem.getRequestResouce(),
						problem.remainingProsperityHeuristic("Request"));
				Node requestMaterialsNode = new Node("requestMaterials", n.getDepth() + 1, n,
						problem.getRequestResouce(), problem.remainingProsperityHeuristic("Request"));
				Node requestEnergyNode = new Node("requestEnergy", n.getDepth() + 1, n, problem.getRequestResouce(),
						problem.remainingProsperityHeuristic("Request"));
				Node build1Node = new Node("build1", n.getDepth() + 1, n, problem.getBuild1Cost(),
						problem.remainingProsperityHeuristic("Build1"));
				Node build2Node = new Node("build2", n.getDepth() + 1, n, problem.getBuild2Cost(),
						problem.remainingProsperityHeuristic("Build2"));
				nodes.add(requestFoodNode);
				nodes.add(requestMaterialsNode);
				nodes.add(requestEnergyNode);
				nodes.add(build1Node);
				nodes.add(build2Node);
				orderQueue(nodes, "GR");
			} else if (QueueFun == "GR2") {
				Node requestFoodNode = new Node("requestFood", n.getDepth() + 1, n, problem.getRequestResouce(),
						problem.resourceCostHeuristic());
				Node requestMaterialsNode = new Node("requestMaterials", n.getDepth() + 1, n,
						problem.getRequestResouce(), problem.resourceCostHeuristic());
				Node requestEnergyNode = new Node("requestEnergy", n.getDepth() + 1, n, problem.getRequestResouce(),
						problem.resourceCostHeuristic());
				Node build1Node = new Node("build1", n.getDepth() + 1, n, problem.getBuild1Cost(),
						problem.resourceCostHeuristic());
				Node build2Node = new Node("build2", n.getDepth() + 1, n, problem.getBuild2Cost(),
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

	public Deque<Node> expandID(Node n, Deque<Node> nodes) throws IOException {
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
			problem.reset();
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

	public void orderQueue(Deque<Node> nodes, String SearchMode) {
		ArrayList<Node> tempNodes = new ArrayList<Node>();
		while (!nodes.isEmpty()) {
			tempNodes.add(nodes.poll());
		}

		if (SearchMode == "UC") {
			for (int j = 0; j < tempNodes.size(); j++) {
				Node minNode = tempNodes.get(j);
				int minIndex = j;
				for (int k = j + 1; k < tempNodes.size(); k++) {
					if (minNode.getRootPathCost() >= tempNodes.get(k).getRootPathCost()) {
						minNode = tempNodes.get(k);
						minIndex = k;
					}
				}
				nodes.add(minNode);
				tempNodes.remove(minIndex);
				if (minIndex != j) {
					j--;
				}
			}
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
				if (minIndex != j) {
					j--;
				}
			}
		}
	}

	public boolean goalTest(Node n) {
		int goalState = n.getState()[0];
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
		int i = 0;
		while (!path.isEmpty()) {
			Node currentNode = path.peek();
			if (i == 0) {
				System.out.println("Root Node: " + path.peek().getStateString() + " ,Root Path Cost: "
						+ path.peek().getRootPathCost() + " ,Heuristic Cost: " + path.peek().getHeuristicCost()
						+ " ,At Depth: " + path.peek().getDepth());
			} else {
				System.out.println("Node: " + path.peek().getStateString() + " ,Root Path Cost: "
						+ path.peek().getRootPathCost() + " ,Heuristic Cost: " + path.peek().getHeuristicCost()
						+ " ,At Depth: " + path.peek().getDepth());
			}
			path.poll();
			Node nextNode = path.peek();
			if (!path.isEmpty()) {
				System.out.println("");
				System.out.println("|");
				System.out.println("| " + path.peek().getOperateString());
				System.out.println("|");
				System.out.println("v");
				System.out.println("");
			}
			i++;
		}
	}

	public static void main(String[] args) throws IOException {
		LLAPProblem problem = new LLAPProblem();
		GenericSearch xGenericSearch = new GenericSearch(problem);
		xGenericSearch.GeneralSearch(problem, "GR2");
//		if (xGenericSearch.success == true) {
//			System.out.println("Yeeehaw: " + xGenericSearch.goalNode.getDepth());
//			System.out.println("Yeeehaw: " + xGenericSearch.goalNode.getStateString());
//		} else {
//			System.out.println("Neehaw: " + xGenericSearch.goalNode.getDepth());
//		}
		xGenericSearch.visualize(xGenericSearch.pathNodes);
	}

}
