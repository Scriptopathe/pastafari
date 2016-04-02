package pastafari.units;

import pastafari.Grid;
import pastafari.Player;
import pastafari.Tile;
import pastafari.Tile.Type;

public class RangedUnit extends Unit {
	private int maxRange;
	
	public RangedUnit(Player player, Tile tile, int strength, int defense, int maxAction, int maxHP, int minRange, int maxRange, int buildCost) {
		super(player, tile, strength, defense, maxAction, maxHP, minRange, buildCost);
		this.maxRange = maxRange;
	}
	
	public int getMaxRange() {
		return maxRange;
	}
	
	public boolean inRange(Unit unit) {
		if(unit.getTile().getType() == Type.FOREST) return false;
		double dist = Grid.getDistance(this.getTile(), unit.getTile());
		return dist >= this.getRange() && dist <= this.getMaxRange();
	}
}
