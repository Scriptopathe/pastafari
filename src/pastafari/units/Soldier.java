package pastafari.units;

import pastafari.Player;
import pastafari.Tile;

public class Soldier extends Unit {
	public static final int STRENGTH = 3;
	public static final int DEFENSE = 3;
	public static final int MAX_ACTION = 4;
	public static final int MAX_HP = 4;
	public static final int RANGE = 1;
	public static final int COST = 60;
	
	public Soldier(int id, Tile tile, Player player) {
		super(id, player, tile, STRENGTH, DEFENSE, MAX_ACTION, MAX_HP, RANGE, COST, UnitType.SOLDIER);
	}
}