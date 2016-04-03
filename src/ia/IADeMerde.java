package ia;

import java.util.List;

import pastafari.GameServer;
import pastafari.Pathfinding;
import pastafari.Tile;
import pastafari.structures.BuildingType;
import pastafari.structures.City;
import pastafari.units.Engineer;
import pastafari.units.UnitType;

public class IADeMerde implements IAInterface {
	
	private boolean init;
	private City ennemyCity;
	private Engineer winner;
	
	public IADeMerde() {
		init = true;
	}
	
	public boolean isEnnemyCity(Tile tile) {
		return tile.getBuildingType() == BuildingType.CITY && !tile.getOwner().isMe(); 
	}
	
	@Override
	public void makeTurn(GameServer srv) {
		if(init) {
			ennemyCity = srv.getGameState().getGrid().getCity(srv.getGameState().getEnnemyId());
			init = false;
			srv.sendCreate(UnitType.ENGINEER);
			winner = (Engineer) srv.getGameState().getGrid().getCity(srv.getGameState().getMyId()).getTile().getUnit();
			
		}
		
		System.out.println("ENGINEER: " + winner.getTile().getX() + "," + winner.getTile().getY());
		System.out.println("CITY: " + ennemyCity.getTile().getX() + "," + ennemyCity.getTile().getY());

		while(winner.getCurrentAction() >= 2 && !isEnnemyCity(winner.getTile())) {
			List<Tile> path = new Pathfinding(srv).FindPath(winner.getTile(), ennemyCity.getTile(), true, true);
			System.out.println(path);
			Tile nextTile = path.get(0);
			srv.sendMove(winner.getId(), nextTile.getX(), nextTile.getY());
		}
		
		if(isEnnemyCity(winner.getTile()) && winner.canDestroy()) {
			srv.sendDestroy(winner.getId());
		}
		srv.endTurn();
	}
}
