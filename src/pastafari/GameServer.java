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
			this.myId = -1;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String receive()
	{
		String in;
		try {
			this.log("receiving...");
			in = inClient.readLine().toLowerCase();
			this.log("input: " + in);
			return in;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * Envoie une commande au serveur.
	 * Retourne le résulat de la commande, et met à jour le GameState.
	 * @param command
	 * @return
	 */
	public String sendCommand(String command)
	{
		// Attrape bug !
		if(command.equals("E"))
		{
			this.endTurn();
			return "";
		}
		
		this.send(command);
		return this.receive();
	}
	
	/** 
	 * Marque la fin du tour du serveur.
	 */
	public void endTurn()
	{
		this.send("E");
	}
	
	/**
	 * Envoie un message au serveur.
	 * @param command
	 */
	private void send(String command)
	{
		this.log("send: " + command);
		outClient.println(command);
	}
	
	public void log(String str)
	{
		System.out.println("[Client " + myId + "] " + str);
	}
	
	public void run()
	{
		// Démarrage
		this.myId = Integer.parseInt(this.receive().replace("player", ""));
		this.send("OK");
		
		// Le joueur 0 commence.
		if(myId == 0)
		{
			ia.makeTurn(this);
		}
		
		while(true)
		{
			String input = this.receive();
			// Changement de tour
			if(input.contains("player") && input.contains("turn"))
			{
				int finished = Integer.parseInt(input.replace("player", "").replace("turn", "").trim());
				currentPlayer = (finished + 1) % 2;
				// Si c'est à notre tour, on lance l'IA.
				if(currentPlayer == myId)
				{
					ia.makeTurn(this);
				}
			}
		}
	}
}
