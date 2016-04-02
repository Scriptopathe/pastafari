package pastafari.units;

import pastafari.Grid;
import pastafari.Player;
import pastafari.Tile;
import pastafari.TileType;
import pastafari.structures.BuildingType;

public abstract class Unit {
	
	private int id;
	private int strength;
	private int defense;
	private int currentAction;
	private int maxAction;
	private int currentHP;
	private int maxHP;
	private Player player;
	private int range;
	private Tile tile;
	private int buildCost;
	private UnitType type;
	
	public Unit(int id, Player player, Tile tile, int strength, int defense, int maxAction, int maxHP, int range, int buildCost, UnitType type) {
		this.player = player;		
		this.strength = strength;
		this.defense = defense;
		this.maxAction = maxAction;
		this.currentAction = 2;
		this.maxHP = maxHP;
		this.currentHP = this.maxHP;
		this.range = range;
		this.tile = tile;
		this.buildCost = buildCost;
		this.id = id;
		this.type = type;
	}
	
	public int getMoveCost(Tile to) {
		if(Grid.getDistance(this.tile, to) >= 1) return Integer.MAX_VALUE;
		if(!to.isAccessible(this.type == UnitType.ENGINEER)) return Integer.MAX_VALUE;
		
		int cost = 2;
		
		if(to.getType() == TileType.MOUNTAIN) cost += 2;
		if(to.getBuildingType() == BuildingType.ROAD) cost /= 2;
		
		return cost;
	}
	
	public boolean canMove(Tile to) {	
		return getMoveCost(to) <= this.currentAction;
	}
	
	public void moveTo(Tile to) {
		int cost = this.getMoveCost(to);
		if(cost <= this.currentAction) {
			this.tile.setUnit(null);
			to.setUnit(this);
			this.setTile(to);
			
			this.currentAction -= cost;
		}
	}
	
	public void setTile(Tile tile) {
		this.tile = tile;
	}
	
	public boolean isAlive() {
		return this.currentHP > 0;
	}
	
	public void newTurn() {
		if(this.currentHP > 0) this.currentAction = this.maxAction;
	}
	
	public boolean inRange(Unit unit) {
		return Grid.getDistance(this.getTile(), unit.getTile()) <= range;
	}
	
	public UnitType getType() {
		return type;
	}
	
	public Tile getTile() {
		return tile;
	}
	
	public int getCurrentAction() {
		return currentAction;
	}

	public int getRange() {
		return range;
	}
	
	public int getDefense() {
		return defense;
	}
	
	public int getStrength() {
		return strength;
	}
	
	public int getCurrentHP() {
		return currentHP;
	}
	
	public int getMaxHP() {
		return maxHP;
	}
	
	public int getMaxAction() {
		return maxAction;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public int getBuildCost() {
		return buildCost;
	}
	
	public int getId() {
		return id;
	}
	
	public void setCurrentHP(int currentHP) {
		this.currentHP = currentHP;
	}
	
	public void setCurrentAction(int currentAction) {
		this.currentAction = currentAction;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	
	public static Unit unitFrom(String type, int id, Tile tile, Player player) {
		if(type.equals("a")) return new Archer(id, tile, player);
		else if(type.equals("b")) return new Ballista(id, tile, player);
		else if(type.equals("n")) return new Dwarf(id, tile, player);
		else if(type.equals("i")) return new Engineer(id, tile, player);
		else if(type.equals("c")) return new Paladin(id, tile, player);
		else if(type.equals("p")) return new Peasant(id, tile, player);
		else if(type.equals("e")) return new Scout(id, tile, player);
		else if(type.equals("s")) return new Soldier(id, tile, player);
		else return null;
	}
}