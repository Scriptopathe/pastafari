package pastafari.structures;

import pastafari.Tile;

public abstract class Building {
	private int buildCost;
	private int maxHP;
	private int currentHP;
	private Tile tile;
	private BuildingType type;
	
	public Building(Tile tile, int buildCost, int maxHP, BuildingType type) {
		this.buildCost = buildCost;
		this.tile = tile;
		this.maxHP = maxHP;
		this.currentHP = this.maxHP;
		this.type = type;
	}
	
	public int getBuildCost() {
		return this.buildCost;
	}
	
	public Tile getTile() {
		return tile;
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
	
	public static Building buildingFrom(String type, Tile tile) {
		if(type.equals("f")) return new Castle(tile);
		else if(type.equals("R")) return new Road(tile);
		else if(type.equals("P")) return new Bridge(tile);
		else if(type.equals("H")) return new Hospital(tile);
		else return null;
	}
}
