package pastafari;

import java.util.HashMap;

public class GameState {
	private HashMap<Integer, Player> players;
	private Grid grid;
	private int size;
	private int id;
	
	public GameState(int size) {
		this.players = new HashMap<>();
		this.grid = new Grid(size);
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