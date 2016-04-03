package pastafari;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import ia.IAInterface;
import pastafari.structures.Building;
import pastafari.structures.BuildingType;
import pastafari.units.Unit;
import pastafari.units.UnitType;

public class GameServer extends Thread {
	boolean verbose = true;
	public Scanner scanIn;
	Socket clientSocket;
	PrintWriter outClient;
	BufferedReader inClient;
	IAInterface ia;
	GameState state;
	int myId;
	int currentPlayer;
	int playersCount = 2;
	boolean iaRunning = false;
	
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
			System.exit(1);
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
			System.exit(1);
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
		if(currentPlayer != myId)
		{
			throw new RuntimeException("send: " + command + "; Ce n'est plus le tour de l'IA " + myId + " mais de " + currentPlayer + " !");
		}
		
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
	
	public boolean sendAttack(int id, int x, int y){
		return this.sendCommand("A," + Integer.toString(id) + "," + Integer.toString(x) + "," + Integer.toString(y));
	}
	
	public boolean sendCreate(UnitType type){
		if (type == UnitType.VOID)
			return false;
		
		return this.sendCommand("C," + Unit.getCharCode(type));
	}

	public boolean sendBuild(int engineerId, BuildingType type){
		return this.sendCommand("B," + Integer.toString(engineerId) + "," + Building.getBuildingCode(type));
	}

	public boolean sendDestroy(int engineerId){
		return this.sendCommand("D," + Integer.toString(engineerId));
	}
	
	public boolean sendMove(int id, int x, int y){
		return this.sendCommand("M," + Integer.toString(id) + "," + Integer.toString(x) + "," + Integer.toString(y));
	}
	
	private boolean processResponse(String srvResponse)
	{
		updateState(srvResponse);
		if(srvResponse.contains("ko")) System.exit(0);
		return srvResponse.contains("ok");
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
		
		// Premier tour : la map
		this.updateState(this.receive());
		
		// Le joueur 0 commence.
		if(myId == 0)
		{
			makeTurn();
		}
		
		while(true)
		{
			String input = this.receive();
			this.updateState(input);
		}
	}
	
	/** Lance le tour de l'IA */
	private void makeTurn()
	{
		if(this.iaRunning)
		{
			log("IA already running...");
			return;
		}
		this.iaRunning = true;
		
		try {
			System.out.println("MAKE TURN " + this.myId);
			this.ia.makeTurn(this);
		}
		catch(RuntimeException e)
		{
			e.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		this.iaRunning = false;
	}
	
	private void updateState(String gridStr)
	{
		int mapX = 0;
		int mapY = 0;
		
		// Taille de la map
		int len = gridStr.split("u")[0].replaceAll("[^\\[]", "").length();
		int size = (int)Math.sqrt(len);
		int players = 2;
		
		// Preprocess
		gridStr = gridStr.replace("];];]:u", "];];]@u");
		gridStr = gridStr.replace("];];", "];]$");
		// System.out.println(gridStr);
		String map = gridStr.split("@")[0].split("m:\\[")[1];
		
		// Cr�ation du nouveau state
		GameState state = new GameState(size, myId);
		Grid grid = new Grid(size);
		state.setGrid(grid);
		
		// Cr�ation des joueurs
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
			// System.out.println("line: " + line);
			for(int j = 0; j < cases.length - 1; j++)
			{
				String tile = cases[j];
				tile = tile.replace("[", "");
				tile = tile.replace("]", "");
				String[] values = tile.split(",");
				
				TileType type;
				if(values[0].equals("f"))
					type = TileType.FOREST;
				else if(values[0].equals("m"))
					type = TileType.MOUNTAIN;
				else if(values[0].equals("r"))
					type = TileType.RIVER;
				else if(values[0].equals("p"))
					type = TileType.LOWLAND;
				else
					type = TileType.LOWLAND;
				
				Tile newTile = new Tile(mapX, mapY, type);
				// Setup du tile
				Player owner = state.getPlayer(Integer.parseInt(values[3]));
				newTile.setOwner(owner);
				newTile.setUnit(Unit.unitFrom(values[1], -1, newTile, owner));
				newTile.setBuilding(Building.buildingFrom(values[2], newTile));
				grid.setTile(mapX, mapY, newTile);
		
				mapY += 1;
			}
			mapX += 1;
			mapY = 0;
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
				state.getPlayer(playerId).addUnit(grid.getTile(x, y).getUnit());
			}
			
			String goldStr = units[units.length-1].replace(";", "").replace(":", "").replace("$", "");
			
			state.getPlayer(playerId).setGold(Integer.parseInt(goldStr));
			state.getPlayer(playerId).setCity(state.getGrid().getCity(playerId));
		}
		
		this.state = state;
		
		// Next player
		if(!gridStr.startsWith("m"))
		{
			int nextPlayer = Integer.parseInt(""+gridStr.charAt(0));
			currentPlayer = nextPlayer;
			if(!iaRunning && nextPlayer == myId)
			{
				makeTurn();
			}
			else
			{

			}
		}
	}
	
	
	/**
	 * Obtient le game state actuel.
	 * @return
	 */
	public GameState getGameState()
	{
		return this.state;
	}

}
