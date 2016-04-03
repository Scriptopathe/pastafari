package pastafari.units;

import pastafari.Player;
import pastafari.Tile;
import pastafari.structures.Bridge;
import pastafari.structures.Building;
import pastafari.structures.BuildingType;
import pastafari.structures.Castle;
import pastafari.structures.Hospital;
import pastafari.structures.Road;

public class Engineer extends Unit {
	public static final int STRENGTH = 0;
	public static final int DEFENSE = 2;
	public static final int MAX_ACTION = 4;
	public static final int MAX_HP = 4;
	public static final int RANGE = 0;
	public static final int COST = 50;
	
	private boolean different;
	
	public Engineer(int id, Tile tile, Player player) {
		super(id, player, tile, STRENGTH, DEFENSE, MAX_ACTION, MAX_HP, RANGE, COST, UnitType.ENGINEER);
		
		this.different = false;
	}
	
	public boolean canBuild() {
		return this.getCurrentAction() >= 2 && this.getTile().getBuildingType() == BuildingType.VOID;
	}
	
	public void build(BuildingType type) {
		if(this.canBuild()) {
			Building built = null;
			if(type == BuildingType.BRIDGE) built = new Bridge(this.getTile());
			else if(type == BuildingType.CASTLE) built = new Castle(this.getTile());
			else if(type == BuildingType.HOSPITAL) built = new Hospital(this.getTile());
			else if(type == BuildingType.ROAD) built = new Road(this.getTile());
			this.getTile().setBuilding(built);
			this.setCurrentAction(this.getCurrentAction() - 2);
		}
	}
	
	public boolean canDestroy() {
		return this.getCurrentAction() >= 2 && this.getTile().getBuildingType() != BuildingType.VOID;
	}
	
	public void destroy() {
		if(this.canDestroy()) {
			this.getTile().setBuilding(null);
			this.setCurrentAction(this.getCurrentAction() - 2);
		}
	}
	
	public boolean isDifferent(){
		return this.different;
	}
	
	public void setDifferent(){
		this.different = true;
	}
}

//import game.Coordonnee;
//import game.Land;
//import pastafari.structures.City;
//import pastafari.structures.Castle;
//import pastafari.structures.Hopital;
//import pastafari.structures.Bridge;
//import pastafari.structures.Road;
//import pastafari.structures.Structure;
///*    */ 
///*    */ public class Engineer extends Unit
///*    */ {
///*    */   public Engineer(Land pos, int p)
///*    */   {
///* 16 */     this.strengh = 0;
///* 17 */     this.defense = 2;
///* 18 */     this.action = 2;
///* 19 */     this.actionMax = 4;
///* 20 */     this.life = 4;
///* 21 */     this.lifeMax = 4;
///* 22 */     this.position = pos;
///* 23 */     this.player = p;
///* 24 */     this.range = 0;
///* 25 */     this.type = unitType.INGE;
///*    */   }
///*    */   
///*    */   public int move(Coordonnee cible) {
///* 29 */     int distance = this.position.place.distance(cible);
///* 30 */     int status = 0;
///* 31 */     if (distance < this.action)
///*    */     {
///* 33 */       this.action -= distance;
///*    */     }
///*    */     else
///*    */     {
///* 37 */       status = -1;
///*    */     }
///* 39 */     return status;
///*    */   }
///*    */   
///*    */   public int build(Structure struct) {
///* 43 */     int res = 0;
///* 44 */     if (this.position.struct == null)
///*    */     {
///* 46 */       if (this.action >= 2)
///*    */       {
///* 48 */         switch (struct)
///*    */         {
///*    */         case ROUTE: 
///* 51 */           this.position.struct = new Castle(this.position.place);
///* 52 */           break;
///*    */         case FORT: 
///* 54 */           this.position.struct = new Hopital(this.position.place);
///* 55 */           break;
///*    */         case HOPITAL: 
///* 57 */           this.position.struct = new Bridge(this.position.place);
///* 58 */           break;
///*    */         case PONT: 
///* 60 */           this.position.struct = new Road(this.position.place);
///* 61 */           break;
///*    */         case VILLE: 
///* 63 */           this.position.struct = new City(this.position.place);
///* 64 */           break;
///*    */         }
///*    */         
///*    */         
///*    */ 
///*    */ 
///* 70 */         this.action -= 2;
///*    */       }
///*    */       else
///*    */       {
///* 74 */         res = -1;
///*    */       }
///*    */       
///*    */     }
///*    */     else {
///* 79 */       res = -1;
///*    */     }
///* 81 */     return res;
///*    */   }
///*    */   
///*    */   public int remove()
///*    */   {
///* 86 */     if (this.action >= 2)
///*    */     {
///* 88 */       this.position.struct = null;
///* 89 */       this.action -= 2;
///* 90 */       return 0;
///*    */     }
///*    */     
///*    */ 
///* 94 */     return -1;
///*    */   }
///*    */   
///*    */   public static final int getCost()
///*    */   {
///* 99 */     return 50;
///*    */   }
///*    */ }
//
//
///* Location:              C:\Users\Proïd\git\DRIVE FINALISTES\Server.jar!\Unit\Ingenieur.class
// * Java compiler version: 7 (51.0)
// * JD-Core Version:       0.7.1
// */