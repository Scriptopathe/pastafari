package pastafari;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import ia.IAInterface;
import pastafari.structures.Building;
import pastafari.units.Unit;

public class GameServer extends Thread {
	boolean verbose = true;
	Socket clientSocket;
	PrintWriter outClient;
	BufferedReader inClient;
	Scanner scanIn;
	IAInterface ia;
	GameState state;
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
	 * Retourne le rï¿½sulat de la commande, et met ï¿½ jour le GameState.
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
	 * Envoie un message de log ï¿½ la console.
	 * @param str
	 */
	public void log(String str)
	{
		System.out.println("[Client " + myId + "] " + str);
	}
	
	public void run()
	{
		// Dï¿½marrage
		this.myId = Integer.parseInt(this.receive().replace("player", ""));
		this.send("OK");
		
		// Le joueur 0 commence.
		if(myId == 0)
		{
			ia.makeTurn(this);
		}
		
		// Premier tour : la map
		this.parseGrid(this.receive());
		
		while(true)
		{
			String input = this.receive();
			// Changement de tour
			if(input.contains("player") && input.contains("turn"))
			{
				int finished = Integer.parseInt(input.replace("player", "").replace("turn", "").trim());
				currentPlayer = (finished + 1) % playersCount;
				// Si c'est ï¿½ notre tour, on lance l'IA.
				if(currentPlayer == myId)
				{
					ia.makeTurn(this);
				}
			}
		}
	}
	
	private void updateState(String gridStr)
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
		int players = 2;
		System.out.println("Size = " + size);
		
		// Preprocess
		gridStr = gridStr.replace("];];]:u", "];];]@u");
		gridStr = gridStr.replace("];];", "];]$");
		System.out.println(gridStr);
		String map = gridStr.split("@")[0].split("m:\\[")[1];
		
		// Création du nouveau state
		GameState state = new GameState(size, myId);
		Grid grid = new Grid(size);
		state.setGrid(grid);
		
		// Création des joueurs
		for(int player = 0; player < players; player++)
		{
			state.addPlayer(new Player(player, player == myId));
		}
		
		// ---- DEBUT PARSE
		String[] lines = map.split("\\$");
		for(int i = 0; i < lines.length - 1; i++)
		{
			String line = lines[i];
			String[] cases = line.split(";");
			System.out.println("line: " + line);
			for(int j = 0; j < cases.length - 1; j++)
			{
				String tile = cases[j];
				tile = tile.replace("[", "");
				tile = tile.replace("]", "");
				String[] values = tile.split(",");
				
				TileType type;
				if(values[0].equals("F"))
					type = TileType.FOREST;
				else if(values[0].equals("M"))
					type = TileType.MOUNTAIN;
				else if(values[0].equals("R"))
					type = TileType.RIVER;
				else if(values[0].equals("P"))
					type = TileType.LOWLAND;
				else
					type = TileType.LOWLAND;
				
				Tile newTile = new Tile(mapX, mapY, type);
				// Setup du tile
				Player owner = state.getPlayer(Integer.parseInt(values[3]));
				newTile.setOwner(owner);
				newTile.setUnit(Unit.unitFrom(values[1], newTile, owner));
				newTile.setBuilding(Building.buildingFrom(values[2], mapX, mapY));
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
		


		// Parsing de la 2e partie du fichier.
		String[] playerUnits = gridStr.split("@u")[1].split(":p[0-9]:");
		for(int player = 1; player < playerUnits.length; player++)
		{
			int playerId = player - 1;
			playerUnits[player] = playerUnits[player].replace("[", "").replace("]", "");
			String[] units = playerUnits[player].split(";");
			for(int unitId = 0; unitId < units.length - 1; unitId++)
			{
				String unit = units[unitId];
				String[] values = unit.split(",");
				if(values.length < 5)
					continue;

				int id = Integer.parseInt(values[0]);
				int actions = Integer.parseInt(values[1]);
				int life = Integer.parseInt(values[2]);
				int x = Integer.parseInt(values[3]);
				int y = Integer.parseInt(values[4]);
				
				grid.getTile(x, y).getUnit().setID(id);
				grid.getTile(x, y).getUnit().setCurrentHP(life);
				grid.getTile(x, y).getUnit().setCurrentAction(actions);
			}
			
			String goldStr = units[units.length-1].replace(";", "").replace(":", "").replace("$", "");
			
			state.getPlayer(playerId).setGold(Integer.parseInt(goldStr));
			state.getPlayer(playerId).setCity(state.getGrid().getCity());
		}
		
		this.state = state;
	}
	
	

}
