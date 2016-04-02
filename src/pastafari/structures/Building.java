package pastafari.structures;

import pastafari.Tile;

public abstract class Building {
	private int buildCost;	
	private Tile tile;
	private BuildingType type;
	
	public Building(Tile tile, int buildCost, BuildingType type) {
		this.buildCost = buildCost;
		this.tile = tile;
		this.type = type;
	}
	
	public int getBuildCost() {
		return this.buildCost;
	}
	
	public Tile getTile() {
		return tile;
	}

	public BuildingType getType() {
		return this.type;
	}
	
	public static Building buildingFrom(String type, Tile tile) {
		if(type.equals("f")) return new Castle(tile);
		else if(type.equals("r")) return new Road(tile);
		else if(type.equals("p")) return new Bridge(tile);
		else if(type.equals("h")) return new Hospital(tile);
		else if(type.equals("v")) return new City(tile);
		else return null;
	}
	
	public static char getBuildingCode(BuildingType type){
		switch (type){
		case BRIDGE:
			return 'P';
		case CASTLE:
			return 'F';
		case ROAD:
			return 'R';
		case HOSPITAL:
			return 'H';
		default:
			return 'V';
		}
	}
}
