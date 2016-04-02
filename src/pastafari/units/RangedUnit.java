package pastafari.units;

import pastafari.Player;
import pastafari.Tile;

public class RangedUnit extends Unit {
	private int maxRange;
	
	public RangedUnit(Player player, Tile tile, int strength, int defense, int maxAction, int maxHP, int minRange, int maxRange, int buildCost) {
		super(player, tile, strength, defense, maxAction, maxHP, minRange, buildCost);
		this.maxRange = maxRange;
	}
}
