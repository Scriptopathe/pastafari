package pastafari.structures;

import pastafari.Tile;

public class City extends Building {
	public static final int BUILD_COST = 0;
	
	public City(Tile tile) {
		super(tile, BUILD_COST, BuildingType.CITY);
	}
}