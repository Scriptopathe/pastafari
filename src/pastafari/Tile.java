package pastafari;

import pastafari.structures.Building;
import pastafari.structures.BuildingType;
import pastafari.units.Unit;
import pastafari.units.UnitType;

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
	
	public BuildingType getBuildingType() {
		if(this.getBuilding() == null) return BuildingType.VOID;
		else return this.getBuilding().getType();
	}
	
	public UnitType getUnitType() {
		if(this.getUnit() == null) return UnitType.VOID;
		else return this.getUnit().getType();
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
		return this.getUnitType() == UnitType.VOID && (this.getType() != TileType.RIVER || iAmEngineer || getBuildingType() == BuildingType.BRIDGE);
	}
}
