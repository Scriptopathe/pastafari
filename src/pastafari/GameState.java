package pastafari;

import java.util.HashMap;

public class GameState {
	private HashMap<Integer, Player> players;
	private Grid grid;
	private int myId;

	public GameState(int size, int myId) {
		this.players = new HashMap<>();
		this.players.put(-1, Player.NoPlayer);
		this.grid = new Grid(size);
		this.myId = myId;
	}
	
	public Grid getGrid() {
		return grid;
	}
	
	public void setGrid(Grid grid)
	{
		this.grid = grid;
	}
	
	public void addPlayer(Player player) {
		this.players.put(player.getId(), player);
	}
	
	public Player getPlayer(int id) {
		return this.players.get(id);
	}
	
	public Player getMyPlayer() {
		return this.players.get(this.myId);
	}

	public Player getOtherPlayer() {
		return this.players.get(this.myId ^ 1);
	}
	
	public int getMyId() {
		return myId;
	}
	
	public int getEnnemyId() {
		return myId == 1 ? 0 : 1;
	}
}