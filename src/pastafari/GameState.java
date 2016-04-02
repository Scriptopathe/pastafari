package pastafari;

import java.util.HashMap;

public class GameState {
	private HashMap<Integer, Player> players;
	private Grid grid;
	
	public GameState(int width, int height) {
		this.players = new HashMap<>();
		this.grid = new Grid(width, height);
	}
	
	public Grid getGrid() {
		return grid;
	}
	
	public HashMap<Integer, Player> getPlayers() {
		return players;
	}
}