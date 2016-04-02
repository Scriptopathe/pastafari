package pastafari.structures;

import pastafari.Tile;

public class Bridge extends Building {
	
	public static final int BUILD_COST = 50;
	
	public Bridge(Tile tile) {
		super(tile, BUILD_COST, BuildingType.BRIDGE);
	}
}