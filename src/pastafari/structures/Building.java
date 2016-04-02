package pastafari.structures;

public abstract class Building {
	private int buildCost;
	private int maxHP;
	private int currentHP;
	private int x;
	private int y;
	private BuildingType type;
	
	public Building(int x, int y, int buildCost, int maxHP, BuildingType type) {
		this.buildCost = buildCost;
		this.x = x;
		this.y = y;
		this.maxHP = maxHP;
		this.currentHP = this.maxHP;
		this.type = type;
	}
	
	public int getBuildCost() {
		return this.buildCost;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getCurrentHP() {
		return currentHP;
	}
	
	public int getMaxHP() {
		return maxHP;
	}
	
	public void setCurrentHP(int currentHP) {
		this.currentHP = currentHP;
	}
	
	public BuildingType getType() {
		return this.type;
	}
	
	public static Building buildingFrom(String type, int x, int y) {
		if(type.equals("f")) {
			return new Castle(x, y);
		} else if(type.equals("R")) {
			return new Road(x, y);
		} else if(type.equals("P")) {
			return new Bridge(x, y);
		} else if(type.equals("H")) {
			return new Hospital(x, y);
		} else {
			return null;
		}
	}
}
