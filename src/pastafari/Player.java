package pastafari;

import java.util.ArrayList;

import pastafari.structures.City;
import pastafari.units.Unit;

public class Player {
	private int id;
	private int gold;
	private boolean alive;
	private boolean isMe;
	
	private ArrayList<Unit> units;
	private City city;
	
	public Player(int id, boolean isMe) {
		this.id = id;
		this.gold = 100;
		this.alive = true;
		this.isMe = isMe;
		
		this.units = new ArrayList<>();
	}
	
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
	public void setGold(int gold) {
		this.gold = gold;
	}
	
	public int getGold() {
		return gold;
	}
	
	public int getId() {
		return id;
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public boolean isMe() {
		return isMe;
	}
	
	public void addUnit(Unit u){
		units.add(u);
	}
	
	public ArrayList<Unit> getUnits(){
		return this.units;
	}
	public void setCity(City c) {
		city = c;
	}
	public City getCity() {
		return city;
	}
}
