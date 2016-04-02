package pastafari.structures;

import pastafari.Tile;

public class Castle extends Building {
	public static final int BUILD_COST = 100;
	public static final int MAX_HP = 5;
	
	public Castle(Tile tile) {
		super(tile, BUILD_COST, MAX_HP, BuildingType.CASTLE);
	}
}