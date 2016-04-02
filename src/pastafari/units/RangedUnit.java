package pastafari.units;

import pastafari.Grid;
import pastafari.Player;
import pastafari.Tile;
import pastafari.TileType;

public class RangedUnit extends Unit {
	private int maxRange;
	
	public RangedUnit(int id, Player player, Tile tile, int strength, int defense, int maxAction, int maxHP, int minRange, int maxRange, int buildCost, UnitType type) {
		super(id, player, tile, strength, defense, maxAction, maxHP, minRange, buildCost, type);
		this.maxRange = maxRange;
	}
	
	public int getMaxRange() {
		return maxRange;
	}
	
	public boolean inRange(Unit unit) {
		if(unit.getTile().getType() == TileType.FOREST) return false;
		double dist = Grid.getDistance(this.getTile(), unit.getTile());
		return dist >= this.getRange() && dist <= this.getMaxRange();
	}
}
