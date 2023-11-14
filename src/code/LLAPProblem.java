package code;

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

}
