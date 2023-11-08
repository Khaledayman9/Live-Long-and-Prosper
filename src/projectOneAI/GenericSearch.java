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

	public GenericSearch(LLAPProblem problem) {
		this.problem = problem;
		this.success = false;
		this.pathNodes = new LinkedList<Node>();
	}

	public void GeneralSearch(LLAPProblem problem, String QueueFun) {
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
//				n.setRootPathCost(problem.getMoneySpent());
				n.setStateString(setStringState(problem.getInitialProsperityLevel(), problem.getMoneySpent(),
						problem.getInitialFood(), problem.getInitialMaterials(), problem.getInitialEnergy()));
				n.setState(setArrayState(problem.getInitialProsperityLevel(), problem.getMoneySpent(),
						problem.getInitialFood(), problem.getInitialMaterials(), problem.getInitialEnergy()));
			}
			if (goalTest(n)) {
				success = true;
				this.goalNode = n;
				break;
			} else {
				nodes = expandPath(n, nodes, QueueFun);
			}
			// Visualization
			pathNodes.add(n);
		}
	}

	public String setStringState(int prosperityLevel, int moneySpent, int initialFood, int initialMaterials,
			int initialEnergy) {
		String result = "Prosperity level = " + prosperityLevel + " ,Money Spent: " + moneySpent + " ,Food: "
				+ initialFood + " ,Materials: " + initialMaterials + " ,Energy: " + initialEnergy + ".";
		return result;
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
			Node waitNode = new Node("wait", n.getDepth() + 1, n, problem.getWaitCost());
			Node build1Node = new Node("build1", n.getDepth() + 1, n, problem.getBuild1Cost());
			Node build2Node = new Node("build2", n.getDepth() + 1, n, problem.getBuild2Cost());
			if (QueueFun == "BF") {
				nodes.add(waitNode);
				nodes.add(build1Node);
				nodes.add(build2Node);
			} else if (QueueFun == "DF") {
				nodes.addFirst(waitNode);
				nodes.addFirst(build1Node);
				nodes.addFirst(build2Node);
			} else if (QueueFun == "UC") {
				nodes.add(waitNode);
				nodes.add(build1Node);
				nodes.add(build2Node);
				orderQueue(nodes);
			}

			return nodes;
		} else {
			Node requestFoodNode = new Node("requestFood", n.getDepth() + 1, n, problem.getRequestResouce());
			Node requestMaterialsNode = new Node("requestMaterials", n.getDepth() + 1, n, problem.getRequestResouce());
			Node requestEnergyNode = new Node("requestEnergy", n.getDepth() + 1, n, problem.getRequestResouce());
			Node build1Node = new Node("build1", n.getDepth() + 1, n, problem.getBuild1Cost());
			Node build2Node = new Node("build2", n.getDepth() + 1, n, problem.getBuild2Cost());
			if (QueueFun == "BF") {
				nodes.add(requestFoodNode);
				nodes.add(requestMaterialsNode);
				nodes.add(requestEnergyNode);
				nodes.add(build1Node);
				nodes.add(build2Node);
			} else if (QueueFun == "DF") {
				nodes.addFirst(requestFoodNode);
				nodes.addFirst(requestMaterialsNode);
				nodes.addFirst(requestEnergyNode);
				nodes.addFirst(build1Node);
				nodes.addFirst(build2Node);
			} else if (QueueFun == "UC") {
				nodes.add(requestFoodNode);
				nodes.add(requestMaterialsNode);
				nodes.add(requestEnergyNode);
				nodes.add(build1Node);
				nodes.add(build2Node);
				orderQueue(nodes);
			}
			return nodes;
		}
	}

	public void orderQueue(Deque<Node> nodes) {
		ArrayList<Node> tempNodes = new ArrayList<Node>();
		while (!nodes.isEmpty()) {
			tempNodes.add(nodes.poll());
		}
		for (int j = 0; j < tempNodes.size(); j++) {
			System.out.println(j);
			Node minNode = tempNodes.get(j);
			int minIndex = j;
			for (int k = j + 1; k < tempNodes.size(); k++) {
				if (minNode.getRootPathCost() > tempNodes.get(k).getRootPathCost()) {
					minNode = tempNodes.get(k);
					minIndex = k;
					System.out.println("at " + k + ", min is: " + minNode);
				}
			}
			nodes.add(minNode);
			tempNodes.remove(minIndex);
			if (minIndex != j) {
				j--;
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

	public void makeSureSorted(Deque<Node> nodes) {
		for (int i = 0; i < nodes.size(); i++) {
			System.out.println("First at " + i + "is: " + nodes.poll().getRootPathCost());
		}
	}

	public static void main(String[] args) throws IOException {
		LLAPProblem problem = new LLAPProblem();
		GenericSearch xGenericSearch = new GenericSearch(problem);
//		xGenericSearch.GeneralSearch(problem, "DF");
//		if (xGenericSearch.success == true) {
//			System.out.println("Yeeehaw: " + xGenericSearch.goalNode.getDepth());
//			System.out.println("Yeeehaw: " + xGenericSearch.goalNode.getStateString());
//		} else {
//			System.out.println("Neehaw: " + xGenericSearch.goalNode.getDepth());
//		}
		Deque<Node> nodes = new LinkedList<Node>();
		nodes.add(new Node("her", 0, null, 10));
		nodes.add(new Node("here", 0, null, 5));
		nodes.add(new Node("her2", 0, null, 8));
		nodes.add(new Node("her3", 0, null, 2));
		// xGenericSearch.makeSureSorted(nodes);
		xGenericSearch.orderQueue(nodes);
		xGenericSearch.makeSureSorted(nodes);
	}

}
