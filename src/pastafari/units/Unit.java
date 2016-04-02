package pastafari.units;

import pastafari.Grid;
import pastafari.Player;
import pastafari.Tile;

public abstract class Unit {
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
	
	public Unit(Player player, Tile tile, int strength, int defense, int maxAction, int maxHP, int range, int buildCost) {
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
		
		// add this unit to the list of unit of the player
		//this.player
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
}