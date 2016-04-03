package ia;

import java.util.ArrayList;

import pastafari.GameServer;
import pastafari.GameState;
import pastafari.Player;
import pastafari.units.Unit;

public class IAHumanDebug implements IAInterface {
	@Override
	public boolean makeTurn(GameServer srv) {
		srv.log("MAKE TURN : Type command");
		String bl = srv.scanIn.nextLine();
		while(!bl.equals("E"))
		{
			srv.sendCommand(bl);
			
			srv.log("Type command");
			bl = srv.scanIn.nextLine();
		}
		srv.endTurn();
		srv.log("END TURN");
		return false;
	}
}
