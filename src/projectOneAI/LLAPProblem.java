package projectOneAI;

import java.io.IOException;

public class LLAPProblem {

	private int budget;
	private int initialProsperityLevel;
	private int initialFood;
	private int initialMaterials;
	private int initialEnergy;
	private int unitPriceFood;
	private int unitPriceMaterials;
	private int unitPriceEnergy;
	private int amountRequestFood;
	private int delayRequestFood;
	private int amountRequestMaterials;
	private int delayRequestMaterials;
	private int amountRequestEnergy;
	private int delayRequestEnergy;
	private int priceBUILD1;
	private int foodUseBUILD1;
	private int materialsUseBUILD1;
	private int energyUseBUILD1;
	private int priceBUILD2;
	private int foodUseBUILD2;
	private int materialsUseBUILD2;
	private int energyUseBUILD2;
	private int prosperityBUILD1;
	private int prosperityBUILD2;
	private int moneySpent;
	private int delay;
	private boolean foodReq;
	private boolean energyReq;
	private boolean materialsReq;
	private String initialState;

	public LLAPProblem(String initialState) {
		this.initialState = initialState;
		initialize(initialState);
	}

	public String getInitialState() {
		return initialState;
	}

	public void setInitialState(String initialState) {
		this.initialState = initialState;
	}

	public void initialize(String initialState) {
//		String[] lineStrings = this.readLines("Init");
//		String finalString = lineStrings[0];
		String firstSplit = initialState.replace(';', ',');
		String[] secondSplit = firstSplit.split(",");
		this.initialProsperityLevel = Integer.parseInt(secondSplit[0]);
		this.initialFood = Integer.parseInt(secondSplit[1]);
		this.initialMaterials = Integer.parseInt(secondSplit[2]);
		this.initialEnergy = Integer.parseInt(secondSplit[3]);
		this.unitPriceFood = Integer.parseInt(secondSplit[4]);
		this.unitPriceMaterials = Integer.parseInt(secondSplit[5]);
		this.unitPriceEnergy = Integer.parseInt(secondSplit[6]);
		this.amountRequestFood = Integer.parseInt(secondSplit[7]);
		this.delayRequestFood = Integer.parseInt(secondSplit[8]);
		this.amountRequestMaterials = Integer.parseInt(secondSplit[9]);
		this.delayRequestMaterials = Integer.parseInt(secondSplit[10]);
		this.amountRequestEnergy = Integer.parseInt(secondSplit[11]);
		this.delayRequestEnergy = Integer.parseInt(secondSplit[12]);
		this.priceBUILD1 = Integer.parseInt(secondSplit[13]);
		this.foodUseBUILD1 = Integer.parseInt(secondSplit[14]);
		this.materialsUseBUILD1 = Integer.parseInt(secondSplit[15]);
		this.energyUseBUILD1 = Integer.parseInt(secondSplit[16]);
		this.prosperityBUILD1 = Integer.parseInt(secondSplit[17]);
		this.priceBUILD2 = Integer.parseInt(secondSplit[18]);
		this.foodUseBUILD2 = Integer.parseInt(secondSplit[19]);
		this.materialsUseBUILD2 = Integer.parseInt(secondSplit[20]);
		this.energyUseBUILD2 = Integer.parseInt(secondSplit[21]);
		this.prosperityBUILD2 = Integer.parseInt(secondSplit[22]);
		this.moneySpent = 0;
		this.budget = 100000;
		this.energyReq = false;
		this.foodReq = false;
		this.materialsReq = false;
		this.delay = 0;
	}

	public void reset(String initialState) {
		initialize(initialState);
	}

	public int getMoneySpent() {
		return moneySpent;
	}

	public void setMoneySpent(int moneySpent) {
		this.moneySpent = moneySpent;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public boolean isFoodReq() {
		return foodReq;
	}

	public void setFoodReq(boolean foodReq) {
		this.foodReq = foodReq;
	}

	public boolean isEnergyReq() {
		return energyReq;
	}

	public void setEnergyReq(boolean energyReq) {
		this.energyReq = energyReq;
	}

	public boolean isMaterialsReq() {
		return materialsReq;
	}

	public void setMaterialsReq(boolean materialsReq) {
		this.materialsReq = materialsReq;
	}

	public int getBudget() {
		return budget;
	}

	public void setBudget(int budget) {
		this.budget = budget;
	}

	public int getInitialProsperityLevel() {
		return initialProsperityLevel;
	}

	public void setInitialProsperityLevel(int initialProsperityLevel) {
		this.initialProsperityLevel = initialProsperityLevel;
	}

	public int getInitialFood() {
		return initialFood;
	}

	public void setInitialFood(int initialFood) {
		this.initialFood = initialFood;
	}

	public int getInitialMaterials() {
		return initialMaterials;
	}

	public void setInitialMaterials(int initialMaterials) {
		this.initialMaterials = initialMaterials;
	}

	public int getInitialEnergy() {
		return initialEnergy;
	}

	public void setInitialEnergy(int initialEnergy) {
		this.initialEnergy = initialEnergy;
	}

	public int getUnitPriceFood() {
		return unitPriceFood;
	}

	public void setUnitPriceFood(int unitPriceFood) {
		this.unitPriceFood = unitPriceFood;
	}

	public int getUnitPriceMaterials() {
		return unitPriceMaterials;
	}

	public void setUnitPriceMaterials(int unitPriceMaterials) {
		this.unitPriceMaterials = unitPriceMaterials;
	}

	public int getUnitPriceEnergy() {
		return unitPriceEnergy;
	}

	public void setUnitPriceEnergy(int unitPriceEnergy) {
		this.unitPriceEnergy = unitPriceEnergy;
	}

	public int getAmountRequestFood() {
		return amountRequestFood;
	}

	public void setAmountRequestFood(int amountRequestFood) {
		this.amountRequestFood = amountRequestFood;
	}

	public int getDelayRequestFood() {
		return delayRequestFood;
	}

	public void setDelayRequestFood(int delayRequestFood) {
		this.delayRequestFood = delayRequestFood;
	}

	public int getAmountRequestMaterials() {
		return amountRequestMaterials;
	}

	public void setAmountRequestMaterials(int amountRequestMaterials) {
		this.amountRequestMaterials = amountRequestMaterials;
	}

	public int getDelayRequestMaterials() {
		return delayRequestMaterials;
	}

	public void setDelayRequestMaterials(int delayRequestMaterials) {
		this.delayRequestMaterials = delayRequestMaterials;
	}

	public int getAmountRequestEnergy() {
		return amountRequestEnergy;
	}

	public void setAmountRequestEnergy(int amountRequestEnergy) {
		this.amountRequestEnergy = amountRequestEnergy;
	}

	public int getDelayRequestEnergy() {
		return delayRequestEnergy;
	}

	public void setDelayRequestEnergy(int delayRequestEnergy) {
		this.delayRequestEnergy = delayRequestEnergy;
	}

	public int getPriceBUILD1() {
		return priceBUILD1;
	}

	public void setPriceBUILD1(int priceBUILD1) {
		this.priceBUILD1 = priceBUILD1;
	}

	public int getFoodUseBUILD1() {
		return foodUseBUILD1;
	}

	public void setFoodUseBUILD1(int foodUseBUILD1) {
		this.foodUseBUILD1 = foodUseBUILD1;
	}

	public int getMaterialsUseBUILD1() {
		return materialsUseBUILD1;
	}

	public void setMaterialsUseBUILD1(int materialsUseBUILD1) {
		this.materialsUseBUILD1 = materialsUseBUILD1;
	}

	public int getEnergyUseBUILD1() {
		return energyUseBUILD1;
	}

	public void setEnergyUseBUILD1(int energyUseBUILD1) {
		this.energyUseBUILD1 = energyUseBUILD1;
	}

	public int getPriceBUILD2() {
		return priceBUILD2;
	}

	public void setPriceBUILD2(int priceBUILD2) {
		this.priceBUILD2 = priceBUILD2;
	}

	public int getFoodUseBUILD2() {
		return foodUseBUILD2;
	}

	public void setFoodUseBUILD2(int foodUseBUILD2) {
		this.foodUseBUILD2 = foodUseBUILD2;
	}

	public int getMaterialsUseBUILD2() {
		return materialsUseBUILD2;
	}

	public void setMaterialsUseBUILD2(int materialsUseBUILD2) {
		this.materialsUseBUILD2 = materialsUseBUILD2;
	}

	public int getEnergyUseBUILD2() {
		return energyUseBUILD2;
	}

	public void setEnergyUseBUILD2(int energyUseBUILD2) {
		this.energyUseBUILD2 = energyUseBUILD2;
	}

	public int getProsperityBUILD1() {
		return prosperityBUILD1;
	}

	public void setProsperityBUILD1(int prosperityBUILD1) {
		this.prosperityBUILD1 = prosperityBUILD1;
	}

	public int getProsperityBUILD2() {
		return prosperityBUILD2;
	}

	public void setProsperityBUILD2(int prosperityBUILD2) {
		this.prosperityBUILD2 = prosperityBUILD2;
	}

//	public int noOfLinesPerFile(String fn) throws IOException {
//		File f = new File(fn + ".txt");
//		FileReader fr = new FileReader(f);
//		BufferedReader br = new BufferedReader(fr);
//		String line = "";
//		int numberOfLines = 0;
//		while ((line = br.readLine()) != null) {
//			numberOfLines++;
//		}
//		br.close();
//		return numberOfLines;
//	}
//
//	public String[] readLines(String fn) throws IOException {
//		File f = new File(fn + ".txt");
//		FileReader fr = new FileReader(f);
//		BufferedReader br = new BufferedReader(fr);
//		String line;
//		int numberOfLines = noOfLinesPerFile(fn);
//		String[] arr = new String[numberOfLines];
//		int i = 0;
//		while ((line = br.readLine()) != null) {
//			arr[i] = line;
//			i++;
//		}
//		br.close();
//		fr.close();
//		return arr;
//	}

	public int getRequestResouce() {
		int val = this.moneySpent + (unitPriceEnergy + unitPriceFood + unitPriceMaterials);
		return val;
	}

	public int getBuild1Cost() {
		int val = this.moneySpent + (priceBUILD1 + unitPriceEnergy * energyUseBUILD1 + unitPriceFood * foodUseBUILD1
				+ unitPriceMaterials * materialsUseBUILD1);
		return val;

	}

	public int getBuild2Cost() {
		int val = this.moneySpent + (priceBUILD2 + unitPriceEnergy * energyUseBUILD2 + unitPriceFood * foodUseBUILD2
				+ unitPriceMaterials * materialsUseBUILD2);
		return val;
	}

	public int getWaitCost() {
		int val = this.moneySpent + (unitPriceEnergy + unitPriceFood + unitPriceMaterials);
		return val;
	}

	public int remainingProsperityHeuristic(String Operator) {
		int remainingProsperity = 0;
		if (Operator == "Root") {
			remainingProsperity = this.initialProsperityLevel;
		}
		if (Operator == "Build1") {
			int currentProsperity = this.initialProsperityLevel + prosperityBUILD1;
			remainingProsperity = 100 - currentProsperity;

		} else if (Operator == "Build2") {
			int currentProsperity = this.initialProsperityLevel + prosperityBUILD2;
			remainingProsperity = 100 - currentProsperity;
		} else {
			int currentProsperity = this.initialProsperityLevel;
			remainingProsperity = 100 - currentProsperity;
		}
		if (remainingProsperity < 0) {
			return 0;
		}
		return remainingProsperity;
	}

	public int resourceCostHeuristic() {
		int currentResourcesCost = this.getRequestResouce(); // Calculate the cost of requesting resources
		int build1Cost = this.getBuild1Cost(); // Calculate the cost of BUILD1
		int build2Cost = this.getBuild2Cost(); // Calculate the cost of BUILD2
		int waitCost = this.getWaitCost(); // Calculate the cost of WAIT

		// Calculate the total cost
		int totalCost = currentResourcesCost + Math.min(build1Cost, build2Cost) + waitCost;
		return totalCost;
	}

	public void RequestFood() {
		if (delay == 0) {
			this.moneySpent += (unitPriceEnergy + unitPriceFood + unitPriceMaterials);
			this.initialFood -= 1;
			this.initialEnergy -= 1;
			this.initialMaterials -= 1;
			this.delay = delayRequestFood;
			this.foodReq = true;
		}
	}

	public void RequestMaterials() {
		if (delay == 0) {
			this.moneySpent += (unitPriceEnergy + unitPriceFood + unitPriceMaterials);
			this.initialFood -= 1;
			this.initialEnergy -= 1;
			this.initialMaterials -= 1;
			this.delay = delayRequestMaterials;
			this.materialsReq = true;
		}
	}

	public void RequestEnergy() {
		if (delay == 0) {
			this.moneySpent += (unitPriceEnergy + unitPriceFood + unitPriceMaterials);
			this.initialFood -= 1;
			this.initialEnergy -= 1;
			this.initialMaterials -= 1;
			this.delay = delayRequestEnergy;
			this.energyReq = true;
		}
	}

	public void Build1() {
		this.initialEnergy -= energyUseBUILD1;
		this.initialFood -= foodUseBUILD1;
		this.initialMaterials -= materialsUseBUILD1;
		this.moneySpent += (priceBUILD1 + unitPriceEnergy * energyUseBUILD1 + unitPriceFood * foodUseBUILD1
				+ unitPriceMaterials * materialsUseBUILD1);
		this.budget -= (priceBUILD1 + unitPriceEnergy * energyUseBUILD1 + unitPriceFood * foodUseBUILD1
				+ unitPriceMaterials * materialsUseBUILD1);
		this.initialProsperityLevel += prosperityBUILD1;
		if (delay != 0) {
			delay--;
		} else {
			if (foodReq) {
				this.initialFood += this.amountRequestFood;
				if (this.initialFood > 50) {
					this.initialFood = 50;
				}
			} else if (energyReq) {
				this.initialEnergy += this.amountRequestEnergy;
				if (this.initialEnergy > 50) {
					this.initialEnergy = 50;
				}
			} else if (materialsReq) {
				this.initialMaterials += this.amountRequestMaterials;
				if (this.initialMaterials > 50) {
					this.initialMaterials = 50;
				}
			}
			foodReq = false;
			energyReq = false;
			materialsReq = false;
		}
	}

	public void Build2() {
		this.initialEnergy -= energyUseBUILD2;
		this.initialFood -= foodUseBUILD2;
		this.initialMaterials -= materialsUseBUILD2;
		this.moneySpent += (priceBUILD2 + unitPriceEnergy * energyUseBUILD2 + unitPriceFood * foodUseBUILD2
				+ unitPriceMaterials * materialsUseBUILD2);
		this.budget -= (priceBUILD2 + unitPriceEnergy * energyUseBUILD2 + unitPriceFood * foodUseBUILD2
				+ unitPriceMaterials * materialsUseBUILD2);
		this.initialProsperityLevel += prosperityBUILD2;
		if (delay != 0) {
			delay--;
		} else {
			if (foodReq) {
				this.initialFood += this.amountRequestFood;
				if (this.initialFood > 50) {
					this.initialFood = 50;
				}
			} else if (energyReq) {
				this.initialEnergy += this.amountRequestEnergy;
				if (this.initialEnergy > 50) {
					this.initialEnergy = 50;
				}
			} else if (materialsReq) {
				this.initialMaterials += this.amountRequestMaterials;
				if (this.initialMaterials > 50) {
					this.initialMaterials = 50;
				}
			}
			foodReq = false;
			energyReq = false;
			materialsReq = false;
		}
	}

	public void WAIT() {
		this.budget -= (unitPriceEnergy + unitPriceFood + unitPriceMaterials);
		this.moneySpent += (unitPriceEnergy + unitPriceFood + unitPriceMaterials);
		this.initialFood -= 1;
		this.initialEnergy -= 1;
		this.initialMaterials -= 1;
		if (delay != 0) {
			delay--;
		} else {
			if (foodReq) {
				this.initialFood += this.amountRequestFood;
				if (this.initialFood > 50) {
					this.initialFood = 50;
				}
			} else if (energyReq) {
				this.initialEnergy += this.amountRequestEnergy;
				if (this.initialEnergy > 50) {
					this.initialEnergy = 50;
				}
			} else if (materialsReq) {
				this.initialMaterials += this.amountRequestMaterials;
				if (this.initialMaterials > 50) {
					this.initialMaterials = 50;
				}
			}
			foodReq = false;
			energyReq = false;
			materialsReq = false;
		}
	}

	public static void main(String[] args) throws IOException {
		String initialState = "17;" + "49,30,46;" + "7,57,6;" + "7,1;20,2;29,2;" + "350,10,9,8,28;" + "408,8,12,13,34;";
		LLAPProblem problem = new LLAPProblem(initialState);
		System.out.println(problem.getInitialProsperityLevel());
		System.out.println(problem.getInitialFood());
		System.out.println(problem.getInitialMaterials());
		System.out.println(problem.getInitialEnergy());
		System.out.println(problem.getUnitPriceFood());
		System.out.println(problem.getUnitPriceMaterials());
		System.out.println(problem.getUnitPriceEnergy());
		System.out.println(problem.getAmountRequestFood());
		System.out.println(problem.getDelayRequestFood());
		System.out.println(problem.getAmountRequestMaterials());
		System.out.println(problem.getDelayRequestMaterials());
		System.out.println(problem.getAmountRequestEnergy());
		System.out.println(problem.getDelayRequestEnergy());
		System.out.println(problem.getPriceBUILD1());
		System.out.println(problem.getMaterialsUseBUILD1());
		System.out.println(problem.getEnergyUseBUILD1());
		System.out.println(problem.getProsperityBUILD1());
		System.out.println(problem.getPriceBUILD2());
		System.out.println(problem.getMaterialsUseBUILD2());
		System.out.println(problem.getEnergyUseBUILD2());
		System.out.println(problem.getProsperityBUILD2());
	}
}
