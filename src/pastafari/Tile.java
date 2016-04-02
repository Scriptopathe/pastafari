package pastafari;

import pastafari.structures.Building;
import pastafari.units.Unit;

public class Tile {
	public static enum Type {
		FOREST,
		LOWLAND,
		MOUNTAIN,
		RIVER
	}
	
	private int x;
	private int y;
	private Type type;
	private Player owner;
	private Unit unit;
	private Building building;
	
	public Tile(int x, int y, Type type) {
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
	
	public Type getType() {
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
	
	public boolean isAccessible(){
		// ajouter le type bridge
		return this.unit == null && (this.getType() != Type.RIVER);
	}
}
