package pastafari;

import pastafari.structures.Building;
import pastafari.structures.BuildingType;
import pastafari.units.Unit;

public class Tile {

	private int x;
	private int y;
	private TileType type;
	private Player owner;
	private Unit unit;
	private Building building;
	
	public Tile(int x, int y, TileType type) {
		this.x = x;
		this.y = y;
		this.type = type;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Building getBuilding() {
		return building;
	}
	
	public Player getOwner() {
		return owner;
	}
	
	public Unit getUnit() {
		return unit;
	}
	
	public TileType getType() {
		return type;
	}
	
	public void setBuilding(Building building) {
		this.building = building;
	}
	
	public void setOwner(Player owner) {
		this.owner = owner;
	}
	
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
	public boolean isOwned() {
		return this.owner == null;
	}
	
	public boolean isAccessible(boolean iAmEngineer){
		// ajouter le type bridge
		return this.unit == null && (this.getType() != TileType.RIVER || iAmEngineer || building.getType() == BuildingType.BRIDGE);
	}
}
