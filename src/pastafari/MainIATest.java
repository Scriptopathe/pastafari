package pastafari;

import ia.IARenaud;
import ia.IAtest;

public class MainIATest {

	public static void main(String[] args) {
		new GameServer("127.0.0.1", 8080, new IAtest()).start();
	}

}
