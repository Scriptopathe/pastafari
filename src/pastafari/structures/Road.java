package pastafari.structures;

import pastafari.Tile;

public class Road extends Building {
	public static final int BUILD_COST = 50;
	
	public Road(Tile tile) {
		super(tile, BUILD_COST, BuildingType.ROAD);
	}
}
