package pastafari.structures;

import pastafari.Tile;

public class Road extends Building {
	public static final int BUILD_COST = 50;
	public static final int MAX_HP = 1;
	
	public Road(Tile tile) {
		super(tile, BUILD_COST, MAX_HP, BuildingType.ROAD);
	}
}
