package pastafari.units;

import pastafari.Player;
import pastafari.Tile;

public class Ballista extends RangedUnit {
	public static final int STRENGTH = 6;
	public static final int DEFENSE = 4;
	public static final int MAX_ACTION = 2;
	public static final int MAX_HP = 4;
	public static final int MIN_RANGE = 3;
	public static final int MAX_RANGE = 6;
	public static final int COST = 100;
	
	private boolean protectingCity;

	public Ballista(int id, Tile tile, Player player) {
		super(id, player, tile, STRENGTH, DEFENSE, MAX_ACTION, MAX_HP, MIN_RANGE, MAX_RANGE, COST, UnitType.BALLISTA);
	}
	
	public boolean isProtectingCity() {
		return protectingCity;
	}

	public void setProtectingCity(boolean protectingCity) {
		this.protectingCity = protectingCity;
	}
}