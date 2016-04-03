package ia;

import java.util.ArrayList;
import java.util.List;

import pastafari.GameServer;
import pastafari.GameState;
import pastafari.Pathfinding;
import pastafari.Player;
import pastafari.Tile;
import pastafari.units.Unit;
import pastafari.units.UnitType;

public class IAAstar implements IAInterface {
	public int i(String s)
	{
		return Integer.parseInt(s);
	}
	
	@Override
	public void makeTurn(GameServer srv) {
		srv.log("MAKE TURN : Type command");
		Pathfinding f = new Pathfinding(srv);
		GameState state = srv.getGameState();
		while(true)
		{
			Tile tile = srv.getGameState().getPlayer(0).getCity().getTile();
			srv.sendCreate(UnitType.PEASANT);
			List<Tile> b = f.GetCCL(srv.getGameState().getPlayer(0).getCity().getTile().getUnit());
			srv.log("path: (" + b.size() + "): " + b);
			srv.sendMove(0, tile.getX()+1, tile.getY());
			
			srv.sendCreate(UnitType.SOLDIER);
			b = f.GetCCL(srv.getGameState().getPlayer(0).getCity().getTile().getUnit());
			srv.log("path: (" + b.size() + "): " + b);
			/*String str = srv.scanIn.nextLine();
			String[] args = str.split(" ");
			List<Tile> b = f.FindPath(state.getGrid().getTile(i(args[0]), i(args[1])),
										state.getGrid().getTile(i(args[2]), i(args[3])), true);*/
			
			

			srv.scanIn.nextLine();
		}
	}
}
