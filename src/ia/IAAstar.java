package ia;

import java.util.ArrayList;
import java.util.List;

import pastafari.GameServer;
import pastafari.GameState;
import pastafari.Pathfinding;
import pastafari.Player;
import pastafari.Tile;
import pastafari.units.Unit;

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
			String str = srv.scanIn.nextLine();
			String[] args = str.split(" ");
			List<Tile> b = f.FindPath(state.getGrid().getTile(i(args[0]), i(args[1])),
										state.getGrid().getTile(i(args[2]), i(args[3])), true);
			srv.log("path: ");
			for(Tile t : b)
			{
				srv.log(t.toString());
			}
		}
	}
}
