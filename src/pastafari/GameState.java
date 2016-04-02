package pastafari;

import java.util.HashMap;

public class GameState {
	private HashMap<Integer, Player> players;
	private Grid grid;
	
	public GameState(int size) {
		this.players = new HashMap<>();
		this.grid = new Grid(size);
	}
	
	public Grid getGrid() {
		return grid;
	}
	
	public HashMap<Integer, Player> getPlayers() {
		return players;
	}
}