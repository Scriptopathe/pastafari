/*    */ package game;
/*    */ import unit.Unit;
import struct.Batiment;
/*    */ 
/*    */ 
/*    */ public class Land
/*    */ {
/*    */   public Coordonnee place;
/*    */   public LandType type;
/*    */   public Batiment struct;
/*    */   public int player;
/*    */   public Unit unit;
/*    */   
/*    */   public Land(Coordonnee lock, LandType land, Batiment struct)
/*    */   {
/* 17 */     this.place = lock;
/* 18 */     this.type = land;
/* 19 */     this.struct = struct;
/* 20 */     this.player = -1;
/* 21 */     this.unit = null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Proïd\git\DRIVE FINALISTES\Server.jar!\Game\Land.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */