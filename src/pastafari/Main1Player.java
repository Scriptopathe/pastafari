package pastafari;

import ia.IARenaud;

public class Main1Player {

	public static void main(String[] args) {
		new GameServer("127.0.0.1", 8080, new IARenaud()).start();
	}

}
