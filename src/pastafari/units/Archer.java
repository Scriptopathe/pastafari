package pastafari.units;

import pastafari.Player;
import pastafari.Tile;

public class Archer extends RangedUnit {
	public static final int STRENGTH = 3;
	public static final int DEFENSE = 1;
	public static final int MAX_ACTION = 4;
	public static final int MAX_HP = 1;
	public static final int MIN_RANGE = 2;
	public static final int MAX_RANGE = 3;
	public static final int COST = 60;
	
	public Archer(int id, Tile tile, Player player) {
		super(id, player, tile, STRENGTH, DEFENSE, MAX_ACTION, MAX_HP, MIN_RANGE, MAX_RANGE, COST, UnitType.ARCHER);
	}

}