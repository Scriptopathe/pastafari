package pastafari;

import java.util.HashMap;

public class GameState {
	private HashMap<Integer, Player> players;
	private Grid grid;
	private int size;
	private int id;
	
	public GameState(int size, int myID) {
		this.players = new HashMap<>();
		this.grid = new Grid(size, size);
		this.size = size;
		this.id = myID;
	}
	
	public Grid getGrid() {
		return grid;
	}
	
	public void addPlayer(Player player) {
		this.players.put(player.getId(), player);
	}
	
	public Player getPlayer(int id) {
		return this.players.get(id);
	}
	
	public int getSize() {
		return size;
	}
}