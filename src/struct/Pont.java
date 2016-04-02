/*    */ package struct;
import game.Coordonnee;
/*    */ 
/*    */ public class Pont extends Batiment
/*    */ {
/*    */   public Pont(Coordonnee pos)
/*    */   {
/*  9 */     this.PVMax = 2;
/* 10 */     this.PV = 2;
/* 11 */     this.place = pos;
/* 12 */     this.type = Structure.PONT;
/*    */   }
/*    */   
/*    */   public Structure getType()
/*    */   {
/* 17 */     return Structure.PONT;
/*    */   }
/*    */   
/*    */   public static final int getCoast()
/*    */   {
/* 22 */     return 50;
/*    */   }
/*    */ }


/* Location:              C:\Users\Proïd\git\DRIVE FINALISTES\Server.jar!\Struct\Pont.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */