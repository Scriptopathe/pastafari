package ia;

import pastafari.GameServer;

public class IADebile implements IAInterface {

	enum State
	{
		Farming,
		Attacking
	}
	
	State state;
	@Override
	public void makeTurn(GameServer srv) {
		// TODO Auto-generated method stub
		if(srv.getGameState().getMyPlayer().getGold() < 100)
		{
			
		}
	}

}
