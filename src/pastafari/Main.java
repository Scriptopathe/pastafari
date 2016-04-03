package pastafari;

import ia.IAAstar;
import ia.IADeMerde;
import ia.IAHumanDebug;
import ia.IAtest;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hello");
		for(int i = 0; i < 2; i++)
		{
			new GameServer("127.0.0.1", 8080, new IAtest()/* new IAInterface() {
				
				@Override
				public void makeTurn(GameServer srv) {
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
				}
			}*/).start();
		}
		

	}

}
