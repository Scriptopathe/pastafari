package pastafari.structures;

import pastafari.Tile;

public class Hospital extends Building {
	public static final int BUILD_COST = 100;
	public static final int MAX_HP = 2;
	
	public Hospital(Tile tile) {
		super(tile, BUILD_COST, MAX_HP, BuildingType.HOSPITAL);
	}
}