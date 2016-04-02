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
	int playersCount = 2;
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
	 * Retourne le r�sulat de la commande, et met � jour le GameState.
	 * @param command
	 * @return
	 */
	public boolean sendCommand(String command)
	{
		// Attrape bug !
		if(command.equals("E"))
		{
			this.endTurn();
			return true;
		}
		
		this.send(command);
		String response = this.receive();
		return processResponse(response);
	}
	
	private boolean processResponse(String srvResponse)
	{
		parseGrid(srvResponse);
		return srvResponse.contains("OK");
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
	
	/**
	 * Envoie un message de log � la console.
	 * @param str
	 */
	public void log(String str)
	{
		System.out.println("[Client " + myId + "] " + str);
	}
	
	public void run()
	{
		// D�marrage
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
				currentPlayer = (finished + 1) % playersCount;
				// Si c'est � notre tour, on lance l'IA.
				if(currentPlayer == myId)
				{
					ia.makeTurn(this);
				}
			}
		}
	}
	
	private void parseGrid(String gridStr)
	{
		
		boolean parsingMap = false;
		boolean parsingUnits = false;
		int mapX = 0;
		int mapY = 0;
		int dataId = 0;
		int depth = -1;
		
		// Taille de la map
		int len = gridStr.split("U")[0].replaceAll("[^\\[]", "").length();
		int size = (int)Math.sqrt(len);
		System.out.println("Size = " + size);
		
		// Preprocess
		gridStr = gridStr.replace("];];];u", "];];]@u");
		gridStr = gridStr.replace("];];", "];]$");
		gridStr = gridStr.replace("];", "],");
		String map = gridStr.split("@")[0].split("m\\[")[1];
		
		// Grille
		Grid grid = new Grid(size, size);
		
		System.out.println(map);
		String[] lines = map.split("\\$");
		for(int i = 0; i < lines.length - 1; i++)
		{
			String line = lines[i];
			String[] cases = line.split(",");
			System.out.println("line: " + line);
			for(int j = 0; j < cases.length - 1; j++)
			{
				String tile = cases[j];
				tile = tile.replace("[", "");
				tile = tile.replace("]", "");
				String[] values = tile.split(";");
				
				Tile.Type type;
				if(values[0].equals("F"))
					type = Tile.Type.FOREST;
				else if(values[0].equals("M"))
					type = Tile.Type.MOUNTAIN;
				else if(values[0].equals("R"))
					type = Tile.Type.RIVER;
				else if(values[0].equals("P"))
					type = Tile.Type.LOWLAND;
				else
					type = Tile.Type.LOWLAND;
				
				Tile newTile = new Tile(mapX, mapY, type);
				grid.setTile(mapX, mapY, newTile);
				for(String value : values)
				{
					System.out.println("value: (" + mapX + ", " + mapY + ")" + value);
				}
				mapX += 1;
			}
			mapY += 1;
			mapX = 0;
		}
		System.out.println(map);
		

	}
}
