package pastafari.units;

import pastafari.Player;
import pastafari.Tile;

public class Peasant extends Unit {
	public static final int STRENGTH = 1;
	public static final int DEFENSE = 1;
	public static final int MAX_ACTION = 2;
	public static final int MAX_HP = 1;
	public static final int RANGE = 1;
	public static final int COST = 10;
	
	public Peasant(int id, Tile tile, Player player) {
		super(id, player, tile, STRENGTH, DEFENSE, MAX_ACTION, MAX_HP, RANGE, COST, UnitType.PEASANT);
	}
}