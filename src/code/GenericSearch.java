package code;

import java.util.ArrayList;
import java.util.LinkedList;

public class GenericSearch {
	private LinkedList<Node> nodes;
	private LinkedList<Node> uniqueValues;
	private Node goalNode;
	private boolean success;
	private int p;
	private int l;
	private String QueueFun;
	private boolean visualize;

	public GenericSearch() {
		this.nodes = new LinkedList<Node>();
		this.uniqueValues = new LinkedList<Node>();
		this.goalNode = null;
		this.success = false;
		this.p = 1;
		this.l = 0;
		this.QueueFun = "";
	}

	public void GeneralSearch(LLAPProblem problem, String QueueFun, Boolean visualize) {
		if (QueueFun.toLowerCase().equals("bf") || QueueFun.toLowerCase().equals("df")
				|| QueueFun.toLowerCase().equals("uc") || QueueFun.toLowerCase().equals("as1")
				|| QueueFun.toLowerCase().equals("as2") || QueueFun.toLowerCase().equals("id")
				|| QueueFun.toLowerCase().equals("gr1") || QueueFun.toLowerCase().equals("gr2")) {
			this.visualize = visualize;
			this.QueueFun = QueueFun;
			Node xNode = new Node(problem.getInitialProsperityLevel(), problem.getMoneySpent(),
					problem.getInitialFood(), problem.getInitialMaterials(), problem.getInitialEnergy(), 0,
					problem.getMoneySpent(), -1, "Root", 0);
			if (this.QueueFun.toLowerCase().equals("gr1") || this.QueueFun.toLowerCase().equals("as1")) {
				xNode.setHeuristicCost(heuristicFunction1(xNode, problem));
			} else if (this.QueueFun.toLowerCase().equals("gr2") || this.QueueFun.toLowerCase().equals("as2")) {
				xNode.setHeuristicCost(heuristicFunction2(xNode, problem));
			}
			nodes.add(xNode);
			uniqueValues.add(xNode);
			while (true) {
				if (success)
					break;
				if (nodes.isEmpty()) {
					this.goalNode = null;
					this.success = false;
					break;
				}
				if (visualize) {
					printQueue(nodes);
				}
				Node n = nodes.poll();
				if (n.getProsperity() >= 100) {
					this.goalNode = n;
					this.success = true;
					break;
				} else if (QueueFun.toLowerCase().equals("bf")) {
					expandBF(n, problem);
				} else if (QueueFun.toLowerCase().equals("df")) {
					expandDF(n, problem);
				} else if (QueueFun.toLowerCase().equals("uc")) {
					expandBF(n, problem);
					orderQueue(nodes, "UC");
				} else if (QueueFun.toLowerCase().equals("id")) {
					expandID(n, problem);
				} else if (QueueFun.toLowerCase().equals("gr1") || QueueFun.toLowerCase().equals("gr2")) {
					expandBF(n, problem);
					orderQueue(nodes, "GR");
				} else if (QueueFun.toLowerCase().equals("as1") || QueueFun.toLowerCase().equals("as2")) {
					expandBF(n, problem);
					orderQueue(nodes, "AS");
				}

			}
		} else {
			System.out.println("You have not enetered a Valid Search Algorithm.");
			System.out.println("The options are: [BF, DF, UC, ID, AS1, AS2, GR1, GR2]");
			return;
		}
	}

	public void orderQueue(LinkedList<Node> nodes, String SearchMode) {
		ArrayList<Node> tempNodes = new ArrayList<Node>();
		while (!nodes.isEmpty()) {
			Node elementNode = nodes.peek();
			nodes.poll();
			tempNodes.add(elementNode);
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
				if (tempNodes.size() > 0) {
					j--;
				}
			}
		} else if (SearchMode == "GR") {
			for (int j = 0; j < tempNodes.size(); j++) {
				Node minNode = tempNodes.get(j);
				int minIndex = j;
				for (int k = j + 1; k < tempNodes.size(); k++) {
					if (minNode.getHeuristicCost() >= tempNodes.get(k).getHeuristicCost()) {
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
		} else if (SearchMode == "AS") {
			for (int j = 0; j < tempNodes.size(); j++) {
				Node minNode = tempNodes.get(j);
				int minIndex = j;
				for (int k = j + 1; k < tempNodes.size(); k++) {
					if ((minNode.getHeuristicCost() + minNode.getRootPathCost()) >= (tempNodes.get(k).getHeuristicCost()
							+ tempNodes.get(k).getRootPathCost())) {
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

	public void expandID(Node n, LLAPProblem problem) {
		if (n.getDepth() < l) {
			expandDF(n, problem);
		} else {
			this.l++;
			nodes.removeAll(nodes);
			uniqueValues.removeAll(uniqueValues);
			GeneralSearch(problem, "id", this.visualize);
		}

	}

	public void expandBF(Node n, LLAPProblem problem) {
		if (n.getDelay() > 0) {
			if (CheckBuild1(n, problem)) {
				Node Node = buildNode1(n, problem);
				if (!exists(Node, uniqueValues)) {
					uniqueValues.add(Node);
					nodes.add(Node);
				}
			}
			if (CheckBuild2(n, problem)) {
				Node Node = buildNode2(n, problem);
				if (!exists(Node, uniqueValues)) {
					uniqueValues.add(Node);
					nodes.add(Node);
				}
			}
			if (CheckWait(n, problem)) {
				Node Node = waitNode(n, problem);
				if (!exists(Node, uniqueValues)) {
					uniqueValues.add(Node);
					nodes.add(Node);
				}
			}
		} else {
			if (CheckBuild1(n, problem)) {
				Node Node = buildNode1(n, problem);
				if (!exists(Node, uniqueValues)) {
					uniqueValues.add(Node);
					nodes.add(Node);
				}
			}
			if (CheckBuild2(n, problem)) {
				Node Node = buildNode2(n, problem);
				if (!exists(Node, uniqueValues)) {
					uniqueValues.add(Node);
					nodes.add(Node);
				}
			}
			if (CheckRequestFood(n, problem)) {
				Node Node = requestFoodNode(n, problem);
				if (!exists(Node, uniqueValues)) {
					uniqueValues.add(Node);
					nodes.add(Node);
				}
			}
			if (CheckRequestEnergy(n, problem)) {
				Node Node = requestEnergyNode(n, problem);
				if (!exists(Node, uniqueValues)) {
					uniqueValues.add(Node);
					nodes.add(Node);
				}
			}
			if (CheckRequestMaterials(n, problem)) {
				Node Node = requestMaterialsNode(n, problem);
				if (!exists(Node, uniqueValues)) {
					uniqueValues.add(Node);
					nodes.add(Node);
				}
			}
		}
	}

	public void expandDF(Node n, LLAPProblem problem) {
		if (n.getDelay() > 0) {
			if (CheckBuild1(n, problem)) {
				Node Node = buildNode1(n, problem);
				if (!exists(Node, uniqueValues)) {
					uniqueValues.add(Node);
					nodes.addFirst(Node);
				}
			}
			if (CheckBuild2(n, problem)) {
				Node Node = buildNode2(n, problem);
				if (!exists(Node, uniqueValues)) {
					uniqueValues.add(Node);
					nodes.addFirst(Node);
				}
			}
			if (CheckWait(n, problem)) {
				Node Node = waitNode(n, problem);
				if (!exists(Node, uniqueValues)) {
					uniqueValues.add(Node);
					nodes.addFirst(Node);
				}
			}
		} else {
			if (CheckBuild1(n, problem)) {
				Node Node = buildNode1(n, problem);
				if (!exists(Node, uniqueValues)) {
					uniqueValues.add(Node);
					nodes.addFirst(Node);
				}
			}
			if (CheckBuild2(n, problem)) {
				Node Node = buildNode2(n, problem);
				if (!exists(Node, uniqueValues)) {
					uniqueValues.add(Node);
					nodes.addFirst(Node);
				}
			}
			if (CheckRequestFood(n, problem)) {
				Node Node = requestFoodNode(n, problem);
				if (!exists(Node, uniqueValues)) {
					uniqueValues.add(Node);
					nodes.addFirst(Node);
				}
			}
			if (CheckRequestEnergy(n, problem)) {
				Node Node = requestEnergyNode(n, problem);
				if (!exists(Node, uniqueValues)) {
					uniqueValues.add(Node);
					nodes.addFirst(Node);
				}
			}
			if (CheckRequestMaterials(n, problem)) {
				Node Node = requestMaterialsNode(n, problem);
				if (!exists(Node, uniqueValues)) {
					uniqueValues.add(Node);
					nodes.addFirst(Node);
				}
			}
		}
	}

	public void printQueue(LinkedList<Node> nodes) {
		ArrayList<Node> tempNodes = new ArrayList<Node>();
		System.out.println("Expansion Queue At Time " + this.p + " Is the Following: ");
		while (!nodes.isEmpty()) {
			Node elementNode = nodes.peek();
			if (elementNode.getParentNode() == null) {
				System.out.println("[Root Node]\nState -> (Prosperity:" + elementNode.getProsperity() + ", Money Spent:"
						+ elementNode.getMoneySpent() + ", Food:" + elementNode.getFood() + ", Materials:"
						+ elementNode.getMaterials() + ", Energy:" + elementNode.getEnergy() + ")\nDepth: "
						+ elementNode.getDepth() + " - Path Cost: " + elementNode.getRootPathCost()
						+ " - Heuristic Cost: " + elementNode.getHeuristicCost());
				System.out.println("");
			} else {
				System.out.println(elementNode.toString());
				System.out.println("");
			}
			nodes.poll();
			tempNodes.add(elementNode);
		}
		System.out.println("_____________________________________________________________________________________");
		while (!tempNodes.isEmpty()) {
			Node Element = tempNodes.remove(0);
			nodes.add(Element);
		}
		System.out.println("");
		this.p++;
	}

	public int heuristicFunction1(Node node, LLAPProblem problem) {
		int val1 = (problem.getPriceBUILD1() + problem.getUnitPriceEnergy() * problem.getEnergyUseBUILD1()
				+ problem.getUnitPriceFood() * problem.getFoodUseBUILD1()
				+ problem.getUnitPriceMaterials() * problem.getMaterialsUseBUILD1());
		int val2 = (problem.getPriceBUILD2() + problem.getUnitPriceEnergy() * problem.getEnergyUseBUILD2()
				+ problem.getUnitPriceFood() * problem.getFoodUseBUILD2()
				+ problem.getUnitPriceMaterials() * problem.getMaterialsUseBUILD2());
		int val3 = problem.getUnitPriceEnergy() + problem.getUnitPriceFood() + problem.getUnitPriceMaterials();
		int result = ((val1 + val2 + 4 * val3) / 6) * (100 - node.getProsperity());
		return result;
	}

	public int heuristicFunction2(Node node, LLAPProblem problem) {
		int remainingBudget = problem.getBudget() - node.getMoneySpent();
		int result = (remainingBudget * (100 - node.getProsperity()));
		return result;
	}

	public Node buildNode1(Node n, LLAPProblem problem) {
		Node xNode = new Node(n.getProsperity() + problem.getProsperityBUILD1(),
				n.getMoneySpent()
						+ (problem.getPriceBUILD1() + problem.getUnitPriceEnergy() * problem.getEnergyUseBUILD1()
								+ problem.getUnitPriceFood() * problem.getFoodUseBUILD1()
								+ problem.getUnitPriceMaterials() * problem.getMaterialsUseBUILD1()),
				n.getFood() - problem.getFoodUseBUILD1(), n.getMaterials() - problem.getMaterialsUseBUILD1(),
				n.getEnergy() - problem.getEnergyUseBUILD1(), "BUILD1", n.getDepth() + 1, n,
				n.getMoneySpent()
						+ (problem.getPriceBUILD1() + problem.getUnitPriceEnergy() * problem.getEnergyUseBUILD1()
								+ problem.getUnitPriceFood() * problem.getFoodUseBUILD1()
								+ problem.getUnitPriceMaterials() * problem.getMaterialsUseBUILD1()),
				-1, "None", 0);
		if (xNode.getProsperity() > 100) {
			xNode.setProsperity(100);
		}
		if (n.getDelay() > 0) {
			xNode.setDelay(n.getDelay() - 1);
			xNode.setType(n.getType());
		} else if (n.getDelay() == 0) {
			if (n.getType() == "food") {
				xNode.setFood(n.getFood() + problem.getAmountRequestFood());
				if (xNode.getFood() >= 50) {
					xNode.setFood(50 - problem.getFoodUseBUILD1());
				} else {
					xNode.setFood(n.getFood() - problem.getFoodUseBUILD1() + problem.getAmountRequestFood());
				}
			} else if (n.getType() == "energy") {
				xNode.setEnergy(n.getEnergy() + problem.getAmountRequestEnergy());
				if (xNode.getEnergy() >= 50) {
					xNode.setEnergy(50 - problem.getEnergyUseBUILD1());
				} else {
					xNode.setEnergy(n.getEnergy() - problem.getEnergyUseBUILD1() + problem.getAmountRequestEnergy());
				}
			} else if (n.getType() == "materials") {
				xNode.setMaterials(n.getMaterials() + problem.getAmountRequestMaterials());
				if (xNode.getMaterials() >= 50) {
					xNode.setMaterials(50 - problem.getMaterialsUseBUILD1());
				} else {
					xNode.setMaterials(
							n.getMaterials() - problem.getMaterialsUseBUILD1() + problem.getAmountRequestMaterials());
				}
			}
		}
		if (this.QueueFun.toLowerCase().equals("gr1") || this.QueueFun.toLowerCase().equals("as1")) {
			xNode.setHeuristicCost(heuristicFunction1(xNode, problem));
		} else if (this.QueueFun.toLowerCase().equals("gr2") || this.QueueFun.toLowerCase().equals("as2")) {
			xNode.setHeuristicCost(heuristicFunction2(xNode, problem));
		}

		return xNode;
	}

	public Node buildNode2(Node n, LLAPProblem problem) {
		Node xNode = new Node(n.getProsperity() + problem.getProsperityBUILD2(),
				n.getMoneySpent()
						+ (problem.getPriceBUILD2() + problem.getUnitPriceEnergy() * problem.getEnergyUseBUILD2()
								+ problem.getUnitPriceFood() * problem.getFoodUseBUILD2()
								+ problem.getUnitPriceMaterials() * problem.getMaterialsUseBUILD2()),
				n.getFood() - problem.getFoodUseBUILD2(), n.getMaterials() - problem.getMaterialsUseBUILD2(),
				n.getEnergy() - problem.getEnergyUseBUILD2(), "BUILD2", n.getDepth() + 1, n,
				n.getMoneySpent()
						+ (problem.getPriceBUILD2() + problem.getUnitPriceEnergy() * problem.getEnergyUseBUILD2()
								+ problem.getUnitPriceFood() * problem.getFoodUseBUILD2()
								+ problem.getUnitPriceMaterials() * problem.getMaterialsUseBUILD2()),
				-1, "None", 0);
		if (xNode.getProsperity() > 100) {
			xNode.setProsperity(100);
		}
		if (n.getDelay() > 0) {
			xNode.setDelay(n.getDelay() - 1);
			xNode.setType(n.getType());
		} else if (n.getDelay() == 0) {
			if (n.getType() == "food") {
				xNode.setFood(n.getFood() + problem.getAmountRequestFood());
				if (xNode.getFood() >= 50) {
					xNode.setFood(50 - problem.getFoodUseBUILD2());
				} else {
					xNode.setFood(n.getFood() - problem.getFoodUseBUILD2() + problem.getAmountRequestFood());
				}
			} else if (n.getType() == "energy") {
				xNode.setEnergy(n.getEnergy() + problem.getAmountRequestEnergy());
				if (xNode.getEnergy() >= 50) {
					xNode.setEnergy(50 - problem.getEnergyUseBUILD2());
				} else {
					xNode.setEnergy(n.getEnergy() - problem.getEnergyUseBUILD2() + problem.getAmountRequestEnergy());
				}
			} else if (n.getType() == "materials") {
				xNode.setMaterials(n.getMaterials() + problem.getAmountRequestMaterials());
				if (xNode.getMaterials() >= 50) {
					xNode.setMaterials(50 - problem.getMaterialsUseBUILD2());
				} else {
					xNode.setMaterials(
							n.getMaterials() - problem.getMaterialsUseBUILD2() + problem.getAmountRequestMaterials());
				}
			}

		}
		if (this.QueueFun.toLowerCase().equals("gr1") || this.QueueFun.toLowerCase().equals("as1")) {
			xNode.setHeuristicCost(heuristicFunction1(xNode, problem));
		} else if (this.QueueFun.toLowerCase().equals("gr2") || this.QueueFun.toLowerCase().equals("as2")) {
			xNode.setHeuristicCost(heuristicFunction2(xNode, problem));
		}
		return xNode;
	}

	public Node requestFoodNode(Node n, LLAPProblem problem) {
		Node xNode = new Node(n.getProsperity(),
				(n.getMoneySpent() + problem.getUnitPriceEnergy() + problem.getUnitPriceFood()
						+ problem.getUnitPriceMaterials()),
				n.getFood() - 1, n.getMaterials() - 1, n.getEnergy() - 1, "RequestFood", n.getDepth() + 1, n,
				(n.getMoneySpent() + problem.getUnitPriceEnergy() + problem.getUnitPriceFood()
						+ problem.getUnitPriceMaterials()),
				problem.getDelayRequestFood(), "food", 0);
		if (n.getDelay() == 0) {
			if (n.getType() == "food") {
				xNode.setFood(n.getFood() + problem.getAmountRequestFood());
				if (xNode.getFood() >= 50) {
					xNode.setFood(49);
				} else {
					xNode.setFood(n.getFood() + problem.getAmountRequestFood() - 1);
				}
			} else if (n.getType() == "energy") {
				xNode.setEnergy(n.getEnergy() + problem.getAmountRequestEnergy());
				if (xNode.getEnergy() >= 50) {
					xNode.setEnergy(49);
				} else {
					xNode.setEnergy(n.getEnergy() + problem.getAmountRequestEnergy() - 1);
				}
			} else if (n.getType() == "materials") {
				xNode.setMaterials(n.getMaterials() + problem.getAmountRequestMaterials());
				if (xNode.getMaterials() >= 50) {
					xNode.setMaterials(49);
				} else {
					xNode.setMaterials(n.getMaterials() + problem.getAmountRequestMaterials() - 1);
				}
			}
		}
		if (this.QueueFun.toLowerCase().equals("gr1") || this.QueueFun.toLowerCase().equals("as1")) {
			xNode.setHeuristicCost(heuristicFunction1(xNode, problem));
		} else if (this.QueueFun.toLowerCase().equals("gr2") || this.QueueFun.toLowerCase().equals("as2")) {
			xNode.setHeuristicCost(heuristicFunction2(xNode, problem));
		}
		return xNode;
	}

	public Node requestEnergyNode(Node n, LLAPProblem problem) {
		Node xNode = new Node(n.getProsperity(),
				(n.getMoneySpent() + problem.getUnitPriceEnergy() + problem.getUnitPriceFood()
						+ problem.getUnitPriceMaterials()),
				n.getFood() - 1, n.getMaterials() - 1, n.getEnergy() - 1, "RequestEnergy", n.getDepth() + 1, n,
				(n.getMoneySpent() + problem.getUnitPriceEnergy() + problem.getUnitPriceFood()
						+ problem.getUnitPriceMaterials()),
				problem.getDelayRequestEnergy(), "energy", 0);

		if (n.getDelay() == 0) {
			if (n.getType() == "food") {
				xNode.setFood(n.getFood() + problem.getAmountRequestFood());
				if (xNode.getFood() >= 50) {
					xNode.setFood(49);
				} else {
					xNode.setFood(n.getFood() + problem.getAmountRequestFood() - 1);
				}
			} else if (n.getType() == "energy") {
				xNode.setEnergy(n.getEnergy() + problem.getAmountRequestEnergy());
				if (xNode.getEnergy() >= 50) {
					xNode.setEnergy(49);
				} else {
					xNode.setEnergy(n.getEnergy() + problem.getAmountRequestEnergy() - 1);
				}
			} else if (n.getType() == "materials") {
				xNode.setMaterials(n.getMaterials() + problem.getAmountRequestMaterials());
				if (xNode.getMaterials() >= 50) {
					xNode.setMaterials(49);
				} else {
					xNode.setMaterials(n.getMaterials() + problem.getAmountRequestMaterials() - 1);
				}
			}
		}
		if (this.QueueFun.toLowerCase().equals("gr1") || this.QueueFun.toLowerCase().equals("as1")) {
			xNode.setHeuristicCost(heuristicFunction1(xNode, problem));
		} else if (this.QueueFun.toLowerCase().equals("gr2") || this.QueueFun.toLowerCase().equals("as2")) {
			xNode.setHeuristicCost(heuristicFunction2(xNode, problem));
		}
		return xNode;
	}

	public Node requestMaterialsNode(Node n, LLAPProblem problem) {
		Node xNode = new Node(n.getProsperity(),
				(n.getMoneySpent() + problem.getUnitPriceEnergy() + problem.getUnitPriceFood()
						+ problem.getUnitPriceMaterials()),
				n.getFood() - 1, n.getMaterials() - 1, n.getEnergy() - 1, "RequestMaterials", n.getDepth() + 1, n,
				(n.getMoneySpent() + problem.getUnitPriceEnergy() + problem.getUnitPriceFood()
						+ problem.getUnitPriceMaterials()),
				problem.getDelayRequestMaterials(), "materials", 0);
		if (n.getDelay() == 0) {
			if (n.getType() == "food") {
				xNode.setFood(n.getFood() + problem.getAmountRequestFood());
				if (xNode.getFood() >= 50) {
					xNode.setFood(49);
				} else {
					xNode.setFood(n.getFood() + problem.getAmountRequestFood() - 1);
				}
			} else if (n.getType() == "energy") {
				xNode.setEnergy(n.getEnergy() + problem.getAmountRequestEnergy());
				if (xNode.getEnergy() >= 50) {
					xNode.setEnergy(49);
				} else {
					xNode.setEnergy(n.getEnergy() + problem.getAmountRequestEnergy() - 1);
				}
			} else if (n.getType() == "materials") {
				xNode.setMaterials(n.getMaterials() + problem.getAmountRequestMaterials());
				if (xNode.getMaterials() >= 50) {
					xNode.setMaterials(49);
				} else {
					xNode.setMaterials(n.getMaterials() + problem.getAmountRequestMaterials() - 1);
				}
			}
		}
		if (this.QueueFun.toLowerCase().equals("gr1") || this.QueueFun.toLowerCase().equals("as1")) {
			xNode.setHeuristicCost(heuristicFunction1(xNode, problem));
		} else if (this.QueueFun.toLowerCase().equals("gr2") || this.QueueFun.toLowerCase().equals("as2")) {
			xNode.setHeuristicCost(heuristicFunction2(xNode, problem));
		}
		return xNode;
	}

	public Node waitNode(Node n, LLAPProblem problem) {
		Node xNode = new Node(n.getProsperity(),
				(n.getMoneySpent() + problem.getUnitPriceEnergy() + problem.getUnitPriceFood()
						+ problem.getUnitPriceMaterials()),
				n.getFood() - 1, n.getMaterials() - 1, n.getEnergy() - 1, "WAIT", n.getDepth() + 1, n,
				(n.getMoneySpent() + problem.getUnitPriceEnergy() + problem.getUnitPriceFood()
						+ problem.getUnitPriceMaterials()),
				n.getDelay() - 1, n.getType(), 0);

		if (this.QueueFun.toLowerCase().equals("gr1") || this.QueueFun.toLowerCase().equals("as1")) {
			xNode.setHeuristicCost(heuristicFunction1(xNode, problem));
		} else if (this.QueueFun.toLowerCase().equals("gr2") || this.QueueFun.toLowerCase().equals("as2")) {
			xNode.setHeuristicCost(heuristicFunction2(xNode, problem));
		}
		return xNode;
	}

	public boolean CheckRequestFood(Node n, LLAPProblem problem) {
		if ((n.getFood() > problem.getFoodUseBUILD1() && n.getFood() > problem.getFoodUseBUILD2())) {
			return false;
		} else if (n.getFood() > n.getMaterials() && n.getFood() > n.getEnergy()) {
			return false;
		} else if (n.getType() == "food") {
			return false;
		} else {
			int newMoneySpent = n.getMoneySpent() + problem.getUnitPriceEnergy() + problem.getUnitPriceFood()
					+ problem.getUnitPriceMaterials();
			int newFood = n.getFood() - 1;
			int newMaterials = n.getMaterials() - 1;
			int newEnergy = n.getEnergy() - 1;
			if (n.getDelay() == 0) {
				if (n.getType() == "food") {
					newFood = n.getFood() + problem.getAmountRequestFood();
					if (newFood >= 50) {
						newFood = 49;
					} else {
						newFood = n.getFood() + problem.getAmountRequestFood() - 1;
					}
				} else if (n.getType() == "energy") {
					newEnergy = n.getEnergy() + problem.getAmountRequestEnergy();
					if (newEnergy >= 50) {
						newEnergy = 49;
					} else {
						newEnergy = n.getEnergy() + problem.getAmountRequestEnergy() - 1;
					}
				} else if (n.getType() == "materials") {
					newMaterials = n.getMaterials() + problem.getAmountRequestMaterials();
					if (newMaterials >= 50) {
						newMaterials = 49;
					} else {
						newMaterials = n.getMaterials() + problem.getAmountRequestMaterials() - 1;
					}
				}
			}
			if (newFood < 0 || newEnergy < 0 || newMaterials < 0 || newMoneySpent > 100000) {
				return false;
			}
			return true;
		}
	}

	public boolean CheckRequestMaterials(Node n, LLAPProblem problem) {
		if ((n.getMaterials() > problem.getMaterialsUseBUILD1()
				&& n.getMaterials() > problem.getMaterialsUseBUILD2())) {
			return false;
		} else if (n.getMaterials() > n.getFood() && n.getMaterials() > n.getEnergy()) {
			return false;
		} else if (n.getType() == "materials") {
			return false;
		} else {
			int newMoneySpent = n.getMoneySpent() + problem.getUnitPriceEnergy() + problem.getUnitPriceFood()
					+ problem.getUnitPriceMaterials();
			int newFood = n.getFood() - 1;
			int newMaterials = n.getMaterials() - 1;
			int newEnergy = n.getEnergy() - 1;
			if (n.getDelay() == 0) {
				if (n.getType() == "food") {
					newFood = n.getFood() + problem.getAmountRequestFood();
					if (newFood >= 50) {
						newFood = 49;
					} else {
						newFood = n.getFood() + problem.getAmountRequestFood() - 1;
					}
				} else if (n.getType() == "energy") {
					newEnergy = n.getEnergy() + problem.getAmountRequestEnergy();
					if (newEnergy >= 50) {
						newEnergy = 49;
					} else {
						newEnergy = n.getEnergy() + problem.getAmountRequestEnergy() - 1;
					}
				} else if (n.getType() == "materials") {
					newMaterials = n.getMaterials() + problem.getAmountRequestMaterials();
					if (newMaterials >= 50) {
						newMaterials = 49;
					} else {
						newMaterials = n.getMaterials() + problem.getAmountRequestMaterials() - 1;
					}
				}
			}
			if (newFood < 0 || newEnergy < 0 || newMaterials < 0 || newMoneySpent > 100000) {
				return false;
			}
			return true;
		}
	}

	public boolean CheckRequestEnergy(Node n, LLAPProblem problem) {
		if ((n.getEnergy() > problem.getEnergyUseBUILD1() && n.getEnergy() > problem.getEnergyUseBUILD2())) {
			return false;
		} else if (n.getEnergy() > n.getMaterials() && n.getEnergy() > n.getFood()) {
			return false;
		} else if (n.getType() == "energy") {
			return false;
		} else {
			int newMoneySpent = n.getMoneySpent() + problem.getUnitPriceEnergy() + problem.getUnitPriceFood()
					+ problem.getUnitPriceMaterials();
			int newFood = n.getFood() - 1;
			int newMaterials = n.getMaterials() - 1;
			int newEnergy = n.getEnergy() - 1;
			if (n.getDelay() == 0) {
				if (n.getType() == "food") {
					newFood = n.getFood() + problem.getAmountRequestFood();
					if (newFood >= 50) {
						newFood = 49;
					} else {
						newFood = n.getFood() + problem.getAmountRequestFood() - 1;
					}
				} else if (n.getType() == "energy") {
					newEnergy = n.getEnergy() + problem.getAmountRequestEnergy();
					if (newEnergy >= 50) {
						newEnergy = 49;
					} else {
						newEnergy = n.getEnergy() + problem.getAmountRequestEnergy() - 1;
					}
				} else if (n.getType() == "materials") {
					newMaterials = n.getMaterials() + problem.getAmountRequestMaterials();
					if (newMaterials >= 50) {
						newMaterials = 49;
					} else {
						newMaterials = n.getMaterials() + problem.getAmountRequestMaterials() - 1;
					}
				}
			}
			if (newFood < 0 || newEnergy < 0 || newMaterials < 0 || newMoneySpent > 100000) {
				return false;
			}
			return true;
		}
	}

	public boolean CheckWait(Node n, LLAPProblem problem) {
		int newMoneySpent = n.getMoneySpent() + problem.getUnitPriceEnergy() + problem.getUnitPriceFood()
				+ problem.getUnitPriceMaterials();
		int newFood = n.getFood() - 1;
		int newMaterials = n.getMaterials() - 1;
		int newEnergy = n.getEnergy() - 1;
		if (newFood < 0 || newEnergy < 0 || newMaterials < 0 || newMoneySpent > 100000) {
			return false;
		}
		return true;
	}

	public boolean CheckBuild1(Node n, LLAPProblem problem) {
		int newMoneySpent = n.getMoneySpent()
				+ (problem.getPriceBUILD1() + problem.getUnitPriceEnergy() * problem.getEnergyUseBUILD1()
						+ problem.getUnitPriceFood() * problem.getFoodUseBUILD1()
						+ problem.getUnitPriceMaterials() * problem.getMaterialsUseBUILD1());
		int newFood = n.getFood() - problem.getFoodUseBUILD1();
		int newMaterials = n.getMaterials() - problem.getMaterialsUseBUILD1();
		int newEnergy = n.getEnergy() - problem.getEnergyUseBUILD1();
		if (n.getDelay() == 0) {
			if (n.getType() == "food") {
				newFood = n.getFood() + problem.getAmountRequestFood();
				if (newFood >= 50) {
					newFood = 50 - problem.getFoodUseBUILD1();
				} else {
					newFood = n.getFood() + problem.getAmountRequestFood() - problem.getFoodUseBUILD1();
				}
			} else if (n.getType() == "energy") {
				newEnergy = n.getEnergy() + problem.getAmountRequestEnergy();
				if (newEnergy >= 50) {
					newEnergy = 50 - problem.getEnergyUseBUILD1();
				} else {
					newEnergy = n.getEnergy() + problem.getAmountRequestEnergy() - problem.getEnergyUseBUILD1();
				}
			} else if (n.getType() == "materials") {
				newMaterials = n.getMaterials() + problem.getAmountRequestMaterials();
				if (newMaterials >= 50) {
					newMaterials = 50 - problem.getMaterialsUseBUILD1();
				} else {
					newMaterials = n.getMaterials() + problem.getAmountRequestMaterials()
							- problem.getMaterialsUseBUILD1();
				}
			}
		}
		if (newFood < 0 || newEnergy < 0 || newMaterials < 0 || newMoneySpent > 100000) {
			return false;
		}
		return true;
	}

	public boolean CheckBuild2(Node n, LLAPProblem problem) {
		int newMoneySpent = n.getMoneySpent()
				+ (problem.getPriceBUILD2() + problem.getUnitPriceEnergy() * problem.getEnergyUseBUILD2()
						+ problem.getUnitPriceFood() * problem.getFoodUseBUILD2()
						+ problem.getUnitPriceMaterials() * problem.getMaterialsUseBUILD2());
		int newFood = n.getFood() - problem.getFoodUseBUILD2();
		int newMaterials = n.getMaterials() - problem.getMaterialsUseBUILD2();
		int newEnergy = n.getEnergy() - problem.getEnergyUseBUILD2();
		if (n.getDelay() == 0) {
			if (n.getType() == "food") {
				newFood = n.getFood() + problem.getAmountRequestFood();
				if (newFood >= 50) {
					newFood = 50 - problem.getFoodUseBUILD2();
				} else {
					newFood = n.getFood() + problem.getAmountRequestFood() - problem.getFoodUseBUILD2();
				}
			} else if (n.getType() == "energy") {
				newEnergy = n.getEnergy() + problem.getAmountRequestEnergy();
				if (newEnergy >= 50) {
					newEnergy = 50 - problem.getEnergyUseBUILD2();
				} else {
					newEnergy = n.getEnergy() + problem.getAmountRequestEnergy() - problem.getEnergyUseBUILD2();
				}
			} else if (n.getType() == "materials") {
				newMaterials = n.getMaterials() + problem.getAmountRequestMaterials();
				if (newMaterials >= 50) {
					newMaterials = 50 - problem.getMaterialsUseBUILD2();
				} else {
					newMaterials = n.getMaterials() + problem.getAmountRequestMaterials()
							- problem.getMaterialsUseBUILD2();
				}
			}
		}
		if (newFood < 0 || newEnergy < 0 || newMaterials < 0 || newMoneySpent > 100000) {
			return false;
		}
		return true;
	}

	public boolean exists(Node n, LinkedList<Node> nodes) {
		for (int i = 0; i < nodes.size(); i++) {
			if (n.getFood() == nodes.get(i).getFood() && n.getEnergy() == nodes.get(i).getEnergy()
					&& n.getMaterials() == nodes.get(i).getMaterials()
					&& n.getProsperity() == nodes.get(i).getProsperity()
					&& n.getMoneySpent() == nodes.get(i).getMoneySpent()
					&& n.getOperateString() == nodes.get(i).getOperateString()
					&& n.getDelay() == nodes.get(i).getDelay() && n.getType() == nodes.get(i).getType()) {
				return true;
			}
		}
		return false;
	}

	public LinkedList<Node> getNodes() {
		return nodes;
	}

	public void setNodes(LinkedList<Node> nodes) {
		this.nodes = nodes;
	}

	public LinkedList<Node> getUniqueValues() {
		return uniqueValues;
	}

	public void setUniqueValues(LinkedList<Node> uniqueValues) {
		this.uniqueValues = uniqueValues;
	}

	public int getP() {
		return p;
	}

	public void setP(int p) {
		this.p = p;
	}

	public int getL() {
		return l;
	}

	public void setL(int l) {
		this.l = l;
	}

	public String getQueueFun() {
		return QueueFun;
	}

	public void setQueueFun(String queueFun) {
		QueueFun = queueFun;
	}

	public Node getGoalNode() {
		return goalNode;
	}

	public void setGoalNode(Node goalNode) {
		this.goalNode = goalNode;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String returnString() {
		String output = "";
		int size = 0;
		LinkedList<Node> xDeque = new LinkedList<Node>();
		xDeque.addFirst(this.goalNode);
		Node parentNode = goalNode.getParentNode();
		while (parentNode.getOperateString() != "Root") {
			xDeque.addFirst(parentNode);
			parentNode = parentNode.getParentNode();
		}
		size = xDeque.size();
		while (!xDeque.isEmpty()) {
			if (xDeque.size() == 1) {
				output += xDeque.peek().getOperateString();
			} else {
				output += xDeque.peek().getOperateString() + ",";
			}
			xDeque.poll();
		}
		output += ";" + goalNode.getRootPathCost() + ";" + size;
		return output;
	}
}