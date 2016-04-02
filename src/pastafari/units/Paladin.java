package pastafari.units;


import pastafari.Player;
import pastafari.Tile;

public class Paladin extends Unit {
	public static final int STRENGTH = 4;
	public static final int DEFENSE = 4;
	public static final int MAX_ACTION = 6;
	public static final int MAX_HP = 3;
	public static final int RANGE = 1;
	public static final int COST = 100;
	
	public Paladin(Tile tile, Player player) {
		super(player, tile, STRENGTH, DEFENSE, MAX_ACTION, MAX_HP, RANGE, COST);
	}
}