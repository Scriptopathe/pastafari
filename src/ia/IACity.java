package ia;

import java.util.ArrayList;

import pastafari.GameServer;
import pastafari.GameState;
import pastafari.Player;
import pastafari.units.Unit;

public class IACity implements IAInterface{

	@Override
	public void makeTurn(GameServer srv) {
		GameState state = srv.getGameState();
		int myID = state.getMyId();
		Player pMe = state.getPlayer(myID);
		int newPlace[] = pMe.getCity().leftPlace(state.getGrid());
		/*
		while (pMe.getGold() > 10 && newPlace != null){ // si on a la place et l'argent
			srv.sendCommand("C,P");
			pMe.setGold(pMe.getGold() - 10);
			srv.sendCommand(command)
			newPlace = pMe.getCity().leftPlace(state.getGrid());
		}
		*/
		srv.sendCommand("C,P");
	}
}
