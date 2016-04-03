package ia;

import pastafari.GameServer;
import pastafari.GameState;
import pastafari.Player;
import pastafari.Tile;
import pastafari.TileType;
import pastafari.structures.BuildingType;
import pastafari.units.Unit;
import pastafari.units.UnitType;

public class IAArmy implements IAInterface {

	@Override
	public void makeTurn(GameServer srv) {
		GameState game = srv.getGameState();
		Player me = game.getMyPlayer();
		
		for(Unit u : me.getUnits())
		if(u.getType() == UnitType.ENGINEER) {
			// If on river: make bridge
			// If on mountain: make road ?
			// If on city (opponent!): KILL
			// else move toward opponent's city
		} else /* also move fucking lazy peasant  if(u.getType() != UnitType.PEASANT)*/ {
			
		}
	}
	
	public static boolean canAttackMove(boolean isEngineer, int action, Tile to) {
		if(to.getType() == TileType.RIVER && !(isEngineer || to.getBuildingType() == BuildingType.BRIDGE)) return false;
		
		int cost = 2;
		
		if(to.getType() == TileType.MOUNTAIN) cost += 2;
		if(to.getBuildingType() == BuildingType.ROAD) cost /= 2;
		
		return cost <= action;
	}
}
