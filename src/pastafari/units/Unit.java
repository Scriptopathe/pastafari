package pastafari.units;

import pastafari.Grid;
import pastafari.Player;
import pastafari.Tile;

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
	
	public static Unit unitFrom(String type, int id, Tile tile, Player player) {
		if(type == "A") return new Archer(id, tile, player);
		else if(type == "B") return new Ballista(id, tile, player);
		else if(type == "N") return new Dwarf(id, tile, player);
		else if(type == "I") return new Engineer(id, tile, player);
		else if(type == "C") return new Paladin(id, tile, player);
		else if(type == "P") return new Peasant(id, tile, player);
		else if(type == "E") return new Scout(id, tile, player);
		else if(type == "S") return new Soldier(id, tile, player);
		else return null;
	}
	
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
	
	public boolean canMove(Tile to) {
		
		// TODO
		return false;
	}
	
	public void moveTo(Tile to) {
		
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
}