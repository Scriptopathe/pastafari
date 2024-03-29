package ia;

import java.util.HashSet;
import java.util.PriorityQueue;

import pastafari.GameServer;
import pastafari.GameState;
import pastafari.Grid;
import pastafari.Pathfinding;
import pastafari.Player;
import pastafari.units.Peasant;
import pastafari.units.Unit;
import pastafari.units.UnitType;

public class IAtest implements IAInterface {
	GameState game;
	GameServer srv;
	IACity city = new IACity(false);
	
	@Override
	public boolean makeTurn(GameServer srv) {
		this.srv = srv;
		game = srv.getGameState();
//		game.getGrid().display();
		//srv.sendCommand(""); 
		// move to attack
		srv.log("-- Explore ----------------");
		moveExplore(game);
		srv.log("-- Army -------------------");
		IAArmy army = new IAArmy();
		army.makeTurn(srv);
		
		// buy&move units
		srv.log("-- City -------------------");
		city.makeTurn(srv);
		
		srv.endTurn();
		return false;
	}
	
	public void moveExplore(GameState game) {
		double mat[][] = game.getGrid().getMatrice(2);

		PriorityQueue<SortedPeasantMove> sorted = new PriorityQueue<>();
		Player me = game.getMyPlayer();
		for(Unit u : me.getUnits()) {
			// System.err.println("id is " + me.getId());
			if (me.getId() == 1){
				// System.err.println(me.getUnits());
			}
			if(u.getType() == UnitType.PEASANT) {
				int x = u.getTile().getX();
				int y = u.getTile().getY();
				for(int i=-1;i<=1;i++)
				for(int j=-1;j<=1;j++)
				if(0<=i+x && i+x<game.getGrid().getSize() && 0<=y+j && y+j<game.getGrid().getSize()) {
					if(mat[x][y] < mat[i+x][j+y]) {
						sorted.add(new SortedPeasantMove((Peasant) u, mat[i+x][j+y], i+x, j+y));
					}
				}
			}
		}
		
		HashSet<Integer> done = new HashSet<>();
		while(!sorted.isEmpty()) {
			SortedPeasantMove spm = sorted.poll();
			if(done.add(spm.p.getId()) && Grid.canMove(false, spm.p.getCurrentAction(), game.getGrid().getTile(spm.dx, spm.dy)) && game.getGrid().getTile(spm.dx, spm.dy).getBuilding() != me.getCity() && mat[spm.dx][spm.dy] > 0) {
				if (srv.sendMove(spm.p.getId(), spm.dx, spm.dy))
					mat[spm.dx][spm.dy] = 0;
			}
		}
	}
	class SortedPeasantMove implements Comparable<SortedPeasantMove> {
		Peasant p;
		double v;
		int dx,dy;
		
		public SortedPeasantMove(Peasant p, double v, int dx, int dy) {
			this.p = p;
			this.v = v;
			this.dx = dx;
			this.dy = dy;
		}
		
		@Override
		public int compareTo(SortedPeasantMove o) {
			if(this.v > o.v)
				return -1;
			else if(this.v == o.v)
				return Grid.getDistance(p.getTile(), game.getMyPlayer().getCity().getTile())
						> Grid.getDistance(o.p.getTile(), game.getMyPlayer().getCity().getTile()) ?
								-1:+1;
			else 
				return +1;
		}
	}
}
