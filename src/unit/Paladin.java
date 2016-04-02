/*    */ package unit;
import game.Land;
/*    */ 
/*    */ public class Paladin extends Unit
/*    */ {
/*    */   public Paladin(Land pos, int p) {
/*  8 */     this.strengh = 4;
/*  9 */     this.defense = 4;
/* 10 */     this.action = 2;
/* 11 */     this.actionMax = 6;
/* 12 */     this.life = 3;
/* 13 */     this.lifeMax = 3;
/* 14 */     this.position = pos;
/* 15 */     this.player = p;
/* 16 */     this.range = 1;
/* 17 */     this.type = unitType.PALA;
/*    */   }
/*    */   
/*    */   public static final int getCost()
/*    */   {
/* 22 */     return 100;
/*    */   }
/*    */ }


/* Location:              C:\Users\Proïd\git\DRIVE FINALISTES\Server.jar!\Unit\Paladin.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */