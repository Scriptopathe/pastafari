package pastafari;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import ia.IAInterface;

public class GameServer extends Thread {
	boolean verbose = true;
	Socket clientSocket;
	PrintWriter outClient;
	BufferedReader inClient;
	Scanner scanIn;
	IAInterface ia;
	int myId;
	int currentPlayer;
	
	public GameServer(String ip, int port, IAInterface ia)	{

		try {
			this.scanIn = new Scanner(System.in);
			this.clientSocket = new Socket(ip, port);
			this.inClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			this.outClient = new PrintWriter(clientSocket.getOutputStream(), true);
			this.ia = ia;
			System.out.println("Start OK");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private String receive()
	{
		String in;
		try {
			in = inClient.readLine();
			System.out.println("input: " + in);
			return in;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	private void send(String command)
	{
		System.out.println("Client " + myId + " sends: " + command);
		outClient.println(command);
	}
	
	public void run()
	{
		// Démarrage
		System.out.println("Client starting");
		this.myId = Integer.parseInt(this.receive().replace("player", ""));
		this.send("OK");
		System.out.println("Client started");
		currentPlayer = 0;
		myId = 0;
		while(true)
		{
			String input = this.receive();
			// Changement de tour
			if(input.contains("player") && input.contains("turn"))
			{
				currentPlayer = Integer.parseInt(input.replace("player", "").replace("turn", ""));
				if(currentPlayer == myId)
				{
					this.ia.makeTurn(this);
				}
			}
			else
			{
				// update du game state
			}
		}
	}
}
