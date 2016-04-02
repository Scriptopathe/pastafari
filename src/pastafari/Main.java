package pastafari;

import ia.IAInterface;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hello");
		new GameServer("127.0.0.1", 8080, new IAInterface() {
			
			@Override
			public void makeTurn(GameServer srv) {
				// TODO Auto-generated method stub
				
			}
		}).start();
		
		new GameServer("127.0.0.1", 8080, new IAInterface() {
			
			@Override
			public void makeTurn(GameServer srv) {
				// TODO Auto-generated method stub
				
			}
		}).start();
	}

}
